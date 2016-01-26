;module.exports = (function initServerApp() {
	'use strict';

	// Custom Modules
	var PlayersDB   = require('./PlayerDatabase/playerDBDispatcher');
	var GamesDB     = require('./GameDatabase/gameDBDispatcher');

	var MatchMaking = require('./MatchMaking/matchMakingDispatcher');

	// file name for debugging
	var fileName = global.utility.path.basename(__filename) + ' ->';

	return {
		/**
		 * Log player into server
		 * @param {string} user       - user name
		 * @param {string} pass       - password
		 * @param {Function} callback - send info back to the server with
		 * @return {Object}           - Return status defining whether account was made or not
		 */
		createAccount: function(user, pass, callback) {
			console.log(fileName, 'createAccount: entered function');

			// Call Player Db to test credentials
			PlayersDB.createAccount(user, pass, function loginToServer(err) {
				// Check valid credentials	
				if(!err) {
					console.log(fileName, 'Create Account: Valid username, sent success');
					callback(global.config.server.httpStatusCodes.success);
				} else {
					console.log(fileName, 'Create Account: Invalid username');
					callback(global.config.server.httpStatusCodes.validationError, 'User name already exists');
				}
			});
		},

		/**
		 * Log player into server
		 * @param {string} user       - user name
		 * @param {string} pass       - password
		 * @param {Function} callback - send info back to the server with
		 * @return {Object}           - Return status defining whether credentials are correct or not
		 */
		login: function(user, pass, callback) {
			console.log(fileName, 'login: entered function');

			// Call Player Db to test credentials
			PlayersDB.login(user, function loginToServer(err, validPass) {
				// Check valid credentials	
				if(!err) {
					// Check password
					if(pass === validPass) {
						console.log(fileName, 'login: valid password');
						callback(global.config.server.httpStatusCodes.success);
					} else {
						console.log(fileName, 'login: invalid password');
						callback(global.config.server.httpStatusCodes.validationError, 'Invalid user name or password');
					}
				} else {
					console.log(fileName, 'login: multiple or 0 accounts found');
					callback(global.config.server.httpStatusCodes.validationError, 'Invalid user name or password');
				}
			});
		},

		/**
		 * Add move to database
		 * @param {string} gameID     - id for game
		 * @param {string} move       - users move
		 * @param {Function} callback - send info back to the server with
		 * @return {Object}           - Return status defining whether adding move was succesful or not
		 */
		addMove: function(user, move, callback) {
			console.log(fileName, 'addMove: entered function');

			GamesDB.addMove(user, move, function addMoveSuccesful(err) {
				// Check if adding move to database was succesful
				if(!err) {
					console.log(fileName, 'addMove: sending back succes');
					callback(global.config.server.httpStatusCodes.success);
				} else {
					console.log(fileName, 'addMove: sending back error, no game found');
					callback(global.config.server.httpStatusCodes.error, 'Error adding move, no valid game found');
				}
			});
		},

		// TODO: askUndoMove server path
		/**
		 * Remove move from database
		 * @param {string} user       - users name
		 * @param {string} move       - users move
		 * @param {Function} callback - send info back to the server with
		 * @return {Object}           - Return status defining whether adding move was succesful or not
		 */
		undoMove: function(user, callback) {
			console.log(fileName, 'undoMove: entered function');

			// Todo: update to get users current game id
			GamesDB.removeMove(gameID, function removeMoveAndUpdateDB(err) {
				if(!err) {
					console.log(fileName, 'undoMove: succesfull undid move');
					callback(global.config.server.httpStatusCodes.succesful);
				} else {
					console.log(fileName, 'undoMove: unable to undomove');
					callback(global.config.server.httpStatusCodes.error, 'Error: coudln\t undo move');
				}			
			});
		},

		/**
		 * End game and declare winner
		 * @param {string} user       - id for game
		 * @param {Function} callback - send info back to the server with
		 * @return {Object}           - Return status defining whether adding move was succesful or not
		 */
		forfeit: function(user, callback) {
			console.log(fileName, 'forfeit: entered function');

			GamesDB.forfeit(gameID, function forfeitGame(err) {
				if(!err) {
					console.log(fileName, 'forfeit: entered function');
					callback(global.config.httpStatusCodes.success);
				} else {
					console.log(fileName, 'forfeit: entered function');
					callback(global.config.httpStatusCodes.error, 'Error forfeiting game');
				}
			});
		},

		/**
		 * End game and declare winner
		 * @param {string} user       - user name
		 * @param {Function} callback - send info back to the server with
		 * @return {Object}           - Return status defining whether adding move was succesful or not
		 */
		ratings: function(user, callback) {
			console.log(fileName, 'ratings: entered function');

			PlayersDB.rating(user, function ratings(err, win, loss, draw) {
				if(!err) {
					console.log(fileName, 'ratings: success, sending ratings')
					callback(global.config.server.httpStatusCodes.success, {
						'win': win,
						'loss': loss,
						'draw': draw
					});
				} else {
					console.log(fileName, 'ratings: error from database');

					// Set status to error
					callback(global.config.server.httpStatusCodes.validationError, 'Error getting results for user: ' + user);
				}
			});
		},

		/**
		 * End game and declare winner
		 * @param {string} user       - user name
		 * @param {Function} callback - send info back to the server with
		 * @return {Object}           - Return {object} with updates to the game
		 */
		getUpdate: function(user, isPlaying, callback) {
			console.log(fileName, 'getUpdate: entered function');

			// Get current game id for user
			PlayersDB.getGameID(user, function gameID(err, id) {
				if(!err) {
					// If the user is playing
					if(isPlaying) {
						console.log(fileName, 'getUpdate: valid game id');

						// TODO: update this to go into games db
						// Go to games database and check for updates
						callback(global.config.server.httpStatusCodes.success, 'getUpdate: ID, ' + id + ' is in a game');
					} else if(id) {
						console.log(fileName, 'getUpdate: found game id for player');

						// Player has found a match and will be notified
						callback(global.config.server.httpStatusCodes.success, 'getUpdate: ID, ' + id + ' has found a match');
					} else {
						console.log(fileName, 'getUpdate: no match has been found');

						// Player hasn't found a match yet
						callback(global.config.server.httpStatusCodes.notModified, 'getUpdate: ID, ' + id + ' no match found yet');
					}
				} else {
					console.log(fileName, 'getUpdate: error getting id');
					callback(global.config.server.httpStatusCodes.validationError, 'getUpdate: Error, ' + id);
				}
			});
		},

		/**
		 * End game and declare winner
		 * @param {string} user       - user name
		 * @param {Function} callback - send info back to the server with
		 * @return {Object}           - Return {GameID | String} with game id or a string with queue message
		 */
		getMatch: function(user, callback) {
			// Note, this isn't secure because it doesn't test the username against the player database
			console.log(fileName, 'getMatch: entered function');

			// Add or get player from queue
			var otherPlayer = MatchMaking.goIntoMatchMaking(user);

			// Check if other player found
			if(global.utility.checking.isString(otherPlayer)) {
				console.log(fileName, 'getMatch: found match');

				// Create a game
				GamesDB.createGame(user, otherPlayer, function getGameID(err, id) {
					if(!err) {
						console.log(fileName, 'getMatch: valid game made, sending id to user');

						// TODO: should I put this in after I update the dataabse entries?
						callback(global.config.server.httpStatusCodes.success, id);

						// TODO: update and uncomment
						// PlayersDB.addGameID(user, id);
						// PlayersDB.addGameID(otherPlayer, id);
					}
				});
			} else {
				console.log(fileName, 'getMatch: no opponent found, added to MatchMaking');

				callback(global.config.server.httpStatusCodes.success, "Placed in MatchMaking");
			}
		},

		/**
		 * End game and declare winner
		 * @param {Function} callback - send info back to the server with
		 * @return {Object}           - Return {Array} with available opponenets looking for a game
		 */
		getMatchMaking: function(callback) {
			console.log(fileName, 'getMatchMaking: entered function');
			callback(global.config.server.httpStatusCodes.success, MatchMaking.getDataStructure());
		},
	};
}());