/*
 * Copyright (C) 2009 The Android Open Source Project
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

package com.android.settings.tests;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class BluetoothRequestPermissionTest extends Activity {
    private static final String TAG = "BluetoothRequestPermissionTest";

    private ArrayAdapter<String> mMsgAdapter;

    private class BtOnClickListener implements OnClickListener {
        final boolean mEnableOnly; // enable or enable + discoverable

        public BtOnClickListener(boolean enableOnly) {
            mEnableOnly = enableOnly;
        }

        public void onClick(View v) {
            requestPermission(mEnableOnly);
        }
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.bluetooth_request_permission_test);

        Button enable = (Button) findViewById(R.id.enable);
        enable.setOnClickListener(new BtOnClickListener(true /* enable */));

        Button discover = (Button) findViewById(R.id.discover);
        discover.setOnClickListener(new BtOnClickListener(false /* enable & discoverable */));

        mMsgAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

        ListView listView = (ListView) findViewById(R.id.msg_container);
        listView.setAdapter(mMsgAdapter);

        registerReceiver(mReceiver, new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED));
        addMsg("Initialized");
    }

    void requestPermission(boolean enableOnly) {
        Intent i = new Intent();
        if (enableOnly) {
            addMsg("Starting activity to enable bt");
            i.setAction(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        } else {
            addMsg("Starting activity to enable bt + discovery");
            i.setAction(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            i.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 20);
        }
        startActivityForResult(i, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != 1) {
            Log.e(TAG, "Unexpected onActivityResult " + requestCode + " " + resultCode);
            return;
        }

        if (resultCode == Activity.RESULT_CANCELED) {
            addMsg("Result = RESULT_CANCELED");
        } else if (resultCode == Activity.RESULT_OK) {
            addMsg("Result = RESULT_OK (not expected for discovery)");
        } else {
            addMsg("Result = " + resultCode);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    private void addMsg(String msg) {
        mMsgAdapter.add(msg);
        Log.d(TAG, "msg");
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent == null)
                return;
            if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(intent.getAction())) {
                String stateStr = "???";
                switch (intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothDevice.ERROR)) {
                    case BluetoothAdapter.STATE_OFF:
                        stateStr = "off";
                        break;
                    case BluetoothAdapter.STATE_TURNING_ON:
                        stateStr = "turning on";
                        break;
                    case BluetoothAdapter.STATE_ON:
                        stateStr = "on";
                        break;
                    case BluetoothAdapter.STATE_TURNING_OFF:
                        stateStr = "turning off";
                        break;
                }
                addMsg("Bluetooth status = " + stateStr);
            }
        }
    };
}
