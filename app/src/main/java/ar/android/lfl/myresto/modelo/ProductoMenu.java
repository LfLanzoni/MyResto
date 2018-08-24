package ar.android.lfl.myresto.modelo;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class ProductoMenu implements Parcelable {
    private int id;
    private String nombre;
    private Double precio;

    public ProductoMenu() {
        this.id = 0;
        this.nombre = "nombre";
        this.precio = 0.0;
    }

    public ProductoMenu(int id, String nombre, Double precio) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return nombre+"-"+precio;
   }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nombre);
        dest.writeDouble(precio);
    }
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ProductoMenu> CREATOR = new
            Creator<ProductoMenu>() {
                @Override
                public ProductoMenu createFromParcel(Parcel in) {
                    ProductoMenu aux = new ProductoMenu();
                    aux.id = in.readInt();
                    aux.nombre = in.readString();
                    aux.precio = in.readDouble();
                    return aux;
                }
                @Override
                public ProductoMenu[] newArray(int size) {
                    return new ProductoMenu[size];
                }
            };

    public void loadFromJson(JSONObject fila) {
        try {
            this.setId(fila.getInt("id"));
            this.setPrecio(fila.getDouble("precio"));
            this.setNombre(fila.getString("nombre"));
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
    }

}


