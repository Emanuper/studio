package es.iessaladillo.pedrojoya.pr092;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupToolbar();
        // Se carga el fragmento principal si no venimos de estado anterior.
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new MainFragment())
                    .commit();
        }
    }

    private void setupToolbar() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
    }

    // Fragmento principal
    public static class MainFragment extends Fragment implements OnRefreshListener {

        private static final long MILISEGUNDOS_ESPERA = 2000;
        private SwipeRefreshLayout swlPanel;
        private final SimpleDateFormat mFormateador = new SimpleDateFormat("HH:mm:ss",
                Locale.getDefault());
        private RecyclerView lstLista;
        private ListaAdapter mAdaptador;
        private LinearLayoutManager mLayoutManager;
        private ArrayList<String> mDatos;

        public MainFragment() {
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setRetainInstance(true);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_main, container, false);
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            setHasOptionsMenu(true);
            if (mDatos == null) {
                mDatos = getDatosIniciales();
            }
            setupPanel();
            setupRecyclerView();
            super.onActivityCreated(savedInstanceState);
        }

        // Retorna ArrayList con datos iniciales.
        private ArrayList<String> getDatosIniciales() {
            ArrayList<String> datos = new ArrayList<>();
            datos.add(mFormateador.format(new Date()));
            return datos;
        }

        private void setupRecyclerView() {
            if (getView() != null) {
                lstLista = (RecyclerView) getView().findViewById(R.id.lstLista);
            }
            lstLista.setHasFixedSize(true);
            mAdaptador = new ListaAdapter(mDatos);
            lstLista.setAdapter(mAdaptador);
            mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,
                    false);
            lstLista.setLayoutManager(mLayoutManager);
            lstLista.addItemDecoration(
                    new android.support.v7.widget.DividerItemDecoration(getActivity(),
                            LinearLayoutManager.VERTICAL));
            lstLista.setItemAnimator(new DefaultItemAnimator());
        }

        // Configura el SwipeRefreshLayout.
        private void setupPanel() {
            if (getView() != null) {
                swlPanel = (SwipeRefreshLayout) getView().findViewById(R.id.swlPanel);
                // El fragmento actuará como listener del gesto de swipe.
                swlPanel.setOnRefreshListener(this);
                // Se establecen los colores que debe usar la animación.
                swlPanel.setColorSchemeResources(android.R.color.holo_blue_bright,
                        android.R.color.holo_green_light, android.R.color.holo_orange_light,
                        android.R.color.holo_red_light);
            }
        }

        // Cuando el usuario hace swipe to refresh.
        @Override
        public void onRefresh() {
            refrescar();
        }

        private void refrescar() {
            // Se activa la animación.
            swlPanel.setRefreshing(true);
            // Se simula que la tarea de carga tarda unos segundos.
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Se añaden los datos al adaptador.
                    mAdaptador.addItem(mFormateador.format(new Date()));
                    // Se cancela la animación del panel.
                    swlPanel.setRefreshing(false);
                }
            }, MILISEGUNDOS_ESPERA);
        }

    }

}
