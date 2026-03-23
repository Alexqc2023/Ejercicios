import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App  extends Application{

    @Override
    public void start(Stage escenarioPrincipal) throws Exception{


        Parent raiz = FXMLLoader.load(getClass().getResource("VentanaPrincipal.fxml"));

        Scene escena = new Scene(raiz);

        escenarioPrincipal.setTitle("Gestor de Inventario");
        escenarioPrincipal.setScene(escena);

        escenarioPrincipal.setResizable(false);

        escenarioPrincipal.show();


        


    }
    

    public static void main(String[] args) {
        
        launch(args);
    }

}
