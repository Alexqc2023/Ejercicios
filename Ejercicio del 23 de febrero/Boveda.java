public class Boveda {
    
private int saldo = 50000;


public synchronized void retirar(int monto, String nombreCajero){

if (saldo >= monto){
    saldo -= monto;
    System.out.println(nombreCajero + " retiro " + monto + " Saldo restante:" + saldo);

}else { 

    System.out.println(nombreCajero + " intento retirar" + monto + "Boveda no cuenta con saldo suficiente");




}




}

public int getSaldo(){
    return saldo;
}

}
