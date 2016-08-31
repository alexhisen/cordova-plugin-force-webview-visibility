package com.geotab;

import android.view.View;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CallbackContext;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class BackgroundWatchPosition extends CordovaPlugin {
  public static final String TAG = "BGWP";

  private CordovaWebView webView;
  @Override
  public void initialize(CordovaInterface cordovaParam, CordovaWebView webViewParam) {
    super.initialize(cordovaParam, webViewParam);
    webView = webViewParam;
  }

  private void forceWebViewVisibility(String message) {
    webView.getEngine().getView().dispatchWindowVisibilityChanged(View.VISIBLE);
    webView.sendJavascript("console.warn(\"[" + TAG + "] "  + message.replaceAll("\"", "'") + "\")");
  }


  /**
   * When activity loses focus, tell the android.webkit.WebView that it is still visible.
   * Alternative is to override onWindowVisibilityChanged in cordova.engine.SystemWebView,
   * but that requires a Cordova fork, which isn't the best idea.
   */
  @Override
  public void onStop() {
    super.onStop();

    // Wake up sleeping beauty one second after Maleficent (WebView) has put her to sleep
    Thread thread = new Thread(){
      public void run() {
        try {
          Thread.sleep(1000);
          forceWebViewVisibility("Forcing web view visibility on transition to background");
        } catch (InterruptedException e) {
          // do nothing
        }
      }
    };

    thread.start();
  }

  @Override
  public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {
    if (action.equals("forceWebViewVisibility")) {
      cordova.getThreadPool().execute(new Runnable() {
        public void run() {
          forceWebViewVisibility("Forcing web view visibility by request from app");
          callbackContext.success(); // Thread-safe.
        }
      });
      return true;
    }
    return false;
  }
}