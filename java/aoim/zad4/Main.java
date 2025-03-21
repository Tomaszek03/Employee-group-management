package aoim.zad4;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainScreen.fxml"));
        AnchorPane anchorPane = fxmlLoader.load();
        Scene scene = new Scene(anchorPane, 800, 600);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Grupy pracownicze");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
