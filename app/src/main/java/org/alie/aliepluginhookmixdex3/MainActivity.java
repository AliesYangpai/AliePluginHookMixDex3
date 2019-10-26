package org.alie.aliepluginhookmixdex3;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import org.alie.aliepluginhookmixdex3.plugin.core.PluginManager;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void load(View view) {
        File file = new File(Environment.getExternalStorageDirectory(), "plugin.apk");
        PluginManager.getInstance().installPackage(file.getAbsolutePath(), 0);

    }

    public void start(View view) {
        PluginManager.getInstance().init(this.getApplicationContext());
        PluginManager.getInstance().connectToService();

    }
}
