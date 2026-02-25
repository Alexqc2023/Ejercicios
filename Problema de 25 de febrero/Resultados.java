public class Resultados {

    private int contadorPalabras = 0;
    private StringBuilder reporteDetallado = new StringBuilder();


    public synchronized void registrarResultado(String nombreArchivo, int cantidadPalabras){

        contadorPalabras += cantidadPalabras;

        
        reporteDetallado.append("Archivo: ").append(nombreArchivo).append("\n");
        reporteDetallado.append("palabra encontrada: ").append(cantidadPalabras).append("\n");



    }


    public int getTotalPalabra() {
        return contadorPalabras;
    }

    public String getReporteDetallado() {
        return reporteDetallado.toString();
    }

    



}