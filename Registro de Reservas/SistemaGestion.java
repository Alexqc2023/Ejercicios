import java.util.ArrayList; 
import java.util.List;      
import java.util.Scanner;

public class SistemaGestion {
    
   
    private static List<Reserva> listaReservas = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcion = -1;

        
        do {
            mostrarMenu();
            
            try {
                String entrada = scanner.nextLine();
                opcion = Integer.parseInt(entrada);

                switch (opcion) {
                    
                    case 1 -> registrarReserva();
                    case 2 -> mostrarReservas(); 
                    case 0 -> System.out.println("Saliendo ");
                    default -> System.out.println("Error: opcion no valida.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Error: debe ingresar un numero valido para el menu");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        } while (opcion != 0);
    }

    private static void mostrarMenu() {
        System.out.println("\n--- Gestion de reservas ---"); 
        System.out.println("1. Registrar reserva");
        System.out.println("2. Mostrar reservas");
        System.out.println("0. Salir");
        System.out.print("Seleccione una opcion: ");
    }

    private static void registrarReserva() {
        System.out.println("\n Nueva reserva ");

        try {
            System.out.print("Nombre de cliente: ");
            String nombre = scanner.nextLine();

            
            if (nombre.equalsIgnoreCase("cancelar")) {
                System.out.println("Registro cancelado.");
                return;
            }

            System.out.print("Fecha (dd/MM/yyyy): ");
            String fechaStr = scanner.nextLine();

            System.out.print("Cantidad de personas: ");
            String cantStr = scanner.nextLine(); 
            
            
            int cantidad = Integer.parseInt(cantStr); 

            
            Reserva nuevaReserva = new Reserva(nombre, fechaStr, cantidad);
            
            listaReservas.add(nuevaReserva);
            System.out.println("Reserva registrada con exito.");

        } catch (ReservaInvalidaException e) {
            System.out.println("Error de validacion: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error: La cantidad debe ser un numero entero.");
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
        }
    }

    
    private static void mostrarReservas() {
        System.out.println("\n Lista de reservas");
        if (listaReservas.isEmpty()) {
            System.out.println("No hay reservas.");
        } else {
            for (Reserva r : listaReservas) {
                System.out.println(r.toString());
            }
        }
    }
}