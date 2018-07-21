package ar.android.lfl.myresto.modelo;

//Cada pedido puede tener múltiples ítems solicitados. Cada uno de estos ítems solicitados se guardará en una
//instancia de DetallePedido

public class DetallePedido {
    private int id;
    private ProductoMenu productoPedido;
    private int cantidad;
    private int idGenerator = 0;

    public DetallePedido(){
        this.id=idGenerator++;
    }

    public DetallePedido(int id, ProductoMenu productoPedido, int cantidad) {
        this.id = id;
        this.productoPedido = productoPedido;
        this.cantidad = cantidad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ProductoMenu getProductoPedido() {
        return productoPedido;
    }

    public void setProductoPedido(ProductoMenu productoPedido) {
        this.productoPedido = productoPedido;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
