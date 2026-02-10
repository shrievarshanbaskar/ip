package fozza;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        Fozza fozza = new Fozza();

        // Chat container
        VBox dialogContainer = new VBox(10);
        dialogContainer.setPrefWidth(380);
        dialogContainer.setStyle("-fx-background-color: #2B2B2B;");

        // Scrollable chat area
        ScrollPane scrollPane = new ScrollPane(dialogContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle(
                "-fx-background: #2B2B2B;" +
                        "-fx-background-color: transparent;"
        );
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        // Input field
        TextField input = new TextField();
        input.setPromptText("Enter command here");
        input.setStyle(
                "-fx-background-color: #3C3F41;" +
                        "-fx-text-fill: #EDEDED;" +
                        "-fx-prompt-text-fill: #9E9E9E;"
        );

        // Send button
        Button send = new Button("Send");
        send.setStyle(
                "-fx-background-color: #4E8075;" +
                        "-fx-text-fill: white;"
        );

        // Send handler
        send.setOnAction(e -> {
            String userInput = input.getText();
            if (userInput.isBlank()) {
                return;
            }

            dialogContainer.getChildren().add(dialogBubble(userInput, true));

            String response = fozza.getResponse(userInput);
            dialogContainer.getChildren().add(dialogBubble(response, false));

            input.clear();
            scrollPane.setVvalue(1.0);
        });

        // Enter key support
        input.setOnAction(e -> send.fire());

        VBox root = new VBox(10, scrollPane, input, send);
        root.setStyle("-fx-background-color: #2B2B2B;");

        Scene scene = new Scene(root, 400, 600);
        stage.setScene(scene);
        stage.setTitle("Fozza");
        stage.show();
    }

    // Chat bubble with circular avatar and dark-theme colors
    private VBox dialogBubble(String text, boolean isUser) {

        double avatarSize = 64;

        ImageView avatar = new ImageView(
                new Image(getClass().getResourceAsStream(
                        isUser ? "/images/user.png" : "/images/bot.png"
                ))
        );
        avatar.setFitWidth(avatarSize);
        avatar.setFitHeight(avatarSize);
        avatar.setPreserveRatio(false);

        Circle clip = new Circle(
                avatarSize / 2,
                avatarSize / 2,
                avatarSize / 2
        );
        avatar.setClip(clip);

        Label label = new Label(text);
        label.setWrapText(true);
        label.setMaxWidth(260);

        if (isUser) {
            // User bubble (keep same)
            label.setStyle(
                    "-fx-padding: 10;" +
                            "-fx-background-radius: 12;" +
                            "-fx-background-color: #3A6F4E;" +
                            "-fx-text-fill: #EDEDED;"
            );
        } else {
            // Bot bubble (lighter)
            label.setStyle(
                    "-fx-padding: 10;" +
                            "-fx-background-radius: 12;" +
                            "-fx-background-color: #D6D7D9;" +
                            "-fx-text-fill: #1E1E1E;"
            );
        }

        HBox bubble = new HBox(label);
        bubble.setPrefWidth(380);
        bubble.setAlignment(isUser ? Pos.CENTER_RIGHT : Pos.CENTER_LEFT);

        VBox block = new VBox(4, avatar, bubble);
        block.setAlignment(isUser ? Pos.CENTER_RIGHT : Pos.CENTER_LEFT);

        return block;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
