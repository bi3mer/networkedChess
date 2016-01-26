;module.exports = (function initPlayerDBMongo() {
	'use strict';

	/*






	call callback in utility?








	*/
	var mongoose  = require('mongoose');
	var db = mongoose.createConnection(global.config.db.player.mongo.url, global.config.db.player.mongo.options);

	var PlayerSchema = {
		userName: String,
		passWord: String,
		gameIDs: [String],
		isPlaying: Boolean,
		wins: Number,
		losses: Number,
		draws: Number
	};

	var Player = db.model(global.config.db.player.mongo.url, PlayerSchema);

	// file name for debugging
	var fileName = global.utility.path.basename(__filename) + ' ->';

	/**
	 * Create a query with the user name
	 * @param {String} user - users name
	 * @param {Object}      - return query
	 */
	function createUserNameQuery(user) {
		return {
			userName: user
		};
	};

	/**
	 * Create an account in the database
	 * @param {string} user        - user name
	 * @param {string} pass        - password
	 * @param {function} callback  - Response object to send back to request
	 * @return {bool}              - Return in callback success or not
	 */
	function createNewAccount(user, pass, callback) {
		console.log(fileName, 'createNewAccount: entered function');

		var newAccount = new Player({
			userName: user,
			passWord: pass,
			gameIDs: [],
			isPlaying: false,
			wins: 0,
			losses: 0,
			draws: 0
		});

		// Save new account to database
		newAccount.save(function createNewAccountCallback(err, response) {
			if(!err) {
				console.log(fileName, 'CreateNewAccount: Created account in database');
				callback(false);
			} else {
				console.error(fileName, 'CreateNewAccount: playerDbMongo.js, creating new player error on save: ', err);
				callback(true);
			}
		})
	};
	
	return {
		/**
		 * Create an account in the database if valid user name not found in db
		 * @param {string} user        - user name
		 * @param {string} pass        - password
		 * @param {function} callback  - Response object to send back to request
		 * @return {bool}              - Return in callback success or not
		 */
		createAccount: function(user, pass, callback) {
			console.log(fileName, 'createAccount: entered function');

			// Get user from database
			Player.find(createUserNameQuery(user), function createAccountCheckUser(err, results) {
				// Check if valid results found
				if(results && results.length > 0) {
					// User Name was found
					console.log(fileName, 'createAccount: User with this name found');
					callback(true);
				} else {
					// User Name not found
					console.log(fileName, 'createAccount Creating account in db');
					createNewAccount(user, pass, callback);
				}
			});
		},

		/**
		 * Log player into server
		 * @param {string} user        - user name
		 * @param {function} callback  - Response object to send back to request
		 * @return {bool}              - Return in callback success or not
		 */
		login: function(user, callback) {
			console.log(fileName, 'login: entered function');

			// Get user from database
			Player.find(createUserNameQuery(user), function createAccountCheckUser(err, results) {
				if(results && results.length > 0) {
					// User Name was found
					console.log(fileName, 'login: user with this name found');
					callback(false, results[0].passWord);
				} else {
					// User Name not found
					console.log(fileName, 'login, user with this name not found');
					callback(true);
				}
			});
		},

		/**
		 * Grab wins, losses and draws from the database
		 * @param {string} user                   - user name
		 * @param {function} callback             - Response object to send back to request
		 * @return {bool, Number, Number, Number} - Return in callback success or not, win, loss, and draw counts
		 */
		rating: function(user, callback) {
			console.log(fileName, 'rating: entered function');

			// Get user from database
			Player.find(createUserNameQuery(user), function getUserRating(err, results) {
				if(results && results.length > 0) {
					// User Name was found
					console.log(fileName, 'rating: rating found');

					callback(false, results[0].wins, results[0].losses, results[0].draws);
				} else {
					console.log(fileName, 'ratings: no user found');

					// User Name not found
					callback(true, -1, -1, -1);
				}
			});
		},

		/**
		 * call getGameID(...) on correct db
		 * @param {string} user            - user name
		 * @param {function} callback      - Response object to send back to request
		 * @return {bool, errStr | Number} - Errstr is error string and number on no error is the game id
		 */
		getGameID: function(user, callback) {
			console.log(fileName, 'getGameID()');

			// Get user from database
			Player.find(createUserNameQuery(user), function getUserGameID(err, results) {
				// Make sure we have valid result
				if(global.utility.checking.isFilledArray(results)) {
					console.log(fileName, 'getGameID: valid user found');

					// Check if player is playing and has a game id
					if(results[0].isPlaying && global.utility.checking.isFilledArray(results[0].gameIDs)) {
						console.log(fileName, 'getGameID: sending valid game id');

						// Send game id
						callback(false, results[0].gameIDs[results[0].gameIDs.length - 1]);
					} else {
						console.log(fileName, 'getGameID: user is not currently playing a game');

						// Send bad game id, the player is not playing a game
						callback(false, undefined)
					}
				} else {
					console.log(fileName, 'getGameID: no user found');

					// Send error, the username wasn't found
					callback(true, 'Username not found');
				}
			});
		},

		/**
		 * Add game ID to te player and update inGame to true
		 * @param {string} gameID      - game id
		 * @param {function} callback  - Response object to send back to request
		 */
		addGameID: function(id, callback) {
			console.log(fileName, 'addGameID()');

			// push id to the gameId's field and set isPlaying to true
			Player.update(createUserNameQuery(user), {
				$set: {
					isPlaying: true
				},
				$push: {
					gameIDs: id
				}
			}, function updatedPlayerInfo(err, result){
				if(!err) {
					console.log(fileName, 'getGameID: success updating player info');
					callback(false);
				} else {
					console.log(fileName, 'getGameID: error updating player info');
					callback(true, 'Error adding game id to player')
				}
			});
		},

		/**
		 * call isPlaying(...) on correct db
		 * @param {string} user        - user name
		 * @param {function} callback  - Response object to send back to request
		 */
		isPlaying: function(user, callback) {
			// Find user to test if is playing or not
			Player.find(createUserNameQuery(user), function checkIfPlayerIsPlaying(err, results) {
				// Check if array is empty
				if(global.utility.checking.isFilledArray(results)) {
					// return if user is playing
					callback(results[0].isPlaying);
				} else {
					// Return false because user isn't valid
					callback(false);
				}
			});
		},

		/**
		 * Discconect mongo database
		 * @param {function} callback - Response object to send back to request
		 */
		disconnect: function(callback) {
			console.log(fileName, 'disconnect: discconnecting form database');

			mongoose.disconnect();
			callback(true);
		}
	};
}());