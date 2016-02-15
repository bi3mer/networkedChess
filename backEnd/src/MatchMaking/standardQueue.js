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
			var err = false;

			if(queue.indexOf(user) === -1) {
				// Check if queue has a member
				if(global.utility.checking.isFilledArray(queue)) {
					console.log(fileName, 'goIntoMatchMaking: found other player');

					// Get player id from queue
					otherPlayer = queue.shift();
				} else {
					console.log(fileName, 'goIntoMatchMaking: adding player to queue');

					// Add user to queue
					queue.push(user);
				}
			} else {
				console.log(fileName, 'goIntoMatchMaking: error, already in queue');

				err = true;
			}

			// Return result in callback
			callback(err, otherPlayer);
		},

		/**
		 * @param {Function} callback 
		 * @Return {Array | Object} - Return datastructure that represents queue for debugging
		 */
		getDataStructure: function(callback) {
			console.log(fileName, 'getDataStructure: returning queue: ' + queue);
			callback(queue);
		},

		/**
		 * @param {String} user       - user id
		 * @param {Function} callback 
		 * @Return {String}           - Return id of player or null
		 */
		leaveQueue: function(user, callback) {
			console.log(fileName, 'leaveQueue: leaving queue');
			var index = queue.indexOf(user);
			if(index > -1) {
				console.log(fileName, 'leaveQueue: left queue');

				// Remove only one element at index found
				queue.splice(index, 1);

				// return no error
				callback(false);
			} else {
				console.log(fileName, 'leaveQueue: not in queue');
				callback(true);
			}
		}
	};
}());