;module.exports = (function initServerApp() {
	'use strict';

	// Custom Modules
	var PlayersDB   = require('./PlayerDatabase/playerDBDispatcher');
	var GamesDB     = require('./GameDatabase/gameDBDispatcher');

	var MatchMaking = require('./MatchMaking/matchMakingDispatcher');

	// file name for debugging
	var fileName = global.utility.path.basename(__filename) + ' ->';

	/**
	 * Update the player to no longer playing if they are
	 * @param {string} user       - user name
	 */
	function updatePlayerToNotPlaying(user) {
		console.log(fileName, 'updatePlayerToNotPlaying: entered function');

		// Update isPlaying to false
		PlayersDB.updateIsPlaying(user, false, function setPlayerIsPlayingToFalse(err) {
			if(!err) {
				console.log(fileName, 'updatePlayerToNotPlaying: Updated isPlaying to false');
			} else {
				console.log(fileName, 'updatePlayerToNotPlaying: Error updating isPlaying to false: ' + err);
			}
		});
	};

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

			// CHeck if user is valid
			PlayersDB.getGameID(user, function(err, gameID) {
				if(!err && gameID) {
					console.log(fileName, 'addMove: found player game id');
					GamesDB.addMove(gameID, user, move, function addMoveSuccesful(err) {
						if(!err) {
							console.log(fileName, 'addMove: sending  back success');
							callback(global.config.server.httpStatusCodes.success);
						} else {
							console.log(fileName, 'addMove: sending back error, no game found');
							callback(global.config.server.httpStatusCodes.error, 'Error adding move, no valid game found');
						}
					});
				} else {
					callback(global.config.server.httpStatusCodes.error, 'Error finding valid user or gameID: ' + gameID);
				}
			});
		},

		/**
		 * requestUndo
		 * @param {string} user       - users name
		 * @param {string} move       - users move
		 * @param {Function} callback - send info back to the server with
		 * @return {Object}           - Return status defining whether adding move was succesful or not
		 */
		requestUndo: function(user, callback) {
			console.log(fileName, 'requestUndo: entered function');

			// Check if user is valid
			PlayersDB.getGameID(user, function(err, gameID) {
				if(!err && gameID) {
					GamesDB.addUpdate(gameID, user, global.config.templates.undoRequest, function removeMoveAndUpdateDB(errGame) {
						if(!errGame) {
							console.log(fileName, 'requestUndo: succesfull undid move');
							callback(global.config.server.httpStatusCodes.success);
						} else {
							console.log(fileName, 'requestUndo: unable to request undo move');
							callback(global.config.server.httpStatusCodes.error, 'Error: coudln\t undo move');
						}			
					});
				} else {
					console.log(fileName, 'requestUndo: unable to find game');
					callback(global.config.server.httpStatusCodes.error, 'Unable to find game to undo ' + err);
				}
			});
		},

		/**
		 * acceptOrDenyUndo
		 * @param {string} user       - users name
		 * @param {string} move       - users move
		 * @param {Boolean} accepted  - whether the player has accepted or denied the undo
		 * @param {Function} callback - send info back to the server with
		 * @return {Object}           - Return status defining whether adding move was succesful or not
		 */
		acceptOrDenyUndo: function(user, accepted, callback) {
			console.log(fileName, 'acceptOrDenyUndo()');

			// check if user is valid
			PlayersDB.getGameID(user, function aceeptOrDenyGetGameID(err, gameID) {
				if(!err && gameID) {
					// Check if the player has accepted or declined undo request
					if(accepted) {
						console.log(fileName, 'acceptOrDenyUndo: has accepted undo, updating db');

						// Undo the move on the database
						GamesDB.undoMove(gameID, user, function acceptUndoDB(undoErr, undoneMove) {
							// CHeck for err
							if(!undoErr && undoneMove) {
								console.log(fileName, 'acceptOrDenyUndo: updated db, returning move');
								callback(global.config.server.httpStatusCodes.success, undoneMove);
							} else {
								console.log(fileName, 'acceptOrDenyUndo: unable to update db ->', undoErr);
								callback(global.config.server.httpStatusCodes.error, undoErr);
							}
						});
					} else {
						console.log(fileName, 'acceptOrDenyUndo: has not accepted undo, updating db');

						// update the other players database
						GamesDB.addUpdate(gameID, user, global.config.templates.denyUndo, function addUpdateDenyUndo(addUpdateErr) {
							// check for error
							if(!addUpdateErr) {
								console.log(fileName, 'acceptOrDenyUndo: success, updated databse and sending success to player');
								callback(global.config.server.httpStatusCodes.success, 'success, did not accept undo');
							} else {
								console.log(fileName, 'acceptOrDenyUndo: error, could not deny undo ->', addUpdateErr);
								callback(global.config.server.httpStatusCodes.error, 'error, could not deny move');
							}
						});
					}
				} else {
					console.log(fileName, 'acceptUndo: unable to find game or user');
					callback(global.config.server.httpStatusCodes.error, 'unable to find game to accept or decline undo ' + err);
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

			// Check if user is valid
			PlayersDB.getGameID(user, function(err, gameID) {
				if(!err && gameID) {
					GamesDB.forfeit(gameID, user,function forfeitGame(err, otherPlayer) {
						if(!err) {
							console.log(fileName, 'forfeit: forfeited game');

							// Return success for forfeit succesful
							callback(global.config.server.httpStatusCodes.success);

							console.log(fileName, 'forfeit: updating isPlaying to false');
							PlayersDB.updateIsPlaying(user, false);

							// Update ratings for both players
							PlayersDB.updateRatings(user, {loss: true});
							PlayersDB.updateRatings(otherPlayer, {win: true});
						} else {
							console.log(fileName, 'forfeit: error forfeiting game: ' + err);
							callback(global.config.server.httpStatusCodes.error, 'Error forfeiting game');
						}
					});
				} else {
					console.log(fileName, 'forfeit: No user found');
					callback(global.config.server.httpStatusCodes.error, 'Player is not in game');
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
				if(!err && id) {
					// If the user is playing
					if(isPlaying) {
						console.log(fileName, 'getUpdate: valid game id: ' + id);

						// Go to games database and check for updates
						GamesDB.getUpdate(id, user, function(err, updates, shouldUpdateIsPlaying) {
							// Check for error
							if(!err) {
								console.log(fileName, 'getUpdate: valid game id sending updates: ' + JSON.stringify(updates));
								callback(global.config.server.httpStatusCodes.success, updates);
							} else {
								console.log(fileName, 'getUpdate: invalid game id given');
								callback(global.config.server.httpStatusCodes.error, 'No valid game found');
							}

							// Check whether or not the game is over
							if(shouldUpdateIsPlaying) {
								console.log(fileName, 'getUpdate: updating is playing to false');
								PlayersDB.updateIsPlaying(user, false);
							}
						});
					} else if(id) {
						console.log(fileName, 'getUpdate: found game id for player');

						// Player has found a match and will be notified
						callback(global.config.server.httpStatusCodes.success, {id: id});
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
			console.log(fileName, 'getMatch: entered function');

			// Check if user is valid
			PlayersDB.isUser(user, function(err) {
				if(!err) {
					// Add or get player from queue
					MatchMaking.goIntoMatchMaking(user, function getMatchmatchMaking(matchmakingErr, otherPlayer) {
						// Check if other player found
						if(!matchmakingErr && global.utility.checking.isString(otherPlayer)) {
							console.log(fileName, 'getMatch: found match');

							// Create a game
							GamesDB.createGame(user, otherPlayer, function getGameIDInApp(gameDBErr, id) {
								if(!gameDBErr) {
									console.log(fileName, 'getMatch: valid game made, sending id to user');

									// Send info to user
									callback(global.config.server.httpStatusCodes.success, id);

									// Update game ids for each player
									PlayersDB.addGameID(user, id);
									PlayersDB.addGameID(otherPlayer, id);
								}
							});
						} else if (matchmakingErr) {
							console.log(fileName, 'getMatch: error, was in queue already');

							callback(global.config.server.httpStatusCodes.notModified, "Already in queue");
						} else {
							console.log(fileName, 'getMatch: no opponent found, added to MatchMaking');

							callback(global.config.server.httpStatusCodes.success, "Placed in MatchMaking");
						}
					});

					// Update to not playing, since we are now queueing
					updatePlayerToNotPlaying(user);
				} else {
					console.log(fileName, 'getMatch: Invalid user attempted to go into matchmaking');
					callback(global.config.server.httpStatusCodes.error, 'getMatch: Invalid user attempted to go into matchmaking');
				}
			});
		},

		/**
		 * End game and declare winner
		 * @param {Function} callback - send info back to the server with
		 * @return {Object}           - Return {Array} with available opponenets looking for a game
		 */
		getMatchMaking: function(callback) {
			console.log(fileName, 'getMatchMaking: entered function');

			MatchMaking.getDataStructure(function getMatchMakingData(data) {
				callback(global.config.server.httpStatusCodes.success, data);
			});
		},

		/**
		 * User has decided to leave the queue
		 * @param {String} user
		 * @param {Function} callback
		 */
		cancelQueue: function(user, callback) {
			console.log(fileName, 'cancelQueue()');

			MatchMaking.leaveQueue(user, function leaveQueue(err) {
				if(!err) {
					console.log(fileName, 'cancelQueue: left queeu');
					callback(global.config.server.httpStatusCodes.success, 'Left queue');
				} else {
					console.log(fileName, 'cancelQueue: unable to leave queue');
					callback(global.config.server.httpStatusCodes.error, 'Unable to leave queue');
				}
			});
		},
	};
}());