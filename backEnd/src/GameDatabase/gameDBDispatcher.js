;module.exports = (function initGameDBDispatcher() {
	'use strict';

	// Grab correct db from config
	// var db = require(global.config.db.player.path);
	
	return {
		/**
		 * Call createAccount(...) on correct db
		 * @param {string} user        - user name
		 * @param {string} pass        - password
		 * @param {function} callback  - Response object to send back to request
		 */
		createAccount: function(user, pass, callback) {
			db.createAccount(user, pass, callback);
		},

		/**
		 * Call login(...) on correct db
		 * @param {string} user        - user name
		 * @param {string} pass        - password
		 * @param {function} callback  - Response object to send back to request
		 */
		login: function(user, pass, callback) {
			db.login(user, pass, callback);
		},

		/**
		 * call rating(...) on correct db
		 * @param {string} user        - user name
		 * @param {function} callback  - Response object to send back to request
		 */
		rating: function(user, callback) {
			db.login(user, callback);
		},

		/**
		 * disconnect correct db
		 * @param {function} callback - Response object to send back to request
		 */
		disconnect: function(callback) {
			db.disconnect(call);
		}
	};
}());