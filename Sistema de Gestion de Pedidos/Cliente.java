public abstract class Cliente {
    protected int id;
    protected String nombre;

    public Cliente(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() { 
        return id; 

    }
    public String getNombre() {
         return nombre;

     }

    public abstract double calcularDescuento(double subtotal);

    @Override
    public String toString() {
        return "ID: " + id + " | " + nombre + " (" + this.getClass().getSimpleName() + ")";
    }
}


class ClienteRegular extends Cliente {
    public ClienteRegular(int id, String nombre) {
        super(id, nombre);
    }

    @Override
    public double calcularDescuento(double subtotal) {
        return 0;
    }
}

class ClienteVIP extends Cliente {
    public ClienteVIP(int id, String nombre) {
        super(id, nombre);
    }

    @Override
    public double calcularDescuento(double subtotal) {
        return subtotal * 0.10; 
    }
}