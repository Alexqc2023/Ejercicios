import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Producto {
    
 private SimpleStringProperty nombre;
    private SimpleDoubleProperty precio;
    private SimpleIntegerProperty cantidad;

    public Producto(String nombre, double precio, int cantidad) {
        this.nombre   = new SimpleStringProperty(nombre);
        this.precio   = new SimpleDoubleProperty(precio);
        this.cantidad = new SimpleIntegerProperty(cantidad);
    }

    
    public StringProperty  nombreProperty()   { return nombre; }
    public DoubleProperty  precioProperty()   { return precio; }
    public IntegerProperty cantidadProperty() { return cantidad; }

    
    public String getNombre()   { return nombre.get(); }
    public double getPrecio()   { return precio.get(); }
    public int    getCantidad() { return cantidad.get(); }


    



}
