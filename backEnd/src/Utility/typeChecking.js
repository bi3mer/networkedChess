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
			console.log(fileName, 'isValidCallback: checking if valid or not. TODO: implement this');
			return true;
		},

		/**
		 * Check if the callback is a number
		 * @param {function} callback
		 * @return {bool} - whether the callback is valid or not
		 */
		isValidNumber: function(number) {
			console.log(fileName, 'isValidNumber: checking if valid or not. TODO: implement this');
			// TODO: is this needed?
			return true;
		},

		/**
		 * Check if the bool is a boolean
		 * @param {function} callback
		 * @return {bool} - whether the callback is valid or not
		 */
		isValidBool: function(bool) {
			console.log(fileName, 'isValidBool: checking if valid or not. TODO: implement this');
			// TODO: is this needed?
			return true;
		},

		/**
		 * Check if the callback is a funciton and valid
		 * @param {function} callback
		 * @return {bool} - whether the callback is valid or not
		 */
		isValidObj: function(obj) {
			console.log(fileName, 'isValidObj: checking if valid or not. TODO: implement this');
			return true;
		},

		/**
		 * Check if the callback is a funciton and valid
		 * @param {Array} arr
		 * @return {bool} - whether the array is valid or not
		 */
		isArray: function(arr) {
			console.log(fileName, 'isArray: checking if array is an array');
			if(arr && Array.isArray(arr)) {
				return true;
			}

			return false;
		},

		/**
		 * Check if str is valid string
		 * @param {String} str - a string to be tested
		 *
		 */
		isString: function(str) {
			console.log(fileName, 'isString: checking if string is a string');

			// Check if is valid or not
			if(str && str !== '') {
				return true;
			}

			return false;
		},

		/**
		 * Check if the callback is a funciton and valid
		 * @param {Array} arr
		 * @return {bool} - whether the callback is valid or not
		 */
		isFilledArray: function(arr) {
			console.log(fileName, 'isFilledArray: checking if array is filled and valid or not.');
			if(this.isArray(arr) && arr.length > 0) {
				return true;
			}

			return false;
		}
	}
}());