package game;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.json.JSONException;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class QuizGame extends Application {
    private Button okButton = new Button("Ok");
    private Button checkAnswerButton = new Button("Check answer");
    private Button nextButton = new Button("Next");
    private Button nextQuestionButton = new Button("Next question");
    private int currentQuestion;
    private User user;
    private Quiz quiz;


    public void start(Stage primaryStage) throws Exception{
        // Initialize user object
        user = new User();
        quiz = new Quiz(user);

        // Create UI components
        Label welcomeLabel = new Label("Welcome to the quiz game! \n\nIn this quiz you can challenge your knowledge to answer different\n" +
                "questions with various topics.");
        Label nameLabel = new Label("Enter your name:");
        TextField nameTextField = new TextField();

        // If user presses Enter . The input is saved and Next button is pressed.
        setEnterKeyPressAction(nameTextField, nextButton);

        // Set button action
        nextButton.setOnAction(event -> {
            user.setName(nameTextField.getText());
            Label helloLabel = new Label("Hello, " + user.getName() + "!");

            // Create UI components for difficulty selection
            Label difficultyLabel = new Label("Select the level of quiz difficulty:");
            ChoiceBox levelSelection = new ChoiceBox();
            levelSelection.getItems().add("Easy");
            levelSelection.getItems().add("Medium");
            levelSelection.getItems().add("Hard");

            //When user presses ENTER, Next button is fired.
            setEnterKeyPressAction(levelSelection, nextButton);

            nextButton.setOnAction(event1 -> {
                    String level = (String) levelSelection.getValue();
                    quiz.setLevelOfDifficulty(level.toLowerCase());
                    Label questionLabel = new Label("How many questions would you like to answer? (1-50)");
                    TextField numberTextField = new TextField();
                    setEnterKeyPressAction(numberTextField, nextButton);

                    nextButton.setOnAction(event2 -> {
                        int numOfQuestions = Integer.parseInt(numberTextField.getText());
                        quiz.setNumOfQuestions(numOfQuestions);
                        Label startGameLabel = new Label("Lets start the game with " + quiz.getNumOfQuestions() + " " + quiz.getLevelOfDifficulty() +" questions!");
                        try {
                            quiz.readQuestionsURL();
                        } catch (IOException e) {
                            System.out.println(e.getMessage());
                        } catch (JSONException e) {
                            System.out.println(e.getMessage());
                        }

                        //Defining and initializing labels
                        Label quizQuestionLabel = new Label();
                        Label quizAnswerLabel = new Label();
                        Label quizCorrectLabel = new Label();
                        List<Question> questions = quiz.getQuestions();

                        //Defining and initializing radiobuttons
                        final ToggleGroup group = new ToggleGroup();
                        RadioButton rb1 = new RadioButton();
                        RadioButton rb2 = new RadioButton();
                        RadioButton rb3 = new RadioButton();
                        RadioButton rb4 = new RadioButton();
                        rb1.setUserData(0);
                        rb2.setUserData(1);
                        rb3.setUserData(2);
                        rb4.setUserData(3);

                        rb1.setToggleGroup(group);
                        rb1.setSelected(true);
                        rb2.setToggleGroup(group);
                        rb3.setToggleGroup(group);
                        rb4.setToggleGroup(group);

                        okButton.setOnAction(event3 -> {

                            final Question[] question = {questions.get(currentQuestion)};
                            quizQuestionLabel.setText((currentQuestion + 1) + "/" + questions.size() + " " + question[0].getQuestion());
                            AtomicReference<List<String>> answers = new AtomicReference<>(question[0].getAnswers());
                            rb1.setText(answers.get().get(0));
                            rb2.setText( answers.get().get(1));
                            rb3.setText(answers.get().get(2));
                            rb4.setText(answers.get().get(3));

                            checkAnswerButton.setOnAction(event4 -> {
                                question[0] = questions.get(currentQuestion);
                                String correctAnswer = question[0].getCorrectAnswer();
                                int selectedAnswerIndex = (int) group.getSelectedToggle().getUserData();
                                String userAnswer = question[0].getAnswers().get(selectedAnswerIndex);

                                if (userAnswer.equals(correctAnswer)) {
                                    user.addScore(1);
                                    quizAnswerLabel.setText("Correct answer! Your score is: " + user.getScore());
                                    quizCorrectLabel.setText("");
                                } else {
                                    quizAnswerLabel.setText("Incorrect answer! Your score is: " + user.getScore());
                                    quizCorrectLabel.setText("Correct answer was: " + correctAnswer);
                                }
                            });

                            nextQuestionButton.setOnAction((event5 -> {
                                quizAnswerLabel.setText("Your score is: " + user.getScore());
                                quizCorrectLabel.setText("");
                                currentQuestion++;
                                if (currentQuestion < questions.size()) {
                                    question[0] = questions.get(currentQuestion);
                                    quizQuestionLabel.setText((currentQuestion +1 )+ "/" + questions.size() + " " + question[0].getQuestion());
                                    answers.set(question[0].getAnswers());
                                    rb1.setText(answers.get().get(0));
                                    rb2.setText(answers.get().get(1));
                                    rb3.setText(answers.get().get(2));
                                    rb4.setText(answers.get().get(3));
                                    rb1.setSelected(true);
                                } else {
                                    showAlert("Quiz ended. Final score: " + user.getScore());
                                }
                            }));


                            VBox gameLayout = new VBox(10);
                            gameLayout.setPadding(new Insets(20));
                            Scene gameScene = new Scene(gameLayout, 600, 300, Color.SNOW);

                            // Create a VBox layout and add components
                            gameLayout.getChildren().addAll(
                                    quizQuestionLabel, rb1, rb2, rb3, rb4,
                                    checkAnswerButton ,nextQuestionButton,
                                    quizAnswerLabel, quizCorrectLabel);

                            // Create the scene
                            // Set the scene on the primary stage
                            if (group.getSelectedToggle() == rb1)
                            primaryStage.setScene(gameScene);

                        });
                        // Create a VBox layout and add components
                        VBox startGameLayout = new VBox(10);
                        startGameLayout.setPadding(new Insets(20));
                        startGameLayout.getChildren().addAll(startGameLabel, okButton);
                        // Create the scene
                        Scene startGameScene = new Scene(startGameLayout, 400, 250, Color.SNOW);
                        // Set the scene on the primary stage
                        primaryStage.setScene(startGameScene);
                    });

                    // Create a VBox layout and add components
                    VBox numOfQuestionLayout = new VBox(10);
                    numOfQuestionLayout.setPadding(new Insets(20));
                    numOfQuestionLayout.getChildren().addAll(questionLabel, numberTextField, nextButton);

                    // Create the scene
                    Scene numOfQuestionScene = new Scene(numOfQuestionLayout, 400, 250, Color.SNOW);

                    // Set the scene on the primary stage
                    primaryStage.setScene(numOfQuestionScene);
                    });

            // Create a VBox layout and add components
            VBox difficultyLayout = new VBox(10);
            difficultyLayout.setPadding(new Insets(20));
            difficultyLayout.getChildren().addAll(helloLabel, difficultyLabel, levelSelection, nextButton);
            // Create the scene
            Scene difficultyScene = new Scene(difficultyLayout, 400, 250, Color.SNOW);
            // Set the scene on the primary stage
            primaryStage.setScene(difficultyScene);
        });
        primaryStage.setOnHiding(event -> {
            Stage endGame = new Stage();
            // Making question and two buttons
            Label label = new Label("Do you really want to quit?");
            Button yesButton = new Button("Yes");
            Button noButton = new Button("No");

            // sündmuse lisamine nupule Jah
            yesButton.setOnAction(closeEvent -> {
                    endGame.hide();
            });

            // sündmuse lisamine nupule Ei
            noButton.setOnAction(closeEvent -> {
                    primaryStage.show();
                    endGame.hide();

            });
            VBox endGameLayout = new VBox(10);
            endGameLayout.setAlignment(Pos.CENTER);
            endGameLayout.setPadding(new Insets(20));

            Label quitLabel = new Label("Do you really want to quit?");

            HBox buttonLayout = new HBox(10);
            buttonLayout.setAlignment(Pos.CENTER);
            buttonLayout.getChildren().addAll(yesButton, noButton);

            endGameLayout.getChildren().addAll(quitLabel, buttonLayout);

            Scene endGameScene = new Scene(endGameLayout, 400, 250);
            endGame.setScene(endGameScene);
            endGame.setTitle("Confirmation");
            endGame.show();
        });

        // Create a VBox layout and add components
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));
        vbox.getChildren().addAll(welcomeLabel, nameLabel, nameTextField, nextButton);

        // Create the scene
        Scene scene = new Scene(vbox, 400, 250, Color.SNOW);

        // Set stage properties
        primaryStage.setTitle("Quiz Game");
        primaryStage.setResizable(true);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showAlert(String message) {
        Stage alertStage = new Stage();
        Label label = new Label(message);
        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> {
            alertStage.close();
            System.exit(0);
        });

        VBox alertLayout = new VBox(10);
        alertLayout.setAlignment(Pos.CENTER);
        alertLayout.setPadding(new Insets(20));
        alertLayout.getChildren().addAll(label, closeButton);

        alertStage.setScene(new Scene(alertLayout, 200, 150));
        alertStage.setTitle("Game over!");
        alertStage.show();
    }
    private void setEnterKeyPressAction(Node node, Button button) {
        node.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                button.fire();
            }
        });
    }

}