package application;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
    public void start(Stage Stage_login) throws Exception{
    	Sql.init();
    	FXMLLoader loader = new FXMLLoader();
        URL location = getClass().getResource("login.fxml");
    	loader.setLocation(location);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        Parent Parent_login = loader.load();
        Stage_login.setTitle("Login UI");
        Stage_login.setScene(new Scene(Parent_login, 420, 280));
        Stage_login.show();
        
        Login_controller login_controller= loader.getController();
        login_controller.init(Stage_login);
    }

    public static void main(String[] args) {
        launch(args);
    }
}