package fozza;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/**
 * Entry point for the JavaFX GUI of the Fozza application.
 */
public class Main extends Application {

    private static final double DEFAULT_WIDTH = 400;
    private static final double DEFAULT_HEIGHT = 600;
    private static final double MIN_WIDTH = 400;
    private static final double MIN_HEIGHT = 600;

    private static final double AVATAR_SIZE = 64;
    private static final double BUBBLE_MAX_WIDTH = 260;

    private static final String DARK_BACKGROUND =
            "-fx-background-color: #2B2B2B;";

    private static final String USER_STYLE =
            "-fx-padding: 10;" +
                    "-fx-background-radius: 12;" +
                    "-fx-background-color: #3A6F4E;" +
                    "-fx-text-fill: #EDEDED;";

    private static final String BOT_STYLE =
            "-fx-padding: 10;" +
                    "-fx-background-radius: 12;" +
                    "-fx-background-color: #D6D7D9;" +
                    "-fx-text-fill: #1E1E1E;";

    private Fozza fozza;

    @Override
    public void start(Stage stage) {
        this.fozza = new Fozza();

        VBox dialogContainer = createDialogContainer();
        ScrollPane scrollPane = createScrollPane(dialogContainer);
        TextField inputField = createInputField();
        Button sendButton = createSendButton();

        configureHandlers(sendButton, inputField, dialogContainer, scrollPane);

        VBox root = new VBox(10, scrollPane, inputField, sendButton);
        root.setStyle(DARK_BACKGROUND);

        Scene scene = new Scene(root, DEFAULT_WIDTH, DEFAULT_HEIGHT);

        stage.setScene(scene);
        stage.setTitle("Fozza");

        // Set lower bounds only (allow resizing larger)
        stage.setMinWidth(MIN_WIDTH);
        stage.setMinHeight(MIN_HEIGHT);

        stage.show();
    }

    private VBox createDialogContainer() {
        VBox container = new VBox(10);
        container.setPrefWidth(380);
        container.setStyle(DARK_BACKGROUND);
        return container;
    }

    private ScrollPane createScrollPane(VBox dialogContainer) {
        ScrollPane scrollPane = new ScrollPane(dialogContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle(
                "-fx-background: #2B2B2B;" +
                        "-fx-background-color: transparent;"
        );
        VBox.setVgrow(scrollPane, Priority.ALWAYS);
        return scrollPane;
    }

    private TextField createInputField() {
        TextField input = new TextField();
        input.setPromptText("Enter command here");
        input.setStyle(
                "-fx-background-color: #3C3F41;" +
                        "-fx-text-fill: #EDEDED;" +
                        "-fx-prompt-text-fill: #9E9E9E;"
        );
        return input;
    }

    private Button createSendButton() {
        Button send = new Button("Send");
        send.setStyle(
                "-fx-background-color: #4E8075;" +
                        "-fx-text-fill: white;"
        );
        return send;
    }

    private void configureHandlers(
            Button send,
            TextField input,
            VBox dialogContainer,
            ScrollPane scrollPane) {

        send.setOnAction(e ->
                handleInput(input, dialogContainer, scrollPane));
        input.setOnAction(e -> send.fire());
    }

    private void handleInput(
            TextField input,
            VBox dialogContainer,
            ScrollPane scrollPane) {

        String userInput = input.getText();
        if (userInput.isBlank()) {
            return;
        }

        dialogContainer.getChildren()
                .add(createDialog(userInput, true));

        String response = fozza.getResponse(userInput);

        dialogContainer.getChildren()
                .add(createDialog(response, false));

        input.clear();

        Platform.runLater(() ->
                scrollPane.setVvalue(1.0));
    }

    private VBox createDialog(String text, boolean isUser) {
        ImageView avatar = createAvatar(isUser);
        Label label = createLabel(text, isUser);

        HBox bubble = new HBox(label);
        bubble.setPrefWidth(380);
        bubble.setAlignment(
                isUser ? Pos.CENTER_RIGHT : Pos.CENTER_LEFT
        );

        VBox block = new VBox(4, avatar, bubble);
        block.setAlignment(
                isUser ? Pos.CENTER_RIGHT : Pos.CENTER_LEFT
        );

        return block;
    }

    private ImageView createAvatar(boolean isUser) {
        ImageView avatar = new ImageView(
                new Image(getClass().getResourceAsStream(
                        isUser
                                ? "/images/user.png"
                                : "/images/bot.png"
                ))
        );

        avatar.setFitWidth(AVATAR_SIZE);
        avatar.setFitHeight(AVATAR_SIZE);

        Circle clip = new Circle(
                AVATAR_SIZE / 2,
                AVATAR_SIZE / 2,
                AVATAR_SIZE / 2
        );
        avatar.setClip(clip);

        return avatar;
    }

    private Label createLabel(String text, boolean isUser) {
        Label label = new Label(text);
        label.setWrapText(true);
        label.setMaxWidth(BUBBLE_MAX_WIDTH);
        label.setStyle(isUser ? USER_STYLE : BOT_STYLE);
        return label;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
