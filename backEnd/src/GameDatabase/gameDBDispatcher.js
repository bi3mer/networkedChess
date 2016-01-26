;module.exports = (function initGameDBDispatcher() {
	'use strict';

	// Grab correct db from config
	var db = require(global.config.db.game.path);
	
	return {
		/**
		 * Call createGame(...) on correct db
		 * @param {String} userOne     - id of first user
		 * @param {String} userTwo     - id of second user
		 * @param {function} callback  - Response object to send back to request
		 */
		createGame: function(id, userOne, userTwo, callback) {
			db.createGame(userOne, userTwo, callback);
		},

		/**
		 * Call getUpdate(...) on correct db
		 * @param {string} id          - id of game
		 * @param {string} user        - user requesting update
		 * @param {function} callback  - Response object to send back to request
		 */
		getUpdate: function(id, user, callback) {
			db.getUpdate(id, callback);
		},

		/**
		 * Call addMove(...) on correct db
		 * @param {string} id          - id of game
		 * @param {string} fromMove    - original place of move
		 * @param {string} toMove      - place that piece moved to
		 * @param {function} callback  - Response object to send back to request
		 */
		addMove: function(id, fromMove, toMove, callback) {
			db.addMove(id, fromMove, toMove, callback);
		},

		/**
		 * disconnect correct db
		 * @param {function} callback - Response object to send back to request
		 */
		disconnect: function(callback) {
			db.disconnect(callback);
		}
	};
}());