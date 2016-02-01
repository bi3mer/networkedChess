;module.exports = (function initTypeChecking() {
	'use strict';

	var fileName = global.utility.path.basename(__filename) + ' ->';

	return {
		/**
		 * Check if the callback is a funciton and valid
		 * @param {function} callback
		 * @return {bool} - whether the callback is valid or not
		 */
		isValidCallback: function(callback) {
			console.log(fileName, 'isValidCallback: checking if valid or not.');

			// CHeck if is valid or not
			if(callback && typeof callback === 'function') {
				console.log(fileName, 'isValidCallback: valid calback');
				return true;
			}
			console.log(fileName, 'isValidCallback: inValid calback');
			return false;
		},

		/**
		 * Check if the callback is a funciton and valid
		 * @param {function} callback
		 * @return {bool} - whether the callback is valid or not
		 */
		isValidObj: function(obj) {
			console.log(fileName, 'isValidObj: checking if valid or not.');

			// CHeck if valid or not
			if(obj && typeof obj !== undefined) {
				console.log(fileName, 'isValidObj: is valid obj');
				return true;
			}

			console.log(fileName, 'isValidObj: is inValid obj');
			return false;
		},

		/**
		 * Check if the callback is a funciton and valid
		 * @param {Array} arr
		 * @return {bool} - whether the array is valid or not
		 */
		isArray: function(arr) {
			console.log(fileName, 'isArray: checking if array is an array');

			// CHeck if is array or not
			if(arr && Array.isArray(arr)) {
				console.log(fileName, 'isArray: is valid array');
				return true;
			}

			console.log(fileName, 'isArray: is inValid array');
			return false;
		},

		/**
		 * Check if str is valid string
		 * @param {String} str - a string to be tested
		 */
		isString: function(str) {
			console.log(fileName, 'isString: checking if string is a string');

			// Check if is valid or not
			if(str && str !== '') {
				console.log(fileName, 'isString: is valid string');
				return true;
			}

			console.log(fileName, 'isString: inValid string');
			return false;
		},

		/**
		 * Check if the callback is a funciton and valid
		 * @param {Array} arr
		 * @return {bool} - whether the callback is valid or not
		 */
		isFilledArray: function(arr) {
			console.log(fileName, 'isFilledArray: checking if array is filled and valid or not.');

			// CHeck if is array and filled
			if(this.isArray(arr) && arr.length > 0) {
				console.log(fileName, 'isArray: is valid filled array');
				return true;
			}

			console.log(fileName, 'isArray: is inValid filled array');
			return false;
		},

		/**
		 * Check is if is an an object and has correct fields
		 * @param {Object} move - object given when a move is made on client side
		 * @return {Boolean}    - whether the move is valid or not
		 */
		isValidMove: function(move) {
			console.log(fileName, 'isValidMove: Checking if move is valid or not');

			// Check if is an obj and has two corrrect field formats
			if(this.isValidObj(move) && this.isString(move.from) && this.isString(move.to)) {
				console.log(fileName, 'isValidMove: valid move');
				return true;
			}

			console.log(fileName, 'isValidMove: invalid move');
			return false;
		}
	};
}());