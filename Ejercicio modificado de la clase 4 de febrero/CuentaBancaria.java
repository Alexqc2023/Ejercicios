public class CuentaBancaria {

    private double saldo;
    private String numeroCuenta;

    public CuentaBancaria(String numeroCuenta, double saldo) {
        this.numeroCuenta = numeroCuenta;
        this.saldo = saldo;
    }

    public void depositar(double monto) {
        if (monto > 0) {
            saldo += monto;
        }
    }

    public void retirar(double monto) {
        if (monto <= saldo && monto > 0) {
            saldo -= monto;
        } else {
            System.out.println("Error: dinero insuficiente o monto invalido.");
        }
    }

    public double getSaldo() {
        return saldo;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }
}