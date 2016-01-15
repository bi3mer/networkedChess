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
		 * @return {Object}     - Return status defining whether account was made or not
		 */
		createAccount: function(user, pass, res) {
			// Call Player Db to test credentials
			PlayersDB.createAccount(user, pass, function loginToServer(err) {
				// Check valid credentials	
				if(!err) {
					// Set status to succesful
					res.status(global.config.server.httpStatusCodes.success);
				} else {
					// Set status to validation error
					res.status(global.config.server.httpStatusCodes.validationError);
					res.send('User name already exists');
				}
				
				res.end();
			});
		},

		/**
		 * Log player into server
		 * @param {string} user - user name
		 * @param {string} pass - password
		 * @param {Object} res  - Response object to send back to request
		 * @return {Object}     - Return status defining whether credentials are correct or not
		 */
		login: function(user, pass, res) {
			// Call Player Db to test credentials
			PlayersDB.login(user, pass, function loginToServer(err) {
				// Check valid credentials	
				if(!err) {
					// Set status to succesful
					res.status(global.config.server.httpStatusCodes.success);

					// TODO: Check if most recent game is still alive

				} else {
					// Set status to validation error
					res.status(global.config.server.httpStatusCodes.validationError);
					res.status('Invalid user name or password');
				}

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
			GamesDB.addMove(gameID, move, function addMoveSuccesful(err) {
				// Check if adding move to database was succesful
				if(!err) {
					// Set status to succesful
					res.status(global.config.server.httpStatusCodes.success);
				} else {
					// Game wasn't found
					res.status(global.config.server.httpStatusCodes.error);
					res.send('Error adding move');
				}

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
			GamesDB.removeMove(gameID, function removeMoveAndUpdateDB(err) {
				if(!err) {
					res.status(global.config.server.httpStatusCodes.succesful);
				} else {
					res.status(global.config.server.httpStatusCodes.error);
					res.send('Error undoing move');
				}

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
			GamesDB.forfeit(gameID, function forfeitGame(err) {
				if(!err) {
					res.status(global.config.httpStatusCodes.succesful);
				} else {
					res.status(global.config.httpStatusCodes.error);
					res.send('Error forfeiting game')
				}

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
			PlayersDB.rating(user, function ratings(err, win, loss, draw) {
				if(!err) {
					res.status(global.config.httpStatusCodes.succesful);

					// send wins, losses,and draws
					res.send({
						'win': win,
						'loss': loss,
						'draw': draw
					});
				} else {
					res.status(global.config.httpStatusCodes.error);
					res.send('Error getting results for user: ', user);
				}

				res.end();
			});
		},
	};
}());