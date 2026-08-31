package doge;

import java.io.IOException;

import doge.ui.MainWindow;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for Duke using FXML.
 */
public class Main extends Application {

    private final Doge doge = new Doge();

    @Override
    public void start(Stage stage) {
        try {
            stage.setMinHeight(400);
            stage.setMinWidth(900);
            stage.setMaxWidth(900);
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setDoge(doge); // inject the Doge instance
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

