package com.wingmedia.utils.device;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by Alan's on 3/21/2017.
 */

public class DeviceUtils {
  @TargetApi(Build.VERSION_CODES.CUPCAKE)
  public static void hideKeyboard(Activity activity) {
    try {
      InputMethodManager inputManager = (InputMethodManager) activity
          .getSystemService(Context.INPUT_METHOD_SERVICE);
      inputManager.hideSoftInputFromWindow(activity.getCurrentFocus()
          .getWindowToken(), 0);
    } catch (Exception e) {
      Log.e(DeviceUtils.class + "", "can't hide keyboard " + activity + " message: " + e.getMessage());
    }
  }

  /**
   * Action send email
   */
  public static void sendEmail(Context context, String email) {
    Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
        "mailto", email, null));
    context.startActivity(Intent.createChooser(emailIntent, "Send email..."));
  }

  /**
   * Action call number
   */
  public static void callPhoneNumber(Context context, String phoneNumber) {
    Intent callIntent = new Intent(Intent.ACTION_DIAL);
    callIntent.setData(Uri.parse("tel:" + Uri.encode(phoneNumber.trim())));
    callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    context.startActivity(callIntent);
  }
}
