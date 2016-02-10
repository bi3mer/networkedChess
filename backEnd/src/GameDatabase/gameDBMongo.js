;module.exports = (function initPlayerDBMongo() {
	'use strict';

	var mongoose  = require('mongoose');
	var db = mongoose.createConnection(global.config.db.game.mongo.url, global.config.db.game.mongo.options);

	// Structure for database
	var GameSchema = {
		blackPlayer: String,
		whitePlayer: String,
		gameOver: Boolean,
		moves: [{
			from: String,
			to: String
		}],
		blackPlayerUpdates: [mongoose.Schema.Types.Mixed],
		whitePlayerUpdates: [mongoose.Schema.Types.Mixed],
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

	/** 
	 * Save game
	 * @param {Object} game        - mongo object to be saved
	 * @param {Function} callback  - returns success or not and other player
	 * @param {String} otherPlayer - username of other player in game
	 */
	function saveGame(game, callback, otherPlayer) {
		console.log(fileName, 'saveGame()');
		
		game.save(function saveForfeitToDB(saveErr) {
			if(!saveErr) {
				console.log(fileName, 'saveGame: Saved forfeit to updates');

				// Check callback
				if(callback && otherPlayer) {
					callback(false, otherPlayer);
				} else {
					callback(false);
				}
			} else {
				console.log(fileName, 'saveGame: error updating db: ' + saveErr);

				// Check callback
				if(callback) {
					callback(true);
				}
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
				gameOver: false,
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

			Game.findOne(createIDQuery(id), function getGameUpdate(err, game) {
				// Check if game was found
				if(!err && game) {
					console.log(fileName, 'getUpdate: found game');

					// Used to reset db updates after being initiailized
					var playerType;

					// Return updates for proper player
					if(game.blackPlayer === user) {
						console.log(fileName, 'getUpdate: return blackPlayer updates');

						// Return black player errors
						callback(false, game.blackPlayerUpdates, game.gameOver);
						playerType = "blackPlayerUpdates";
					} else {
						console.log(fileName, 'getUpdate: return whitePlayer updates');

						// Return white player moves
						callback(false, game.whitePlayerUpdates, game.gameOver);
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
		 * Call addUpdate(...) on correct db
		 * @param {string} id          - id of game
		 * @param {string} user        - user requesting update
		 * @param {function} callback  - Response object to send back to request
		 */
		addUpdate: function(id, user, update, callback) {
			console.log(fileName, 'getUpdate: entered function');

			Game.findOne(createIDQuery(id), function getGameUpdate(err, game) {
				// Check if game was found
				if(!err && game) {
					// chekc player
					if(game.blackPlayer === user) {
						console.log(fileName, 'addUpdate: add update to whitePlayer');
						game.whitePlayerUpdates.push(update);
					} else {
						console.log(fileName, 'addUpdate: add update to blackPlayer');
						game.blackPlayerUpdates.push(update);
					}

					// Return success
					callback(false);
				} else {
					console.log(fileName, 'addUpdate: no game found');

					// return with error
					callback(true);
				}
			});
		},

		/**
		 * Call addMove(...) on correct db
		 * @param {string} id          - id of game
		 * @param {string} user        - user name
		 * @param {Object} move        - Object that represents a move
		 * @param {function} callback  - Response object to send back to request
		 */
		addMove: function(id, user, move, callback) {
			console.log(fileName, 'addMove: entered function');

			Game.findOne(createIDQuery(id), function getGameUpdate(err, game) {
				// Check if game was found
				if(!err && game) {
					// Add to moves
					game.moves.push(move);

					// Add to updates
					if(game.blackPlayer === user) {
						console.log(fileName, 'addUpdate: add update to whitePlayer');
						game.whitePlayerUpdates.push(move);
					} else {
						console.log(fileName, 'addUpdate: add update to blackPlayer');
						game.blackPlayerUpdates.push(move);
					}

					console.log('Game:', JSON.stringify(game));
					console.log('callback:', JSON.stringify(callback));
					// Save updates to game
					saveGame(game, callback);
				} else {
					console.log(fileName, 'addUpdate: unable to find game');
					callback(true);
				}
			});
		},

		/**
		 * Forfeit game and add to updates
		 * @param {string} id         - id of game
		 * @param {string} user       - user name
		 * @param {function} callback - callback to return results with
		 */
		forfeit: function(id, user, callback) {
			console.log(fileName, 'forfeit: entered function with id: ' + id);
			
			Game.findOne(createIDQuery(id), function updateGameWithForfeit(err, game) {
				if(!err && game) {
					console.log(fileName, 'forfeit: updating game information');

					// Create update object
					var update = {
						forfeit: true
					};

					var otherPlayer = game.whitePlayer;

					// end the game
					game.gameOver = true;

					// Debugging, TODO: Remove when done
					console.log('game: ', JSON.stringify(game));

					// Check which player to add the updates to
					if(user === game.whitePlayer) {
						console.log(fileName, 'forfeit: updating black player');
						game.blackPlayerUpdates.push(update);
						otherPlayer = game.blackPlayer;
					} else {
						console.log(fileName, 'forfeit: updating white player');
						game.whitePlayerUpdates.push(update);
					}

					// Save the update
					saveGame(game, callback, otherPlayer);
				} else {
					console.log(fileName, 'forfeit: error finding game: ' + err);
					
					// Check callback
					if(callback) {
						callback(true);
					}
				}
			});
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