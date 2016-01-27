;module.exports = (function initStandardDispatcher() {
	'use strict';

	// file name for debugging
	var fileName = global.utility.path.basename(__filename) + ' ->';

	// Array structure to represent the queue
	var queue = [];
	
	return {
		/**
		 * @param user      - user id
		 * @param {Function} callback 
		 * @Return {String} - Return id of player or null
		 */
		goIntoMatchMaking: function(user, callback) {
			console.log(fileName, 'goIntoMatchMaking: checking for user');

			var otherPlayer = null;

			// Check if queue has a member
			if(global.utility.checking.isFilledArray(queue)) {
				console.log(fileName, 'goIntoMatchMaking: found other player');

				// Get player id from queue
				otherPlayer = queue.shift();
			} else {
				console.log(fileName, 'goIntoMatchMaking: adding player to queue');
				queue.push(user);
			}

			callback(otherPlayer);
		},

		/**
		 * @param {Function} callback 
		 * @Return {Array | Object} - Return datastructure that represents queue for debugging
		 */
		getDataStructure: function(callback) {
			console.log(fileName, 'getDataStructure: returning queue: ' + queue);
			callback(queue);
		}
	};
}());