package ar.android.lfl.myresto;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.Switch;
import android.widget.ToggleButton;

import ar.android.lfl.myresto.modelo.Pedido;
import ar.android.lfl.myresto.modelo.PedidoDAO;
import ar.android.lfl.myresto.modelo.PedidoDAOMemory;


public class MainActivity extends AppCompatActivity {
    private Button btnConfirmar,btnAddProducto;
    private EditText txtNombre;
    private EditText txtPedido;
    private Pedido pedidoActual;
    private CheckBox cbPropina;
    private CheckBox cbCancelar;
    private CheckBox cbBebidaXL;
    private Switch swtNotificacion;
    private RadioGroup rgTipoPedido;
    private RadioButton rbDelibery;
    private RadioButton rbResarvaMesa;
    private ToggleButton tgPago;
    private PedidoDAO pedidoDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pedidoActual = new Pedido();
        pedidoDAO= new PedidoDAOMemory();
        txtNombre = findViewById(R.id.txtNombreCliente);
        txtPedido = findViewById(R.id.txtDetallePedido);
        btnConfirmar = findViewById(R.id.btnConfirmar);
        cbPropina = findViewById(R.id.cbIncluirPropina);
        cbBebidaXL = findViewById(R.id.cbBebidaXL);
        cbCancelar = findViewById(R.id.cbPermitirCancelar);
        swtNotificacion = findViewById(R.id.swtNotificaciones);
        rgTipoPedido = findViewById(R.id.rgTipoPedido);
        rbDelibery = findViewById(R.id.radBtnDelibery);
        rbResarvaMesa = findViewById(R.id.radBtnMesa);
        tgPago = findViewById(R.id.tbFormaPago);

        btnAddProducto = (Button) findViewById(R.id.btnAddProducto);

        btnAddProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent listaMenu= new
                        Intent(MainActivity.this,DetallePedidoActivity.class);
                startActivity(listaMenu);
            }
        });
        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pedidoActual.setNombre(txtNombre.getText().toString());
                pedidoActual.setBebidaXL(cbBebidaXL.isChecked());
                pedidoActual.setIncluyePropina(cbPropina.isChecked());
                pedidoActual.setPermiteCancelar(cbCancelar.isChecked());
                pedidoActual.setEnviarNotificaciones(swtNotificacion.isChecked());
                pedidoActual.setPagoAutomatico(tgPago.isChecked());
                if (rgTipoPedido.getCheckedRadioButtonId() == rbDelibery.getId()){
                    pedidoActual.setEnvioDomicilio(true);
                }else{
                    pedidoActual.setEnvioDomicilio(false);
                }
                Toast.makeText(MainActivity.this,"Pedido creado", Toast.LENGTH_LONG).show();

                //Agregamos el pedido a pedidoDAO

                pedidoDAO.agregar(pedidoActual);
                Intent intentPedido = new Intent(MainActivity.this,ListaPedidosActivity.class);
                intentPedido.putExtra("lista", (Parcelable) pedidoDAO);
                startActivity(intentPedido);
                //reset pedido Actual
                // limpiar la variable para poder cargar un nuevo pedido
                pedidoActual = new Pedido();
                // limpiar el edit text en la pantalla
                txtNombre.setText("");
                txtPedido.setText("");
                //limpiar Checkbox
                cbBebidaXL.setChecked(false);
                cbCancelar.setChecked(false);
                cbPropina.setChecked(false);
                //limpiar Switch
                swtNotificacion.setChecked(false);
                //limpiar Radio Buttons
                rbDelibery.setChecked(false);
                rbResarvaMesa.setChecked(false);
                //limpiar Toggle Button
                tgPago.setChecked(false);
            }

        });

    }


}
