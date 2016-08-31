
var exec = require('cordova/exec');
var channel = require('cordova/channel');

module.exports = {
    forceWebViewVisibility: function() {
        cordova.exec(null, null, 'BackgroundWatchPosition', 'forceWebViewVisibility', []);
    }
};
