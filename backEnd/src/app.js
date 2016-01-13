;module.exports = (function initServerApp() {
	'use strict';

	// Databases
	var PlayersDB = require('./Database/playerDBDispatcher');
	var GamesDB = require('./Database/gameDBDispatcher');

	return {
		/**
		 *
		 */
		login: function(user, pass, res) {

		},

		/**
		 *
		 */
		addMove: function(gameID, move, res) {
			
		}
	}
}());