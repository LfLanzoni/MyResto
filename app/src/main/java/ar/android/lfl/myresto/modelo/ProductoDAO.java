package ar.android.lfl.myresto.modelo;

import java.util.List;

public interface ProductoDAO {
    public List<ProductoMenu> listarMenu();
    public void cargarDatos(String[] datos);
}
