public class Reportes extends Thread {

    private SistemaGestion sistema;

    public Reportes(SistemaGestion sistema){

        this.sistema = sistema;

        setDaemon(true);


    }
    
    @Override
    public void run(){
        while(true){
            try{

                Thread.sleep(10000);
                sistema.generarReporteSistema(true);


            }catch (InterruptedException e){
                break;
            }
        }



    }

}
