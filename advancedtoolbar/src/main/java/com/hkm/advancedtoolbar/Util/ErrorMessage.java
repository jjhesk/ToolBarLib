package com.hkm.advancedtoolbar.Util;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

/**
 * Created by hesk on 25/8/15.
 */
public class ErrorMessage extends DialogFragment {
    private static Runnable onclickrun;

    public static void alert(final String message, FragmentManager manager) {
        ErrorMessage.message(message).show(manager, "errorMessageOnce");
    }

    public static void alert(final String message, FragmentManager manager, Runnable onclickrun) {
        ErrorMessage.onclickrun = onclickrun;
        ErrorMessage.message(message).show(manager, "errorMessageOnce");
    }

    public static Bundle getMessageBundle(final String mes) {
        Bundle h = new Bundle();
        h.putString("message", mes);
        return h;
    }

    public static ErrorMessage message(final String mes) {
        Bundle h = new Bundle();
        h.putString("message", mes);
        ErrorMessage e = new ErrorMessage();
        e.setArguments(h);
        return e;
    }


    protected void onNoticedErrorMessage(DialogInterface dialog, int id, String original_message) {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(getArguments().getString("message"))
                .setNeutralButton("try again", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        onNoticedErrorMessage(dialog, id, getArguments().getString("message"));
                        dialog.dismiss();
                        if (ErrorMessage.onclickrun != null) {
                            ErrorMessage.onclickrun.run();
                            ErrorMessage.onclickrun = null;
                        }
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
