package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.*;
import javafx.geometry.*;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.util.Map;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        Map<String, Integer> dayQuestions = Map.of(
            "Monday", 2,
            "Tuesday", 1,
            "Wednesday", 4,
            "Thursday", 3,
            "Friday", 3,
            "Saturday", 2,
            "Sunday", 1
        );

        BorderPane root = new BorderPane();

        GridPane calendar = new GridPane();
        calendar.setHgap(10);
        calendar.setVgap(10);
        calendar.setPadding(new Insets(10));

        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        for (int i = 0; i < days.length; i++) {
            Label dayLabel = new Label(days[i].substring(0, 2));
            dayLabel.setStyle("-fx-font-weight: bold; -fx-alignment: center;");
            dayLabel.setMinWidth(50);
            dayLabel.setAlignment(Pos.CENTER);
            calendar.add(dayLabel, i, 0);
        }

        VBox sidebar = new VBox(10);
        sidebar.setPadding(new Insets(10));
        sidebar.setStyle("-fx-background-color: #f4f4f4; -fx-border-color: lightgray;");

        Label selectedDate = new Label("No day selected");
        selectedDate.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        Label numQuestionsLabel = new Label("Number of questions: N/A");
        numQuestionsLabel.setStyle("-fx-font-size: 12px;");
        Label showPatternLabel = new Label("Show pattern: Off");
        showPatternLabel.setStyle("-fx-font-size: 12px;");
        Button reorderTopicsButton = new Button("Reorder topics");

        sidebar.getChildren().addAll(selectedDate, numQuestionsLabel, showPatternLabel, reorderTopicsButton);

        ToggleGroup toggleGroup = new ToggleGroup();
        for (int i = 0; i < 7; i++) {
            VBox dayColumn = new VBox();
            dayColumn.setAlignment(Pos.TOP_CENTER);
            dayColumn.setSpacing(5);
            dayColumn.setStyle("-fx-border-color: lightgray; -fx-background-color: white;");
            dayColumn.setPrefWidth(70);
            calendar.add(dayColumn, i, 1);

            ToggleButton dayToggle = new ToggleButton();
            dayToggle.setMinHeight(100);
            dayToggle.setMaxHeight(100);
            dayToggle.setToggleGroup(toggleGroup);
            dayColumn.getChildren().add(dayToggle);

            String dayName = days[i];
            int numQuestions = dayQuestions.getOrDefault(dayName, 0);
            for (int q = 1; q <= numQuestions; q++) {
                Label questionLabel = new Label("Q" + q);
                questionLabel.setStyle("-fx-background-color: #e0e0e0; -fx-border-color: gray; -fx-padding: 2;");
                questionLabel.setMaxWidth(50);
                questionLabel.setAlignment(Pos.CENTER);
                dayColumn.getChildren().add(questionLabel);
            }

            final int dayIndex = i;
            dayToggle.setOnAction(event -> {
                selectedDate.setText("Selected Day: " + days[dayIndex]);
                numQuestionsLabel.setText("Number of questions: " + numQuestions);
            });
        }

        root.setCenter(calendar);
        root.setRight(sidebar);

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setTitle("Scheduler UI");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
