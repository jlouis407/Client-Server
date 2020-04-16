import java.io.*;
import java.net.*;
import java.util.Date;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class Server extends Application {
    @Override

    public void start(Stage primaryStage) {
        TextArea ta = new TextArea();

        Scene scene = new Scene(new ScrollPane(ta), 450, 200);
        primaryStage.setTitle("Server");
        primaryStage.setScene(scene);
        primaryStage.show();

        new Thread(() -> {
            try {
                ServerSocket serverSocket = new ServerSocket(3660);
                Platform.runLater(() ->
                        ta.appendText("Server started at " + new Date() + '\n'));

                Socket socket = serverSocket.accept();

                DataInputStream inputFromClient = new DataInputStream(socket.getInputStream());

                DataOutputStream outputToClient = new DataOutputStream(socket.getOutputStream());

                while (true) {
                    double userNumber = inputFromClient.readDouble();

                    for (int i = 2; i < userNumber; i++) {
                        if (userNumber % i == 0) {
                            String answer = "yes";
                            outputToClient.writeUTF(answer);
                            Platform.runLater(() -> {
                                ta.appendText("Number received from client: " + userNumber + '\n');
                                ta.appendText("The answer is: " + answer + '\n');
                            });
                        } else {
                            String answer = "no";
                            outputToClient.writeUTF(answer);
                            Platform.runLater(() -> {
                                ta.appendText("Number received from client: " + userNumber + '\n');
                                ta.appendText("The answer is: " + answer + '\n');
                            });
                        }
                    }

                   /* Platform.runLater(() -> {
                        ta.appendText("Number received from client: " + userNumber + '\n');
                        ta.appendText("The answer is: " + answer + '\n');
                    });*/
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }).start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
