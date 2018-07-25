package ar.android.lfl.myresto.modelo;

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

}
