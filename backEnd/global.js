;(function initGlobals() {
	// Get configuration file for use throughout server
	global.config = require('../config');

	// Get utilities
	global.utility = {};
	global.utility.path = require('path');
	global.utility.checking = require('./src/Utility/typeChecking');
}());