;module.exports = (function initPlayerDBDispatcher() {
	'use strict';

	// Grab correct db from config
	var db = require(global.config.db.player.path);

	// file name for debugging
	var fileName = global.utility.path.basename(__filename) + ' ->';
	
	return {
		/**
		 * Call createAccount(...) on correct db
		 * @param {string} user        - user name
		 * @param {string} pass        - password
		 * @param {function} callback  - Response object to send back to request
		 */
		createAccount: function(user, pass, callback) {
			console.log(fileName, 'createAccount: dispatching to db');
			db.createAccount(user, pass, callback);
		},

		/**
		 * Call login(...) on correct db
		 * @param {string} user        - user name
		 * @param {function} callback  - Response object to send back to request
		 */
		login: function(user, callback) {
			console.log(fileName, 'login: dispatching to db');
			db.login(user, callback);
		},

		/**
		 * call rating(...) on correct db
		 * @param {string} user        - user name
		 * @param {function} callback  - Response object to send back to request
		 */
		rating: function(user, callback) {
			console.log(fileName, 'rating: dispatching to db');
			db.rating(user, callback);
		},

		/**
		 * call getGameID(...) on correct db
		 * @param {string} user        - user name
		 * @param {function} callback  - Response object to send back to request
		 */
		getGameID: function(user, callback) {
			console.log(fileName, 'getGameID: dispatching to db');
			db.getGameID(user, callback);
		},

		/**
		 * disconnect correct db
		 * @param {function} callback - Response object to send back to request
		 */
		disconnect: function(callback) {
			console.log(fileName, 'disconnect: dispatching to db');
			db.disconnect(call);
		}
	};
}());