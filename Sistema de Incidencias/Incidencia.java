import java.text.SimpleDateFormat;
import java.util.Date;

public class Incidencia {
    
    private int id;
    private String descripcion;
    private Date fechaRegistro;
    private String nivelPrioridad;

    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public Incidencia(int id, String descripcion, String fechaStr, String nivelPrioridad)
            throws DescripcionInvalidaException, FechaInvalidaException, PrioridadInvalidaException {

        this.id = id;
        validarDescripcion(descripcion);
        validarFecha(fechaStr); 
        validarPrioridad(nivelPrioridad);
    }

    private void validarDescripcion(String desc) throws DescripcionInvalidaException {
        if (desc == null) {
            throw new DescripcionInvalidaException("La descripcion es nula");
        }
        if (desc.trim().isEmpty()) {
            throw new DescripcionInvalidaException("La descripcion no puede estar vacia");
        }
        if (desc.trim().length() < 10) {
            throw new DescripcionInvalidaException("Descripcion muy corta (minimo 10 caracteres)");
        }
        this.descripcion = desc.trim();
    }

    private void validarPrioridad(String prioridad) throws PrioridadInvalidaException {
        if (prioridad == null) throw new PrioridadInvalidaException("Es nula");
        
        
        String p = prioridad.trim().toUpperCase();

       
        if (p.equals("ALTA") || p.equals("MEDIA") || p.equals("BAJA")) { 
            this.nivelPrioridad = p;
        } else {
            throw new PrioridadInvalidaException("Prioridad incorrecta (Usa: ALTA, MEDIA, BAJA)");
        }
    }

    private void validarFecha(String fechaStr) throws FechaInvalidaException { 
        sdf.setLenient(false);
        try {
            Date fecha = sdf.parse(fechaStr);
            if (fecha.after(new Date())) {
                throw new FechaInvalidaException("La fecha no puede ser futura");
            }
            this.fechaRegistro = fecha;
        } catch (Exception e) {
            throw new FechaInvalidaException("Formato de fecha incorrecto (dd/MM/yyyy)"); 
        }
    }
  
    @Override
    public String toString() {
        return "ID: " + id + " | Fecha: " + sdf.format(fechaRegistro) + 
               " | Prioridad: " + nivelPrioridad + "\n   Desc: " + descripcion;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
}