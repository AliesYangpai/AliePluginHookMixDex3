package org.alie.aliepluginhookmixdex3;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.alie.aliepluginhookmixdex3.plugin.core.PluginManager;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn1, btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListener();
    }

    private void initView() {
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
    }

    private void initListener() {
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
    }

    public void doLoadApk() {
        File file = new File(Environment.getExternalStorageDirectory(), "plugin.apk");
        PluginManager.getInstance().installPackage(file.getAbsolutePath(), 0);

    }

    public void doInitService() {
        PluginManager.getInstance().init(this.getApplicationContext());
        PluginManager.getInstance().connectToService();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn1:
                doInitService();
                break;
            case R.id.btn2:
                doLoadApk();
                break;
        }
    }
}
