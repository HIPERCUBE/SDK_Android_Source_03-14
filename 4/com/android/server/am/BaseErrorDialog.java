/*
 * Copyright (C) 2006 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.server.am;

import com.android.internal.R;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.Button;

class BaseErrorDialog extends AlertDialog {
    public BaseErrorDialog(Context context) {
        super(context, com.android.internal.R.style.Theme_Dialog_AppError);

        getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM,
                WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        getWindow().setTitle("Error Dialog");
        setIcon(R.drawable.ic_dialog_alert);
    }

    public void onStart() {
        super.onStart();
        setEnabled(false);
        mHandler.sendMessageDelayed(mHandler.obtainMessage(0), 1000);
    }

    public boolean dispatchKeyEvent(KeyEvent event) {
        if (mConsuming) {
            //Log.i(TAG, "Consuming: " + event);
            return true;
        }
        //Log.i(TAG, "Dispatching: " + event);
        return super.dispatchKeyEvent(event);
    }

    private void setEnabled(boolean enabled) {
        Button b = (Button)findViewById(R.id.button1);
        if (b != null) {
            b.setEnabled(enabled);
        }
        b = (Button)findViewById(R.id.button2);
        if (b != null) {
            b.setEnabled(enabled);
        }
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                mConsuming = false;
                setEnabled(true);
            }
        }
    };

    private boolean mConsuming = true;
}
