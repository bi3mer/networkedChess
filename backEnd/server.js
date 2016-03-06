;(function initServer() {
	'use strict';

	// Get globals
	require('./global.js');

	// File name for debugging
	var fileName = global.utility.path.basename(__filename) + ' ->';

	// App
	var serverApp = require('./src/app.js');

	// NPM requirements
	var bodyParser = require('body-parser');
	var app	= require('express')();

	// Accept JSON requests
	app.use(bodyParser.json());
	app.use(bodyParser.urlencoded({
		extended: true
	}));

	/**
	 * Use res to send status and message to user
	 * @param {Object} res     - response object
	 * @param {Number} status  - status to send to user
	 * @Param {Object} message - any kind of message to give to the user
	 */
	function sendMessageToUser(res, status, message) {
		console.log(fileName, 'sendMessageToUser: sending to user');
		res.status(status);
		res.send(message);
	};

	/**
	 * Use res to send generic error user
	 * @param {Object} res - response object
	 */
	function sendErrorToUser(res) {
		sendMessageToUser(res, global.config.server.httpStatusCodes.error, {invalid: true});
	};

	/**
	 * Use res to send generic error user
	 * @param {Object} req - request object
	 */
	function isValidReq(req) {
		return global.utility.checking.isValidObj(req) && global.utility.checking.isValidObj(req.body)
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

		if(isValidReq(req) && global.utility.checking.isString(req.body.user) && global.utility.checking.isString(req.body.pass)) {
			serverApp.createAccount(req.body.user, req.body.pass, function createAccountGetResponse(status, message) {
				console.log(fileName, 'Creating account: sending to user');
				sendMessageToUser(res, status, message);
			});
		} else {
			sendErrorToUser(res);
		}
	});

	// Listen to path to login users in
	app.post(global.config.server.paths.login, function serverLogin(req, res) {
		console.log(fileName, 'Logging in');

		if(isValidReq(req) && global.utility.checking.isString(req.body.user) && global.utility.checking.isString(req.body.pass)) {	
			serverApp.login(req.body.user, req.body.pass, function loginGetResponse(status, message) {
				console.log(fileName, 'Logging in: sending to user');
				console.log(JSON.stringify(message));
				sendMessageToUser(res, status, message);
			});
		} else {
			sendErrorToUser(res);
		}
	});

	// Listen to path to add a move to a users game
	app.post(global.config.server.paths.addMove, function serverAddMove(req, res) {
		console.log(fileName, 'adding move');

		if(isValidReq(req) && global.utility.checking.isString(req.body.user) && global.utility.checking.isValidMove(req.body.move)) {
			serverApp.addMove(req.body.user, req.body.move, function addMoveGetResponse(status, message) {
				console.log(fileName, 'adding move: sending to user');
				sendMessageToUser(res, status, message);
			});
		} else {
			sendErrorToUser(res);
		}
	});

	// Listen to path to requestUndo a move in a users game
	app.post(global.config.server.paths.requestUndo, function serverUndoMove(req, res) {
		console.log(fileName, 'requestUndo move');

		if(isValidReq(req) && global.utility.checking.isString(req.body.user)) {
			serverApp.requestUndo(req.body.user, function undoMoveGetResponse(status, message) {
				console.log(fileName, 'requestUndo move: sending to user');
				sendMessageToUser(res, status, message);
			});
		} else {
			sendErrorToUser(res);
		}
	});

	// Listen to path to acceptOrDenyUndo a move in a users game
	app.post(global.config.server.paths.acceptOrDenyUndo, function serverUndoMove(req, res) {
		console.log(fileName, 'acceptOrDenyUndo move');

		if(isValidReq(req) && global.utility.checking.isString(req.body.user)) {
			serverApp.acceptOrDenyUndo(req.body.user, req.body.acceptUndo, function undoMoveGetResponse(status, message) {
				console.log(fileName, 'acceptOrDenyUndo move: sending to user');
				sendMessageToUser(res, status, message);
			});
		} else {
			sendErrorToUser(res);
		}
	});

	// Listen to path to accept or decline a forfeit for a users game
	app.post(global.config.server.paths.forfeit, function serverForfeit(req, res) {
		console.log(fileName, 'forfeiting game');

		if(isValidReq(req) && global.utility.checking.isString(req.body.user)) {
			serverApp.forfeit(req.body.user, function forfeitGetResponse(status, message) {
				console.log(fileName, 'forfeiting game: sending to user');
				sendMessageToUser(res, status, message);
			});
		} else {
			sendErrorToUser(res);
		}
	});

	// TODO: accept or decline port path

	// Listen to path to get the users ratings
	app.post(global.config.server.paths.ratings, function serverRatings(req, res) {
		console.log(fileName, 'getting server ratings');

		if(isValidReq(req) && global.utility.checking.isString(req.body.user)) {
			serverApp.ratings(req.body.user, function ratingsGetResponse(status, message) {
				console.log(fileName, 'getting server ratings: sending to user');
				sendMessageToUser(res, status, message);
			});
		} else {
			sendErrorToUser(res);
		}
	});

	// Listen to path to get users updates for the game
	app.post(global.config.server.paths.update, function getUpdate(req, res) {
		console.log(fileName, 'getting updates for user');

		// TODO: check isPlaying with isValidBool
		if(isValidReq(req) && global.utility.checking.isString(req.body.user)) {
			serverApp.getUpdate(req.body.user, req.body.isPlaying, function updateGetResponse(status, message) {
				console.log(fileName, 'getting updates for user: sending to user');
				sendMessageToUser(res, status, message);
			});
		} else {
			sendErrorToUser(res);
		}
	});

	// Listen to path to get users updates for the game
	app.post(global.config.server.paths.getMatch, function getMatchCallback(req, res) {
		console.log(fileName, 'getting match for user or adding to queue');

		if(isValidReq(req) && global.utility.checking.isString(req.body.user)) {
			serverApp.getMatch(req.body.user, function createAccountGetResponse(status, message) {
				console.log(fileName, 'getting match for user or adding to queue: sending to user');
				sendMessageToUser(res, status, message);
			});
		} else {
			sendErrorToUser(res);
		}
	});

	// Listen to path to see the queue, NOTE: for debugging
	app.post(global.config.server.paths.getMatchMaking, function getMatchmakingCallback(req, res) {
		console.log(fileName, 'getting queue for user, NOTE: for debugging');

		if(isValidReq(req)) {
			serverApp.getMatchMaking(function getMatchMakingGetResponse(status, message) {
				console.log(fileName, 'getting Matchmaking datastructure for debugging');
				sendMessageToUser(res, status, message);
			});
		} else {
			sendErrorToUser(res);
		}
	});

	// listen to path to cancel if in queue
	app.post(global.config.server.paths.cancelQueue, function cancelQueue(req, res) {
		if(isValidReq(req) && global.utility.checking.isString(req.body.user)) {
			serverApp.cancelQueue(req.body.user, function cancelQueueSuccess(status, message) {
				console.log(fileName, 'cancel queue, sending message');
				sendMessageToUser(res, status, message);
			});
		} else {
			console.log(fileName, 'cancel queue, invalid args');
			sendErrorToUser(res);
		}
	});

	// Open server up to calls on the configurations port
	app.listen(global.config.server.port, function serverListen() {
		console.log(fileName, 'Listening on port: ', global.config.server.port);
	});
}());