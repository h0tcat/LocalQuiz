package app;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class ResultController {
    @FXML
    private Text answerResult;

    @FXML
    private Text commentaryText;

    @FXML
    private Button backTitleButton;

    @FXML
    void clickBackTitleButton() throws IOException {

        Main.page.setScene(new Scene(FXMLLoader.load((getClass().getResource("/Form.fxml")))));
    }

    @FXML
    void initialize() {
        assert answerResult != null : "fx:id=\"answerResult\" was not injected: check your FXML file 'Result.fxml'.";
        assert commentaryText != null : "fx:id=\"commentaryText\" was not injected: check your FXML file 'Result.fxml'.";
        assert backTitleButton != null : "fx:id=\"backTitleButton\" was not injected: check your FXML file 'Result.fxml'.";


        this.commentaryText.wrappingWidthProperty().bind(Main.scene.widthProperty().subtract(13));
        if(Quiz.judgeResult) {//judgeResultがtrueと等しいとき、正解したことを意味する。
            this.answerResult.setText("正解!");
            this.commentaryText.setText(Main.quiz.getCommentary());
        }else{
            this.answerResult.setText("残念...");
            this.commentaryText.setText(Main.quiz.getCommentary());
        }

    }
}
