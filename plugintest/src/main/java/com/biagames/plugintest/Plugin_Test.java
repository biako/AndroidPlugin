package com.biagames.plugintest;

import com.unity3d.player.UnityPlayer;

import android.app.Fragment;
import android.os.*;
import android.widget.Toast;
import android.util.Log;

/**
 * Created by Xiaolong on 4/7/2017.
 */

public class Plugin_Test extends Fragment implements Plugin_Dialog_Fragment.Listener{
    // Constants.
    public static final String TAG = "Plugin_Test_Fragment";
    private static final String CALLBACK_METHOD_NAME = "AlertDidFinishWithResult";

    // Singleton instance.
    public static Plugin_Test instance;

    // Unity context.
    String gameObjectName;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setRetainInstance(true); // Retain between configuration changes (like device rotation)
    }



    // Static test methods:
    public static void setToast(String text) {
        Toast.makeText(UnityPlayer.currentActivity.getApplicationContext(), text, Toast.LENGTH_LONG).show();
    }

    public static String getMessage() {
        return "This is from Android Method";
    }

    public static String setMessage(String text) {
        return "This is from Android Method: " + text;
    }


    // Codes for the Dialog
    // Setup the dialog
    public static void setupDialog(String gameObjectName)
    {
        // Instantiate and add to Unity Player Activity.
        instance = new Plugin_Test();
        instance.gameObjectName = gameObjectName; // Store `GameObject` reference
        UnityPlayer.currentActivity.getFragmentManager().beginTransaction().add(instance, Plugin_Test.TAG).commit();
    }


    // Show the dialog by create a Plugin_Dialog_Fragment
    public void showAlertWithAttributes(String title, String message, String positiveButtonTitle, String negativeButtonTitle)
    {
        Log.i(TAG, TAG+".showAlertWithAttributes(`"+title+"`, `"+message+"`, `"+positiveButtonTitle+"`, `"+negativeButtonTitle+"`)");

        // Create and show dialog.
        Plugin_Dialog_Fragment dialogFragment = Plugin_Dialog_Fragment.newInstance(
                title,
                message,
                positiveButtonTitle,
                negativeButtonTitle
        );
        dialogFragment.show(getFragmentManager(), title);
    }


    // Call back to Unity
    public void dialogDidFinishWithResult(Plugin_Dialog_Fragment dialog, String selectedButtonTitle)
    {
        Log.i(TAG, TAG+".dialogDidFinishWithResult(selectedButtonTitle: `"+selectedButtonTitle+"`)");

        // Call back to Unity.
        SendUnityMessage(CALLBACK_METHOD_NAME, selectedButtonTitle);
    }


    // Region utility for callback to Unity by calling UnityPlayer.UnitySendMessage
    void SendUnityMessage(String methodName, String parameter)
    {
        Log.i(TAG, TAG+"SendUnityMessage(`"+methodName+"`, `"+parameter+"`)");
        UnityPlayer.UnitySendMessage(gameObjectName, methodName, parameter);
    }

}