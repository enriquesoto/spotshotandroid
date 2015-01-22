package com.aqpup.waitforit.utils;

import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

public abstract class BaseSampleActivity extends FragmentActivity {
    FragmentAdapter mCircleAdapter;
    ViewPager mPager;
    PageIndicator mCircleIndicator;
}
