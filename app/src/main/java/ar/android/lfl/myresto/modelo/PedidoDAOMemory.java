package ar.android.lfl.myresto.modelo;


import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;



public class PedidoDAOMemory implements PedidoDAO, Parcelable {
    private static List<Pedido> REPOSITORIO_PEDIDOS = new ArrayList<>();

    @Override
    public void agregar(Pedido pedido){
        REPOSITORIO_PEDIDOS.add(pedido);
    }

    @Override
    public List<Pedido> listarTodos(){
        return REPOSITORIO_PEDIDOS;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public PedidoDAOMemory() {
    }

    protected PedidoDAOMemory(Parcel in) {
    }

    public static final Parcelable.Creator<PedidoDAOMemory> CREATOR = new Parcelable.Creator<PedidoDAOMemory>() {
        @Override
        public PedidoDAOMemory createFromParcel(Parcel source) {
            return new PedidoDAOMemory(source);
        }

        @Override
        public PedidoDAOMemory[] newArray(int size) {
            return new PedidoDAOMemory[size];
        }
    };

    @Override
    public void eliminar(Pedido pedido){
        REPOSITORIO_PEDIDOS.remove(pedido);
    }

    @Override
    public Pedido buscarPorId(Integer id) {
        for(Pedido unPedido: REPOSITORIO_PEDIDOS){
            if(unPedido.getId().equals(id)) return unPedido;
        }
        return null;
    }

    @Override
    public void actualizar(Pedido p){
        Boolean flag=false;
        flag = null!=(this.buscarPorId(p.getId()));
        if(flag){
            int index=REPOSITORIO_PEDIDOS.indexOf(p);
            REPOSITORIO_PEDIDOS.set(index,p);
        }

    }

}

