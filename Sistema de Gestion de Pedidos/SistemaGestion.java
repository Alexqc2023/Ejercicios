import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Date;
import java.text.SimpleDateFormat;

public class SistemaGestion {

   
    private List<Producto> productos;
    private List<Cliente> clientes;
    private List<Pedido> pedidos;
    private Scanner scanner;

    public SistemaGestion() {
        productos = new ArrayList<>();
        clientes = new ArrayList<>();
        pedidos = new ArrayList<>();
        scanner = new Scanner(System.in);
    }

    public void iniciar() {
        int opcion = -1;
        do {
            try {
                mostrarMenu();
                
                if (scanner.hasNextInt()) {
                    opcion = scanner.nextInt();
                    scanner.nextLine(); 
                    ejecutarOpcion(opcion);
                } else {
                    System.out.println(" Error: Debe ingresar un numero.");
                    scanner.nextLine(); 
                }
            } catch (Exception e) {
                
                System.out.println(" Error inesperado: " + e.getMessage());
                scanner.nextLine();
            }
        } while (opcion != 0);
    }

    private void mostrarMenu() {
        System.out.println("\n Sistema de Pedidos  ");

        System.out.println("1. Registrar producto");

        System.out.println("2. Registrar cliente");

        System.out.println("3. Crear Pedido");

        System.out.println("4. Agregar producto a pedido");

        System.out.println("5. Ver detalles de pedido");

        System.out.println("6. Listar productos");

        System.out.println("7. Buscar productos por nombre");

        System.out.println("8. Listar pedidos");

        System.out.println("9. Gestionar estado de pedido");

        System.out.println("0. Salir");

        System.out.print("Seleccione una opcion: ");
    }

    private void ejecutarOpcion(int opcion) {
        
        try {
            switch (opcion) {
                case 1 -> registrarProducto();
                case 2 -> registrarCliente();
                case 3 -> crearPedido();
                case 4 -> agregarProductoAPedido();
                case 5 -> verDetallePedido();
                case 6 -> listarProductos();
                case 7 -> buscarProductosPorNombre();
                case 8 -> listarPedidos();
                case 9 -> gestionarEstadoPedido();
                case 0 -> System.out.println("Saliendo...");
                default -> System.out.println("Opcion no valida ");
            }
        } catch (StockInsuficienteException | PedidoInvalidoException | ProductoNoEncontradoException e) {
            System.out.println(" ERROR DE NEGOCIO: " + e.getMessage());
        } catch (Exception e) {
            System.out.println(" ERROR CRÍTICO: " + e.getMessage());
        } finally {
           
            System.out.print("Presione ENTER para continuar...");
            scanner.nextLine();
        }
    }

    

    private void registrarProducto() {
        System.out.println("\n--- Registro de Producto ---");
        System.out.print("ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        if (buscarProducto(id) != null) {
            System.out.println("Error: Ya existe un producto con ese ID.");
            return;
        }

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        
        if(nombre.trim().isEmpty()) {
            System.out.println("Error: El nombre no puede estar vacio.");
            return;
        }

        System.out.print("Precio: ");
        double precio = scanner.nextDouble();

        System.out.print("Stock: ");
        int stock = scanner.nextInt();

        productos.add(new Producto(id, nombre, precio, stock));
        System.out.println(" Producto registrado.");
    }

    private void registrarCliente() {
        System.out.println("\n Registro de Cliente ");
        System.out.print("ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        if (buscarCliente(id) != null) {
            System.out.println("Error: ID repetido.");
            return;
        }

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        
        System.out.print("Tipo (1. Regular, 2. VIP): ");
        int tipo = scanner.nextInt();

        if (tipo == 1) clientes.add(new ClienteRegular(id, nombre));
        else if (tipo == 2) clientes.add(new ClienteVIP(id, nombre));
        else System.out.println("Tipo inválido.");
    }

    private void crearPedido() {
        System.out.println("\n--- Crear Pedido ---");
        System.out.print("ID Pedido: ");
        int id = scanner.nextInt();
        if (buscarPedido(id) != null) {
            System.out.println("Error: ID de pedido ya existe.");
            return;
        }

        System.out.print("ID del Cliente: ");
        int idCliente = scanner.nextInt();
        Cliente cliente = buscarCliente(idCliente);

        if (cliente == null) {
            System.out.println("Error: Cliente no encontrado.");
            return;
        }

        pedidos.add(new Pedido(id, cliente));
        System.out.println(" Pedido creado exitosamente.");
    }

    

    private void agregarProductoAPedido() throws ProductoNoEncontradoException, StockInsuficienteException, PedidoInvalidoException {
        System.out.print("ID Pedido: ");
        int idPedido = scanner.nextInt();
        Pedido pedido = buscarPedido(idPedido);

        if (pedido == null) throw new PedidoInvalidoException("El pedido con ID " + idPedido + " no existe.");

        listarProductos();
        System.out.print("ID Producto a agregar: ");
        int idProd = scanner.nextInt();
        Producto prod = buscarProducto(idProd);

        if (prod == null) throw new ProductoNoEncontradoException("Producto con ID " + idProd + " no existe en catálogo.");

        System.out.print("Cantidad: ");
        int cant = scanner.nextInt();

       
        pedido.agregarProducto(prod, cant);
        System.out.println(" Producto agregado.");
    }

    private void gestionarEstadoPedido() throws PedidoInvalidoException, StockInsuficienteException {
        System.out.print("ID Pedido: ");
        int id = scanner.nextInt();
        Pedido pedido = buscarPedido(id);

        if (pedido == null) throw new PedidoInvalidoException("Pedido no encontrado.");

        System.out.println("Estado actual: " + pedido.getEstado());
        System.out.println("1. Confirmar | 2. Cancelar");
        int op = scanner.nextInt();

        if (op == 1) confirmarPedido(pedido);
        else if (op == 2) cancelarPedido(pedido);
    }

    private void confirmarPedido(Pedido pedido) throws PedidoInvalidoException, StockInsuficienteException {
        if (!pedido.getEstado().equals(Pedido.ESTADO_BORRADOR)) {
            throw new PedidoInvalidoException("Solo se confirman borradores.");
        }
        if (pedido.getDetalles().isEmpty()) {
            throw new PedidoInvalidoException("No se puede confirmar un pedido vacio.");
        }

       
        for (DetallePedido dp : pedido.getDetalles()) {
            if (dp.getProducto().getStock() < dp.getCantidad()) {
                throw new StockInsuficienteException("Stock insuficiente para " + dp.getProducto().getNombre() + " al confirmar.");
            }
        }

        
        for (DetallePedido dp : pedido.getDetalles()) {
            Producto p = dp.getProducto();
            p.setStock(p.getStock() - dp.getCantidad());
        }

        pedido.setEstado(Pedido.ESTADO_CONFIRMADO);
        System.out.println(" Pedido Confirmado. Stock actualizado.");
    }

    private void cancelarPedido(Pedido pedido) throws PedidoInvalidoException {
        if (!pedido.getEstado().equals(Pedido.ESTADO_CONFIRMADO)) {
            throw new PedidoInvalidoException("Solo se pueden cancelar pedidos confirmados.");
        }

        
        for (DetallePedido dp : pedido.getDetalles()) {
            Producto p = dp.getProducto();
            p.setStock(p.getStock() + dp.getCantidad());
        }

        pedido.setEstado(Pedido.ESTADO_CANCELADO);
        System.out.println(" Pedido Cancelado. Stock restaurado.");
    }


    private void listarProductos() {
        System.out.println(" Lista Productos ");
      
        for (Producto p : productos) {
            System.out.println(p);
        }
    }

    private void listarPedidos() {
        System.out.println(" Lista Pedidos ");
        for (Pedido p : pedidos) {
            System.out.println(p);
        }
    }

    
    private void buscarProductosPorNombre() {
        System.out.print("Ingrese nombre a buscar: ");
        String busqueda = scanner.nextLine().trim().toLowerCase();
        
        System.out.println("Resultados:");
        boolean encontrado = false;
        
        for (Producto p : productos) {
           
            if (p.getNombre().toLowerCase().contains(busqueda)) {
                System.out.println(p);
                encontrado = true;
            }
        }
        
        if (!encontrado) System.out.println("No se encontraron coincidencias.");
    }

    private void verDetallePedido() {
        System.out.print("ID Pedido: ");
        int id = scanner.nextInt();
        Pedido p = buscarPedido(id);
        
        if (p != null) {
            System.out.println(p);
            System.out.println(" Detalles ");
            for (DetallePedido det : p.getDetalles()) {
                System.out.println(det);
            }
        } else {
            System.out.println("Pedido no encontrado.");
        }
    }

    
    private Producto buscarProducto(int id) {
        for (Producto p : productos) {
            if (p.getId() == id) 
                return p;
        }
        return null;
    }

    private Cliente buscarCliente(int id) {
        for (Cliente c : clientes) {
            if (c.getId() == id) 
                return c;
        }
        return null;
    }

    private Pedido buscarPedido(int id) {
        for (Pedido p : pedidos) {
            if (p.getId() == id) return p;
        }
        return null;
    }
}