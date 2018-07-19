package ar.android.lfl.myresto;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import ar.android.lfl.myresto.modelo.ProductoDAO;
import ar.android.lfl.myresto.modelo.ProductoDAOMemory;
import ar.android.lfl.myresto.modelo.ProductoMenu;

public class DetallePedidoActivity extends AppCompatActivity {
    private ProductoDAO productoDao;
    private ArrayAdapter<ProductoMenu> adaptadorLista;
    private ListView listaProductoMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_pedido);
        listaProductoMenu =(ListView) findViewById(R.id.listaProductos);
        Log.d("LISTA DETALLE","ENTRE");
        productoDao = new ProductoDAOMemory();
        String[] listaProductos =getResources().getStringArray(R.array.listaProductos);
        productoDao.cargarDatos(listaProductos);
        adaptadorLista = new ArrayAdapter<ProductoMenu>(DetallePedidoActivity.this,android.R.layout.simple_list_item_single_choice,productoDao.listarMenu());
        listaProductoMenu.setAdapter(adaptadorLista);

    }
}