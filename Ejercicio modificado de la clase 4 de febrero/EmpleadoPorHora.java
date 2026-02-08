public class EmpleadoPorHora extends Empleado {

    private double precioHora;
    private int horasTrabajadas;

    public EmpleadoPorHora(double precioHora, int horasTrabajadas) { 
        this.precioHora = precioHora;
        this.horasTrabajadas = horasTrabajadas;
    }

    @Override
    public double CalcularSalario() {
        
        return precioHora * horasTrabajadas;
    }
}