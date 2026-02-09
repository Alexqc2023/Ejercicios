import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Reserva {
    private String nombreCliente;
    private Date fechaReserva;
    private int cantidadPersonas;

    
    private static final SimpleDateFormat FORMATO_FECHA = new SimpleDateFormat("dd/MM/yyyy");

    public Reserva(String nombreCliente, String fechaStr, int cantidadPersonas) throws ReservaInvalidaException {
       
        if (nombreCliente == null || nombreCliente.trim().isEmpty()) {
            throw new ReservaInvalidaException("El nombre del cliente no puede estar vacio.");
        }
        
        
        if (cantidadPersonas <= 0) {
            throw new ReservaInvalidaException("La cantidad de personas debe ser mayor a 0.");
        }

        
        try {
            FORMATO_FECHA.setLenient(false); 
            this.fechaReserva = FORMATO_FECHA.parse(fechaStr);
        } catch (ParseException e) {
            throw new ReservaInvalidaException("Formato de fecha invalido. Use dd/MM/yyyy.");
        }

        this.nombreCliente = nombreCliente.trim();
        this.cantidadPersonas = cantidadPersonas;
    }

    @Override
    public String toString() {
        String fechaBonita = FORMATO_FECHA.format(fechaReserva);
        return "Cliente: " + nombreCliente + " | Fecha: " + fechaBonita + " | Personas: " + cantidadPersonas;
    }
}