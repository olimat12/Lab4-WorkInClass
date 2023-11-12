package edu.utsa.rqo215_lab4.model;

import android.app.Activity;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.util.Scanner;

public class Trivia {
    private String question;
    private String option1;
    private String option2;
    private String option3;

    //private String[] options = new String[3];

    private String description;

    //Identify which answer is correct
    private String correctAnswer;

    public Trivia(String question, String option1, String option2, String option3, String description) {
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
    }

    public Trivia() {
        System.out.println("Explicit Trivia default constructor");
    }

    //Setters and getters
    public void setQuestion(String question){
        this.question = question;
    }

    public String getQuestion(){
        return this.question;
    }

    public void setOption1(String option1){
        this.option1 = option1;
    }

    public String getOption1(){
        return this.option1;
    }

    public void setOption2(String option2){
        this.option2 = option2;
    }

    public String getOption2(){
        return this.option2;
    }

    public void setOption3(String option3){
        this.option3 = option3;
    }

    public String getOption3(){
        return this.option3;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getDescription(){
        return this.description;
    }

    public void setCorrectAnswer(String correctAnswer){
        this.correctAnswer = correctAnswer;
    }

    public String getCorrectAnswer(){
        return this.correctAnswer;
    }

    /*
    @param: Activity activity
    @param: Trivia object
    loadTrivia: this method takes in an activity (MainActivity) from input as an argument,
    it reads the file and stores one piece of trivia from that file
     */

    public Trivia loadTrivia(Activity activity) {
        //Get an instance of AssetManager
        //Read file in assets using inputStream
        //How many lines are in this file, the number of lines are equivalent to the number of trivia questions
        //Randomly select a number that is smaller than or equal to the number of lines
        //Create an object of Trivia from the line's information
        //Return the object

        AssetManager manager = activity.getAssets();
        Scanner scanner;
        try {
            InputStream input = manager.open("trivia.csv");
            scanner = new Scanner(input);
            int i = 0;
            while(scanner.hasNextLine()){
                i++;
            }
            System.out.println("Number of lines in file: " + i);
            //Randomly select a number from 1 to i
            SecureRandom sRandom = new SecureRandom();
            //+1 Necessary to output correct lines, instead of 0-6, now 1-7
            int lineNumber = sRandom.nextInt(i) + 1;

            int j = 1;
            String line = "";
            while(j < lineNumber){
                line = scanner.nextLine();
                j++;
            }
            //When loop is over, we are processing the right directly before the one we want to return, so...
            line = scanner.nextLine();
            String[] lineSplit = line.trim().split(",");
            this.question = lineSplit[0].trim();
            this.option1 = lineSplit[1].trim();
            this.option2 = lineSplit[2].trim();
            this.option3 = lineSplit[3].trim();
            this.description = lineSplit[4];
            //From index 5 forward is the description
            for(int k = 5; k < lineSplit.length; k++) {
                this.description = this.description + "," + lineSplit[k];
            }
            identifyCorrectAnswer();
            return (this);
        }
        catch(FileNotFoundException e){
            Log.d("File Exception", "File not found");
        }
        catch(IOException e){
            Log.d("File Exception","IO exception, which is super class of FileNotFoundException");
        }

        return (this);
    }

    private void identifyCorrectAnswer(){
        //takes all the options and check if the description contains any of the options, if so, that option becomes the correct answer
        if(this.description.contains(this.option1)){
            this.correctAnswer = this.option1;
        }
        else if(this.description.contains(this.option2)){
            this.correctAnswer = this.option2;
        }
        else{
            this.correctAnswer = this.option3;
        }
    }
}