public class Procesador extends Thread {

    private SistemaGestion sistema;

    public Procesador(SistemaGestion sistema){
        this.sistema = sistema;
    }

    @Override
    public void run(){
        while(true){
            try {

                Thread.sleep(3000);
                sistema.procesarPedidosEnSegundoPlano();



            }catch (InterruptedException e){
                System.out.println("Procesador detenido");
                break;
            }
        }
    }

     
}
