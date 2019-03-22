package Clases;

import java.io.Serializable;

public class Informacion implements Serializable {

    private String Nombre, Mensaje;

    public Informacion() {
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getMensaje() {
        return Mensaje;
    }

    public void setMensaje(String mensaje) {
        Mensaje = mensaje;
    }
}

