;module.exports = (function initPlayerDBMongo() {
	'use strict';

	var mongoose  = require('mongoose');
	var db = mongoose.createConnection(global.config.db.game.mongo.url, global.config.db.game.mongo.options);

	// Structure for database
	var GameSchema = {
		blackPlayer: String,
		whitePlayer: String,
		moves: [{
			fromMove: String,
			toMove: String
		}],
		blackPlayerUpdates: [Object],
		whitePlayerUpdates: [Object],
		lastUpdate: Date
	};

	var Game = db.model(global.config.db.game.mongo.url, GameSchema);

	// file name for debugging
	var fileName = global.utility.path.basename(__filename) + ' ->';

	/**
	 * Create a query with the user name
	 * @param {String} id - games assigned mongo id
	 * @param {Object}    - return query
	 */
	function createIDQuery(id) {
		return {
			_id: id
		};
	};

	/**
	 * reset the updates for the correct type of player
	 * @param {String} id   - id of game
	 * @param {Stirng} type - type of user
	 */
	function resetUpdatesForPlayer(id, type) {
		console.log(fileName, 'resetUpdatesForPlayer: entered function');

		// Create obj that will reset the database
		var resetObj = {};
		resetObj[type] = [];

		// reset the array with the object created
		Game.update(createIDQuery(id), {
			$set: resetObj
		}, function resetPlayerUpdates(err, result) {
			if(!err) {
				console.log(fileName, 'resetUpdatesForPlayer: success updating player info');
			} else {
				// TODO: should we do somethinga bout this?
				console.log(fileName, 'resetUpdatesForPlayer: error updating player info');
			}
		});
	};

		
	return {
		/**
		 * Call createGame(...) on correct db
		 * @param {String} userOne     - id of first user
		 * @param {String} userTwo     - id of second user
		 * @param {function} callback  - Response object to send back to request
		 */
		createGame: function(userOne, userTwo, callback) {
			console.log(fileName, 'createGame: entered function');

			// Initialize new game
			var newGame = new Game({
				blackPlayer: userOne,
				whitePlayer: userTwo,
				moves: [],
				blackPlayerUpdates: [],
				whitePlayerUpdates: [],
				lastUpdate: new Date()
			});

			// Save new account to database
			newGame.save(function createNewGameCallback(err, response) {
				// TODO: return _id was I know how to get it
				console.log(fileName, 'createGame: Resposne -> ' + response.toString());

				if(!err) {
					console.log(fileName, 'createGame: Created game in database');
					callback(false, response._id);
				} else {
					console.error(fileName, 'createGame: creating new createGame error on save: ', err);
					callback(true);
				}
			})
		},

		/**
		 * Call getUpdate(...) on correct db
		 * @param {string} id          - id of game
		 * @param {string} user        - user requesting update
		 * @param {function} callback  - Response object to send back to request
		 */
		getUpdate: function(id, user, callback) {
			console.log(fileName, 'getUpdate: entered function with id: ' + id);

			Game.find(createIDQuery(id), function getGameUpdate(err, results) {
				// Check if game was found
				if(results && results.length > 0) {
					console.log(fileName, 'getUpdate: found game');

					// Used to reset db updates after being initiailized
					var playerType;

					// Return updates for proper player
					if(results[0].blackPlayer === user) {
						// Return white player errors
						callback(false, results[0].blackPlayerUpdates);
						playerType = "blackPlayerUpdates";
					} else {
						// Return black player moves
						callback(false, results[0].whitePlayerUpdates);
						playerType = "whitePlayerUpdates";
					}

					// Empty array of objects for player, for next call
					resetUpdatesForPlayer(id, playerType);
				} else {
					console.log(fileName, 'getUpdate: no game found');

					// No game found, return null for updates with error
					callback(true, null);
				}
			});
		},

		/**
		 * Call addMove(...) on correct db
		 * @param {string} id          - id of game
		 * @param {string} fromMove    - original place of move
		 * @param {string} toMove      - place that piece moved to
		 * @param {function} callback  - Response object to send back to request
		 */
		addMove: function(id, fromMove, toMove, callback) {
			// TODO: Add move and update time since last update
		},

		/**
		 * Discconect mongo database
		 * @param {function} callback - Response object to send back to request
		 */
		disconnect: function(callback) {
			console.log(fileName, 'disconnect: discconnecting form database');

			// Disconnect from database
			mongoose.disconnect();
			callback(true);
		}
	};
}());