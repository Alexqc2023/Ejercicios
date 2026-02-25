import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Main {
    
    public static void main(String[] args) {
        
        System.out.println("Mostrando los archivos creados");

        generarArchivo("Texto 1.txt", 75);
        generarArchivo("Texto 2.txt", 80);
        generarArchivo("Texto 3.txt", 77);

        Resultados resultadosCompartidos = new Resultados();

        LectorDeArchivos hilo1 = new LectorDeArchivos("Texto 1.txt", resultadosCompartidos);
        LectorDeArchivos hilo2 = new LectorDeArchivos("Texto 2.txt", resultadosCompartidos);
        LectorDeArchivos hilo3 = new LectorDeArchivos("Texto 3.txt", resultadosCompartidos);

        System.out.println("Iniciando procesamiento...");
        long tiempoInicio = System.currentTimeMillis();

        hilo1.start();
        hilo2.start();
        hilo3.start();

        try {
            hilo1.join();
            hilo2.join();
            hilo3.join();
        } catch (InterruptedException e) {
            System.out.println("El hilo principal fue interrumpido.");
        }

        long tiempoFin = System.currentTimeMillis();
        long tiempoTotal = tiempoFin - tiempoInicio;
        
        generarReporteFinal(resultadosCompartidos, tiempoTotal);
    }
    
    private static void generarReporteFinal(Resultados resultados, long tiempoTotal) {
        try (PrintWriter pw = new PrintWriter(new FileWriter("estadisticas.txt"))) {
            
            pw.println("========================================");
            pw.println("REPORTE DE PROCESAMIENTO DE ARCHIVOS");
            pw.println("========================================\n");
            
            pw.print(resultados.getReporteDetallado()); 
            
            pw.println("----------------------------------------");
            pw.println("Total de palabras procesadas: " + resultados.getTotalPalabra());
            pw.println("Tiempo de procesamiento: " + tiempoTotal + " ms");
            
            
            System.out.println("Procesamiento completado. Por favor revisa el archivo 'estadisticas.txt'.");
            
        } catch (IOException e) {
            System.out.println("Error al crear el reporte: " + e.getMessage());
        }
    }
   
    private static void generarArchivo(String nombreArchivo, int cantidadPalabras) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(nombreArchivo))) {
            for (int i = 1; i <= cantidadPalabras; i++) {
                pw.print("palabra" + i + " ");
                
                if (i % 10 == 0) {
                    pw.println();
                }
            }
        } catch (IOException e) {
            System.out.println("Error generando archivo de prueba: " + e.getMessage());
        }
    }
}