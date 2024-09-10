package com.nub.studentportal.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.nub.studentportal.R;

public class prempdr {

    public static void fullScreen(Activity context) {
        Window window = context.getWindow();
        WindowCompat.setDecorFitsSystemWindows(window, false);
        WindowInsetsControllerCompat windowInsetsController = WindowCompat.getInsetsController(window, context.findViewById(R.id.main));
        windowInsetsController.hide(WindowInsetsCompat.Type.statusBars());
        if (Build.VERSION.SDK_INT <= 30) {
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            window.getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_FULLSCREEN
            );
        }
    }

    public static boolean internetAvailability(Application application) {
        ConnectivityManager connectivityManager = (ConnectivityManager) application.getSystemService(Context.CONNECTIVITY_SERVICE);
        android.net.Network nw = connectivityManager.getActiveNetwork();
        if (nw == null) return false;
        NetworkCapabilities actNw = connectivityManager.getNetworkCapabilities(nw);
        return actNw != null && (actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) || actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH));
    }

    public static void openActivity(Activity context1, Class<?> context2) {
        context1.startActivity(new Intent(context1, context2));
        Animatoo.animateSlideLeft(context1);
        context1.finish();
    }

    public static void openActivityBack(Activity context1, Class<?> context2) {
        context1.startActivity(new Intent(context1, context2));
        Animatoo.animateSlideRight(context1);
        context1.finish();
    }
}
