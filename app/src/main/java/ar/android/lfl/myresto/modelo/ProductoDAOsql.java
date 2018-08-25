package ar.android.lfl.myresto.modelo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class ProductoDAOsql implements ProductoDAO {
    private List<ProductoMenu> listaProductos;
    private Boolean flagListaCargada;


    public ProductoDAOsql(Context ctx) {
        DatabaseManager.initializeInstance(new MyRestoOpenHelper(ctx));
        flagListaCargada = false;
        listaProductos = new ArrayList<>();
    }


    @Override
    public List<ProductoMenu> listarMenu() {
        if(!flagListaCargada) this.cargarDatos(null);
        return listaProductos;
    }

    @Override
    public void cargarDatos(String[] datos) {
        //CARGAR EL ARREGLO
        //Obtenemos instancia de la db
        SQLiteDatabase dbConn = DatabaseManager.getInstance().openDatabase();
        Cursor cursor = dbConn.rawQuery("SELECT _id,NOMBRE,PRECIO from PRODUCTO ",null);
        while (cursor.moveToNext()){
            ProductoMenu aux = new ProductoMenu();
            aux.setPrecio(cursor.getDouble(cursor.getColumnIndex("PRECIO")));
            aux.setNombre(cursor.getString(cursor.getColumnIndex("NOMBRE")));
            aux.setId(cursor.getInt(cursor.getColumnIndex("_id")));
            listaProductos.add(aux);
        }
        //TENER EN CUENTA QUE LOS NOMBRES DE COLUMNA SON CASE SENSITIVE
        //(sensibles a mayúsculas y minúsculas
        //Cerramos la instancia de la db
        DatabaseManager.getInstance().closeDatabase();

        this.flagListaCargada=true;
    }
}
