package es.iessaladillo.pedrojoya.pr157;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class DetalleActivity extends AppCompatActivity {

    private static final String EXTRA_CONCEPTO = "extra_concepto";

    private Concepto mConcepto;
    private ImageView imgFotoDetalle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);
        if (getIntent() != null && getIntent().hasExtra(EXTRA_CONCEPTO)) {
            mConcepto = getIntent().getParcelableExtra(EXTRA_CONCEPTO);
        }
        initVistas();
    }

    private void initVistas() {
        imgFotoDetalle = (ImageView) findViewById(R.id.imgFotoDetalle);
        if (mConcepto != null) {
            imgFotoDetalle.setImageResource(mConcepto.getFotoResId());
        }
    }

    // Inicia la actividad.
    public static void start(Context contexto, Concepto concepto, Bundle opciones)  {
        Intent intent = new Intent(contexto, DetalleActivity.class);
        intent.putExtra(EXTRA_CONCEPTO, concepto);
        contexto.startActivity(intent, opciones);
    }
}
