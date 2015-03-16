package com.archtouch.appbus.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;

import me.drakeet.materialdialog.MaterialDialog;


public class Utils {

    private static ProgressDialog progressDialog;
    private static MaterialDialog mMaterialDialog;

    /**
     * AlertDialog info user
     * @param title
     * @param msg
     * @param context
     */
    public static void alertDialogOk(String title, String msg, Activity context) {
        mMaterialDialog = new MaterialDialog(context)
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMaterialDialog.dismiss();

                    }
                });

        mMaterialDialog.show();
    }

    /**
     * Show alert custom msg
     * @param msg - Message alert
     * @param context - title alert
     */
    public static void showProgressDialog(CharSequence msg, Context context) {

        //dismiss progress dialog before call again
        dismissProgressDialog();

        progressDialog = new ProgressDialog(context);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(msg);
        progressDialog.setCancelable(false);

        progressDialog.show();

    }

    /**
     * Disabel progress alert msg
     */
    public static void dismissProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }


    /**
     * Verify network connection
     *
     * @param context
     * @return true = OK or false = NOTOK
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connect = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connect.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}