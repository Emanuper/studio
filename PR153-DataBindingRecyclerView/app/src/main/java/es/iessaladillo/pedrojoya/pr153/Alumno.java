package es.iessaladillo.pedrojoya.pr153;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import es.iessaladillo.pedrojoya.pr153.dbutils.RecyclerBindingAdapter;

// Modelo Alumno.
@SuppressWarnings({"unused", "UnusedParameters"})
public class Alumno extends BaseObservable implements RecyclerBindingAdapter.ViewModel {

    private  String nombre;
    private  String direccion;
    private  String foto;

    public Alumno(String nombre, String direccion, int edad, String foto) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.foto = foto;

    }

    @Bindable
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
        notifyPropertyChanged(es.iessaladillo.pedrojoya.pr153.BR.nombre);
    }

    @Bindable
    public String getFoto() {
        return foto;

    }
    public void setFoto(String foto) {
        this.foto = foto;
        notifyPropertyChanged(es.iessaladillo.pedrojoya.pr153.BR.foto);
    }

    @Bindable
    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
        notifyPropertyChanged(es.iessaladillo.pedrojoya.pr153.BR.direccion);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main_item;
    }
}
