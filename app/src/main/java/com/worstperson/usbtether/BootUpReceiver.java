/*
        Copyright 2023 worstperson

        Licensed under the Apache License, Version 2.0 (the "License");
        you may not use this file except in compliance with the License.
        You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

        Unless required by applicable law or agreed to in writing, software
        distributed under the License is distributed on an "AS IS" BASIS,
        WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
        See the License for the specific language governing permissions and
        limitations under the License.
*/

package com.worstperson.usbtether;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;

public class BootUpReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action;
        if ((action = intent.getAction()) != null && action.equals(Intent.ACTION_BOOT_COMPLETED)) {
            Log.i("USBTether", "bootupreceiver onrecieve called...");
            SharedPreferences sharedPref = context.getSharedPreferences("Settings", Context.MODE_PRIVATE);
            boolean serviceEnabled = sharedPref.getBoolean("serviceEnabled", false);
            if (serviceEnabled && !ForegroundService.isStarted) {
                Intent it = new Intent(context.getApplicationContext(), ForegroundService.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    context.startForegroundService(it);
                } else {
                    context.startService(it);
                }
                ForegroundService.isStarted = true;
            }
        }
    }
}
