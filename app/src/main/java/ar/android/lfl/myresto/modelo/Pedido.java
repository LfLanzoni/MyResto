package ar.android.lfl.myresto.modelo;





import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Pedido {
    private Integer id;
    private String nombre,pedido;
    private Boolean envioDomicilio,bebidaXL,permiteCancelar,incluyePropina,enviarNotificaciones,pagoAutomatico;
    private List<DetallePedido> itemPedidos;
    private static int idGenerator = 0;
    private Estado estado;

    public Pedido(){
        this.id = ++Pedido.idGenerator;
        this.itemPedidos = new ArrayList<>();
    }
    public Pedido(Integer nuevoId){
        this.id = nuevoId+2;
        this.itemPedidos = new ArrayList<>();
    }

    @Override
    public String toString() {
        return nombre;
    }

    public List<DetallePedido> getItemPedidos() {
        return itemPedidos;
    }

    public void setItemPedidos(List<DetallePedido> itemPedidos) {
        this.itemPedidos = itemPedidos;
    }

    public void addItemDetalle(DetallePedido prd){
        this.itemPedidos.add(prd);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPedido() {
        return pedido;
    }

    public void setPedido(String pedido) {
        this.pedido = pedido;
    }

    public Boolean getEnvioDomicilio() {
        return envioDomicilio;
    }

    public void setEnvioDomicilio(Boolean envioDomicilio) {
        this.envioDomicilio = envioDomicilio;
    }

    public Boolean getBebidaXL() {
        return bebidaXL;
    }

    public void setBebidaXL(Boolean bebidaXL) {
        this.bebidaXL = bebidaXL;
    }

    public Boolean getPermiteCancelar() {
        return permiteCancelar;
    }

    public void setPermiteCancelar(Boolean permiteCancelar) {
        this.permiteCancelar = permiteCancelar;
    }

    public Boolean getIncluyePropina() {
        return incluyePropina;
    }

    public void setIncluyePropina(Boolean incluyePropina) {
        this.incluyePropina = incluyePropina;
    }

    public Boolean getEnviarNotificaciones() {
        return enviarNotificaciones;
    }

    public void setEnviarNotificaciones(Boolean enviarNotificaciones) {
        this.enviarNotificaciones = enviarNotificaciones;
    }

    public Boolean getPagoAutomatico() {
        return pagoAutomatico;
    }

    public void setPagoAutomatico(Boolean pagoAutomatico) {
        this.pagoAutomatico = pagoAutomatico;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    @Override
    public boolean equals (Object o){
        if(this == o) return true;
        if(o==null || getClass()!= o.getClass()) return false;
        Pedido pedido = (Pedido) o ;
        return Objects.equals(id,pedido.id);
    }

    @Override
    public int hashCode(){
        return Objects.hash(id);
    }

    public Boolean isIncluyePropina(){
        return this.getIncluyePropina();
    }

    public Boolean isBebidaXL(){
        return this.getBebidaXL();
    }

    public Boolean isEnviarNotificaciones(){
        return  this.getEnviarNotificaciones();
    }

    public Boolean isEnvioDomicilio(){
        return this.getEnvioDomicilio();
    }

    public Boolean isPagoAutomatico(){
        return this.getPagoAutomatico();
    }

    public void preparar(){
        if(this.estado==Estado.CONFIRMADO){
            this.estado = Estado.EN_PREPARACION;
        }
    }
    public void enviar(){
        if(this.estado==Estado.EN_PREPARACION){
            this.estado = Estado.EN_ENVIO;
        }
    }
    public void entregar(){
        if(this.estado==Estado.EN_ENVIO){
            this.estado = Estado.ENTREGADO;
        }
    }

    public void loadFromJson(JSONObject fila ){
        try {
            this.setBebidaXL(fila.getBoolean("bebidaXL"));
            this.setId(fila.getInt("id"));
            this.setPagoAutomatico(fila.getBoolean("pagoAutomatico"));
            this.setEnviarNotificaciones(fila.getBoolean("enviarNotificaciones"));
            this.setEnvioDomicilio(fila.getBoolean("envioDomicilio"));
            this.setIncluyePropina(fila.getBoolean("incluyePropina"));
            this.setPermiteCancelar(fila.getBoolean("permiteCancelar"));
            this.setNombre(fila.getString("nombre"));
            this.setEstado(Estado.valueOf(fila.getString("estado")));
            if(fila.getJSONArray("detalle").length()>0){
                this.setItemPedidos(new ArrayList<DetallePedido>());
                JSONArray detallePedido = fila.getJSONArray("detalle");
                for(int j =0;j<detallePedido.length();j++){
                    DetallePedido detalleAux = new DetallePedido();
                    JSONObject filaDetalle = detallePedido.getJSONObject(j);
                    detalleAux.setCantidad(filaDetalle.getInt("cantidad"));
                    detalleAux.setId(filaDetalle.getInt("id"));
                    JSONObject elProducto = filaDetalle.getJSONObject("producto");
                    ProductoMenu prd = new ProductoMenu();
                    prd.setId(elProducto.getInt("id"));
                    prd.setNombre(elProducto.getString("nombre"));
                    prd.setPrecio(elProducto.getDouble("precio"));
                    detalleAux.setProductoPedido(prd);
                    this.addItemDetalle(detalleAux);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public JSONObject toJson()  throws JSONException {
        JSONObject jsonPedido= new JSONObject();
        JSONObject jsonDetalle = new JSONObject();
        JSONArray jsonDetalleArray = new JSONArray();
        JSONObject jsonProducto = new JSONObject();
        try {
            jsonPedido.put("id", this.getId());
            jsonPedido.put("nombre",this.getNombre());
            jsonPedido.put("bebidaXL",this.getBebidaXL());
            jsonPedido.put("enviarNotificaciones",this.getEnviarNotificaciones());
            jsonPedido.put("envioDomicilio",this.getEnvioDomicilio());
            jsonPedido.put("incluyePropina",this.getIncluyePropina());
            jsonPedido.put("estado",this.getEstado());
            jsonPedido.put("pagoAutomatico",this.getPagoAutomatico());
            jsonPedido.put("permiteCancelar",this.getPermiteCancelar());

            for (DetallePedido dp:this.getItemPedidos()) {
                jsonDetalle.put("id",dp.getId());
                jsonDetalle.put("cantidad",dp.getCantidad());
                jsonProducto.put("id",dp.getProductoPedido().getId());
                jsonProducto.put("nombre",dp.getProductoPedido().getNombre());
                jsonProducto.put("precio",dp.getProductoPedido().getPrecio());
                jsonDetalle.put("producto",jsonProducto);
                jsonDetalleArray.put(jsonDetalle);
            }
            jsonPedido.put("detalle",jsonDetalleArray);
        }catch(JSONException e){
            e.printStackTrace();
        }
        return jsonPedido;
    }
}
