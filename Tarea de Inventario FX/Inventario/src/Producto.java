import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Producto {
    

    private final StringProperty nombre;

    private final StringProperty categoria;

    private final DoubleProperty precio;
    
    private final IntegerProperty cantidad;

    public Producto(String nombre, String categoria, Double precio, int cantidad){

        this.nombre = new SimpleStringProperty(nombre);

        this.categoria = new SimpleStringProperty(categoria);

        this.precio = new SimpleDoubleProperty(precio);

        this.cantidad = new SimpleIntegerProperty(cantidad);





    }


    public String getNombre(){
        return nombre.get();
    }

    public void setNombre(String n){
        nombre.set(n);
    }

    public StringProperty nombreProperty(){
        return nombre;
    }

    public String getCategoria(){
        return categoria.get();
    }
    

    public void setCategoria(String c){
        categoria.set(c);
    }

    public StringProperty categoriaProperty(){
        return categoria;
    }

    public Double getPrecio(){
        return precio.get();
    }

    public void setPrecio(Double p){
        precio.set(p);
    }

    public DoubleProperty precioProperty(){
        return precio;
    }

    public Integer getCantidad(){
        return cantidad.get();
    }

    public void setCantidad(int c){
        cantidad.set(c);
    }

    public IntegerProperty cantidadProperty(){
        return cantidad;
    }


    @Override
   public String toString(){
    return getNombre() + "," + getCategoria() + "," + getPrecio() + "," + getCantidad();
   }
    

}
