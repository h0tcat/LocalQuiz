
package app;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button startGameButton;

    @FXML
    void ClickedStartGameButton(ActionEvent event) throws IOException{

			Main.page.setScene(new Scene(FXMLLoader.load((getClass().getResource("/Quiz.fxml")))));
    }

    @FXML
    void initialize() {
        assert startGameButton != null : "fx:id=\"startGameButton\" was not injected: check your FXML file 'Form.fxml'.";

    }
}
