package ar.android.lfl.myresto.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Pedido {
    private Integer id;
    private String nombre;
    private String pedido;
    private Boolean envioDomicilio;
    private Boolean bebidaXL;
    private Boolean permiteCancelar;
    private Boolean incluyePropina;
    private Boolean enviarNotificaciones;
    private Boolean pagoAutomatico;
    private List<ProductoMenu> itemPedidos;
    private static int idGenerator = 0;


    public Pedido(){
        this.id = ++Pedido.idGenerator;
        this.itemPedidos = new ArrayList<>();
    }

    @Override
    public String toString() {
        return nombre;
    }

    public List<ProductoMenu> getItemPedidos() {
        return itemPedidos;
    }

    public void setItemPedidos(List<ProductoMenu> itemPedidos) {
        this.itemPedidos = itemPedidos;
    }

    public void addItemDetalle(DetallePedido prd){
        this.itemPedidos.add(prd.getProductoPedido());
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



}
