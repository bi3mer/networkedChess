
;(function initServer() {
	'use strict';

	// Get configuration file for use throughout server
	global.config = require('../config');

	// Get utilities
	global.utility = {};
	global.utility.path = require('path');
	global.utility.checking = require('./src/Utility/typeChecking');

	// File name for debugging
	var fileName = global.utility.path.basename(__filename) + ' ->';
	// App
	var serverApp = require('./src/app.js');

	// NPM requirements
	var bodyParser = require('body-parser');
	var express	= require('express');
	var app	= express();

	// Accept JSON requests
	app.use(bodyParser.json());
	app.use(bodyParser.urlencoded({
		extended: true
	}));

	/**
	 * Use res to send status and message to user
	 * @param {Number} status  - status to send to user
	 * @Param {Object} message - any kind of message to give to the user
	 */
	function sendMessageToUser(res, status, message) {
		console.log(fileName, 'sendMessageToUser: sending to user');
		res.status(status);
		res.send(message);
	};

	// URL to show the server is up
	app.get('/', function serverOnTest(req, res) {
		console.log(fileName, 'Sending back succes to user on browser');
		res.status(global.config.server.httpStatusCodes.success);
		res.send('Server is running.');
	});

	// Listen to path to create account
	app.post(global.config.server.paths.createAccount, function serverCreateAccount(req, res) {
		console.log(fileName, 'Creating account');

		serverApp.createAccount(req.body.user, req.body.pass, function createAccountGetResponse(status, message) {
			console.log(fileName, 'Creating account: sending to user');
			sendMessageToUser(res, status, message);
		});
	});

	// Listen to path to login users in
	app.post(global.config.server.paths.login, function serverLogin(req, res) {
		console.log(fileName, 'Logging in');

		serverApp.login(req.body.user, req.body.pass, function loginGetResponse(status, message) {
			console.log(fileName, 'Logging in: sending to user');
			sendMessageToUser(res, status, message);
		});
	});

	// Listen to path to add a move to a users game
	app.post(global.config.server.paths.addMove, function serverAddMove(req, res) {
		console.log(fileName, 'adding move');

		serverApp.addMove(req.body.user, req.body.move, function addMoveGetResponse(status, message) {
			console.log(fileName, 'adding move: sending to user');
			sendMessageToUser(res, status, message);
		});
	});

	// Listen to path to undo a move in a users game
	app.post(global.config.server.paths.undoMove, function serverUndoMove(req, res) {
		console.log(fileName, 'undoing move');

		serverApp.undoMove(req.body.user, function undoMoveGetResponse(status, message) {
			console.log(fileName, 'undoing move: sending to user');
			sendMessageToUser(res, status, message);
		});
	});

	// Listen to path to accept or decline a forfeit for a users game
	app.post(global.config.server.paths.forfeit, function serverForfeit(req, res) {
		console.log(fileName, 'forfeiting game');

		serverApp.forfeit(req.body.user, function forfeitGetResponse(status, message) {
			console.log(fileName, 'forfeiting game: sending to user');
			sendMessageToUser(res, status, message);
		});
	});

	// TODO: accept or decline port path

	// Listen to path to get the users ratings
	app.post(global.config.server.paths.ratings, function serverRatings(req, res) {
		console.log(fileName, 'getting server ratings');

		serverApp.ratings(req.body.user, function ratingsGetResponse(status, message) {
			console.log(fileName, 'getting server ratings: sending to user');
			sendMessageToUser(res, status, message);
		});
	});

	// Listen to path to get users updates for the game
	app.post(global.config.server.paths.update, function getUpdate(req, res) {
		console.log(fileName, 'getting updates for user');

		serverApp.getUpdate(req.body.user, req.body.isPlaying, function updateGetResponse(status, message) {
			console.log(fileName, 'getting updates for user: sending to user');
			sendMessageToUser(res, status, message);
		});
	});

	// Listen to path to get users updates for the game
	app.post(global.config.server.paths.getMatch, function getUpdate(req, res) {
		console.log(fileName, 'getting match for user or adding to queue');

		serverApp.getMatch(req.body.user, function createAccountGetResponse(status, message) {
			console.log(fileName, 'getting match for user or adding to queue: sending to user');
			sendMessageToUser(res, status, message);
		});
	});

	// Listen to path to see the queue, NOTE: for debugging
	app.post(global.config.server.paths.getMatchMaking, function getUpdate(req, res) {
		console.log(fileName, 'getting queue for user, NOTE: for debugging');

		serverApp.getMatchMaking(function getMatchMakingGetResponse(status, message) {
			console.log(fileName, 'getting Matchmaking datastructure for debugging');
			sendMessageToUser(res, status, message);
		});
	});

	// TODO: Listen to path to cancel queueing

	// Open server up to calls on the configurations port
	app.listen(global.config.server.port, function serverListen() {
		console.log(fileName, 'Listening on port: ', global.config.server.port);
	});
}());