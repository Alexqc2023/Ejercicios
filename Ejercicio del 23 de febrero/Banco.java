public class Banco {
    
    public static void main(String[] args) {
        System.out.println("Abriendo banco");

        Boveda bovedaPrincipal = new Boveda();

        Cajero cajero1 = new Cajero("Cajero 1", bovedaPrincipal);
        Cajero cajero2 = new Cajero("Cajero 2", bovedaPrincipal);
        Cajero cajero3 = new Cajero("Cajero 3", bovedaPrincipal);

        
        Thread monitorDemonio = new Thread(() -> {
            while (true) {
                try {
                    System.out.println("Monitor ----> el saldo actual de la boveda es: " + bovedaPrincipal.getSaldo());
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    break;
                }
            }
        });

        monitorDemonio.setDaemon(true);
        monitorDemonio.start();
        
        cajero1.start();
        cajero2.start();
        cajero3.start();

        try {
            cajero1.join();
            cajero2.join();
            cajero3.join();
        } catch (InterruptedException e) {
            System.out.println("El hilo principal fue interrumpido");
        }

        System.out.println("\nCierre del banco");
        System.out.println("Resumen de las operaciones");
        System.out.println("Cajero 1 proceso: " + cajero1.getTransaccionesProcesadas() + " transacciones.");
        System.out.println("Cajero 2 proceso: " + cajero2.getTransaccionesProcesadas() + " transacciones.");
        System.out.println("Cajero 3 proceso: " + cajero3.getTransaccionesProcesadas() + " transacciones.");
        System.out.println("Saldo final en boveda: " + bovedaPrincipal.getSaldo());
    }
}