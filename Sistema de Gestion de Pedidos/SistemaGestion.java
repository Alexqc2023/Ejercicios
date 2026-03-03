import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
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
        productos = Collections.synchronizedList(new ArrayList<>());
        clientes = Collections.synchronizedList(new ArrayList<>());
        pedidos = Collections.synchronizedList(new ArrayList<>());
        scanner = new Scanner(System.in);
    }

    public void iniciar() {
        System.out.println("Cargando sistema desde archivos...");
        cargarSistema();

       
        Procesador hiloProcesador = new Procesador(this);
        hiloProcesador.start();
        
        Reportes hiloReportes = new Reportes(this);
        hiloReportes.start();

        int opcion = -1;
        do {
            try {
                mostrarMenu();
                if (scanner.hasNextInt()) {
                    opcion = scanner.nextInt();

                    scanner.nextLine(); 

                    ejecutarOpcion(opcion);

                } else {
                    System.out.println("Error: Debe ingresar un numero.");
                    scanner.nextLine(); 
                }
            } catch (Exception e) {
                System.out.println("Error inesperado: " + e.getMessage());
                scanner.nextLine();
            }
        } while (opcion != 0);
        
        
        hiloProcesador.interrupt();
    }

    private void mostrarMenu() {
        System.out.println("\n--- Sistema de Pedidos ---");
        System.out.println("1. Registrar producto");
        System.out.println("2. Registrar cliente");
        System.out.println("3. Crear Pedido");
        System.out.println("4. Agregar producto a pedido");
        System.out.println("5. Ver detalles de pedido");
        System.out.println("6. Listar productos");
        System.out.println("7. Buscar productos por nombre");
        System.out.println("8. Guardar sistema manualmente");
        System.out.println("9. Cargar sistema desde archivos");
        System.out.println("10. Generar reporte del sistema");
        System.out.println("11. Ver estado de procesamiento");
        System.out.println("0. Salir y Guardar");
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

                case 8 -> guardarSistema();

                case 9 -> cargarSistema();

                case 10 -> generarReporteSistema(false);

                case 11 -> verEstadoProcesamiento();

                case 0 -> {
                    System.out.println("Guardando datos y saliendo...");
                    guardarSistema();
                }
                default -> System.out.println("Opcion no valida");
            }
        } catch (Exception e) {
            System.out.println("ERROR DE NEGOCIO: " + e.getMessage());
        }
    }

   

    public synchronized void registrarProducto() {
        System.out.print("ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        if (buscarProducto(id) != null) {
            System.out.println("Error: Ya existe un producto con ese ID.");
            return;
        }

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Precio: ");
        double precio = scanner.nextDouble();
        System.out.print("Stock: ");
        int stock = scanner.nextInt();

        productos.add(new Producto(id, nombre, precio, stock));
        System.out.println("Producto registrado.");
        guardarProductos(); 
    }

    public synchronized void registrarCliente() {
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
        else System.out.println("Tipo invalido.");
        
        guardarClientes(); 
    }

    public synchronized void crearPedido() {
        System.out.print("ID Pedido: ");
        int id = scanner.nextInt();
        if (buscarPedido(id) != null) {
            System.out.println("Error: ID ya existe.");
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
        System.out.println("Pedido creado.");
        guardarPedidos(); 
    }

    public synchronized void agregarProductoAPedido() throws Exception {
        System.out.print("ID Pedido: ");
        int idPedido = scanner.nextInt();
        Pedido pedido = buscarPedido(idPedido);
        if (pedido == null) throw new PedidoInvalidoException("El pedido no existe.");

        System.out.print("ID Producto a agregar: ");
        int idProd = scanner.nextInt();
        Producto prod = buscarProducto(idProd);
        if (prod == null) throw new ProductoNoEncontradoException("Producto no existe.");

        System.out.print("Cantidad: ");
        int cant = scanner.nextInt();

        pedido.agregarProducto(prod, cant);
        System.out.println("Producto agregado al pedido.");
        guardarPedidos(); 
    }

    

    public synchronized void procesarPedidosEnSegundoPlano() {
        boolean cambios = false;
        synchronized (pedidos) {
            for (Pedido p : pedidos) {
                if (p.getEstado().equals(Pedido.ESTADO_CONFIRMADO)) {
                    p.setEstado(Pedido.ESTADO_PROCESADO);
                    cambios = true;
                }
            }
        }
        if (cambios) {
            guardarPedidos(); 
        }
    }

    private void verEstadoProcesamiento() {
        int pendientes = 0, confirmados = 0, procesados = 0;
        synchronized (pedidos) {
            for (Pedido p : pedidos) {
                switch(p.getEstado()) {
                    case Pedido.ESTADO_BORRADOR -> pendientes++;
                    case Pedido.ESTADO_CONFIRMADO -> confirmados++;
                    case Pedido.ESTADO_PROCESADO -> procesados++;
                }
            }
        }
        System.out.println("\n--- ESTADO DE PROCESAMIENTO ---");
        System.out.println("En Borrador (Pendientes): " + pendientes);
        System.out.println("En Cola (Confirmados): " + confirmados);
        System.out.println("Finalizados (Procesados): " + procesados);
    }

    public synchronized void gestionarEstadoPedido() throws Exception {
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

    private void confirmarPedido(Pedido pedido) throws Exception {
        if (!pedido.getEstado().equals(Pedido.ESTADO_BORRADOR)) {
            throw new PedidoInvalidoException("Solo se confirman borradores.");
        }
        if (pedido.getDetalles().isEmpty()) {
            throw new PedidoInvalidoException("No se puede confirmar un pedido vacio.");
        }

        synchronized (productos) {
            for (DetallePedido dp : pedido.getDetalles()) {
                if (dp.getProducto().getStock() < dp.getCantidad()) {
                    throw new StockInsuficienteException("Stock insuficiente para " + dp.getProducto().getNombre());
                }
            }
            for (DetallePedido dp : pedido.getDetalles()) {
                Producto p = dp.getProducto();
                p.setStock(p.getStock() - dp.getCantidad());
            }
        }

        pedido.setEstado(Pedido.ESTADO_CONFIRMADO);
        System.out.println("Pedido Confirmado. Stock actualizado.");
        guardarSistema();
    }

    private void cancelarPedido(Pedido pedido) throws Exception {
        if (!pedido.getEstado().equals(Pedido.ESTADO_CONFIRMADO)) {
            throw new PedidoInvalidoException("Solo se pueden cancelar pedidos confirmados.");
        }

        synchronized (productos) {
            for (DetallePedido dp : pedido.getDetalles()) {
                Producto p = dp.getProducto();
                p.setStock(p.getStock() + dp.getCantidad());
            }
        }

        pedido.setEstado(Pedido.ESTADO_CANCELADO);
        System.out.println("Pedido Cancelado. Stock restaurado.");
        guardarSistema();
    }


    

    public void guardarSistema() {
        guardarProductos();

        guardarClientes();

        guardarPedidos();


        System.out.println("Sistema guardado exitosamente en archivos.");
    }

    public void cargarSistema() {
        cargarProductos();
        cargarClientes();
        cargarPedidos();
    }

    private synchronized void guardarProductos() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("productos.dat"))) {
            synchronized (productos) {
                for (Producto p : productos) {
                    oos.writeObject(p);
                }
            }
        } catch (IOException e) {
            System.out.println("Error guardando productos: " + e.getMessage());
        }
    }

    private synchronized void cargarProductos() {
        productos.clear();
        File f = new File("productos.dat");
        if (!f.exists()) return;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
            while (true) {
                Producto p = (Producto) ois.readObject();
                productos.add(p);
            }
        } catch (EOFException e) {
            
        } catch (Exception e) {
            System.out.println("Error cargando productos: " + e.getMessage());
        }
    }

    private synchronized void guardarClientes() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("clientes.dat"))) {
            synchronized (clientes) {
                for (Cliente c : clientes) {
                    oos.writeObject(c);
                }
            }
        } catch (IOException e) {
            System.out.println("Error guardando clientes.");
        }
    }

    private synchronized void cargarClientes() {
        clientes.clear();
        File f = new File("clientes.dat");
        if (!f.exists()) return;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
            while (true) {
                Cliente c = (Cliente) ois.readObject();
                clientes.add(c);
            }
        } catch (EOFException e) {
           
        } catch (Exception e) {
            System.out.println("Error cargando clientes.");
        }
    }

    private synchronized void guardarPedidos() {
        try (PrintWriter pw = new PrintWriter(new FileWriter("pedidos.txt"))) {
            synchronized (pedidos) {
                for (Pedido p : pedidos) {
                    pw.println(p.getId() + "|" + 
                               p.getCliente().getId() + "|" + 
                               p.getFechaCreacion().getTime() + "|" + 
                               p.getEstado() + "|" + 
                               p.calcularTotalFinal());
                }
            }
        } catch (IOException e) {
            System.out.println("Error guardando pedidos.");
        }
    }

    private synchronized void cargarPedidos() {
        pedidos.clear();
        File f = new File("pedidos.txt");
        if (!f.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split("\\|");
                int idPedido = Integer.parseInt(partes[0]);
                int idCliente = Integer.parseInt(partes[1]);
                long fechaMs = Long.parseLong(partes[2]);
                String estado = partes[3];

                Cliente cli = buscarCliente(idCliente);
                if (cli != null) {
                    Pedido p = new Pedido(idPedido, cli);
                    p.setFechaCreacion(fechaMs);
                    p.setEstado(estado);
                    pedidos.add(p);
                }
            }
        } catch (IOException e) {
            System.out.println("Error cargando pedidos.");
        }
    }

    public synchronized void generarReporteSistema(boolean silencioso) {
        try (PrintWriter pw = new PrintWriter(new FileWriter("reporte_sistema.txt"))) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            pw.println("--- REPORTE DEL SISTEMA ---");

            pw.println("Generado: " + sdf.format(new Date()));

            pw.println("Total Productos: " + productos.size());

            pw.println("Total Clientes: " + clientes.size());
            
            pw.println("\n--- ALERTAS DE STOCK (< 5) ---");
            synchronized (productos) {
                for (Producto p : productos) {
                    if (p.getStock() < 5) pw.println("- " + p.getNombre() + " (Stock: " + p.getStock() + ")");
                }
            }
            
            pw.println("\n--- INGRESOS CONFIRMADOS/PROCESADOS ---");
            double totalIngresos = 0;
            synchronized (pedidos) {
                for (Pedido p : pedidos) {
                    if (p.getEstado().equals(Pedido.ESTADO_CONFIRMADO) || p.getEstado().equals(Pedido.ESTADO_PROCESADO)) {
                        totalIngresos += p.calcularTotalFinal();
                    }
                }
            }
            pw.println("Total: $" + String.format("%.2f", totalIngresos));

            if (!silencioso) System.out.println("Reporte 'reporte_sistema.txt' generado exitosamente.");

        } catch (IOException e) {
            if (!silencioso) System.out.println("Error al generar reporte.");
        }
    }

    
    private void listarProductos() {
        System.out.println("Lista Productos");
        synchronized (productos) {
            for (Producto p : productos) System.out.println(p);
        }
    }

    private void listarPedidos() {
        System.out.println("Lista Pedidos");
        synchronized (pedidos) {
            for (Pedido p : pedidos) System.out.println(p);
        }
    }

    private void buscarProductosPorNombre() {
        System.out.print("Ingrese nombre a buscar: ");

        String busqueda = scanner.nextLine().trim().toLowerCase();

        boolean encontrado = false;
        synchronized (productos) {
            for (Producto p : productos) {
                if (p.getNombre().toLowerCase().contains(busqueda)) {
                    System.out.println(p);
                    encontrado = true;
                }
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
            for (DetallePedido det : p.getDetalles()) System.out.println(det);
        } else {
            System.out.println("Pedido no encontrado.");
        }
    }

    private Producto buscarProducto(int id) {
        synchronized (productos) {
            for (Producto p : productos) if (p.getId() == id) return p;
        }
        return null;
    }

    private Cliente buscarCliente(int id) {
        synchronized (clientes) {
            for (Cliente c : clientes) 
                
                if (c.getId() == id) return c;
        }
        return null;
    }

    private Pedido buscarPedido(int id) {
        synchronized (pedidos) {
            for (Pedido p : pedidos) 
                
                if (p.getId() == id) return p;
        }
        return null;
    }
}