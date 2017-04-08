package com.biagames.plugintest;

import android.app.Dialog;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.app.DialogFragment;
import android.os.Bundle;

import android.util.Log;
/**
 * Created by Xiaolong on 4/8/2017.
 */

public class Plugin_Dialog_Fragment extends DialogFragment {
    // Constants
    private static final String TAG = "Plugin_Dialog_Fragment";
    private static final String TITLE = "TITLE";
    private static final String MESSAGE = "MESSAGE";
    private static final String POSITIVE_BUTTON_TITLE = "POSITIVE_BUTTON_TITLE";
    private static final String NEGATIVE_BUTTON_TITLE = "NEGATIVE_BUTTON_TITLE";

    // Listener interface
    public interface Listener
    { void dialogDidFinishWithResult(Plugin_Dialog_Fragment dialog, String selectedButtonTitle); }
    
    Listener listener;



    // Factory method to return an new instance
    public static Plugin_Dialog_Fragment newInstance(String title, String message, String positiveButtonTitle, String negativeButtonTitle)
    {
        Log.i(TAG, TAG+".newInstance(`"+title+"`, `"+message+"`, `"+positiveButtonTitle+"`, `"+negativeButtonTitle+"`)");

        Plugin_Dialog_Fragment instance = new Plugin_Dialog_Fragment();

        // Bundle arguments.
        Bundle arguments = new Bundle(4);
        arguments.putString(TITLE, title);
        arguments.putString(MESSAGE, message);
        arguments.putString(POSITIVE_BUTTON_TITLE, positiveButtonTitle);
        arguments.putString(NEGATIVE_BUTTON_TITLE, negativeButtonTitle);
        instance.setArguments(arguments);

        return instance;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.i(TAG, TAG + ".onCreateDialog(`"
                + getArguments().getString(TITLE) + "`, `"
                + getArguments().getString(MESSAGE) + "`, `"
                + getArguments().getString(POSITIVE_BUTTON_TITLE) + "`, `"
                + getArguments().getString(NEGATIVE_BUTTON_TITLE) + "`)");

        // Get main fragment.
        Plugin_Test alert = (Plugin_Test) getFragmentManager().findFragmentByTag(Plugin_Test.TAG);
        listener = (Listener) alert;


        // Build dialog.
        return new AlertDialog.Builder(getActivity())
                .setTitle(getArguments().getString(TITLE))
                .setMessage(getArguments().getString(MESSAGE))
                .setPositiveButton(
                        getArguments().getString(POSITIVE_BUTTON_TITLE),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                listener.dialogDidFinishWithResult(Plugin_Dialog_Fragment.this, Plugin_Dialog_Fragment.this.getArguments().getString(POSITIVE_BUTTON_TITLE));
                            }
                        }
                )
                .setNegativeButton(
                        getArguments().getString(NEGATIVE_BUTTON_TITLE),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                listener.dialogDidFinishWithResult(Plugin_Dialog_Fragment.this, Plugin_Dialog_Fragment.this.getArguments().getString(NEGATIVE_BUTTON_TITLE));
                            }
                        }
                )
                .create();
    }
}
