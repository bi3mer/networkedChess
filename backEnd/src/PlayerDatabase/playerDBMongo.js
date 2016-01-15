;module.exports = (function initPlayerDBMongo() {
	'use strict';

	var mongoose  = require('mongoose');
	mongoose.connect(global.config.player.mongo.url, global.config.player.mongo.options);

	var PlayerSchema = {
		userName: String,
		password: String,
		gameIDs: [String],
		wins: Number,
		losses: Number,
		draws: Number
	};

	var Player = mongoose.model(global.config.player.mongo.url, PlayerSchema);


	/**
	 * Create a query with the user name
	 */
	function createUserNameQuery(user) {
		return {
			userName: user
		};
	};

	/**
	 * Create an account in the database
	 * @param {string} user        - user name
	 * @param {string} pass        - password
	 * @param {function} callback  - Response object to send back to request
	 * @return {bool}              - Return in callback success or not
	 */
	function createNewAccount(user, pass, callback) {
		var newPlayer = new Player({
			userName: user,
			password: pass,
			gameIDs: [],
			wins: 0,
			losses: 0,
			draws: 0
		});

		newPlayer.save(function createNewAccountCallback(err, response) {
			if(!err) {
				callback(true);
			} else {
				console.error('playerDbMongo.js, creating new player error on save: ', err);
				callback(false);
			}
		})
	};
	
	return {
		/**
		 * Create an account in the database if valid user name not found in db
		 * @param {string} user        - user name
		 * @param {string} pass        - password
		 * @param {function} callback  - Response object to send back to request
		 * @return {bool}              - Return in callback success or not
		 */
		createAccount: function(user, pass, callback) {
			// TODO: Check Username
			Player.find(createUserNameQuery(user), function createAccountCheckUser(err, results) {
				if(results && results.length > 0) {
					// User Name was found
					callback(false);
				} else {
					// User Name not found
					createNewAccount(user, pass, callback);
				}
			});
		},

		/**
		 * Log player into server
		 * @param {string} user        - user name
		 * @param {string} pass        - password
		 * @param {function} callback  - Response object to send back to request
		 * @return {bool}              - Return in callback success or not
		 */
		login: function(user, pass, callback) {
			Player.find(createUserNameQuery(user), function createAccountCheckUser(err, results) {
				if(results && results.length > 0) {
					// User Name was found
					console.error('TODO: add password checking');
					console.log('Results: ', results);

					callback(false);
				} else {
					// User Name not found
					callback(false);
				}
			});
		},

		/**
		 * Grab wins, losses and draws from the database
		 * @param {string} user                   - user name
		 * @param {function} callback             - Response object to send back to request
		 * @return {bool, Number, Number, Number} - Return in callback success or not, win, loss, and draw counts
		 */
		rating: function(user, callback) {
			Player.find(createUserNameQuery(user), function createAccountCheckUser(err, results) {
				if(results && results.length > 0) {
					// User Name was found
					console.error('TODO: add rating to callback');
					console.log('Results: ', results);
					
					// TODO: place found values in here
					callback(false);
				} else {
					// User Name not found
					callback(true, -1, -1, -1);
				}
			});
		},

		/**
		 * Discconect mongo database
		 * @param {function} callback - Response object to send back to request
		 */
		disconnect: function(callback) {
			mongoose.disconnect();
			callback(true);
		}
	};
}());