;module.exports = (function initMatchMakingDispatcherDispatcher() {
	'use strict';

	// file name for debugging
	var fileName = global.utility.path.basename(__filename) + ' ->';

	// Get correct queue type	
	var MatchMaking = require(global.config.queue.queuePath);
	
	return {
		/**
		 * @param {String} user       - user id
		 * @param {Function} callback 
		 * @Return {String}           - Return id of player or null
		 */
		goIntoMatchMaking: function(user, callback) {
			console.log(fileName, 'goIntoMatchMaking: dispatching to correct queue');
			MatchMaking.goIntoMatchMaking(user, callback);
		},

		/**
		 * @param {Function} callback 
		 * @Return {Array | Object} - Return datastructure that represents queue for debugging
		 */
		getDataStructure: function(callback) {
			console.log(fileName, 'getDataStructure: dispatching to correct queue ');
			MatchMaking.getDataStructure(callback);
		},

		/**
		 * @param {String} user
		 * @param {function} callback
		 * @Retrun {Boolean | Boolean} - error and if in queue or not
		 */
		isInMatchMaking: function(user, callback) {
			console.log(fileName, 'isInMatchMaking: dispatching to correct queue ');
			MatchMaking.isInMatchMaking(user, callback);
		},

		/**
		 * @param {String} user       - user id
		 * @param {Function} callback 
		 * @Return {String}           - Return id of player or null
		 */
		leaveQueue: function(user, callback) {
			console.log(fileName, 'getDataStructure: leaving queue');
			MatchMaking.leaveQueue(user, callback);
		}
	};
}());