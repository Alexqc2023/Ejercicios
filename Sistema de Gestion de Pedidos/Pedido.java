public class Pedido {
   
    public static final String ESTADO_BORRADOR = "BORRADOR";
    public static final String ESTADO_CONFIRMADO = "CONFIRMADO";
    public static final String ESTADO_CANCELADO = "CANCELADO";

    private int id;
    private Cliente cliente;
    private String estado;
    private DetallePedido[] detalles;
    private int contadorDetalles;

    public Pedido(int id, Cliente cliente) {
        this.id = id;
        this.cliente = cliente;
        this.estado = ESTADO_BORRADOR;
        this.detalles = new DetallePedido[10];
        this.contadorDetalles = 0;
    }

    public int getId() { 
        return id; 

    }
    public String getEstado() { 
        return estado;

     }
    public DetallePedido[] getDetalles() { 
        return detalles; 

    }
    public int getContadorDetalles() { 
        return contadorDetalles; 

    }

    public void setEstado(String nuevoEstado) {
        this.estado = nuevoEstado;
    }

    public boolean agregarProducto(Producto producto, int cantidad) {
        if (!estado.equals(ESTADO_BORRADOR)) {
            System.out.println("Error: solo se agregan productos en estado BORRADOR");
            return false;
        }
        if (contadorDetalles >= detalles.length) {
            System.out.println("Error: pedido lleno");
            return false;
        }

        detalles[contadorDetalles] = new DetallePedido(producto, cantidad);
        contadorDetalles++;
        return true; 
    }

    public double calcularSubtotal() {
        double total = 0;
        for (int i = 0; i < contadorDetalles; i++) {
            total += detalles[i].calcularSubtotal();
        }
        return total;
    }

    public double calcularTotalFinal() {
        double subtotal = calcularSubtotal();
        double descuento = cliente.calcularDescuento(subtotal);
        return subtotal - descuento;
    }

    @Override
    public String toString() {
        return "Pedido " + id + " | Cliente: " + cliente.getNombre() + " | Estado: " + estado + " | Total: $" + calcularTotalFinal();
    }
}