package app;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Random;

public class Quiz{
	
	private Random rand;
	private static boolean debugMode=true;

	protected static String id;

	private static String areaCode;

	private ObjectMapper mapper;
	private JsonNode root;
	private String text,jsonPath;

	private File file;

	private int quizID,randQuizID;

	protected static boolean judgeResult;
	public Quiz() throws URISyntaxException, IOException {
		this.mapper=new ObjectMapper();
		this.rand=new Random();

		if(debugMode) {
			this.file = new File(getClass().getResource("/sample.json").toURI());
			this.root=mapper.readTree(this.file);
		}
		else {

			this.file = new File(getClass().getResource("/data.json").toURI());
			this.root = mapper.readTree(this.file);
		}
		this.quizID=this.rand.nextInt(this.root.size());
		this.randQuizID=this.rand.nextInt(this.root.size());
	}

	private String answerPath;
	public String getAnswer(){
		if(debugMode)
			this.jsonPath=String.format("/%d/quiz/commentary/answer",this.quizID);
		else
			this.jsonPath=String.format("/%d/quiz/commentary/answer",randQuizID);

		this.answerPath=this.root.at(jsonPath).asText();

		return answerPath;
	}

	private String answerCommentarPath;
	public String getCommentary(){

		if(debugMode)
			this.jsonPath=String.format("/%d/quiz/commentary/text",this.quizID);
		else
			this.jsonPath=String.format("/%d/quiz/commentary/text",randQuizID);

		this.answerCommentarPath=this.root.at(jsonPath).asText();
		return this.answerCommentarPath;
	}

	private String choicePath;
	public String getChoice(int index){
		if(debugMode)
			this.jsonPath=String.format("/%d/quiz/choices",this.quizID);
		else
			this.jsonPath=String.format("/%d/quiz/choices",randQuizID);

		this.choicePath=this.root.at(jsonPath).get(index).get("text").asText();
		return choicePath;
	}

	public String getText(){


		if(debugMode)
			this.jsonPath=String.format("/%d/quiz/text",this.quizID);
		else
			this.jsonPath=String.format("/%d/quiz/text",randQuizID); // 存在するクイズ数が乱数の最大値を指す。

		this.text=this.root.at(jsonPath).asText();
		return this.text;

	}

}
