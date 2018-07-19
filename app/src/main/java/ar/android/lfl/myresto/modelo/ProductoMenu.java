package ar.android.lfl.myresto.modelo;

public class ProductoMenu {
    private int id;
    private String nombre;
    private Double precio;

    public ProductoMenu() {
        this.id = 0;
        this.nombre = "nombre";
        this.precio = 0.0;
    }

    public ProductoMenu(int id, String nombre, Double precio) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return nombre+"-"+precio;
    }
}
