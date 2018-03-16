package com.jetruby.androidtestingexample.presentation;

import android.support.constraint.ConstraintLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.jetruby.androidtestingexample.BuildConfig;
import com.jetruby.androidtestingexample.R;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;

/**
 * Created by Artem Leushin on 07.02.2018.
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class RobolectricFragmentTest {

    private LoginActivity activity;

    @Before
    public void setupFragment() {
        activity = Robolectric.setupActivity(LoginActivity.class);
    }

    @Test
    public void textViewTitleIsCorrect() {
        TextView tvTitle = activity.findViewById(R.id.tv_robo_title);
        Assert.assertEquals(tvTitle.getText(), "Robolectric");
    }

    @Test
    public void textViewAppearanceIsCorrect() {
        Assert.assertNotNull(activity);

        TextView tvTitle = activity.findViewById(R.id.tv_robo_title);

        int marginStartExpected = 8;
        int margingStartActual = ((ConstraintLayout.LayoutParams) tvTitle.getLayoutParams()).getMarginStart();
        Assert.assertEquals(marginStartExpected, margingStartActual);

        int marginEndExpected = 8;
        int margingEndActual = ((ConstraintLayout.LayoutParams) tvTitle.getLayoutParams()).getMarginEnd();
        Assert.assertEquals(marginEndExpected, margingEndActual);

        int fontSizeExpected = 24;
        int fontSizeActual = (int) tvTitle.getTextSize();
        Assert.assertEquals(fontSizeExpected, fontSizeActual);

        int textColorActual = tvTitle.getCurrentTextColor();
        int colorExpected = activity.getColor(R.color.colorAccent);
        Assert.assertEquals(colorExpected, textColorActual);
    }

    @Test
    public void imageViewHasCorrectImage() {
        Assert.assertNotNull(activity);

        ImageView ivLogo = activity.findViewById(R.id.iv_robo_logo);

        int drawableResIdExpected = R.drawable.ic_logo_with_bubbles;
        int drawableResIdActual = Shadows.shadowOf(ivLogo.getDrawable()).getCreatedFromResId();

        Assert.assertEquals(drawableResIdExpected, drawableResIdActual);
    }
}
