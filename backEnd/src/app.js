;module.exports = (function initServerApp() {
	'use strict';

	// Databases
	var PlayersDB = require('./PlayerDatabase/playerDBDispatcher');
	var GamesDB   = require('./GameDatabase/gameDBDispatcher');

	// file name for debugging
	var fileName = global.utility.path.basename(__filename) + ' ->';

	return {
		/**
		 * Log player into server
		 * @param {string} user - user name
		 * @param {string} pass - password
		 * @param {Object} res  - Response object to send back to request
		 * @return {Object}     - Return status defining whether account was made or not
		 */
		createAccount: function(user, pass, res) {
			console.log(fileName, 'createAccount: entered function');

			// Call Player Db to test credentials
			PlayersDB.createAccount(user, pass, function loginToServer(err) {
				// Check valid credentials	
				if(!err) {
					console.log(fileName, 'Create Account: Valid username, sent success');
					res.send('Account created');

					// Set status to succesful
					res.status(global.config.server.httpStatusCodes.success);
				} else {
					
					console.log(fileName, 'Create Account: Invalid username');
					res.status(global.config.server.httpStatusCodes.validationError);

					// Set status to validation error
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
			console.log(fileName, 'login: entered function');

			// Call Player Db to test credentials
			PlayersDB.login(user, pass, function loginToServer(err, validPass) {
				// Check valid credentials	
				if(!err) {
					console.log(fileName, 'login: no error found');

					// Check password
					if(pass === validPass) {
						console.log(fileName, 'login: valid password');

						// Set status to succesful
						res.status(global.config.server.httpStatusCodes.success);
					} else {
						console.log(fileName, 'login: invalid password');

						// Set to failure
						res.status(global.config.server.httpStatusCodes.validationError);
						res.status('Invalid user name or password');
					}
				} else {
					console.log(fileName, 'login: multiple or 0 accounts found');

					// Set to failure
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
		addMove: function(user, move, res) {
			console.log(fileName, 'addMove: entered function');

			GamesDB.addMove(user, move, function addMoveSuccesful(err) {
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
		 * @param {string} user   - users name
		 * @param {string} move   - users move
		 * @param {Object} res    - Response object to send back to request
		 * @return {Object}       - Return status defining whether adding move was succesful or not
		 */
		undoMove: function(user, res) {
			console.log(fileName, 'undoMove: entered function');

			// Todo: update to get users current game id
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
		 * @param {string} user   - id for game
		 * @param {Object} res    - Response object to send back to request
		 * @return {Object}       - Return status defining whether adding move was succesful or not
		 */
		forfeit: function(user, res) {
			console.log(fileName, 'forfeit: entered function');

			GamesDB.forfeit(gameID, function forfeitGame(err) {
				if(!err) {
					res.status(global.config.server.httpStatusCodes.success);
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
			console.log(fileName, 'ratings: entered function');

			PlayersDB.rating(user, function ratings(err, win, loss, draw) {
				if(!err) {
					console.log(fileName, 'ratings: success, sending ratings')

					// Set status to success
					res.status(global.config.server.httpStatusCodes.success);

					// send wins, losses,and draws
					res.send({
						'win': win,
						'loss': loss,
						'draw': draw
					});
				} else {
					console.log(fileName, 'ratings: error from database');

					// Set status to error
					res.status(global.config.server.httpStatusCodes.validationError);

					// Send error message
					res.send('Error getting results for user: ' + user);
				}

				res.end();
			});
		},
	};
}());