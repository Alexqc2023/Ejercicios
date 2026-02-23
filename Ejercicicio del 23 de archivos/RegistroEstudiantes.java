import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RegistroEstudiantes {
    
    private static final String ARCHIVO = "estudiantes.dat";
    private static List<Estudiante> listaMemoria = new ArrayList<>();

    public static void main(String[] args){

        Scanner scanner = new Scanner(System.in);
        int opcion = -1;

        System.out.println("Iniciando registro...");
        cargarDatosPrevios();

        do {
            try {
                System.out.println("\nMenu de Estudiantes");
                System.out.println("1. Agregar estudiante");
                System.out.println("2. Buscar estudiante (por matricula)");
                System.out.println("3. Mostrar lista completa");
                System.out.println("0. Salir");

                System.out.print("Elige una opcion: ");
                opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion){
                    case 1 -> agregarEstudiante(scanner);
                    case 2 -> buscarConRandomAccessFile(scanner);
                    case 3 -> listarEstudiantes();
                    case 0 -> System.out.println("Saliendo del sistema...");
                    default -> System.out.println("Opcion no valida.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Error: Debes ingresar un numero valido.");
            } catch (Exception e) {
                System.out.println("Ocurrio un error: " + e.getMessage());
            }

        } while (opcion != 0);

        scanner.close();
    }

    private static void cargarDatosPrevios() {
        File file = new File(ARCHIVO);
        if (!file.exists()) {
            System.out.println("El archivo no existe. Se creara uno nuevo al guardar.");
            return;
        }

        try (RandomAccessFile raf = new RandomAccessFile(ARCHIVO, "r")) {
            while (raf.getFilePointer() < raf.length()) {
                int tamanoObjeto = raf.readInt();
                byte[] datos = new byte[tamanoObjeto];
                raf.readFully(datos);
                
                ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(datos));
                Estudiante est = (Estudiante) ois.readObject();
                listaMemoria.add(est);
            }
            System.out.println("Se cargaron " + listaMemoria.size() + " estudiantes previamente guardados.");
            
        } catch (EOFException e) {
            

            
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al cargar los datos del archivo: " + e.getMessage());
        }
    }

    private static void agregarEstudiante(Scanner scanner) {
        System.out.print("Nombre del estudiante: ");
        String nombre = scanner.nextLine();
        
        System.out.print("Matricula (numero): ");
        int matricula = Integer.parseInt(scanner.nextLine());
        
        System.out.print("Promedio: ");
        double promedio = Double.parseDouble(scanner.nextLine());

        Estudiante nuevo = new Estudiante(nombre, matricula, promedio);
        listaMemoria.add(nuevo);

        try (RandomAccessFile raf = new RandomAccessFile(ARCHIVO, "rw")) {
            raf.seek(raf.length());
            
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(nuevo);
            byte[] datosSerializados = baos.toByteArray();
            
            raf.writeInt(datosSerializados.length);
            raf.write(datosSerializados);
            
            System.out.println("Estudiante registrado y guardado en disco.");
        } catch (IOException e) {
            System.out.println("Error al guardar en el archivo: " + e.getMessage());
        }
    }

    private static void buscarConRandomAccessFile(Scanner scanner) {
        System.out.print("Ingresa la matricula a buscar: ");
        int matBuscada = Integer.parseInt(scanner.nextLine());

        boolean encontrado = false;

        try (RandomAccessFile raf = new RandomAccessFile(ARCHIVO, "r")) { 
            while (raf.getFilePointer() < raf.length()) {
                int tamanoObjeto = raf.readInt();
                byte[] datos = new byte[tamanoObjeto];
                raf.readFully(datos);
                
                ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(datos));
                Estudiante e = (Estudiante) ois.readObject();
                
                if (e.getMatricula() == matBuscada) {
                    System.out.println("\nEstudiante encontrado en el archivo:");
                    System.out.println(e);
                    encontrado = true;
                    break; 
                }
            }
            
            if (!encontrado) {
                System.out.println("Estudiante no encontrado en los registros.");
            }
            
        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado. Aun no hay registros.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error de lectura: " + e.getMessage());
        }
    }

    private static void listarEstudiantes() {
        if (listaMemoria.isEmpty()) {
            System.out.println("No hay estudiantes registrados actualmente.");
            return;
        }
        System.out.println("\n--- Lista completa de estudiantes ---");
        for (Estudiante e : listaMemoria) {
            System.out.println(e);
        }
    }
}