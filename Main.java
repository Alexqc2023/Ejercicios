import java.util.Scanner;

public class Main {
    static Scanner leer = new Scanner(System.in);

    public static void main(String[] args) {
        int opcion = -1;

        do {
            System.out.println("\n Menu de opciones elige una ");
            
            System.out.println("1. Cuenta Bancaria");

            System.out.println("2. Empleados ");

            System.out.println("3. Ocultamiento ");

            System.out.println("4. Arrays y Referencias");
            
            System.out.println("5. Figuras Geometricas ");

            System.out.println("6. Gestion de Producto ");

            System.out.println("7. Inventario ");

            System.out.println("8. Copia de Arrays ");

            System.out.println("0. Salir");

            System.out.print("Elige una opcion: ");

            if (leer.hasNextInt()) {
                opcion = leer.nextInt();
            } else {
                leer.next();
                opcion = -1;
            }

            switch (opcion) {
                
                case 1: ejecutarEjercicio1(); break;

                case 2: ejecutarEjercicio2(); break;

                case 3: ejecutarEjercicio3(); break;

                case 4: ejecutarEjercicio4(); break;

                
                case 5: ejecutarEjercicio5(); break;

                case 6: ejecutarEjercicio6(); break;

                case 7: ejecutarEjercicio7(); break;

                case 8: ejecutarEjercicio8(); break;

                case 0: System.out.println("Nos vemos"); break;

                default: System.out.println("Opcion no valida.");
            }
        } while (opcion != 0);
    }

    
    private static void ejecutarEjercicio1() {
        System.out.println("\n Ejercicio 1: Encapsulamiento ");

        CuentaBancaria c = new CuentaBancaria("ABCDE-1234567888", 1000);

        c.depositar(500);

        c.retirar(200);

        System.out.println("Saldo final: " + c.getSaldo());
    }

    private static void ejecutarEjercicio2() {
        System.out.println("\n Ejercicio 2: Polimorfismo ");
        Empleado e1 = new EmpleadoFijo(3000); 
        Empleado e2 = new EmpleadoPorHora(20, 80); 
        System.out.println("Sueldo Fijo: " + e1.CalcularSalario());
        System.out.println("Sueldo Por Hora: " + e2.CalcularSalario());
    }

    private static void ejecutarEjercicio3() {
        System.out.println("\n Ejercicio 3: Ocultamiento");
        A obj = new ClaseB(); 
        System.out.println("Valor de x: " + obj.x);
    }

    private static void ejecutarEjercicio4() {
        System.out.println("\n Ejercicio 4: Arrays ");
        int[] lista1 = {1, 2, 3};
        int[] lista2 = lista1; 
        lista2[0] = 500; 
        System.out.println("Original pos 0: " + lista1[0]);
    }

    

    private static void ejecutarEjercicio5() {
        System.out.println("\n Ejercicio 5: Figuras ");
        
        
        Figura[] misFiguras = new Figura[3];
        
        misFiguras[0] = new Cuadrado(4);       
        misFiguras[1] = new Rectangulo(3, 5);  
        misFiguras[2] = new Circulo(2.5);     
        
      
        for (Figura f : misFiguras) {
            System.out.println("Area: " + f.area());
        }
    }

    private static void ejecutarEjercicio6() {
        System.out.println("\n Ejercicio 6: Producto Individual ");
        Producto p = new Producto("Laptop Gamer", 1500.0, 10);
        
        System.out.println("Producto: " + p.getNombre());
        System.out.println("Stock inicial: " + p.getStock());
        
        System.out.println(" Vendiendo 3 unidades");
        p.vender(3);
        
        System.out.println("Stock actual: " + p.getStock());
        
        
        System.out.println(" Intentando poner precio -500");
        p.setPrecio(-500); 
    }

    private static void ejecutarEjercicio7() {
        System.out.println("\n Ejercicio 7: Inventario ");
        
        
        Producto[] inventario = new Producto[3];
        
       
        inventario[0] = new Producto("Teclado", 50.0, 100);
        inventario[1] = new Producto("Mouse", 25.0, 0); 
        inventario[2] = new Producto("Monitor", 200.0, 10);
        
        double valorTotal = 0;
        
        System.out.println("Listado de productos disponibles:");
        for (Producto prod : inventario) {
            
            valorTotal += (prod.getPrecio() * prod.getStock());
            
          
            if (prod.getStock() > 0) {
                System.out.println("- " + prod.getNombre() + " ($" + prod.getPrecio() + ")");
            }
        }
        System.out.println("Valor total del inventario: $" + valorTotal);
    }

    private static void ejecutarEjercicio8() {
        System.out.println("\n Ejercicio 8: Copia de Arrays ");
        
        int[] original = {10, 20, 30, 40, 50};
        
       
        int[] copia = original.clone();
        
        System.out.println("Modificando la copia ");
        copia[0] = 999;
        
        System.out.println("Valor en Original[0]: " + original[0]);
        System.out.println("Valor en Copia[0]:    " + copia[0]);
        
        
    }
}