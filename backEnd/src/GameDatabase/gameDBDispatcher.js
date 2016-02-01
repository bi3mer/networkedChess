;module.exports = (function initGameDBDispatcher() {
	'use strict';

	// Grab correct db from config
	var db = require(global.config.db.game.path);

	// file name for debugging
	var fileName = global.utility.path.basename(__filename) + ' ->';
	
	return {
		/**
		 * Call createGame(...) on correct db
		 * @param {String} userOne     - id of first user
		 * @param {String} userTwo     - id of second user
		 * @param {function} callback  - Response object to send back to request
		 */
		createGame: function(userOne, userTwo, callback) {
			console.log(fileName, 'createGame: dispatching to db');
			db.createGame(userOne, userTwo, callback);
		},

		/**
		 * Call getUpdate(...) on correct db
		 * @param {string} id          - id of game
		 * @param {string} user        - user requesting update
		 * @param {function} callback  - Response object to send back to request
		 */
		getUpdate: function(id, user, callback) {
			console.log(fileName, 'getUpdate: dispatching to db');
			db.getUpdate(id, user, callback);
		},

		/**
		 * Call addMove(...) on correct db
		 * @param {string} id          - id of game
		 * @param {string} fromMove    - original place of move
		 * @param {string} toMove      - place that piece moved to
		 * @param {function} callback  - Response object to send back to request
		 */
		addMove: function(id, fromMove, toMove, callback) {
			console.log(fileName, 'addMove: dispatching to db');
			db.addMove(id, fromMove, toMove, callback);
		},

		/**
		 * Call forfeit(...) on correct db
		 * @param {string} id         - id of game
		 * @param {string} user       - user name
		 * @param {function} callback - callback to return results with
		 */
		forfeit: function(id, user, callback) {
			console.log(fileName, 'forfeit: dispatching to db');
			db.forfeit(id, user, callback);
		},

		/**
		 * disconnect correct db
		 * @param {function} callback - Response object to send back to request
		 */
		disconnect: function(callback) {
			console.log(fileName, 'disconnect: dispatching to db');
			db.disconnect(callback);
		}
	};
}());