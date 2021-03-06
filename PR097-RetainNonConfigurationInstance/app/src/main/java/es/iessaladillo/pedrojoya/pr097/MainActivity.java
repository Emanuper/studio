package es.iessaladillo.pedrojoya.pr097;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initVistas();
    }

    private void initVistas() {
        Button btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(view -> SaveActivity.start(MainActivity.this));
        Button btnRetain = (Button) findViewById(R.id.btnRetain);
        btnRetain.setOnClickListener(view -> RetainActivity.start(MainActivity.this));
        Button btnIcepick = (Button) findViewById(R.id.btnIcepick);
        btnIcepick.setOnClickListener(view -> IcepickActivity.start(MainActivity.this));
        Button btnStarter = (Button) findViewById(R.id.btnStarter);
        btnStarter.setOnClickListener(view -> StarterActivity.start(MainActivity.this));
    }

}
