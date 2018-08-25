package ar.android.lfl.myresto.modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class PedidoDAOsql implements PedidoDAO {

    public PedidoDAOsql(Context ctx){
        DatabaseManager.initializeInstance(new
                MyRestoOpenHelper(ctx));
    }

    @Override
    public void agregar(Pedido pedido) {
        SQLiteDatabase dbConn = DatabaseManager.getInstance().openDatabase();
        try {
            dbConn.beginTransaction();
            ContentValues cvPedido = new ContentValues();
            cvPedido.put("NOMBRE", pedido.getNombre());
            cvPedido.put("ESTADO", pedido.getEstado().toString());
            cvPedido.put("BEBIDA_XL", pedido.isBebidaXL() ? 1 : 0);
            cvPedido.put("ENVIAR_NOTIFICACION", pedido.isEnviarNotificaciones() ? 1 : 0);
            cvPedido.put("ENVIO_DOMICILIO", pedido.isEnvioDomicilio() ? 1 : 0);
            cvPedido.put("INCLUYE_PROPINA", pedido.isIncluyePropina() ? 1 : 0);
            cvPedido.put("PAGO_AUTOMATICO", pedido.isPagoAutomatico() ? 1 : 0);
            cvPedido.put("PERMITE_CANCELAR", pedido.isPermiteCancelar() ? 1 : 0);
            long idPedidoInsertado = dbConn.insert("PEDIDO", "_id", cvPedido);
            for (DetallePedido det : pedido.getItemPedidos()) {
                ContentValues cvDetallePedido = new ContentValues();
                cvDetallePedido.put("ID_PRODUCTO", det.getProductoPedido().getId());
                cvDetallePedido.put("CANTIDAD", det.getCantidad());
                cvDetallePedido.put("ID_PEDIDO", idPedidoInsertado);
                dbConn.insert("PEDIDO_DETALLE", "_id", cvDetallePedido);
            }
            dbConn.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbConn.endTransaction();
        }
    }

    @Override
    public List<Pedido> listarTodos() {
        List<Pedido> resultado = new ArrayList<>();
        SQLiteDatabase dbConn = DatabaseManager.getInstance().openDatabase();
        Cursor cursor = dbConn.rawQuery("SELECT _ID,NOMBRE,BEBIDA_XL,ENVIAR_NOTIFICACION,ESTADO,ENVIO_DOMICILIO,INCLUYE_PROPINA,PAGO_AUTOMATICO,PERMITE_CANCELAR from PEDIDO ", null);
        while (cursor.moveToNext()) {
            Pedido aux = new Pedido();
            aux.setId(cursor.getInt(cursor.getColumnIndex("_id")));
            aux.setNombre(cursor.getString(cursor.getColumnIndex("NOMBRE")));
            aux.setBebidaXL(cursor.getInt(cursor.getColumnIndex("BEBIDA_XL")) > 0);
            aux.setEnviarNotificaciones(cursor.getInt(cursor.getColumnIndex("ENVIAR_NOTIFICACION")) > 0);
            aux.setEstado(Estado.valueOf(cursor.getString(cursor.getColumnIndex("ESTADO"))
            ));
            aux.setEnvioDomicilio(cursor.getInt(cursor.getColumnIndex("ENVIO_DOMICILIO")) >
                    0);
            aux.setIncluyePropina(cursor.getInt(cursor.getColumnIndex("INCLUYE_PROPINA")) >
                    0);
            aux.setPagoAutomatico(cursor.getInt(cursor.getColumnIndex("PAGO_AUTOMATICO"))
                    > 0);
            aux.setPermiteCancelar(cursor.getInt(cursor.getColumnIndex("PERMITE_CANCELAR")
            ) > 0);
            String[] args = {aux.getId() + ""};
            Cursor cursorDetallePedido = dbConn.rawQuery("SELECT _ID,ID_PRODUCTO,CANTIDAD from PEDIDO_DETALLE WHERE ID_PEDIDO = ? ", args);
            while (cursorDetallePedido.moveToNext()) {
                DetallePedido detalle = new DetallePedido();
                detalle.setId(cursorDetallePedido.getInt(cursorDetallePedido.getColumnIndex("_id")));
                detalle.setCantidad(cursorDetallePedido.getInt(cursorDetallePedido.getColumnIndex("CANTIDAD")));
                detalle.setProductoPedido(this.getProductoPorId(cursorDetallePedido.getInt(cursorDetallePedido.getColumnIndex("ID_PRODUCTO"))));
                aux.addItemDetalle(detalle);
            }
            resultado.add(aux);
        }
        DatabaseManager.getInstance().closeDatabase();
        return resultado;
    }

    private ProductoMenu getProductoPorId(Integer id){
        SQLiteDatabase dbConn = DatabaseManager.getInstance().openDatabase();
        ProductoMenu aux = new ProductoMenu();
        String[] args = { id+"" };
        Cursor cursorProducto = dbConn.rawQuery("SELECT _id,nombre,precio from PRODUCTO WHERE _id =? ",args);
        if (cursorProducto.moveToNext()){
            aux.setPrecio(cursorProducto.getDouble(cursorProducto.getColumnIndex("PRECIO")
            ));
            aux.setNombre(cursorProducto.getString(cursorProducto.getColumnIndex("NOMBRE")
            ));
            aux.setId(cursorProducto.getInt(cursorProducto.getColumnIndex("_id")));
        }
        DatabaseManager.getInstance().closeDatabase();
        return aux;
    }



    @Override
    public void eliminar(Pedido pedido) {
        SQLiteDatabase dbConn = DatabaseManager.getInstance().openDatabase();
        try {
            dbConn.beginTransaction();
            dbConn.delete("PEDIDO", "_id="+pedido.getId(),null);
            for (DetallePedido det : pedido.getItemPedidos()) {
                dbConn.delete("PEDIDO_DETALLE", "_id="+det.getId(),null);
            }
            dbConn.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbConn.endTransaction();
        }
    }

    @Override
    public Pedido buscarPorId(Integer id) {
        SQLiteDatabase dbConn = DatabaseManager.getInstance().openDatabase();
        String [] buscarId = new String[]{String.valueOf(id)};
        Cursor cursor = dbConn.rawQuery("SELECT _ID,NOMBRE,BEBIDA_XL,ENVIAR_NOTIFICACION,ESTADO,ENVIO_DOMICILIO,INCLUYE_PROPINA,PAGO_AUTOMATICO,PERMITE_CANCELAR from PEDIDO where _id= ? ",buscarId);
        if (cursor.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            Pedido aux = new Pedido();
            do {
                aux.setId(cursor.getInt(cursor.getColumnIndex("_id")));
                aux.setNombre(cursor.getString(cursor.getColumnIndex("NOMBRE")));
                aux.setBebidaXL(cursor.getInt(cursor.getColumnIndex("BEBIDA_XL")) > 0);
                aux.setEnviarNotificaciones(cursor.getInt(cursor.getColumnIndex("ENVIAR_NOTIFICACION")) > 0);
                aux.setEstado(Estado.valueOf(cursor.getString(cursor.getColumnIndex("ESTADO"))
                ));
                aux.setEnvioDomicilio(cursor.getInt(cursor.getColumnIndex("ENVIO_DOMICILIO")) >
                        0);
                aux.setIncluyePropina(cursor.getInt(cursor.getColumnIndex("INCLUYE_PROPINA")) >
                        0);
                aux.setPagoAutomatico(cursor.getInt(cursor.getColumnIndex("PAGO_AUTOMATICO"))
                        > 0);
                aux.setPermiteCancelar(cursor.getInt(cursor.getColumnIndex("PERMITE_CANCELAR")
                ) > 0);
                String[] args = {aux.getId() + ""};
                Cursor cursorDetallePedido = dbConn.rawQuery("SELECT _ID,ID_PRODUCTO,CANTIDAD from PEDIDO_DETALLE WHERE ID_PEDIDO = ? ", args);
                while (cursorDetallePedido.moveToNext()) {
                    DetallePedido detalle = new DetallePedido();
                    detalle.setId(cursorDetallePedido.getInt(cursorDetallePedido.getColumnIndex("_id")));
                    detalle.setCantidad(cursorDetallePedido.getInt(cursorDetallePedido.getColumnIndex("CANTIDAD")));
                    detalle.setProductoPedido(this.getProductoPorId(cursorDetallePedido.getInt(cursorDetallePedido.getColumnIndex("ID_PRODUCTO"))));
                    aux.addItemDetalle(detalle);
                }
            } while(cursor.moveToNext());
            DatabaseManager.getInstance().closeDatabase();
            return aux;
        }
        else {
            DatabaseManager.getInstance().closeDatabase();
            return null;
        }
    }

    @Override
    public void actualizar(Pedido pedido) {
        SQLiteDatabase dbConn = DatabaseManager.getInstance().openDatabase();
        try {
            dbConn.beginTransaction();
            ContentValues cvPedido = new ContentValues();

            cvPedido.put("NOMBRE", pedido.getNombre());
            cvPedido.put("ESTADO", pedido.getEstado().toString());
            cvPedido.put("BEBIDA_XL", pedido.isBebidaXL() ? 1 : 0);
            cvPedido.put("ENVIAR_NOTIFICACION", pedido.isEnviarNotificaciones() ? 1 : 0);
            cvPedido.put("ENVIO_DOMICILIO", pedido.isEnvioDomicilio() ? 1 : 0);
            cvPedido.put("INCLUYE_PROPINA", pedido.isIncluyePropina() ? 1 : 0);
            cvPedido.put("PAGO_AUTOMATICO", pedido.isPagoAutomatico() ? 1 : 0);
            cvPedido.put("PERMITE_CANCELAR", pedido.isPermiteCancelar() ? 1 : 0);
            long idPedidoInsertado = dbConn.update("PEDIDO",  cvPedido,"_id="+pedido.getId(),null);
            for (DetallePedido det : pedido.getItemPedidos()) {
                ContentValues cvDetallePedido = new ContentValues();
                cvDetallePedido.put("ID_PRODUCTO", det.getProductoPedido().getId());
                cvDetallePedido.put("CANTIDAD", det.getCantidad());
                cvDetallePedido.put("ID_PEDIDO", idPedidoInsertado);
                dbConn.update("PEDIDO_DETALLE",  cvDetallePedido,"_id="+det.getId(),null);
            }
            dbConn.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbConn.endTransaction();
        }
    }
}
