import java.io.File;
import java.io.FileWriter;
import javafx.scene.control.Label;
import java.util.Scanner;

import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class VentanaPrincipal {
    

    @FXML private TableView<Producto> tablaProductos;
    @FXML private TableColumn<Producto, String> colNombre;
    @FXML private TableColumn<Producto, String > colCategoria;
    @FXML private TableColumn<Producto, Double> colPrecio;
    @FXML private TableColumn<Producto, Integer> colCantidad;

    @FXML private ProgressBar barraProgreso;
    @FXML private Label lblEstado;

    private ObservableList<Producto> listaInventario = FXCollections.observableArrayList();


    @FXML
    public void initialize(){

        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        colPrecio.setCellValueFactory( new PropertyValueFactory<>("precio"));
        colCantidad.setCellValueFactory( new PropertyValueFactory<>("cantidad"));

        tablaProductos.setItems(listaInventario);

        



    }

    @FXML
    void AgregarInventario(ActionEvent event){

        try {
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SegundaVentana.fxml"));
            Parent root = loader.load();

            FormularioController controlador = loader.getController();
            controlador.setListaInventario(listaInventario);

            Stage stage = new Stage();
            stage.setTitle("Agregar producto");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);

            stage.showAndWait();


        } catch (Exception e) {
            e.printStackTrace();
        }



    }

    @FXML
    void EliminarProducto(ActionEvent event){

        Producto seleccionado = tablaProductos.getSelectionModel().getSelectedItem();

        if (seleccionado == null){

            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setHeaderText("Seleccione un producto para continuar");
            alerta.setContentText(" debes seleccionar uno obligatoriamente");
            alerta.showAndWait();

            return;
        }




        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setHeaderText("Confirmar eliminacion");
        confirmacion.setContentText("seguro que quieres eliminar :" + seleccionado);



        if (confirmacion.showAndWait().get() == javafx.scene.control.ButtonType.OK){
            listaInventario.remove(seleccionado);
        }



    }


    @FXML
    void GuardarProducto(ActionEvent event){
        if (listaInventario.isEmpty()) {

            Alert vacio = new Alert(Alert.AlertType.WARNING);
            vacio.setHeaderText("Inventario vacio");
            vacio.setContentText("no hay nada que guardar");
            vacio.showAndWait();
            return;


        }

       lblEstado.setText("Guardando archivo..");

        Thread hilo = new Thread(() -> {
            try {
                
                FileWriter escritor = new FileWriter("inventario.txt");

                for (Producto p : listaInventario) {
                    escritor.write(p.toString() + "\n");
                    Thread.sleep(200);
                }

                escritor.close();

                Platform.runLater(() -> {
                    lblEstado.setText("Guardado de forma correcta");
                    barraProgreso.setProgress(1.0);
                });

            } catch (Exception e) {
                Platform.runLater(() -> lblEstado.setText("Error al guardar"));
            }
        });

        hilo.setDaemon(true);
        hilo.start();
    }

    @FXML
    void CargarProducto(ActionEvent event){
        lblEstado.setText("Cargando Inventario ...");
        listaInventario.clear();
        barraProgreso.setProgress(0.0);

        
        Thread hiloLectura = new Thread(() -> {
            try {
                File archivo = new File("inventario.txt");

                if(!archivo.exists()){
                    Platform.runLater(() -> lblEstado.setText("No hay inventario para cargar"));
                    return;
                }

                Scanner lector = new Scanner(archivo);
                
                while (lector.hasNextLine()){
                    String linea = lector.nextLine();
                    String[] partes = linea.split(",");

                    String nom = partes[0];
                    String cat = partes[1];
                    double pre = Double.parseDouble(partes[2]);
                    int cant = Integer.parseInt(partes[3]);

                    Producto p = new Producto(nom, cat, pre, cant);

                    Platform.runLater(() -> listaInventario.add(p));
                    Thread.sleep(200);
                }
                
                lector.close();
                Platform.runLater(() -> {
                    lblEstado.setText("Carga completa");
                    barraProgreso.setProgress(1.0);
                });

            } catch (Exception e) {
                Platform.runLater(() -> lblEstado.setText("Error al cargar"));
            }
        });

        hiloLectura.setDaemon(true);
        hiloLectura.start();
    }
}