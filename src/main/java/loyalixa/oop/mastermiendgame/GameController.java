package loyalixa.oop.mastermiendgame;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.control.*;

import java.util.*;
public class GameController {
    static int CURRENT_ROW=0;
    static int NUM_OF_SELECTED_COLORS=0;
    static Color[]COLORS ={Color.RED,Color.BLUE,Color.GREEN,Color.ORANGE};
    static String[]COLOR_HEX_CODES ={"#FF0000","#0000FF","#008000","#FFA500"};
    ArrayList<Integer>generatedIndexes = new ArrayList<>(Arrays.asList(0,1,2,3));
    static int [][]CIRCLES_STATUS =new int[8][4];

    @FXML private GridPane feedbackGrid;
    @FXML private GridPane gameGrid;
    @FXML private GridPane solutionGrid;



    @FXML void initialize(){
        resetGameState();
        initializeRows();
        hideGeneratedColors();
        prepareGameGrid();

    }




    private void prepareGameGrid(){
    for (int i = 0; i < 8; i++) {
        for (int j = 0; j < 4; j++) {
            Circle circle = (Circle) getNode(gameGrid, i, j);
            configureContextMenu(circle, i, j);

        }}}
    private void configureContextMenu(Circle circle,int i,int j) {
        ContextMenu contextMenu = new ContextMenu();
        for (int k = 0; k < 4; k++) {
            MenuItem colorItem = new MenuItem("   ");
            colorItem.setStyle("-fx-background-color:" + COLOR_HEX_CODES[k] + "; -fx-background-radius:50px;");
            int finalK = k;

        colorItem.setOnAction(event -> {
            circle.setFill(COLORS[finalK]);
            if (CIRCLES_STATUS[i][j] == 0) {
                NUM_OF_SELECTED_COLORS++;
                CIRCLES_STATUS[i][j] = 1;
            }
            if (NUM_OF_SELECTED_COLORS == 4) {
                Button checkButton = (Button) ((StackPane) getNode(feedbackGrid, CURRENT_ROW, 0)).getChildren().get(1);
                checkButton.setDisable(false);
            }
        });
        contextMenu.getItems().add(colorItem);}


        circle.setOnMouseClicked(mouseEvent -> contextMenu.show(circle, mouseEvent.getScreenX(),mouseEvent.getScreenY()));
    }
    private void initializeRows(){
        for (int i = 0; i < 8; i++) {
            if(i==0)activeRow(i);
            else deactiveRow(i);

        }
    }
    public Node getNode(GridPane gridPane,int row,int col){
        for (Node node:gridPane.getChildren()) {
            Integer rowIndex=GridPane.getRowIndex(node);
            Integer colIndex =GridPane.getColumnIndex(node);
            if (rowIndex==null) rowIndex=0;
            if (colIndex==null) colIndex=0;
            if (rowIndex==row && colIndex==col){
                return node;
            }

        }
return null;
    }
    private void deactiveRow(int row){
        for (int i = 0; i < 4; i++) {
            Circle circle =(Circle)getNode(gameGrid,row,i);
            circle.setDisable(true);
            circle.setOpacity(0.5);
            circle.setFill(Color.WHEAT);
    }
        StackPane stackPane=(StackPane) getNode(feedbackGrid,row,0);
        GridPane smallFeedbackGrid = (GridPane) stackPane.getChildren().get(0);
        smallFeedbackGrid.setVisible(true);
        smallFeedbackGrid.setOpacity(0.5);
        Button button=(Button) stackPane.getChildren().get(1);
        button.setDisable(true);
        button.setVisible(false);
        for (int i = 0; i < 4; i++) {
            Circle circle=(Circle) getNode(smallFeedbackGrid,0,i);
            circle.setFill(Color.WHEAT);}

        }
    private void activeRow(int row){
        for (int i = 0; i < 4; i++) {
            Circle circle =(Circle)getNode(gameGrid,row,i);
            circle.setDisable(false);
            circle.setOpacity(1);
            circle.setFill(Color.WHEAT);
        }
        StackPane stackPane=(StackPane) getNode(feedbackGrid,row,0);
        GridPane smallFeedbackGrid = (GridPane) stackPane.getChildren().get(0);
        smallFeedbackGrid.setVisible(false);
        smallFeedbackGrid.setOpacity(1);
        Button button=(Button) stackPane.getChildren().get(1);
        button.setDisable(true);
        button.setVisible(true);
        for (int i = 0; i < 4; i++) {
            Circle circle=(Circle) getNode(smallFeedbackGrid,0,i);
            circle.setFill(Color.WHEAT);}

    }
    public void resetGameState() {
        Collections.shuffle(generatedIndexes);
        CURRENT_ROW = 0;
        NUM_OF_SELECTED_COLORS = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 4; j++) {
                CIRCLES_STATUS[i][j] = 0;
            }
        }

    }

    @FXML public void checkButton(){

        int numOfBlacks=0;
        NUM_OF_SELECTED_COLORS =0;
        for (int i = 0; i < 4; i++) {
             Circle circle=(Circle) getNode(gameGrid,CURRENT_ROW,i);
             if (circle.getFill()==COLORS[generatedIndexes.get(i)]){
                 numOfBlacks++;

             }}
        for (int i = 0; i < numOfBlacks; i++) {
            StackPane stackPane=(StackPane)getNode(feedbackGrid,CURRENT_ROW,0);
            GridPane smallGridPane=(GridPane)stackPane.getChildren().get(0);
            Circle c=(Circle) getNode(smallGridPane,0,i);
            c.setFill(Color.BLACK);}
        StackPane stackPane=(StackPane) getNode(feedbackGrid,CURRENT_ROW,0);
        Button button=(Button) stackPane.getChildren().get(1);
        GridPane gridPane=(GridPane) stackPane.getChildren().get(0);
        gridPane.setVisible(true);
        gridPane.setOpacity(1);
        button.setVisible(false);

            CURRENT_ROW++;

            if (numOfBlacks==4){
                showGeneratedColors();
                return;
            }
            if (CURRENT_ROW > 7){
                showGeneratedColors();
                return;
            }
        activeRow(CURRENT_ROW);
        }
    public void showGeneratedColors(){
        for (int i = 0; i < 4; i++) {
            StackPane stackPane=(StackPane)getNode(solutionGrid,0,i);
            Circle circle= (Circle) stackPane.getChildren().get(1);
            circle.setFill(COLORS[generatedIndexes.get(i)]);}
        solutionGrid.setOpacity(1);

    }public void hideGeneratedColors(){
        for (int i = 0; i < 4; i++) {
            StackPane stackPane=(StackPane) getNode(solutionGrid,0,i);
            Circle circle=(Circle) stackPane.getChildren().get(1);
            circle.setFill(null);
        }
        solutionGrid.setOpacity(0.5);
    }


    public void startNewGame(MouseEvent startNewGame) {
    }
}




















