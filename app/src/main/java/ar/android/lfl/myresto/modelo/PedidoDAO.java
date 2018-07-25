package ar.android.lfl.myresto.modelo;
import java.util.List;

public interface PedidoDAO {
    public void agregar(Pedido pedido);
    public List<Pedido> listarTodos();
    public void eliminar (Pedido pedido);
    public Pedido buscarPorId(Integer id);
    public void actualizar(Pedido p);

}
