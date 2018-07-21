package ar.android.lfl.myresto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import ar.android.lfl.myresto.modelo.ProductoDAO;
import ar.android.lfl.myresto.modelo.ProductoDAOMemory;
import ar.android.lfl.myresto.modelo.ProductoMenu;

public class DetallePedidoActivity extends AppCompatActivity {
    private ProductoDAO productoDao;
    private ArrayAdapter<ProductoMenu> adaptadorLista;
    private ListView listaMenu;
    private ProductoMenu productoElegido;
    private Integer cantidadProducto;
    private TextView txtCantidad;
    private Button btnMenosProducto,btnMasProducto,btnAgregarProducto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_pedido);
        listaMenu =(ListView) findViewById(R.id.listaProductos);
        txtCantidad =(TextView) findViewById(R.id.detPedProductoCantidad);
        btnMenosProducto = (Button) findViewById(R.id.btnMenosProducto);
        btnMasProducto = (Button) findViewById(R.id.btnMasProducto);
        btnAgregarProducto = (Button) findViewById(R.id.btnAddProducto);
        cantidadProducto=0;
        txtCantidad.setText(cantidadProducto.toString());
        listaMenu.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        productoDao = new ProductoDAOMemory();
        String[] listaProductos =getResources().getStringArray(R.array.listaProductos);
        productoDao.cargarDatos(listaProductos);
        adaptadorLista = new ArrayAdapter<ProductoMenu>(DetallePedidoActivity.this,android.R.layout.simple_list_item_single_choice,productoDao.listarMenu());
        listaMenu.setAdapter(adaptadorLista);
        listaMenu.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent,
                                            View view, int position, long id) {
                         productoElegido= (ProductoMenu) listaMenu.getItemAtPosition(position);

                    }
                });
        btnAgregarProducto.setEnabled(false);
        btnMenosProducto.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(cantidadProducto>0){
                            cantidadProducto--;
                            txtCantidad.setText(cantidadProducto.toString());
                        }
                        if(cantidadProducto==0){
                            btnAgregarProducto.setEnabled(false);
                        }
                        else{btnAgregarProducto.setEnabled(true);}
                    }
                });

        btnMasProducto.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cantidadProducto++;
                        txtCantidad.setText(cantidadProducto.toString());
                        btnAgregarProducto.setEnabled(true);
                    }});

            btnAgregarProducto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.putExtra("producto", productoElegido);
                    intent.putExtra("cantidad", cantidadProducto);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });

    }
}