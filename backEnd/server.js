
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
	app.post(global.config.server.paths.login, function login(req, res) {

	});

	app.post(global.config.server.paths.addMove, function addMove(req, res) {

	})

	app.post(global.config.server.paths.undoMove, function undoMove(req, res) {

	});

	app.post(global.config.server.paths.forfeit, function undoMove(req, res) {

	});

	app.post(global.config.server.paths.ratings, function undoMove(req, res) {

	});

	// Open server up to calls
    app.listen(global.config.server.port, function serverListen() {
        console.log('Listening on port: ', global.config.server.port);
   });
}());