import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;

public class Pedido {
    
    public static final String ESTADO_BORRADOR = "BORRADOR";
    public static final String ESTADO_CONFIRMADO = "CONFIRMADO";
    public static final String ESTADO_CANCELADO = "CANCELADO";

    private int id;
    private Cliente cliente;
    private String estado;
    private List<DetallePedido> detalles; 
    private Date fechaCreacion;           

    public Pedido(int id, Cliente cliente) {
        this.id = id;
        this.cliente = cliente;
        this.estado = ESTADO_BORRADOR;
        this.detalles = new ArrayList<>(); 
        this.fechaCreacion = new Date();  
    }

    public int getId() {
         return id; 
        }
    public String getEstado() {
         return estado; 
        }
    public List<DetallePedido> getDetalles() { 
        return detalles;
     }
    public Date getFechaCreacion() { return fechaCreacion; }

    public void setEstado(String nuevoEstado) { 
        this.estado = nuevoEstado; 
    }

  
    public void agregarProducto(Producto producto, int cantidad) throws PedidoInvalidoException, StockInsuficienteException {
        if (!estado.equals(ESTADO_BORRADOR)) {
            throw new PedidoInvalidoException("Solo se pueden agregar productos a pedidos en BORRADOR.");
        }
        
        if (producto.getStock() < cantidad) {
             throw new StockInsuficienteException("Stock insuficiente en producto: " + producto.getNombre());
        }

        detalles.add(new DetallePedido(producto, cantidad));
    }

    public double calcularTotalFinal() {
        double total = 0;
        
        for (DetallePedido d : detalles) {
            total += d.calcularSubtotal();
        }
        double descuento = cliente.calcularDescuento(total);
        return total - descuento;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return "Pedido " + id + " | Fecha: " + sdf.format(fechaCreacion) + 
               "  Cliente: " + cliente.getNombre() + "  Estado: " + estado +  "  Total: $" + String.format("%.2f", calcularTotalFinal());
    }
}