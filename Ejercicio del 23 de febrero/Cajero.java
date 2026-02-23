import java.util.concurrent.ThreadLocalRandom;

public class Cajero extends Thread {

    private Boveda boveda;
    private int transaccionesProcesadas = 0; 

    public Cajero(String nombre, Boveda boveda) {
        super(nombre);
        this.boveda = boveda;
    }

    @Override
    public void run() {
        int cantidadClientes = ThreadLocalRandom.current().nextInt(3, 6);
        System.out.println(getName() + " inicio su turno. Atendera a " + cantidadClientes + " clientes.");

        for (int i = 1; i <= cantidadClientes; i++) {
            try {
                int tiempoEspera = ThreadLocalRandom.current().nextInt(1000, 3001);
                Thread.sleep(tiempoEspera);

                int montoRetiro = ThreadLocalRandom.current().nextInt(500, 2001);

                boveda.retirar(montoRetiro, getName());
                transaccionesProcesadas++;

            } catch (InterruptedException e) {
                System.out.println(getName() + " fue interrumpido.");
            }
        }

        System.out.println(getName() + " ha terminado su turno.");
    }

    public int getTransaccionesProcesadas() {
        return transaccionesProcesadas;
    }
}