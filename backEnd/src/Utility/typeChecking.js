;module.exports = (function initTypeChecking() {
	'use strict';

	var fileName = global.utility.path.baseName(__filename) + ' ->';

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
		 * Check if the string is a string and not empty
		 * @param {function} callback
		 * @return {bool} - whether the callback is valid or not
		 */
		isValidString: function(string) {
			console.log(fileName, 'isValidString: checking if valid or not. TODO: implement this');
			// TODO: is this needed?
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
		isValidObj: function(callback) {
			console.log(fileName, 'isValidObj: checking if valid or not. TODO: implement this');
			return true;
		},
	}
}());