
package app;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.media.AudioClip;
import javafx.scene.text.Text;

public class QuizController {


    @FXML
    private CheckBox firstChoice;

    @FXML
    private CheckBox secondChoices;

    @FXML
    private CheckBox thirdChoices;

    @FXML
    private Button answerButton;

    @FXML
    private Text quizText;

    private AudioClip[] sound;

    private CheckBox[] judgeBox; //判定時に使うチェックボックス格納用配列変数

    @FXML
    void ClickFirstChoice() {
        this.secondChoices.setSelected(false);
        this.thirdChoices.setSelected(false);
    }

    @FXML
    void ClickSecondChoice() {
        this.firstChoice.setSelected(false);
        this.thirdChoices.setSelected(false);
    }

    @FXML
    void ClickThirdChoice() {
        this.firstChoice.setSelected(false);
        this.secondChoices.setSelected(false);
    }

    @FXML
    void ClickedAnserButton() throws IOException {

        //どれか必ず問題の選択がされているはず
        boolean isChecked= this.firstChoice.isSelected() ||
                this.secondChoices.isSelected() ||
                this.thirdChoices.isSelected();

        if(isChecked){
           Quiz.judgeResult=this.Judge();
           if(Quiz.judgeResult) { //judgeResultがtrueと等しいとき、正解したことを意味する。
               this.sound[1].play();
               Main.page.setScene(new Scene(FXMLLoader.load((getClass().getResource("/fxml/Result.fxml")))));
           }else{
               this.sound[2].play();
               Main.page.setScene(new Scene(FXMLLoader.load((getClass().getResource("/fxml/Result.fxml")))));
           }
        }
    }

    private boolean Judge(){
        int truedIndex=-1; // チェックされているjudgeBoxのインデックス。
        for(int i=0;i<this.judgeBox.length;i++){
            if(this.judgeBox[i].isSelected()) {
                truedIndex = i;
                break;
            }
        }


        return this.judgeBox[truedIndex].getText().equals(Main.quiz.getAnswer());
    }

    @FXML
    void initialize() throws URISyntaxException, IOException, InterruptedException {
        assert quizText != null : "fx:id=\"QuizText\" was not injected: check your FXML file 'Quiz.fxml'.";
        assert firstChoice != null : "fx:id=\"firstChoice\" was not injected: check your FXML file 'Quiz.fxml'.";
        assert secondChoices != null : "fx:id=\"secondChoices\" was not injected: check your FXML file 'Quiz.fxml'.";
        assert thirdChoices != null : "fx:id=\"thirdChoices\" was not injected: check your FXML file 'Quiz.fxml'.";
        assert answerButton != null : "fx:id=\"answerButton\" was not injected: check your FXML file 'Quiz.fxml'.";

        Thread.sleep(1000); //少し間を空ける
        sound=new AudioClip[3];
        sound[0]=new AudioClip(getClass().getResource("/sound/クイズ出題1.mp3").toURI().toString());
        sound[1]=new AudioClip(getClass().getResource("/sound/クイズ正解1.mp3").toURI().toString());

        sound[2]=new AudioClip(getClass().getResource("/sound/クイズ不正解1.mp3").toURI().toString());
        sound[0].play();

        Main.quiz=new Quiz();

        this.quizText.wrappingWidthProperty().bind(Main.scene.widthProperty().subtract(13));

        ArrayList<String> quizChoices= new ArrayList<>();
        this.quizText.setText(Main.quiz.getText());

        this.judgeBox=new CheckBox[3];

        for(int i=0;i<3;i++){
            quizChoices.add(Main.quiz.getChoice(i));
        }
        Collections.shuffle(quizChoices);
        for(int i=0;i<3;i++) {

            if(i % 3==0) {
                this.judgeBox[0] = this.firstChoice;
                this.firstChoice.setText(quizChoices.get(i));
            }
            else if(i % 3==1) {
                this.judgeBox[1] = this.secondChoices;
                this.secondChoices.setText(quizChoices.get(i));
            }
            else {
                this.judgeBox[2]=this.thirdChoices;
                this.thirdChoices.setText(quizChoices.get(i));
            }
        }

    }
}
