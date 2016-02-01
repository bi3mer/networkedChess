;module.exports = (function initPlayerDBMongo() {
	'use strict';

	// Create connection to database
	var mongoose  = require('mongoose');
	var db = mongoose.createConnection(global.config.db.player.mongo.url, global.config.db.player.mongo.options);

	// Databse structure for Player
	var PlayerSchema = {
		userName: String,
		passWord: String,
		gameIDs: [String],
		isPlaying: Boolean,
		wins: Number,
		losses: Number,
		draws: Number
	};

	// Add model to player databsae
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

		// Create the new account
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
			Player.findOne(createUserNameQuery(user), function getUserGameID(err, user) {
				// Make sure we have valid result
				if(!err && user) {
					console.log(fileName, 'getGameID: valid user found');

					console.log('result (1): ' + JSON.stringify(user));
					console.log('result (2): ' + user.gameIDs[user.gameIDs.length - 1])

					// Check if player is playing and has a game id
					if(user.isPlaying && user.gameIDs) {
						console.log(fileName, 'getGameID: sending valid game id');

						// Send game id
						callback(false, user.gameIDs[user.gameIDs.length - 1]);
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
		 * @param {string} user        - user name
		 * @param {string} gameID      - game id
		 * @param {function} callback  - Response object to send back to request
		 */
		addGameID: function(user, id, callback) {
			console.log(fileName, 'addGameID() push ' + id + ' to user ' + user);

			Player.findOne(createUserNameQuery(user), function addingGameID(err, doc) { 	
				if(!err && doc && doc !== null) {
					console.log(fileName, 'addGameID: success getting player info');

					// Update data
					doc.isPlaying = true;
					doc.gameIDs.push(id);

					// Save data
					doc.save(function savingUpdatedDoc(errSave) {
						if(!errSave) {
							console.log(fileName, 'addGameID: success updating player info');

							if(callback) {
								callback(false);
							}
						} else {
							console.log(fileName, 'addGameID: Error saving ' + errSave);

							if(callback) {
								callback(true, 'Error saving game id to plyaer');
							}
						}
					});
				} else {
					console.log(fileName, 'addGameID: error getting and updating player info');
					if(callback) {
						callback(true, 'Error adding game id to player');
					}
				}
			});

		},

		/**
		 * Get whether or not the player is playing or not
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
		 * Update the player to playing or not
		 * @param {string} user        - user name
		 * @param {bool} isPlaying     - update val
		 * @param {function} callback  - Response object to send back to request
		 */
		updateIsPlaying: function(user, isPlaying, callback) {
			console.log(fileName, 'updateIsPlaying: dispatching to db');
			
			Player.findOne(createUserNameQuery(user), function updatingIsPlaying(err, player) {
				if(!err) {
					console.log(fileName, 'updateIsPlaying: found player');

					// Update is playing
					player.isPlaying = isPlaying;

					player.save(function createNewAccountCallback(err, response) {
						if(!err) {
							console.log(fileName, 'updateIsPlaying: saved isPlaying');

							// Check callback
							if(callback) {
								callback(false);
							}
						} else {
							console.error(fileName, 'updateIsPlaying: Error saving isPlaying: ', err);

							// Check callback
							if(callback) {
								callback(true);
							}
						}
					});
				} else {
					console.error(fileName, 'updateIsPlaying: Error finding user ' + user + ': ', err);

					// Check callback
					if(callback) {
						callback(true);
					}
				}
			});
		},

		/**
		 * Check if is a valid  user
		 * @param {string} user        - user name
		 * @param {function} callback  - Response object to send back to request with found or not
		 */
		isUser: function(user, callback) {
			console.log(fileName, 'isUser: entered function');

			Player.findOne(createUserNameQuery(user), function addingGameID(err, doc) { 
				if(!err && doc && doc !== null) {
					console.log(fileName, 'isUser: Valid user found');
					callback(false);
				} else {
					console.log(fileName, 'isUser: Invlaid user');
					callback(true);
				}
			});
		},

		/**
		 * Update ratings for the player in the db using the result object
		 * @param {string} user             - user name of player
		 * @param {Object} result           - results of game
		 * @param {Boolean} updateIsPlaying - flag on whether or not to also change isPlaying
		 * @param {Function } callback      - return results in callback
		 */
		updateRatings: function(user, result, callback, updateIsPlaying) {
			console.log(fileName, 'updateRatings: entered function');

			Player.findOne(createUserNameQuery(user), function updateRankingsInDB(err, user) {
				if(!err && user) {
					// Update ratings
					if(result && result.win) {
						console.log(fileName, 'updateRatings: updated wins');
						++ user.wins;
					} else if (result && result.loss) {
						console.log(fileName, 'updateRatings: updated losses');
						++ user.losses;
					} else {
						console.log(fileName, 'updateRatings: updated draws');
						++ user.draws;
					}

					if(updateIsPlaying) {
						console.log(fileName, 'updateRatings: updating is playing flag set off');
						user.isPlaying = false;
					}

					// save to db
					user.save(function updateWithNewRatings(err) {
						if(!err) {
							console.log(fileName, 'updateRatings: saved new ratings');

							// Check callback
							if(callback) {
								callback(false);
							}
						} else {
							console.log(fileName, 'updateRatings: err saving new ratings: ' + err);

							// Check callback
							if(callback) {
								callback(false);
							}
						}
					});
				} else {
					console.log(fileName, 'updateRatings: Invlaid user');

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

			mongoose.disconnect();
			callback(true);
		}
	};
}());