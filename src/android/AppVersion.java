package uk.co.whiteoctober.cordova;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.PackageManager;

public class AppVersion extends CordovaPlugin {
  @Override
  public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {

    try {
      if (action.equals("getAppName")) {
        PackageManager packageManager = this.cordova.getActivity().getPackageManager();
        ApplicationInfo app = packageManager.getApplicationInfo(this.cordova.getActivity().getPackageName(), 0);
        callbackContext.success((String)packageManager.getApplicationLabel(app));
        return true;
      }
      if (action.equals("getPackageName")) {
        callbackContext.success(this.cordova.getActivity().getPackageName());
        return true;
      }
      if (action.equals("getVersionNumber")) {
        PackageManager packageManager = this.cordova.getActivity().getPackageManager();
        callbackContext.success(packageManager.getPackageInfo(this.cordova.getActivity().getPackageName(), 0).versionName);
      return true;
      }
      if (action.equals("getVersionCode")) {
        PackageManager packageManager = this.cordova.getActivity().getPackageManager();
        callbackContext.success(packageManager.getPackageInfo(this.cordova.getActivity().getPackageName(), 0).versionCode);
      return true;
      }
      if (action.equals("getAppSignature")) {
        PackageManager packageManager = this.cordova.getActivity().getPackageManager();
        callbackContext.success(packageManager.getPackageInfo(this.cordova.getActivity().getPackageName(), PackageManager.GET_SIGNATURES));
        
        Signature[] signatures = packageInfo.signatures;
        if (signatures.length > 0) {
            Signature signature = signatures[0];
            return getSHA1(signature.toByteArray());
        }
      return true;
      }
      return false;
    } catch (NameNotFoundException e) {
      callbackContext.success("N/A");
      return true;
    }
  }
          
  public static String getSHA1(byte[] sig) {
      MessageDigest digest = null;
      try {
          digest = MessageDigest.getInstance("SHA1", "BC");
      } catch (NoSuchAlgorithmException e) {
          e.printStackTrace();
      } catch (NoSuchProviderException e) {
          e.printStackTrace();
      }
      digest.update(sig);
      byte[] hashtext = digest.digest();
      return bytesToHex(hashtext);
  }

  //util method to convert byte array to hex string
  public static String bytesToHex(byte[] bytes) {
      final char[] hexArray = {'0', '1', '2', '3', '4', '5', '6', '7', '8',
              '9', 'A', 'B', 'C', 'D', 'E', 'F'};
      char[] hexChars = new char[bytes.length * 2];
      int v;
      for (int j = 0; j < bytes.length; j++) {
          v = bytes[j] & 0xFF;
          hexChars[j * 2] = hexArray[v >>> 4];
          hexChars[j * 2 + 1] = hexArray[v & 0x0F];
      }
      return new String(hexChars);
  }   
}
