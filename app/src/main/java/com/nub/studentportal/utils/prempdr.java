package com.nub.studentportal.utils;

import android.app.Activity;
import android.view.Window;

import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import com.nub.studentportal.R;

public class prempdr {

    public static void fullScreen(Activity context) {
        Window window = context.getWindow();
        WindowCompat.setDecorFitsSystemWindows(window, false);
        WindowInsetsControllerCompat windowInsetsController = WindowCompat.getInsetsController(window, context.findViewById(R.id.main));
        //windowInsetsController.setAppearanceLightStatusBars(true);
        //windowInsetsController.setAppearanceLightNavigationBars(true);
    }
}
