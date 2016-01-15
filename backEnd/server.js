
;(function initServer() {
	'use strict';

	// Get configuration file for use throughout server
	global.config = require('../config');

	// App
	var serverApp = require('./src/app.js');

	// NPM requirements
	var express	= require('express');
	var bodyParser = require('body-parser');
	var ejs	= require('ejs');
	var path = require('path');
	var app	= express();

	app.use(bodyParser.json());
	app.use(bodyParser.urlencoded({
		extended: true
	}));

	// Paths
	app.post(global.config.server.paths.createAccount, function serverCreateAccount(req, res) {
		serverApp.createAccount(req.body.user, req.body.pass, res);
	});

	app.post(global.config.server.paths.login, function serverLogin(req, res) {
		serverApp.login(req.body.user, req.body.pass, res);
	});

	app.post(global.config.server.paths.addMove, function serverAddMove(req, res) {
		serverApp.addMove(req.body.gameID, req.body.move, res);
	})

	app.post(global.config.server.paths.undoMove, function serverUndoMove(req, res) {
		serverApp.undoMove(req.body.gameID, res);
	});

	app.post(global.config.server.paths.forfeit, function serverForfeit(req, res) {
		serverApp.forfeit(req.body.gameID, req.body.user, res);
	});

	app.post(global.config.server.paths.ratings, function serverRatings(req, res) {
		serverApp.ratings(req.body.user, res);
	});

	// Open server up to calls
    app.listen(global.config.server.port, function serverListen() {
        console.log('Listening on port: ', global.config.server.port);
   });
}());