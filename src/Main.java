
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Clase Main que muestra la interfaz grafica. 
 * @author Alfredo Quezada 
 * @version prueba
 * @since 16/3/2020
 */
public class Main extends Application {

    /**
     * For init the app
     * @param args the default instance args of the Main
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * para moestrar el JavaFX Stage
     * @param primaryStage unico JavaFX Stage mostrado en pantalla. 
     */
    @Override
    public void start(Stage primaryStage) {
        BinaryTreeTranslatorView binaryTreeTranslatorView = new BinaryTreeTranslatorView();
        binaryTreeTranslatorView.show(primaryStage);
    }


}
