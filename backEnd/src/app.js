;module.exports = (function initServerApp() {
	'use strict';

	// Databases
	var PlayersDB = require('./PlayerDatabase/playerDBDispatcher');
	var GamesDB   = require('./GameDatabase/gameDBDispatcher');

	return {
		/**
		 * Log player into server
		 * @param {string} user - user name
		 * @param {string} pass - password
		 * @param {Object} res  - Response object to send back to request
		 * @return {Object}     - Return status defining whether credentials are correct or not
		 */
		login: function(user, pass, res) {
			// Call Player Db to test credentials
			PlayersDB.login(user, pass, function loginToServer(isValidCredentials) {
				// Check valid credentials	
				if(isValidCredentials) {
					// Set status to succesful
					res.status(global.config.server.httpStatusCodes.success);

					// TODO: Check if most recent game is still alive

				} else {
					// Set status to validation error
					res.status(global.config.server.httpStatusCodes.validationError);
				}
				
				// TODO: Check if send and end is necessary, or if we can remove some of them
				res.send();
				res.end();
			});
		},

		/**
		 * Add move to database
		 * @param {string} gameID - id for game
		 * @param {string} move   - users move
		 * @param {Object} res    - Response object to send back to request
		 * @return {Object}       - Return status defining whether adding move was succesful or not
		 */
		addMove: function(gameID, move, res) {
			GamesDB.addMove(gameID, move, function addMoveSuccesful(addMoveSuccesful) {
				// Check if adding move to database was succesful
				if(addMoveSuccesful) {
					// Set status to succesful
					res.status(global.config.server.httpStatusCodes.success);
				} else {
					// Game wasn't found
					res.status(global.config.server.httpStatusCodes.error);
				}

				// TODO: Check if send and end is necessary, or if we can remove some of them
				res.send();
				res.end();
			});
		},

		/**
		 * Remove move from database
		 * @param {string} gameID - id for game
		 * @param {string} move   - users move
		 * @param {Object} res    - Response object to send back to request
		 * @return {Object}       - Return status defining whether adding move was succesful or not
		 */
		undoMove: function(gameID, res) {
			GamesDB.removeMove(gameID, function removeMoveAndUpdateDB(removeMoveSuccesful) {
				if(removeMoveSuccesful) {
					res.status(global.config.server.httpStatusCodes.succesful);
				} else {
					res.status(global.config.server.httpStatusCodes.error);
				}

				// TODO: Check if send and end is necessary, or if we can remove some of them
				res.send();
				res.end();				
			});
		},

		/**
		 * End game and declare winner
		 * @param {string} gameID - id for game
		 * @param {string} user   - users name
		 * @param {Object} res    - Response object to send back to request
		 * @return {Object}       - Return status defining whether adding move was succesful or not
		 */
		forfeit: function(gameID, user, res) {
			GamesDB.forfeit(gameID, function forfeitGame(forfeitSuccesful) {
				if(forfeitSuccesful) {
					res.status(global.config.httpStatusCodes.succesful);
				} else {
					res.status(global.config.httpStatusCodes.error);
				}

				// TODO: Check if send and end is necessary, or if we can remove some of them
				res.send();
				res.end();
			});
		},

		/**
		 * End game and declare winner
		 * @param {string} user   - user name
		 * @param {Object} res    - Response object to send back to request
		 * @return {Object}       - Return status defining whether adding move was succesful or not
		 */
		ratings: function(user, res) {
			PlayersDB.rating(user, function ratings(error, win, loss, draw) {
				if(!error) {
					res.status(global.config.httpStatusCodes.succesful);

					// send wins, losses,and draws
					res.send({
						'win': win,
						'loss': loss,
						'draw': draw
					});
				} else {
					res.status(global.config.httpStatusCodes.error);
					res.send();
				}

				res.end();
			});
		},
	};
}());