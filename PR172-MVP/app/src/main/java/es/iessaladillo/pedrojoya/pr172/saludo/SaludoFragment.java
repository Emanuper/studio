package es.iessaladillo.pedrojoya.pr172.saludo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.Unbinder;
import es.iessaladillo.pedrojoya.pr172.R;


@SuppressWarnings("WeakerAccess")
public class SaludoFragment extends Fragment implements SaludoContract.View {


    @BindView(R.id.txtNombre)
    EditText txtNombre;
    @BindView(R.id.chkEducado)
    CheckBox chkEducado;
    @BindView(R.id.btnSaludar)
    Button btnSaludar;

    private SaludoContract.Presenter mPresentador;
    private Unbinder mUnbinder;

    public SaludoFragment() {
    }

    public static SaludoFragment newInstance() {
        return new SaludoFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // El fragmento se mantendrá en memoria.
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_saludo, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Se crea el Presentador.
        mPresentador = new SaludoPresenter(SaludoRepository.getInstance(), this);
    }

    @Override
    public void mostrarSaludo(String mensaje) {
        Toast.makeText(getContext(), mensaje, Toast.LENGTH_LONG).show();
    }

    @Override
    public void cambiarTextoModo(boolean educado) {
        chkEducado.setText(educado ? getString(R.string.saludar_educadamente) : getString(
                R.string.saludar_normal));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @OnClick(R.id.btnSaludar)
    public void btnSaludoarOnClick() {
        mPresentador.onSaludar(txtNombre.getText().toString(), chkEducado.isChecked());
    }

    @SuppressWarnings("UnusedParameters")
    @OnCheckedChanged(R.id.chkEducado)
    public void chkEducadoOnCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        mPresentador.onCambiarModoSaludo(isChecked);
    }
}
