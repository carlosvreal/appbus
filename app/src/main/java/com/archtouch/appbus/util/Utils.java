package com.archtouch.appbus.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.WindowManager;

import me.drakeet.materialdialog.MaterialDialog;

/**
 * Created by real on 8/3/15.
 */
public class Utils {

    public static ProgressDialog progressDialog;
    public static MaterialDialog mMaterialDialog;

    /**
     *
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
     *
     * @param mensagem
     * @param context
     */
    public static void showProgressDialog(CharSequence mensagem, Context context) {

        dismissProgressDialog();

        progressDialog = new ProgressDialog(context);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(mensagem);
        progressDialog.setCancelable(false);
//        progressDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        progressDialog.show();
    }

    public static void dismissProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
