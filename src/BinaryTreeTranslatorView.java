//Importacion de todos los paquetes a usar. 
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.*;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * Class that manages the operations between the user interface and the logic of the program
 * manages the JavaFx stages and operates in the BinarySearchTree instance to feed the user
 * @author Alfredo Quezada 
 * @version prueba 
 * @since  17/03/2020
 */
public class BinaryTreeTranslatorView {

    /**
     * TextAreas para mostrar los resultados y el FileChooser
     */
    private TextArea inputTextArea;
    private TextArea outputTextArea;
    private FileChooser fileChooser;
    /**
     * Se llama y empieza el BinarySearchTree
     */
    private final BinarySearchTree<Association<String, String>> myBinarySearchTree = new BinarySearchTree<>();
    /**
     * Control variables
     */
    private boolean isDictionaryLoaded, isTextToTranslateLoaded = false;

    /**
     * Muestra la etapa 
     * 
     * @param stage La etapa que muestra el estado actual. 
     */
    public void show(final Stage stage) {
        inputTextArea = new TextArea("");
        outputTextArea = new TextArea("...");
        fileChooser = new FileChooser();

        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"));

        final String currentPath = Paths.get(".").toAbsolutePath().normalize().toString();
        fileChooser.setInitialDirectory(new File(currentPath));

        final BorderPane border = new BorderPane();
        final HBox hbox = addHBox(stage);
        border.setTop(hbox);
        border.setLeft(addVBox());

        final Scene scene = new Scene(border, 800, 600);
        stage.setTitle("Traductor");
        stage.setScene(scene);
        stage.show();

    }

    /**
     * Se llama el Hbox que contiene botones de leer y limpiar. 
     * @param stage Se renderiza la Interfaz. 
     * @return Regresa la Hbox ya llena.  
     */
    public HBox addHBox(final Stage stage) {
        final HBox hbox = new HBox();
        hbox.setPadding(new Insets(15, 12, 15, 12));
        hbox.setSpacing(6);
        hbox.setStyle("-fx-background-color: #455a64;");

        final Button buttonLoadTextToTranslate = new Button("Cargar el texto a traducir...");
        buttonLoadTextToTranslate.setPrefSize(200, 20);
        buttonLoadTextToTranslate.setOnAction(e -> {
            final File selectedFile = fileChooser.showOpenDialog(stage);
            if (selectedFile != null) {
                BufferedReader bufferedReader = null;
                try {
                    bufferedReader = new BufferedReader(new FileReader(selectedFile));
                    String text;
                    while ((text = bufferedReader.readLine()) != null) {
                        inputTextArea.appendText("\n" + text);
                    }

                } catch (final FileNotFoundException ex) {
                    Logger.getLogger(BinaryTreeTranslatorView.class.getName()).log(Level.SEVERE, null, ex);
                } catch (final IOException ex) {
                    Logger.getLogger(BinaryTreeTranslatorView.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        bufferedReader.close();
                    } catch (final IOException ex) {
                        Logger.getLogger(BinaryTreeTranslatorView.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                // TODO: muestra ya el anuncio de carga.
                isTextToTranslateLoaded = true;

                // El anuncio que muestra la carga 
                final Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Archivo de texto,cargado!");
                alert.setHeaderText("El archivo de texto fue cargado con exito");
                alert.setContentText("Ya puedes traducir tu texto :) ");
                alert.showAndWait();

            }
        });

        // Boton para cargar archivo. 
        final Button buttonLoadDictionary = new Button("Cargar Diccionario.");
        buttonLoadDictionary.setPrefSize(180, 20);
        buttonLoadDictionary.setOnAction(e -> {
            final File selectedFile = fileChooser.showOpenDialog(stage);
            if (selectedFile != null) {

                BufferedReader bufferedReader = null;
                try {
                    bufferedReader = new BufferedReader(new FileReader(selectedFile));
                    String text;
                    while ((text = bufferedReader.readLine()) != null) {
                        if (text.charAt(0) == '(') {
                            text = text.substring(1);
                        }
                        if (text.charAt(text.length() - 1) == ')') {
                            text = text.substring(0, text.length() - 1);
                        }
                        final String[] temp = text.split(",");
                        if (temp.length > 1) {
                            // Crea una nueva associacion con el valor de temp[0] = palabra en ingles. 
                            // temp[1] = palabra en español 
                            final Association<String, String> a = new Association(temp[0], temp[1]);
                            myBinarySearchTree.add(a);
                        }
                    }

                } catch (final FileNotFoundException ex) {
                    Logger.getLogger(BinaryTreeTranslatorView.class.getName()).log(Level.SEVERE, null, ex);
                } catch (final IOException ex) {
                    Logger.getLogger(BinaryTreeTranslatorView.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        bufferedReader.close();
                    } catch (final IOException ex) {
                        Logger.getLogger(BinaryTreeTranslatorView.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                isDictionaryLoaded = true;

                final List<Association<String, String>> list = myBinarySearchTree.inOrder();
                String dictionaryContent = "";

                for (final Association association : list)
                    dictionaryContent += association.getKey() + "," + association.getValue() + "\n";

                // Mensaje de alerta por si falla en la carga. 
                final Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Diccionario cargado!");
                alert.setHeaderText("Tu diccionario fue cargado con exito :) ..");
                alert.setContentText("Contiene estas palabras:\n" + dictionaryContent);
                alert.showAndWait();
            }
        });

        // Este es el boton que inicia la traduccion. 
        final Button buttonRun = new Button("Traducir");
        buttonRun.setPrefSize(100, 20);
        buttonRun.setStyle("-fx-background-color: #388e3c;");
        buttonRun.setOnAction(e -> {

            if (isTextToTranslateLoaded && isDictionaryLoaded) {
                // Separa el inputTextArea en lineas y las guarda como Array
                final List<String> initLines = Arrays.asList(inputTextArea.getText().split("\n"));

                for (final String line : initLines) {
                    
                    final String[] textToTranslate = line.split(" ");
                    for (final String word : textToTranslate) {
                        // Para cada palabra obtiene la asociacion que coincide con el key
                        final Association<String, String> a = myBinarySearchTree.get(new Association<>(word, null));
                        if (a != null) {
                            // Si es que existe una asociacion la agrega en el outputTextArea 
                            outputTextArea.appendText(a.getValue().toString());
                        } else {
                            outputTextArea.appendText(" " + "*" + word + "*" + " ");
                        }
                    }
                    outputTextArea.appendText("\n");

                }

            } else {
                // Mensaje de error por si la carga del diccionario o el texto no es correcta.  
                final Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error");
                alert.setHeaderText("Cargar archivos");
                alert.setContentText("El texto o el diccionario no se ha podido cargar :( ...");
                alert.showAndWait();

            }

        });

        try {
            Thread.sleep(3000);
        } catch (final InterruptedException e1) {
            e1.printStackTrace();
        }

        outputTextArea.appendText("Output" + "\n");

        // Boton para limpiar los TextArea. 
        final Button buttonClear = new Button("Limpiar");
        buttonClear.setPrefSize(100, 20);
        buttonClear.setOnAction(e -> {
            inputTextArea.clear();
            outputTextArea.clear();
            outputTextArea.appendText("Output" + "\n");
            isDictionaryLoaded = isTextToTranslateLoaded = false;
        });

        hbox.getChildren().addAll(buttonLoadDictionary, buttonLoadTextToTranslate, buttonClear, buttonRun);

        return hbox;
    }

    /**
     * Agrega un TextArea a la pantalla y lo muetras en el output del TextArea
     *
     * @return Se agrega la HBox a la Interfaz. 
     */
    public VBox addVBox() {
        final VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(8);

        //Agregar el TextArea a la VBox
        inputTextArea.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        vbox.getChildren().add(inputTextArea);

        outputTextArea.setFont(Font.font("Arial", FontWeight.MEDIUM, 14));
        vbox.getChildren().add(outputTextArea);


        return vbox;
    }



}
