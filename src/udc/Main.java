package udc;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import udc.objects.time.concrete.Agenda;

import java.time.format.DateTimeFormatter;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Model model = new Model();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));

        Parent root = loader.load();
        primaryStage.setScene(new Scene(root));
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();

        loader.<Controller>getController().setModel(model);
        loader.<Controller>getController().setStage(primaryStage);
    }


    public static void main(String[] args) {
        launch(args);
    }
}