package com.bingo.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import com.bingo.ui.R;

/**
 * AbstractDialog
 */
public abstract class AbstractDialog extends Dialog {
    protected Context context;
    protected View rootView;

    protected AbstractDialog(@NonNull Context context) {
        this(context, R.style.lib_pub_dialog_style, false, 0, 0, 0);
    }

    protected AbstractDialog(@NonNull Context context, @StyleRes int themeResId) {
        this(context, themeResId, false, 0, 0, 0);
    }

    /**
     * Creates a dialog window that uses a custom dialog style.
     *
     * @param context    Context
     * @param themeResId The dialog's layout resource
     * @param isSetWin   Set the gravity of the window
     * @param gravity    The desired gravity constant
     * @param width      The dialog's width
     * @param heith      The dialog's height
     */
    protected AbstractDialog(@NonNull Context context, @StyleRes int themeResId, boolean isSetWin, int gravity, int width, int heith) {
        super(context, themeResId);
        this.context = context;
        this.rootView = LayoutInflater.from(context).inflate(getLayoutRes(), null);
        setContentView(this.rootView);
        setCanceledOnTouchOutside(true);
        setCancelable(true);
        if (isSetWin) {
            Window dialogWindow = getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(-1);
                dialogWindow.setBackgroundDrawableResource(android.R.color.transparent);
                dialogWindow.getDecorView().setPadding(0, 0, 0, 0);
                dialogWindow.setGravity(gravity);
                // Get the current layout param of the dialog
                WindowManager.LayoutParams p = dialogWindow.getAttributes();
                // Set dialog's width
                p.width = width;
                // Set dialog's height
                p.height = heith;
                dialogWindow.setAttributes(p);
            }
        }
        init(this.rootView);
    }

    protected AbstractDialog(Context context, int themeResId, ViewGroup.LayoutParams params) {
        super(context, themeResId);
        this.context = context;
        this.rootView = LayoutInflater.from(context).inflate(getLayoutRes(), null);
        setContentView(this.rootView, params);
        setCanceledOnTouchOutside(true);
        setCancelable(true);
        Window dialogWindow = getWindow();
        if (dialogWindow != null) {
            dialogWindow.setWindowAnimations(-1);
            dialogWindow.setBackgroundDrawableResource(android.R.color.transparent);
            dialogWindow.getDecorView().setPadding(0, 0, 0, 0);
        }
        init(this.rootView);
    }

    /**
     * Show dialog
     */
    @Override
    public void show() {
        if (context instanceof Activity && ((Activity) context).isFinishing()) {
            return;
        }
        if (!isShowing()) {
            super.show();
        }
    }

    /**
     * Dismiss dialog
     */
    @Override
    public void dismiss() {
        if (isShowing()) {
            super.dismiss();
        }
    }

    protected abstract int getLayoutRes();

    protected abstract void init(View rootView);
}
