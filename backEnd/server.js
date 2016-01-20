
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

	// Listen to path to create account
	app.post(global.config.server.paths.createAccount, function serverCreateAccount(req, res) {
		console.log(fileName, 'Creating account');
		serverApp.createAccount(req.body.user, req.body.pass, res);
	});

	// Listen to path to login users in
	app.post(global.config.server.paths.login, function serverLogin(req, res) {
		console.log(fileName, 'Logging in');
		serverApp.login(req.body.user, req.body.pass, res);
	});

	// Listen to path to add a move to a users game
	app.post(global.config.server.paths.addMove, function serverAddMove(req, res) {
		console.log(fileName, 'adding move');
		serverApp.addMove(req.body.user, req.body.move, res);
	});

	// Listen to path to undo a move in a users game
	app.post(global.config.server.paths.undoMove, function serverUndoMove(req, res) {
		console.log(fileName, 'undoing move');
		serverApp.undoMove(req.body.user, res);
	});

	// Listen to path to forfeit a users game
	app.post(global.config.server.paths.forfeit, function serverForfeit(req, res) {
		console.log(fileName, 'forfeiting game');
		serverApp.forfeit(req.body.user, res);
	});

	// Listen to path to get the users ratings
	app.post(global.config.server.paths.ratings, function serverRatings(req, res) {
		console.log(fileName, 'getting server ratings');
		serverApp.ratings(req.body.user, res);
	});

	// Listen to path to get users updates for the game
	app.post(global.config.server.paths.update, function getUpdate(req, res) {
		console.log(fileName, 'getting updates for user');
		serverApp.getUpdate(req.body.user, res);
	});

	// Open server up to calls on the configurations port
	app.listen(global.config.server.port, function serverListen() {
		console.log(fileName, 'Listening on port: ', global.config.server.port);
	});
}());