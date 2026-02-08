    public class Producto {
    private int id;
    private String nombre;
    private double precio;
    private int stock;

    public Producto(int id, String nombre, double precio, int stock) {
        this.id = id;
        this.nombre = nombre;
        
        
        if (!setPrecio(precio)) {
            this.precio = 0.1; 
        }
        if (!setStock(stock)) {
            this.stock = 0;    
        }
    }

    public int getId() {
         return id; 
        }
        
    public String getNombre() {
        
        return nombre; 

    }
    public double getPrecio() { 
        return precio; 

    }
    public int getStock() { 
        return stock; 

    }

    public boolean setPrecio(double precio) {
        if (precio <= 0) {
            System.out.println("Error: precio debe ser mayor a 0.");
            return false;
        }
        this.precio = precio;
        return true;
    }

    public boolean setStock(int stock) {
        if (stock < 0) {
            System.out.println("Error: stock no puede ser negativo.");
            return false;
        }
        this.stock = stock;
        return true;
    }

    @Override
    public String toString() {
        return "ID: " + id + " | " + nombre + " | Precio: $" + precio + " | Stock: " + stock;
    }
}