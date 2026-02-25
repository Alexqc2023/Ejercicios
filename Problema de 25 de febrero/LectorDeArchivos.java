import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LectorDeArchivos  extends Thread{

private String nombreArchivos;
private Resultados resultados;


public LectorDeArchivos(String nombreArchivos, Resultados resultados){
   this.nombreArchivos = nombreArchivos;
   this.resultados = resultados;
}


@Override
public void run(){
int contadorLocal = 0;

try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivos))) {
            String linea;
            
            
            while ((linea = br.readLine()) != null) {
                linea = linea.trim(); 
                
                if (!linea.isEmpty()) {
                    

                    String[] palabras = linea.split("\\s+");
                    contadorLocal += palabras.length;
                }
            }
            
            
            resultados.registrarResultado(nombreArchivos, contadorLocal);
            System.out.println(" Hilo terminado: " + nombreArchivos);

        } catch (IOException e) {
            System.out.println(" Error el archivo no se pudo leer" + nombreArchivos + ": " + e.getMessage());
        }
    }



}



    

