import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage ventanaPrincipal) throws Exception {
        
        Parent raiz = FXMLLoader.load(getClass().getResource("Registro.fxml"));

        ventanaPrincipal.setTitle(" Registro ");
        ventanaPrincipal.setScene(new Scene(raiz, 600, 500));
        ventanaPrincipal.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}