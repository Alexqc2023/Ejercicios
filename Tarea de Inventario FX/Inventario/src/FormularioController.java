import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class FormularioController {

    @FXML private TextField txtNombre;
    @FXML private TextField txtCategoria;
    @FXML private TextField txtPrecio;
    @FXML private TextField txtCantidad;

    @FXML private Button btnGuardar;
    @FXML private Button btnCancelar;

    private ObservableList<Producto> listaInventario;

    public void setListaInventario(ObservableList<Producto> lista){
        this.listaInventario = lista;
    }

    
    @FXML
    void Guardardatos(ActionEvent event){

        String nombre = txtNombre.getText();
        String categoria = txtCategoria.getText();
        String precioTexto = txtPrecio.getText();
        String cantidadTexto = txtCantidad.getText();

        if (nombre.isEmpty() || categoria.isEmpty() || precioTexto.isEmpty() || cantidadTexto.isEmpty()){

            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setHeaderText("espacios vacios");
            alerta.setContentText("Llena todos los campos por favor");

            alerta.showAndWait();

            return;




        }


        try {


          double precioVenta = Double.parseDouble(precioTexto);
          int Stock = Integer.parseInt(cantidadTexto);

          Producto nuevoProducto = new Producto(nombre, categoria, precioVenta, Stock);

          listaInventario.add(nuevoProducto);

          Alert exito = new Alert(Alert.AlertType.INFORMATION);
          exito.setHeaderText("Exito");
          exito.setContentText("El producto ha sido guardado en el inventario de forma correcta");
          exito.showAndWait();

          cerrarVentana();
            


            
        } catch (NumberFormatException e) {

            Alert errorNumeros = new Alert(Alert.AlertType.ERROR);
            errorNumeros.setHeaderText("Datos invalidos");
            errorNumeros.setContentText("El precio y la cantidad deben ser numeros para que sea valido");
            errorNumeros.showAndWait();

            
        }




        
    }


    @FXML
    void CancelarAccion(ActionEvent event){
        cerrarVentana();
    }


    private void cerrarVentana(){


        javafx.stage.Stage stage = (javafx.stage.Stage) btnCancelar.getScene().getWindow();
        stage.close();


    }




}
