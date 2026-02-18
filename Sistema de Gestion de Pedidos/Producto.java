    public class Producto {
    private int id;
    private String nombre;
    private double precio;
    private int stock;

    public Producto(int id, String nombre, double precio, int stock) {
        this.id = id;
       
        if (nombre == null || nombre.trim().isEmpty()) {
            this.nombre = "Sin Nombre";
        } else {
            this.nombre = nombre.trim();
        }
        
        if (!setPrecio(precio)) this.precio = 0.1;
        if (!setStock(stock)) this.stock = 0;
    }

    
    public int getId() { 
        return id;
     }

    public String getNombre()  {
         return nombre; 
        }
    
    public double getPrecio()  { 
        return precio;
     }

    public int getStock()  {
         return stock; 
        }

    public boolean setPrecio(double precio) {
        if (precio <= 0) return false;
        this.precio = precio;
        return true;
    }

    public boolean setStock(int stock) {
        if (stock < 0) return false;
        this.stock = stock;
        return true;
    }

    @Override
    public String toString() {
        return "ID: " + id + " | " + nombre + " | $" + String.format("%.2f", precio) + " | Stock: " + stock;
    }
}