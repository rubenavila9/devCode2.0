package ariel.az.devcode20.models;

public class Fuente {

    String titulos;
    int imagenes;
    int estado;

    public Fuente(String titulos, int imagenes, int estado) {
        this.titulos = titulos;
        this.imagenes = imagenes;
        this.estado = estado;
    }

    public String getTitulos() {
        return titulos;
    }

    public void setTitulos(String titulos) {
        this.titulos = titulos;
    }

    public int getImagenes() {
        return imagenes;
    }

    public void setImagenes(int imagenes) {
        this.imagenes = imagenes;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
}
