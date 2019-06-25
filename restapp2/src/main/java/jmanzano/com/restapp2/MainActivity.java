package jmanzano.com.restapp2;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RESTfulService.startServer(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RESTfulService.stopServer(this);
    }
}
