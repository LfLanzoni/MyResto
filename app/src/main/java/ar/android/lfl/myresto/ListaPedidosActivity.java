package ar.android.lfl.myresto;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import ar.android.lfl.myresto.modelo.Pedido;
import ar.android.lfl.myresto.modelo.PedidoDAO;
import ar.android.lfl.myresto.modelo.PedidoDAOMemory;
import ar.android.lfl.myresto.modelo.PedidoDaoJson;

public class ListaPedidosActivity extends AppCompatActivity {
    PedidoDAO pedidoDAO;
    PedidoAdapter adaptadorPedido;
    Button btnNuevoPedido;
    ListView listaPedidos;
    String CHANNEL_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pedidos);
        this.createNotificationChannel();
        btnNuevoPedido = (Button) findViewById(R.id.btnNuevosPedidos);
        listaPedidos = (ListView) findViewById(R.id.listaPedidos);
        pedidoDAO = new PedidoDaoJson(this);
        this.adaptadorPedido = new PedidoAdapter(this, pedidoDAO.listarTodos());
        this.listaPedidos.setAdapter(this.adaptadorPedido);

        //LOGICA BOTON NUEVO PEDIDO
        this.btnNuevoPedido.setOnClickListener(new View.OnClickListener() {
             @Override
            public void onClick(View view) {
                 Intent intent = new Intent(ListaPedidosActivity.this,MainActivity.class);
                 startActivity(intent);
             }
        });

        //LOGICA BORRAR
        this.listaPedidos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                int itemPosition = position;
                Pedido itemValue = (Pedido) listaPedidos.getItemAtPosition(position);
                pedidoDAO.eliminar(itemValue);
                adaptadorPedido.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(), "Borrar elemento de posicion :" + itemPosition +
                        "  id: " + itemValue.getId() + " nombre: " + itemValue.getNombre(), Toast.LENGTH_LONG).show();
                return false;
            }
        });

    }

    //CANAL DE NOTIFICACIONES
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "canal1",
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("descripcion");
            NotificationManager notificationManager
                    = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

        }

    }
}
