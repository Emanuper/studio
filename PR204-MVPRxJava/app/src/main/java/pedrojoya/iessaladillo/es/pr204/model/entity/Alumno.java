package pedrojoya.iessaladillo.es.pr204.model.entity;

public class Alumno {

    private final String nombre;
    private final String direccion;
    private final String urlFoto;

    public Alumno(String nombre, String direccion, String urlFoto) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.urlFoto = urlFoto;

    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getNombre() {
        return nombre;
    }

}
