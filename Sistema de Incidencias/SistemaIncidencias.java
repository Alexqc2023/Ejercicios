import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SistemaIncidencias {
    
    private static List<Incidencia> lista = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcion = -1;

        do {
            System.out.println("\n MENU ");
            System.out.println("1. Registrar | 2. Listar | 3. Buscar | 0. Salir");
            System.out.print("Opcion: ");

            try {
                String entrada = scanner.nextLine();
                opcion = Integer.parseInt(entrada);

                switch(opcion){
                    case 1: registrar(); break;
                    case 2: listar(); break;
                    case 3: buscar(); break;
                    case 0: System.out.println("Saliendo"); break;
                    default: System.out.println("Opcion no valida");
                }
            } catch (Exception e ){
                System.out.println("Error: debes ingresar un numero");
            }
        } while (opcion != 0); 
    }

    private static void registrar(){
        System.out.println("\n Nueva Incidencia ");
        try {
            System.out.print("ID: ");
            int id = Integer.parseInt(scanner.nextLine());

            System.out.print("Descripcion: ");
            String desc = scanner.nextLine();

            System.out.print("Fecha (dd/MM/yyyy): ");
            String fecha = scanner.nextLine();

            System.out.print("Prioridad (ALTA/MEDIA/BAJA): ");
            String prio = scanner.nextLine();

            Incidencia nueva = new Incidencia(id, desc, fecha, prio);
            lista.add(nueva);

            System.out.println("Incidencia registrada de manera correcta");

        } catch (NumberFormatException e ){
            System.out.println("El ID debe ser un numero");
        } catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void listar(){
        System.out.println("\n--- Listado ---");
        if (lista.isEmpty()) {
            System.out.println("(No hay registros)");
        }
        for (Incidencia i : lista){
            System.out.println(i);
        }
    }

    private static void buscar(){
        System.out.print("Buscar palabra: ");
        String palabra = scanner.nextLine().toLowerCase();

        boolean encontrada = false;

        for (Incidencia i : lista){
            if (i.getDescripcion().toLowerCase().contains(palabra)){
                System.out.println(i);
                encontrada = true;
            }
        }

        if (!encontrada) System.out.println("No se encontraron coincidencias");
    }
}