package sample;

import java.io.*;
import java.net.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Client extends Application {

    DataOutputStream toServer = null;
    BufferedReader fromServer = null;
    Socket echoSocket = null;


    @Override
    public void start(Stage primaryStage) throws Exception{
        BorderPane paneForTextField = new BorderPane();
        paneForTextField.setPadding(new Insets(5, 5, 5, 5));
        paneForTextField.setStyle("-fx-border-color: green");
        paneForTextField.setLeft(new Label("Enter a number to check prime: "));

        TextField tf = new TextField();
        tf.setAlignment(Pos.BOTTOM_RIGHT);
        paneForTextField.setCenter(tf);

        BorderPane mainPane = new BorderPane();
        TextArea ta = new TextArea();
        mainPane.setCenter(new ScrollPane(ta));
        mainPane.setTop(paneForTextField);

        Scene scene = new Scene(mainPane, 450, 200);
        primaryStage.setTitle("Client");
        primaryStage.setScene(scene);
        primaryStage.show();

        tf.setOnAction(e -> {
            try {
                double userNumber = Double.parseDouble(tf.getText().trim());

                toServer.writeDouble(userNumber);
                toServer.flush();

                String answer = fromServer.readLine();

                ta.appendText("Number is " + userNumber + "\n");
                ta.appendText("Answer received from the server is " + answer + '\n');
            }
            catch (IOException ex) {
                System.err.println(ex);
            }
        });

        try {
            Socket socket = new Socket("localhost", 3660);
            fromServer = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
            toServer = new DataOutputStream(socket.getOutputStream());
        }
            catch (IOException ex) {
            ta.appendText(ex.toString() + '\n');
        }
    }


    public static void main(String[] args) {

        launch(args);
    }
  }
