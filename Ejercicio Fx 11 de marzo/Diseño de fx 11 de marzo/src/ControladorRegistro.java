import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ControladorRegistro {

    
    @FXML private TextField txtNombre;

    @FXML private TextField txtPrecio;

    @FXML private TextField txtCantidad;

    @FXML private Button btnAgregar;

    @FXML private Button btnEliminar;

    @FXML private Label lblError;
    @FXML private TableView<Producto> tabla;

    
    private ObservableList<Producto> productos = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        
       
        TableColumn<Producto, String> colNombre = new TableColumn<>("Nombre");
        TableColumn<Producto, Double> colPrecio = new TableColumn<>("Precio");
        TableColumn<Producto, Integer> colCantidad = new TableColumn<>("Cantidad");

        
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));

        
        colPrecio.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Double valor, boolean empty) {
                super.updateItem(valor, empty);
                setText(empty || valor == null ? null : String.format("%.2f", valor));
            }
        });

        
        tabla.getColumns().addAll(colNombre, colPrecio, colCantidad);

      
        productos.add(new Producto("Laptop", 850.00, 5));
        productos.add(new Producto("Mouse", 12.50, 30));
        productos.add(new Producto("Teclado", 35.00, 15));
        
        
        tabla.setItems(productos);


       
        btnAgregar.setOnAction(e -> {
            
            lblError.setText(""); 
            
            String nombre = txtNombre.getText().trim();

            if (nombre.isEmpty()) {
                lblError.setText("Error: El nombre no puede estar vacio.");
                return;
            }

            try {
                double precio = Double.parseDouble(txtPrecio.getText().trim());
                int cantidad = Integer.parseInt(txtCantidad.getText().trim());

                if (precio < 0 || cantidad < 0) {
                    lblError.setText("Error: No se permiten valores negativos.");
                    return;
                }

                
                Producto nuevoProducto = new Producto(nombre, precio, cantidad);
                productos.add(nuevoProducto);

               
                txtNombre.clear();
                txtPrecio.clear();
                txtCantidad.clear();

            } catch (NumberFormatException ex) {
               
                lblError.setText("Error: El precio y la cantidad deben ser numeros.");
            }
        });


        
        btnEliminar.setOnAction(e -> {
           
            Producto seleccionado = tabla.getSelectionModel().getSelectedItem();

            if (seleccionado != null) {
               
                productos.remove(seleccionado);
                lblError.setText(""); 
            } else {
                lblError.setText("Error: Selecciona un producto de la tabla para eliminarlo.");
            }
        });
    }
}