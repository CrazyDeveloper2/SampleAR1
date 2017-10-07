package prabhat.artk6_test;

import android.app.Application;

import org.artoolkit.ar6.base.assets.AssetHelper;

/**
 * Created by Me on 10/7/2017.
 */

public class ARTrackingApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        AssetHelper assetHelper = new AssetHelper(getAssets());
        assetHelper.cacheAssetFolder(this, "Data");
    }
}
