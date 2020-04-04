BackgroundWatchPosition Plugin
======

Cordova plugin that automatically forces Android System WebView to think that its window is still visible when your app is running in the background. This allows JavaScript code to continue to execute for more than 5 minutes after the app goes into background.

This has been implemented after Chromium [decided to disable watchPosition](https://bugs.chromium.org/p/chromium/issues/detail?id=585055) (and getCurrentPosition) when the browser is not in the foreground. Justifiably so, but a lot of Cordova applications did not expect this.

Although the plugin sets the WebView to VISIBLE automatically 1s after it goes into background, it's possible to also do this manually with:
```
cordova.plugins.backgroundWatchPosition.forceWebViewVisibility();
```

Copyright: Geotab
Author: Mike Murkovic
