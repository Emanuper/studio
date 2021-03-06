package es.iessaladillo.pedrojoya.pr111;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


@SuppressWarnings({"WeakerAccess", "unused", "CanBeFinal"})
public class AlumnoActivity extends AppCompatActivity {

    public static final String EXTRA_ALUMNO = "tarea";
    @BindView(R.id.txtNombre)
    EditText txtNombre;
    @BindView(R.id.spnCurso)
    Spinner spnCurso;
    @BindView(R.id.txtTelefono)
    EditText txtTelefono;
    @BindView(R.id.txtDireccion)
    EditText txtDireccion;
    @BindView(R.id.btnGuardar)
    FloatingActionButton btnGuardar;
    @BindView(R.id.lblNombre)
    TextView lblNombre;
    @BindView(R.id.lblCurso)
    TextView lblCurso;
    @BindView(R.id.lblTelefono)
    TextView lblTelefono;
    @BindView(R.id.lblDireccion)
    TextView lblDireccion;

    private Alumno mAlumno;
    private ArrayAdapter<CharSequence> adaptadorCursos;
    private Random mAleatorio;

    // Al crear la actividad.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumno);
        ButterKnife.bind(this);
        // Se obtiene el alumno enviado como extra (si lo hay).
        if (getIntent() != null && getIntent().hasExtra(EXTRA_ALUMNO)) {
            mAlumno = getIntent().getParcelableExtra(EXTRA_ALUMNO);
            setTitle(R.string.modificar_alumno);
        } else {
            setTitle(R.string.agregar_alumno);
        }
        // Se carga el spinner de cursos.
        cargarCursos();
        // Se obtienen e inicializan las vistas.
        initVistas();
        mAleatorio = new Random();
    }

    // Obtiene e inicializa las vistas.
    private void initVistas() {
        // Si tenemos alumno que mostrar, la mostramos.
        if (mAlumno != null) {
            alumnoToVistas();
        }
        // Se cambia el color del TextView dependiendo de si el EditText
        // correspondiente tiene el foco o no.
        txtNombre.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            // Al cambiar el foco.
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                setColorSegunFoco(lblNombre, hasFocus);
            }

        });
        txtTelefono.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            // Al cambiar el foco.
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                setColorSegunFoco(lblTelefono, hasFocus);
            }

        });
        txtDireccion.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            // Al cambiar el foco.
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                setColorSegunFoco(lblDireccion, hasFocus);
            }

        });
        spnCurso.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            // Al cambiar el foco.
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                setColorSegunFoco(lblCurso, hasFocus);
            }

        });
        // Comprobaciones iniciales.
        setColorSegunFoco(lblNombre, true);
    }

    // Agrega el alumno a la base de datos.
    private void agregar() {
        if (!TextUtils.isEmpty(txtNombre.getText().toString()) && !TextUtils.isEmpty(
                txtTelefono.getText().toString())) {
            mAlumno = new Alumno();
            mAlumno.setAvatar(getRandomAvatarUrl());
            vistasToAlumno();
            mAlumno.save();
            finish();
        } else {
            Toast.makeText(this, "Nombre y teléfono son obligatorios", Toast.LENGTH_SHORT).show();
        }
    }

    private void actualizar() {
        if (!TextUtils.isEmpty(txtNombre.getText().toString()) && !TextUtils.isEmpty(
                txtTelefono.getText().toString())) {
            vistasToAlumno();
            mAlumno.save();
            finish();
        } else {
            Toast.makeText(this, "Nombre y teléfono son obligatorios", Toast.LENGTH_SHORT).show();
        }
    }

    // Establece el color y estilo del TextView dependiendo de si el
    // EditText correspondiente tiene el foco o no.
    private void setColorSegunFoco(TextView lbl, boolean hasFocus) {
        if (hasFocus) {
            lbl.setTextColor(ContextCompat.getColor(this, R.color.accent));
            lbl.setTypeface(Typeface.DEFAULT_BOLD);
        } else {
            lbl.setTextColor(ContextCompat.getColor(this, R.color.accent_light));
            lbl.setTypeface(Typeface.DEFAULT);
        }
    }

    // Cuando se pulsa sobre Guardar.
    @OnClick(R.id.btnGuardar)
    public void guardar() {
        if (mAlumno == null) {
            agregar();
        } else {
            actualizar();
        }
    }

    // Muestra los datos del objeto Alumno en las vistas.
    private void alumnoToVistas() {
        txtNombre.setText(mAlumno.getNombre());
        txtTelefono.setText(mAlumno.getTelefono());
        txtDireccion.setText(mAlumno.getDireccion());
        spnCurso.setSelection(adaptadorCursos.getPosition(mAlumno.getCurso()), true);
    }

    // Carga los cursos en el spinner.
    private void cargarCursos() {
        // Se crea un ArrayAdapter para el spinner, que use layouts estándar
        // tanto para cuando no está desplegado como para cuando sí lo está. La
        // fuente de datos para el adaptador es un array de constantes de
        // cadena.
        adaptadorCursos = ArrayAdapter.createFromResource(this, R.array.cursos,
                android.R.layout.simple_spinner_item);
        adaptadorCursos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCurso.setAdapter(adaptadorCursos);
    }

    // Llena el objeto Alumno con los datos de las vistas.
    private void vistasToAlumno() {
        mAlumno.setNombre(txtNombre.getText().toString());
        mAlumno.setTelefono(txtTelefono.getText().toString());
        mAlumno.setDireccion(txtDireccion.getText().toString());
        mAlumno.setCurso((String) spnCurso.getSelectedItem());
    }

    // Retorna una url aleatoria correspondiente a una imagen para el avatar.
    private String getRandomAvatarUrl() {
        final String baseUrl = "http://lorempixel.com/100/100/";
        final String[] tipos = {"abstract", "animals", "business", "cats", "city", "food",
                                "night", "life", "fashion", "people", "nature", "sports",
                                "technics", "transport"};
        return baseUrl + tipos[mAleatorio.nextInt(tipos.length)] + "/" + (mAleatorio.nextInt(10)
                + 1) + "/";
    }

}



