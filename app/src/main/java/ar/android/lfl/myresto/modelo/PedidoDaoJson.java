package ar.android.lfl.myresto.modelo;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class PedidoDaoJson implements PedidoDAO,Parcelable{
    private Context ctx;
    private final String FILENAME = "pedido";
    private static List<Pedido> REPOSITORIO_PEDIDOS = new ArrayList<>();


    public PedidoDaoJson (Context context){
        ctx=context;
        cargarLista();
    }

    private void guardarLista(){
        JSONArray arregloPedidos = new JSONArray();
        for(Pedido p : this.REPOSITORIO_PEDIDOS){
            try {
                arregloPedidos.put(p.toJson());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        escribirEnArchivo(arregloPedidos);
    }

    @Override
    public void agregar(Pedido pedido) {
         if(REPOSITORIO_PEDIDOS.size()==0){
           REPOSITORIO_PEDIDOS.add(pedido);
        }
        else {
            if (this.buscarPorId(pedido.getId()) == null) {
                REPOSITORIO_PEDIDOS.add(pedido);
            } else {
                actualizar(pedido);
            }
        }
            guardarLista();
    }

    @Override
    public void actualizar(Pedido pedido){
        int indice = REPOSITORIO_PEDIDOS.indexOf(pedido);
        REPOSITORIO_PEDIDOS.set(indice,pedido);
    }

    @Override
    public void eliminar(Pedido pedido){
        REPOSITORIO_PEDIDOS.remove(pedido);
        guardarLista();
    }

    @Override
    public List<Pedido> listarTodos(){
        return REPOSITORIO_PEDIDOS;
    }

    @Override
    public Pedido buscarPorId(Integer id){
        for (Pedido unPedido:REPOSITORIO_PEDIDOS) {
            if(unPedido.getId().equals(id)){
                return unPedido;
            }
        }
        return null;
    }

    private void escribirEnArchivo(JSONArray arregloPedidos){
        FileOutputStream fos = null;
        try {
            fos = ctx.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            Log.d("ARCHIVOCREE",arregloPedidos.toString());
            fos.write(arregloPedidos.toString().getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cargarLista(){
        try {
            JSONArray datos = (JSONArray) new
                    JSONTokener(this.leerArchivo()).nextValue();
            REPOSITORIO_PEDIDOS.clear();
            for(int i=0;i<datos.length();i++){
                JSONObject fila = datos.getJSONObject(i);
                Pedido unPedido = new Pedido();
                unPedido.loadFromJson(fila);
                REPOSITORIO_PEDIDOS.add(unPedido);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String leerArchivo(){
        FileInputStream fis = null;
        StringBuilder sb = new StringBuilder();
        try {
            fis = ctx.openFileInput(FILENAME);
            InputStreamReader inputStreamReader = new
                    InputStreamReader(fis);
            BufferedReader bufferedReader = new
                    BufferedReader(inputStreamReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        ///dest.writeParcelable(this.ctx, flags);
        //dest.writeString(this.FILENAME);
    }

    protected PedidoDaoJson(Parcel in) {
        //this.ctx = in.readParcelable(Context.class.getClassLoader());
      //  this.FILENAME = in.readString();
    }

    public static final Creator<PedidoDaoJson> CREATOR = new Creator<PedidoDaoJson>() {
        @Override
        public PedidoDaoJson createFromParcel(Parcel source) {
            return new PedidoDaoJson(source);
        }

        @Override
        public PedidoDaoJson[] newArray(int size) {
            return new PedidoDaoJson[size];
        }
    };
}
