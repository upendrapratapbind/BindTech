package com.elearning.bindtech.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.elearning.bindtech.R;
import com.elearning.bindtech.interfaces.DialogManagerCallbacks;


public class DialogManager {

    private static Dialog mDialog;
    private static Button mDialogBtn, mDialogCancelBtn, mCancelBtn;
    private static TextView mDialogAlertTxt;
    private static TextView mDialogTitleTxt;

    public static void showToast(Context context, String message) {
        Toast mToast = Toast.makeText(context, message, Toast.LENGTH_LONG);
        //  TextView mToastTxt = mToast.getView().findViewById(android.R.id.tabs);
        // TextView mToastTxt = mToast.getView().findViewById(android.R.id.message);
        mToast.show();
    }

    public static void showConformPopup(final Context mContext,
                                        final DialogManagerCallbacks mDialoginterface, String mTitle,
                                        String mMessage, String positiveBtn, String negetiveBtn) {

        mDialog = getDialog(mContext, R.layout.popup_general_confirm);
        mDialog.setCancelable(false);
        mDialog.setCanceledOnTouchOutside(false);

        ViewGroup root = mDialog
                .findViewById(R.id.parent_view_for_font);

        mCancelBtn = mDialog.findViewById(R.id.cancel_dialog);
        mDialogAlertTxt = mDialog.findViewById(R.id.alert_text);
        mDialogTitleTxt = mDialog.findViewById(R.id.header_txt);

        mDialogBtn = mDialog.findViewById(R.id.process_btn);
        mDialogCancelBtn = mDialog.findViewById(R.id.cancel_btn);
        mDialogCancelBtn.setVisibility(View.GONE);
        mDialogTitleTxt.setText(mTitle);
        mDialogAlertTxt.setText(getMessage(mMessage));
        mDialogBtn.setText(positiveBtn);
        mCancelBtn.setText(negetiveBtn);

        mDialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                mDialoginterface.onOkClick();
            }
        });

        mCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                mDialoginterface.onCancelClick(v);
            }
        });

        mDialog.show();
    }

    public static void showSingleBtnPopup(final Context mContext,
                                          final DialogManagerCallbacks mDialoginterface, String mTitle,
                                          String mMessage, String mBtnTxt) {

        try {


            mDialog = getDialog(mContext, R.layout.popup_single_btn);
            mDialog.setCancelable(false);
            mDialog.setCanceledOnTouchOutside(false);

            ViewGroup root = mDialog.findViewById(R.id.parent_view_for_font);

            mCancelBtn = mDialog.findViewById(R.id.cancel_dialog);
            mDialogAlertTxt = mDialog.findViewById(R.id.alert_text);
            mDialogTitleTxt = mDialog.findViewById(R.id.header_txt);

            mDialogBtn = mDialog.findViewById(R.id.process_btn);
            mDialogCancelBtn = mDialog.findViewById(R.id.cancel_btn);
            mDialogCancelBtn.setVisibility(View.GONE);
            mDialogTitleTxt.setText(mTitle);
            mDialogAlertTxt.setText(getMessage(mMessage));
            mDialogBtn.setText(mBtnTxt);

            mDialogBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialog.dismiss();
                    if (mDialoginterface != null)
                        mDialoginterface.onOkClick();
                }
            });


            mDialog.show();
        } catch (Exception e) {
            Log.e("Dialog error", e.getMessage());
        }

    }

    public static Dialog getDialog(Context mContext, int mLayout) {
        Dialog mDialog = new Dialog(mContext);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(mLayout);
        mDialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT));
        mDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);
        mDialog.getWindow().setGravity(Gravity.CENTER);

        mDialog.setCancelable(true);

        mDialog.setCanceledOnTouchOutside(false);

        return mDialog;
    }

    private static String getMessage(String mMessage) {
        return (mMessage.trim().charAt(mMessage.trim().length() - 1) == '.' || mMessage.charAt(mMessage.length() - 1) == '?') ? mMessage : mMessage + ".";
    }
}
