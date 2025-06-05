package io.github.projectbullethell.android;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import MainMethod.bulletHellMain;

/** Launches the Android application. */
public class AndroidLauncher extends AndroidApplication {

    String plattform;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.useAccelerometer = false;
        config.useCompass = false;
        config.useWakelock = true;
        config.useImmersiveMode = true;
        config.numSamples = 2;

        plattform = "android";
        initialize(new bulletHellMain(plattform), config);
    }
}
