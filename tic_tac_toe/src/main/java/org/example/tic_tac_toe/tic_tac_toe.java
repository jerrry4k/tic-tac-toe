package org.example.tic_tac_toe;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class tic_tac_toe extends Application {

    private final Button[] cells = new Button[9];
    private final char[] board = new char[9];

    private char currentPlayer = 'X';
    private boolean gameOver = false;

    private int xScore = 0;
    private int oScore = 0;
    private int draws = 0;

    private Label statusLabel;
    private Label scoreLabel;
    private Button newGameButton;

    private final int[][] winningCombinations = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}
    };

    @Override
    public void start(Stage stage) {

        Label title = new Label("TIC-TAC-TOE");
        title.setFont(Font.font("Arial", 30));
        title.setTextFill(Color.web("#7C3AED"));

        statusLabel = new Label("Player X's turn");
        statusLabel.setFont(Font.font("Arial", 18));

        scoreLabel = new Label();
        scoreLabel.setFont(Font.font("Arial", 15));
        updateScore();

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(8);
        grid.setVgap(8);

        for (int i = 0; i < 9; i++) {
            final int index = i;

            Button cell = new Button("");
            cell.setPrefSize(110, 110);
            cell.setFont(Font.font("Arial", 38));
            cell.setStyle(
                    "-fx-background-color: #F3E8FF;" +
                            "-fx-background-radius: 12;" +
                            "-fx-border-radius: 12;" +
                            "-fx-cursor: hand;"
            );

            cell.setOnAction(event -> makeMove(index));

            cells[i] = cell;
            grid.add(cell, i % 3, i / 3);
        }

        newGameButton = new Button("New Game");
        newGameButton.setFont(Font.font("Arial", 15));
        newGameButton.setStyle(
                "-fx-background-color: #7C3AED;" +
                        "-fx-text-fill: white;" +
                        "-fx-background-radius: 8;" +
                        "-fx-padding: 10 25 10 25;" +
                        "-fx-cursor: hand;"
        );
        newGameButton.setOnAction(event -> resetBoard());

        Button resetScoresButton = new Button("Reset Scores");
        resetScoresButton.setFont(Font.font("Arial", 15));
        resetScoresButton.setOnAction(event -> {
            xScore = 0;
            oScore = 0;
            draws = 0;
            updateScore();
            resetBoard();
        });

        HBox buttons = new HBox(12, newGameButton, resetScoresButton);
        buttons.setAlignment(Pos.CENTER);

        VBox top = new VBox(12, title, statusLabel, scoreLabel);
        top.setAlignment(Pos.CENTER);
        top.setPadding(new Insets(20));

        VBox bottom = new VBox(buttons);
        bottom.setAlignment(Pos.CENTER);
        bottom.setPadding(new Insets(20));

        BorderPane root = new BorderPane();
        root.setTop(top);
        root.setCenter(grid);
        root.setBottom(bottom);
        root.setStyle("-fx-background-color: #FFFFFF;");

        Scene scene = new Scene(root, 420, 550);

        stage.setTitle("Tic-Tac-Toe");
        stage.setScene(scene);
        stage.setMinWidth(380);
        stage.setMinHeight(500);
        stage.show();
    }

    private void makeMove(int index) {

        if (gameOver || board[index] != '\0') {
            return;
        }

        board[index] = currentPlayer;
        cells[index].setText(String.valueOf(currentPlayer));

        if (currentPlayer == 'X') {
            cells[index].setTextFill(Color.web("#7C3AED"));
        } else {
            cells[index].setTextFill(Color.web("#EA580C"));
        }

        if (checkWinner()) {
            gameOver = true;

            if (currentPlayer == 'X') {
                xScore++;
            } else {
                oScore++;
            }

            statusLabel.setText("Player " + currentPlayer + " wins!");
            updateScore();
            return;
        }

        if (isDraw()) {
            gameOver = true;
            draws++;
            statusLabel.setText("It's a draw!");
            updateScore();
            return;
        }

        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
        statusLabel.setText("Player " + currentPlayer + "'s turn");
    }

    private boolean checkWinner() {

        for (int[] combination : winningCombinations) {
            int a = combination[0];
            int b = combination[1];
            int c = combination[2];

            if (board[a] != '\0'
                    && board[a] == board[b]
                    && board[b] == board[c]) {

                cells[a].setStyle(
                        "-fx-background-color: #BBF7D0;" +
                                "-fx-background-radius: 12;"
                );
                cells[b].setStyle(
                        "-fx-background-color: #BBF7D0;" +
                                "-fx-background-radius: 12;"
                );
                cells[c].setStyle(
                        "-fx-background-color: #BBF7D0;" +
                                "-fx-background-radius: 12;"
                );

                return true;
            }
        }

        return false;
    }

    private boolean isDraw() {
        for (char cell : board) {
            if (cell == '\0') {
                return false;
            }
        }
        return true;
    }

    private void updateScore() {
        scoreLabel.setText(
                "X Wins: " + xScore +
                        "     O Wins: " + oScore +
                        "     Draws: " + draws
        );
    }

    private void resetBoard() {
        for (int i = 0; i < 9; i++) {
            board[i] = '\0';
            cells[i].setText("");
            cells[i].setStyle(
                    "-fx-background-color: #F3E8FF;" +
                            "-fx-background-radius: 12;" +
                            "-fx-border-radius: 12;" +
                            "-fx-cursor: hand;"
            );
        }

        currentPlayer = 'X';
        gameOver = false;
        statusLabel.setText("Player X's turn");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
