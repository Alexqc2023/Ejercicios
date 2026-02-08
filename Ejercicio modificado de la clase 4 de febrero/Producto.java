    public class Producto {
        private String nombre;
        private double precio;
        private int stock;

        public Producto(String nombre, double precio, int stock) {
            this.nombre = nombre;
            
            setPrecio(precio);
            setStock(stock);
        }

        public void vender(int cantidad) {
            if (cantidad <= stock && cantidad > 0) {
                stock -= cantidad;
                System.out.println("Venta realizada.");
            } else {
                System.out.println("Error: Stock insuficiente o cantidad invalida.");
            }
        }

        
        public String getNombre() 
        { return nombre; }

        public void setNombre(String nombre) 

        { this.nombre = nombre; }

        public double getPrecio()
        { return precio; }

        public void setPrecio(double precio) {
            if (precio >= 0) {
                this.precio = precio;
            } else {
                System.out.println("Error: El precio no puede ser negativo.");
            }
        }

        public int getStock() 
        { return stock; }
        
        public void setStock(int stock) {
            if (stock >= 0) {
                this.stock = stock;
            } else {
                System.out.println("Error: El stock no puede ser negativo.");
            }
        }
    }