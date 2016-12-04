/*
       Licensed to the Apache Software Foundation (ASF) under one
       or more contributor license agreements.  See the NOTICE file
       distributed with this work for additional information
       regarding copyright ownership.  The ASF licenses this file
       to you under the Apache License, Version 2.0 (the
       "License"); you may not use this file except in compliance
       with the License.  You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

       Unless required by applicable law or agreed to in writing,
       software distributed under the License is distributed on an
       "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
       KIND, either express or implied.  See the License for the
       specific language governing permissions and limitations
       under the License.
 */

package com.ionicframework.xwalktabs569224;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import org.apache.cordova.*;
import org.crosswalk.engine.XWalkCordovaView;
import org.crosswalk.engine.XWalkWebViewEngine;
import org.xwalk.core.XWalkActivityDelegate;
import org.xwalk.core.XWalkDialogManager;
import org.xwalk.core.XWalkInitializer;
import org.xwalk.core.XWalkPreferences;
import org.xwalk.core.XWalkResourceClient;
import org.xwalk.core.XWalkUIClient;
import org.xwalk.core.XWalkView;

//implements XWalkInitializer.XWalkInitListener
public class MainActivity extends CordovaActivity
{
    private XWalkActivityDelegate mActivityDelegate;

    private XWalkView xWalkWebView;
    private XWalkWebViewEngine xwe;
    private XWalkCordovaView swv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Runnable cancelCommand = new Runnable() {
            public void run() {
                MainActivity.this.onXWalkFailed();
            }
        };
        Runnable completeCommand = new Runnable() {
            public void run() {
                MainActivity.this.onXWalkReady();
            }
        };

        this.mActivityDelegate = new XWalkActivityDelegate(this, cancelCommand, completeCommand);

        // turn on debugging
        // XWalkPreferences.setValue(XWalkPreferences.REMOTE_DEBUGGING, true);
    }

    protected void onXWalkReady() {
        Log.d("MainActivity", "onXWalkReady");
        CordovaWebViewEngine cordovawebviewEngine = super.makeWebViewEngine();
        XWalkWebViewEngine xwe = (XWalkWebViewEngine) cordovawebviewEngine;

        setContentView(xwe.getView());
        XWalkCordovaView swv = (XWalkCordovaView) xwe.getView();

        swv.load(launchUrl, null);
    }

    protected void onXWalkFailed() {
        Log.d("MainActivity", "onXWalkFailed");
        this.finish();
    }

    protected XWalkDialogManager getDialogManager() {
        return this.mActivityDelegate.getDialogManager();
    }

    public boolean isXWalkReady() {
        return this.mActivityDelegate.isXWalkReady();
    }

    public boolean isSharedMode() {
        return this.mActivityDelegate.isSharedMode();
    }

    public boolean isDownloadMode() {
        return this.mActivityDelegate.isDownloadMode();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(swv != null) {
            swv.pauseTimers();
            swv.onHide();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.mActivityDelegate.onResume();
        if(swv != null) {
            swv.resumeTimers();
            swv.onShow();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(swv != null) {
            swv.onDestroy();
        }
    }

//    @Override
//    public void onXWalkInitStarted() {
//        Log.d("MainActivity", "onXWalkInitStarted");
//
//    }
//
//    @Override
//    public void onXWalkInitCancelled() {
//        Log.d("MainActivity", "onXWalkInitCancelled");
//
//    }
//
//    @Override
//    public void onXWalkInitFailed() {
//        Log.d("MainActivity", "onXWalkInitFailed");
//
//    }
//
//    @Override
//    public void onXWalkInitCompleted() {
//        Log.d("MainActivity", "onXWalkInitCompleted");
//
//    }
}
