package es.iessaladillo.pedrojoya.pr027.modelos;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

import es.iessaladillo.pedrojoya.pr027.bd.DbContract;

public class Alumno implements Parcelable {

    private long id;
    private String nombre;
    private String telefono;
    private String curso;
    private String direccion;

    public Alumno() {
        this.id = 0;
        this.nombre = null;
        this.telefono = null;
        this.curso = null;
        this.direccion = null;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    // Retorna un ContentValues con los datos del alumno.
    public ContentValues toContentValues() {
        ContentValues valores = new ContentValues();
        valores.put(DbContract.Alumno.NOMBRE, nombre);
        valores.put(DbContract.Alumno.CURSO, curso);
        valores.put(DbContract.Alumno.TELEFONO, telefono);
        valores.put(DbContract.Alumno.DIRECCION, direccion);
        return valores;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.nombre);
        dest.writeString(this.telefono);
        dest.writeString(this.curso);
        dest.writeString(this.direccion);
    }

    @SuppressWarnings("WeakerAccess")
    protected Alumno(Parcel in) {
        this.id = in.readLong();
        this.nombre = in.readString();
        this.telefono = in.readString();
        this.curso = in.readString();
        this.direccion = in.readString();
    }

    public static final Parcelable.Creator<Alumno> CREATOR = new Parcelable.Creator<Alumno>() {
        public Alumno createFromParcel(Parcel source) {
            return new Alumno(source);
        }

        public Alumno[] newArray(int size) {
            return new Alumno[size];
        }
    };
}
