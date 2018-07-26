package ar.android.lfl.myresto;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ar.android.lfl.myresto.modelo.DetallePedido;
import ar.android.lfl.myresto.modelo.Pedido;

public class PedidoAdapter extends ArrayAdapter<Pedido> {
    private Context context;
    private List<Pedido> listaPedidos;

    public PedidoAdapter(Context ctx, List<Pedido> lista){
        super(ctx, 0 , lista);
        this.context = ctx;
        this.listaPedidos = lista;
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View fila =convertView;
        if(fila==null) {
            fila =  LayoutInflater.from(this.context).inflate(R.layout.fila_pedido, parent, false);
        }
        Pedido pedido = this.listaPedidos.get(position);
        double monto = 0.0;
        for(DetallePedido det : pedido.getItemPedidos()){
            monto += det.getCantidad()*det.getProductoPedido().getPrecio() ;
        }
        TextView tvNombre = (TextView) fila.findViewById(R.id.fila_PedidoNombre);
        TextView tvCantItems = (TextView) fila.findViewById(R.id.filaPedidoCantItems);
        TextView tvMonto = (TextView) fila.findViewById(R.id.filaPedidoMonto);
        TextView tvEstado = (TextView) fila.findViewById(R.id.filaPedidoEstado);
        ImageView imgPropina = (ImageView) fila.findViewById(R.id.filaPedidoImgPropina);
        Button btnVer = (Button) fila.findViewById(R.id.filaPedidoBtnVerPedido);
        Button btnEstado = (Button) fila.findViewById(R.id.filaPedidoBtnCambiarEstado);
        tvNombre.setText("Cliente : "+pedido.getNombre());
        tvCantItems.setText("Items: "+pedido.getItemPedidos().size());
        tvMonto.setText("$"+monto);
        tvEstado.setText(pedido.getEstado().toString());
        if(pedido.isIncluyePropina()){
            imgPropina.setImageResource(android.R.drawable.btn_star_big_on);
        }else{
            imgPropina.setImageResource(android.R.drawable.btn_star_big_off);
        }
        switch (pedido.getEstado()){
            case CONFIRMADO:
                btnEstado.setText("Preparar");
                btnEstado.setEnabled(true);
                break;
            case EN_PREPARACION:
                btnEstado.setText("Preparando....");
                btnEstado.setEnabled(false);
                break;
            case EN_ENVIO:
                btnEstado.setText("Entregar");
                btnEstado.setEnabled(true);
                break;
            default:
                btnEstado.setText("Finalizado");
                btnEstado.setEnabled(false);
                break;
        }

        btnVer.setTag(pedido);
        btnVer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int idPedido = ((Pedido) v.getTag()).getId();
                Intent intentDetalle = new Intent(context,MainActivity.class);
                intentDetalle.putExtra("idPedido",idPedido);
                // intentDetalle tiene que pedir que se muestre MainActivity
                // y pone como extra el idPedido del pedido del botón actual
                context.startActivity(intentDetalle);
            }
        });

        btnEstado.setTag(pedido);
        btnEstado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pedido p = (Pedido) v.getTag();
                switch (p.getEstado()){
                    case CONFIRMADO:
                        p.preparar();
                        break;
                    case EN_ENVIO:
                        p.entregar();
                        break;
                }
            }
        });
        return fila;
    }

}
