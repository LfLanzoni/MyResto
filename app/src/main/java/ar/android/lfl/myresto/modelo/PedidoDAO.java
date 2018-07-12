package ar.android.lfl.myresto.modelo;
import java.util.List;

public interface PedidoDAO {
    public void agregar(Pedido pedido);
    public List<Pedido> listarTodos();
}
