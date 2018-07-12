package ar.android.lfl.myresto;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import ar.android.lfl.myresto.modelo.Pedido;
import ar.android.lfl.myresto.modelo.PedidoDAO;
import ar.android.lfl.myresto.modelo.PedidoDAOMemory;

public class ListaPedidosActivity extends AppCompatActivity {
    PedidoDAO pedidoDAO;
    ArrayAdapter<Pedido> adaptadorPedido;
    Button btnNuevoPedido;
    ListView listaPedidos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pedidos);
        btnNuevoPedido = (Button) findViewById(R.id.btnNuevosPedidos);
        listaPedidos =(ListView) findViewById(R.id.listaPedidos);

        final Intent intentPedido = getIntent();
        pedidoDAO = new PedidoDAOMemory();
        pedidoDAO=intentPedido.getParcelableExtra("lista");
        Log.d("LISTA 2",pedidoDAO.listarTodos().toString());

        this.adaptadorPedido = new ArrayAdapter<>(ListaPedidosActivity.this,android.R.layout.simple_list_item_1,pedidoDAO.listarTodos());
        this.listaPedidos.setAdapter(this.adaptadorPedido);
        this.btnNuevoPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // setResult(RESULT_OK,intentPedido);
                finish();
            }
        });
    }


}
