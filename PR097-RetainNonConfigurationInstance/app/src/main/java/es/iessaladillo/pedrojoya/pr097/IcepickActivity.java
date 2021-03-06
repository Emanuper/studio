package es.iessaladillo.pedrojoya.pr097;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import icepick.Icepick;
import icepick.State;

public class IcepickActivity extends AppCompatActivity {

    @SuppressWarnings("WeakerAccess")
    @State
    int mContador = 0;

    private TextView lblMarcador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contador);
        // Se restaura el estado usando Icepick.
        Icepick.restoreInstanceState(this, savedInstanceState);
        initVistas();
        actualizarMarcador();
    }

    private void initVistas() {
        lblMarcador = (TextView) findViewById(R.id.lblMarcador);
        Button btnIncrementar = (Button) findViewById(R.id.btnIncrementar);
        btnIncrementar.setOnClickListener(view -> incrementarContador());
    }

    private void incrementarContador() {
        mContador++;
        actualizarMarcador();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Se guarda el estado usando Icepick.
        Icepick.saveInstanceState(this, outState);
    }

    private void actualizarMarcador() {
        lblMarcador.setText(String.valueOf(mContador));
    }

    // Inicia la actividad.
    public static void start(Context context) {
        context.startActivity(new Intent(context, IcepickActivity.class));
    }

}
