import java.util.Scanner;

public class SistemaGestion {

    private Producto[] productos;
    private Cliente[] clientes;
    private Pedido[] pedidos;

    private int cantProductos = 0;
    private int cantClientes = 0;
    private int cantPedidos = 0;

    private Scanner scanner;

    public SistemaGestion() {
        productos = new Producto[50];
        clientes = new Cliente[50];
        pedidos = new Pedido[50];
        scanner = new Scanner(System.in);
    }

    public void iniciar() {
        int opcion = -1;
        do {
            mostrarMenu();
            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                scanner.nextLine(); 
                ejecutarOpcion(opcion);
            } else {
                System.out.println(" Error: Ingrese un numero valido.");
                scanner.nextLine();
            }
        } while (opcion != 0);
    }

    private void mostrarMenu() {
        System.out.println("\n--- Sistema de Pedidos ---");

        System.out.println("1. Registrar producto");

        System.out.println("2. Registrar cliente");

        System.out.println("3. Crear Pedido");

        System.out.println("4. Agregar producto a pedido");

        System.out.println("5. Ver detalles de pedido");

        System.out.println("6. Listar productos");

        System.out.println("7. Listar pedidos");

        System.out.println("8. Cambiar estado de pedido");

        System.out.println("0. Salir");

        System.out.print("Seleccione una opcion: ");
    }

    private void ejecutarOpcion(int opcion) {
        switch (opcion) {
            case 1 -> registrarProducto();
            case 2 -> registrarCliente();
            case 3 -> crearPedido();
            case 4 -> agregarProductoAPedido();
            case 5 -> verDetallePedido();
            case 6 -> listarProductos();
            case 7 -> listarPedidos();
            case 8 -> gestionarEstadoPedido();
            case 0 -> System.out.println(" Saliendo.. ");
            default -> System.out.println("Opcion no valida.");
        }
    }

    private void registrarProducto() {
        System.out.println("\n Registro de Producto ");
        System.out.print("ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        if (buscarProducto(id) != null) {
            System.out.println(" Error: Ya existe un producto con ese ID.");
            return;
        }

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Precio: ");
        double precio = scanner.nextDouble();

        System.out.print("Stock: ");
        int stock = scanner.nextInt();

       
        if (precio <= 0) {
            System.out.println(" Error: El precio debe ser mayor a 0.");
            return;
        }
        if (stock < 0) {
            System.out.println(" Error: El stock no puede ser negativo.");
            return;
        }

        productos[cantProductos++] = new Producto(id, nombre, precio, stock);
        System.out.println(" Producto registrado.");
    }

    private void registrarCliente() {
        System.out.println("\n Registro de Cliente ");
        System.out.print("ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        if (buscarCliente(id) != null) {
            System.out.println(" Error: ID de cliente repetido.");
            return;
        }

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Tipo (1. Regular, 2. VIP): ");
        int tipo = scanner.nextInt();

        if (tipo == 1) {
            clientes[cantClientes++] = new ClienteRegular(id, nombre);
            System.out.println(" Cliente Regular registrado.");
        } else if (tipo == 2) {
            clientes[cantClientes++] = new ClienteVIP(id, nombre);
            System.out.println(" Cliente VIP registrado.");
        } else {
            System.out.println(" Tipo invalido.");
        }
    }

    private void crearPedido() {
        System.out.println("\n Crear Pedido ");
        System.out.print("ID Pedido: ");
        int id = scanner.nextInt();
        if (buscarPedido(id) != null) {
            System.out.println(" Error: ID de pedido ya existe.");
            return;
        }

        System.out.print("ID del Cliente: ");
        int idCliente = scanner.nextInt();
        Cliente cliente = buscarCliente(idCliente);

        if (cliente == null) {
            System.out.println(" Error: Cliente no encontrado.");
            return;
        }

        pedidos[cantPedidos++] = new Pedido(id, cliente);
        System.out.println(" Pedido creado.");
    }

    private void agregarProductoAPedido() {
        System.out.print("ID Pedido: ");
        int idPedido = scanner.nextInt();
        Pedido pedido = buscarPedido(idPedido);

        if (pedido == null) {
            System.out.println(" Pedido no encontrado.");
            return;
        }

        listarProductos();
        System.out.print("ID Producto a agregar: ");
        int idProd = scanner.nextInt();
        Producto prod = buscarProducto(idProd);

        if (prod == null) {
            System.out.println(" Producto no existe.");
            return;
        }

        System.out.print("Cantidad: ");
        int cant = scanner.nextInt();

        if (prod.getStock() < cant) {
            System.out.println(" Error: Stock insuficiente. Solo hay " + prod.getStock());
            return;
        }

        if (pedido.agregarProducto(prod, cant)) {
            System.out.println(" Producto agregado.");
        }
    }

    private void gestionarEstadoPedido() {
        System.out.print("ID Pedido: ");
        int id = scanner.nextInt();
        Pedido pedido = buscarPedido(id);

        if (pedido == null) {
            System.out.println(" Pedido no encontrado.");
            return;
        }

        System.out.println("Estado actual: " + pedido.getEstado());
        System.out.println("1. Confirmar | 2. Cancelar");
        int op = scanner.nextInt();

        if (op == 1) confirmarPedido(pedido);
        else if (op == 2) cancelarPedido(pedido);
    }

    private void confirmarPedido(Pedido pedido) {
        if (!pedido.getEstado().equals(Pedido.ESTADO_BORRADOR)) {
            System.out.println(" Solo se confirman borradores.");
            return;
        }
        if (pedido.getContadorDetalles() == 0) {
            System.out.println(" El pedido esta vacio.");
            return;
        }

      
        DetallePedido[] detalles = pedido.getDetalles();
        for (int i = 0; i < pedido.getContadorDetalles(); i++) {
            Producto p = detalles[i].getProducto();
            if (p.getStock() < detalles[i].getCantidad()) {
                System.out.println(" Stock insuficiente para: " + p.getNombre());
                return;
            }
        }

        
        for (int i = 0; i < pedido.getContadorDetalles(); i++) {
            Producto p = detalles[i].getProducto();
            p.setStock(p.getStock() - detalles[i].getCantidad());
        }

        pedido.setEstado(Pedido.ESTADO_CONFIRMADO);
        System.out.println(" Pedido CONFIRMADO. Stock descontado.");
    }

    private void cancelarPedido(Pedido pedido) {
        if (!pedido.getEstado().equals(Pedido.ESTADO_CONFIRMADO)) {
            System.out.println(" Solo se cancelan pedidos confirmados.");
            return;
        }

        DetallePedido[] detalles = pedido.getDetalles();
        for (int i = 0; i < pedido.getContadorDetalles(); i++) {
            Producto p = detalles[i].getProducto();
            p.setStock(p.getStock() + detalles[i].getCantidad());
        }

        pedido.setEstado(Pedido.ESTADO_CANCELADO);
        System.out.println(" Pedido CANCELADO. Stock restaurado.");
    }

    private void listarProductos() {
        System.out.println(" Lista Productos ");
        for (int i = 0; i < cantProductos; i++)
            System.out.println(productos[i]);
    }

    private void listarPedidos() {
        System.out.println(" Lista Pedidos ");
        for (int i = 0; i < cantPedidos; i++)
            System.out.println(pedidos[i]);
    }

    private void verDetallePedido() {
        System.out.print("ID Pedido: ");
        int id = scanner.nextInt();
        Pedido p = buscarPedido(id);
        
        if (p != null) {
            System.out.println(p);
            
            DetallePedido[] detalles = p.getDetalles();
            for (int i = 0; i < p.getContadorDetalles(); i++) {
                System.out.println(" - " + detalles[i]);
            }
        } else {
            System.out.println("Pedido no encontrado.");
        }
    }

    private Producto buscarProducto(int id) {
        for (int i = 0; i < cantProductos; i++) {
            if (productos[i].getId() == id)
                return productos[i];
        }
        return null;
    }

    private Cliente buscarCliente(int id) {
        for (int i = 0; i < cantClientes; i++) {
            if (clientes[i].getId() == id)
                return clientes[i];
        }
        return null;
    }

    private Pedido buscarPedido(int id) {
        for (int i = 0; i < cantPedidos; i++) {
            if (pedidos[i].getId() == id)
                return pedidos[i];
        }
        return null;
    }
}