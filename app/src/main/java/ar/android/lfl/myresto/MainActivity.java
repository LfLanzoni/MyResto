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
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Switch;
import android.widget.ToggleButton;

import ar.android.lfl.myresto.modelo.DetallePedido;
import ar.android.lfl.myresto.modelo.Pedido;
import ar.android.lfl.myresto.modelo.PedidoDAO;
import ar.android.lfl.myresto.modelo.PedidoDAOMemory;
import ar.android.lfl.myresto.modelo.ProductoMenu;


public class MainActivity extends AppCompatActivity {
    private Button btnConfirmar,btnAddProducto;
    private EditText txtNombre;
    private EditText txtPedido;
    private TextView txtTotalPedido;
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
    public Double total=0.0;
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
        txtTotalPedido = findViewById(R.id.txtTotalPedido);
        btnAddProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent listaMenu= new Intent(MainActivity.this,DetallePedidoActivity.class);
                startActivityForResult(listaMenu,999);
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

                pedidoDAO.agregar(pedidoActual);
                Intent intentPedido = new Intent(MainActivity.this,ListaPedidosActivity.class);
                intentPedido.putExtra("lista", (Parcelable) pedidoDAO);
                startActivity(intentPedido);
                //reset pedido Actual
                pedidoActual = new Pedido();
                // limpiar  pantalla
                txtNombre.setText("");
                txtPedido.setText("");
                cbBebidaXL.setChecked(false);
                cbCancelar.setChecked(false);
                cbPropina.setChecked(false);
                swtNotificacion.setChecked(false);
                rbDelibery.setChecked(false);
                rbResarvaMesa.setChecked(false);
                tgPago.setChecked(false);
                txtTotalPedido.setText("$ 0");
            }

        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(requestCode==999){

                int cantidad = data.getIntExtra("cantidad",0);
                ProductoMenu prod= (ProductoMenu)
                        data.getParcelableExtra("producto");
                DetallePedido detalle = new DetallePedido();
                detalle.setCantidad(cantidad);
                detalle.setProductoPedido(prod);
                pedidoActual.addItemDetalle(detalle);
                txtPedido.getText().append(prod.getNombre()+ " $"+ (prod.getPrecio()*cantidad)+"\r\n");
                total = total+(prod.getPrecio()*cantidad);
                txtTotalPedido.setText("Total $"+total.toString());
            }
        }
    }

}
