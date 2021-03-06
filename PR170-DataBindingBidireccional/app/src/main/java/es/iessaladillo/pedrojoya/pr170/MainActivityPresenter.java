package es.iessaladillo.pedrojoya.pr170;

import android.databinding.DataBindingUtil;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.Random;

import es.iessaladillo.pedrojoya.pr170.databinding.ActivityMainBinding;

@SuppressWarnings("WeakerAccess")
public class MainActivityPresenter {

    private final Random mAleatorio = new Random();

    public void btnSaludarOnClick(View v) {
        // Se obtiene el binding y el viewModel.
        ActivityMainBinding binding = DataBindingUtil.findBinding(v);
        MainActivityViewModel viewModel = binding.getViewModel();
        StringBuilder sb = new StringBuilder(v.getContext().getString(R.string.buenos_dias));
        if (viewModel.isEducado()) {
            sb.append(" ");
            sb.append(v.getContext().getString(R.string.tenga_usted));
            if (!viewModel.getTratamiento().isEmpty()) {
                sb.append(" ");
                sb.append(viewModel.getTratamiento());
            }
        }
        sb.append(" ");
        sb.append(viewModel.getNombre());
        // Se muestra el mensaje
        Snackbar.make(v, sb.toString(), Snackbar.LENGTH_LONG).show();
    }

    // Recibe directamente el viewModel para no tener que obtenerlo (sólo con vinculación de
    // listener).
    @SuppressWarnings("SameReturnValue")
    public boolean btnSaludarOnLongClick(View v) {
        ActivityMainBinding binding = DataBindingUtil.findBinding(v);
        MainActivityViewModel viewModel = binding.getViewModel();
        Toast.makeText(v.getContext(), viewModel.getNombre(), Toast.LENGTH_SHORT).show();
        return true;
    }

    public void imgImagenOnClick(View v) {
        Picasso.with(v.getContext()).load(
                v.getContext().getString(R.string.lorempixel) + (mAleatorio.nextInt(9) + 1)).into(
                (ImageView) v);
    }


}
