package ar.android.lfl.myresto;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import ar.android.lfl.myresto.modelo.PedidoDAO;
import ar.android.lfl.myresto.modelo.PedidoDAOMemory;

public class ListaPedidosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pedidos);
        
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
