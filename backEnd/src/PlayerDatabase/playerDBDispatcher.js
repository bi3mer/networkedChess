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
		 * call isPlaying(...) on correct db
		 * @param {string} user        - user name
		 * @param {function} callback  - Response object to send back to request
		 */
		isPlaying: function(user, callback) {
			console.log(fileName, 'isPlaying: dispatching to db');
			db.getGameID(user, callback);
		},

		/**
		 * call updateIsPlaying(...) on correct db
		 * @param {string} user        - user name
		 * @param {bool} isPlaying     - update val
		 * @param {function} callback  - Response object to send back to request
		 */
		updateIsPlaying: function(user, isPlaying, callback) {
			console.log(fileName, 'updateIsPlaying: dispatching to db');
			db.updateIsPlaying(user, isPlaying, callback);
		},

		/**
		 * call isPlaying(...) on correct db
		 * @param {string} user        - user name
		 * @param {function} callback  - Response object to send back to request with is user and is playing
		 */
		isUser: function(user, callback) {
			console.log(fileName, 'isPlaying: dispatching to db');
			db.isUser(user, callback);
		},

		/**
		 * Update ratings for the player in the db using the result object
		 * @param {string} user        - user name of player
		 * @param {Object} result      - results of game
		 * @param {Function } callback - return results in callback
		 */
		updateRatings: function(user, result, callback) {
			console.log(fileName, 'updateRatings: dispatching to db');
			db.updateRatings(user, result, callback);

		},
		
		/**
		 * call getGameID(...) on correct db
		 * @param {string} user        - user name
		 * @param {function} callback  - Response object to send back to request
		 */
		addGameID: function(user, id, callback) {
			console.log(fileName, 'getGameID: dispatching to db');
			db.addGameID(user, id, callback);
		}
	};
}());