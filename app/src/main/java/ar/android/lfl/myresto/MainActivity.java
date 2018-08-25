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
import ar.android.lfl.myresto.modelo.Estado;
import ar.android.lfl.myresto.modelo.PedidoDAOsql;
import ar.android.lfl.myresto.modelo.Pedido;
import ar.android.lfl.myresto.modelo.PedidoDAO;
import ar.android.lfl.myresto.modelo.PedidoDAOMemory;
import ar.android.lfl.myresto.modelo.PedidoDAOsql;
import ar.android.lfl.myresto.modelo.PedidoDaoJson;
import ar.android.lfl.myresto.modelo.ProductoMenu;


public class MainActivity extends AppCompatActivity {
    private Button btnConfirmar,btnAddProducto;
    private EditText txtNombre;
    private EditText txtPedido;
    private TextView tvTotalPedido;
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
    public Double total=0.00;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //pedidoDAO= new PedidoDaoJson(this);
        pedidoDAO = new PedidoDAOsql(this);

        if(pedidoDAO.listarTodos().size()>0){
            pedidoActual=new Pedido();
        }else{pedidoActual=new Pedido(pedidoDAO.listarTodos().size());}

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
        tvTotalPedido = findViewById(R.id.txtTotalPedido);
        tvTotalPedido.setText("$ "+total.toString());


        if(getIntent().getExtras()!=null){
            int idPedidoSeleccionado = getIntent().getExtras().getInt("idPedido",-1);
            if(idPedidoSeleccionado>0){
                this.loadPedido(idPedidoSeleccionado);
            }
        }

        //LOGICA BOTON MENU
        btnAddProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent listaMenu= new Intent(MainActivity.this,DetallePedidoActivity.class);
                startActivityForResult(listaMenu,999);
            }
        });


        //LOGICA BOTON CONFIRMAR
        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean flagActulizar =false;
                if(pedidoDAO.buscarPorId(pedidoActual.getId())!= null) {
                    flagActulizar = true;
                }
                pedidoActual.setEstado(Estado.CONFIRMADO);
                pedidoActual.setNombre(txtNombre.getText().toString());
                pedidoActual.setBebidaXL(cbBebidaXL.isChecked());
                pedidoActual.setIncluyePropina(cbPropina.isChecked());
                pedidoActual.setPermiteCancelar(cbCancelar.isChecked());
                pedidoActual.setEnviarNotificaciones(swtNotificacion.isChecked());
                pedidoActual.setPagoAutomatico(tgPago.isChecked());
                pedidoActual.setEnvioDomicilio(rgTipoPedido.getCheckedRadioButtonId()==R.id.radBtnDelibery);
                Toast.makeText(MainActivity.this,"Pedido creado", Toast.LENGTH_LONG).show();

                if(flagActulizar==true){
                    pedidoDAO.actualizar(pedidoActual);
                }else{
                    pedidoDAO.agregar(pedidoActual);
                }

                Intent intentPedido = new Intent(MainActivity.this,ListaPedidosActivity.class);
                //intentPedido.putExtra("lista", (Parcelable) pedidoDAO);
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
                total =0.00;
                tvTotalPedido.setText("$ "+total.toString());

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
                tvTotalPedido.setText("Total $"+total.toString());
            }
        }
    }
    private void loadPedido(int id){
        pedidoActual= this.pedidoDAO.buscarPorId(id);
        txtNombre.setText(pedidoActual.getNombre());
        cbBebidaXL.setChecked(pedidoActual.isBebidaXL());
        cbPropina.setChecked(pedidoActual.isIncluyePropina());
        swtNotificacion.setChecked(pedidoActual.isEnviarNotificaciones());
        RadioButton rbDelivery = (RadioButton) findViewById(R.id.radBtnDelibery);
        RadioButton rbMesa = (RadioButton) findViewById(R.id.radBtnMesa);
        if(pedidoActual.isEnvioDomicilio()){
            rbDelivery.setChecked(true);
            rbMesa.setChecked(false);
        }else{
            rbDelivery.setChecked(false);
            rbMesa.setChecked(true);
        }

        tgPago.setChecked(pedidoActual.isPagoAutomatico());
        double totalOrden = 0.0;
        for(DetallePedido det : pedidoActual.getItemPedidos()){
            txtPedido.getText().append(det.getProductoPedido().getNombre()+
                    " $"+(det.getProductoPedido().getPrecio()*det.getCantidad())+"\r\n");
            totalOrden += det.getCantidad()*det.getProductoPedido().getPrecio();
        }
        total=totalOrden;
        tvTotalPedido.setText("$"+totalOrden);
    }



}
