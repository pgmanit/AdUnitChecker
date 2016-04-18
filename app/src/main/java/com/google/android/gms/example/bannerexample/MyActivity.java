/*
 * Copyright (C) 2013 Google, Inc.
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
package com.google.android.gms.example.bannerexample;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherAdView;

/**
 * Main Activity. Inflates main activity xml and child fragments.
 */
public class MyActivity extends ActionBarActivity
{

    private static final int BANNER = 0;
    private static final int SMART_BANNER = 1;
    private static final int MEDIUM_RECTANGLE = 2;
    private FrameLayout flAd;
    private RadioGroup rgAdSize;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        flAd = (FrameLayout) findViewById(R.id.flAd);
        rgAdSize = (RadioGroup) findViewById(R.id.rgAdSize);
        ((RadioButton) rgAdSize.getChildAt(0)).setChecked(true);
    }

    public void clickSubmit(View view)
    {
        EditText editText = (EditText) findViewById(R.id.etUnitId);
        if(TextUtils.isEmpty(editText.getText()))
        {
            Toast.makeText(this, "Please enter unit id", Toast.LENGTH_SHORT).show();
            return;
        }

        flAd.removeAllViews();
        PublisherAdView mAdView = new PublisherAdView(this);
        mAdView.setAdUnitId(editText.getText().toString());

        int index = rgAdSize.indexOfChild(findViewById(rgAdSize.getCheckedRadioButtonId()));
        switch(index)
        {
            case BANNER:
                mAdView.setAdSizes(AdSize.BANNER);
                break;
            case SMART_BANNER:
                mAdView.setAdSizes(AdSize.SMART_BANNER);
                break;
            case MEDIUM_RECTANGLE:
                mAdView.setAdSizes(AdSize.MEDIUM_RECTANGLE);
                break;
            default:
                mAdView.setAdSizes(AdSize.BANNER);
                break;
        }

        mAdView.setAdListener(new AdListener()
        {
            @Override
            public void onAdFailedToLoad(int errorCode)
            {
                super.onAdFailedToLoad(errorCode);
                Toast.makeText(MyActivity.this, "Ad failed to load", Toast.LENGTH_SHORT).show();
            }
        });
        PublisherAdRequest adRequest = new PublisherAdRequest.Builder().build();

        // Start loading the ad in the background.
        mAdView.loadAd(adRequest);
        flAd.addView(mAdView);
    }
}
