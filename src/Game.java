import chessboard.ChessBoard;
import chessboard.Square;
import def.Player;
import def.Point;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import pieces.Piece;

import java.util.ArrayList;

public class Game {

    private Piece selectedPiece = null;

    ChessBoard chessBoard = new ChessBoard();
    Player player2 = Player.getPlayer2();
    Player player1 = Player.getPlayer1();
    Player currentPlayer = player1;

    ArrayList<Square> accessibleSquares;
    GridPane plchess;

    private double initialX, initialY;

    public String title = "Chess Game";

    public GridPane init() {
        GridPane gridchess = chessBoard.createChessBoard();
        plchess = initializePlayers(gridchess);
        plchess.setOnMouseClicked(event -> handleMouseClick(event, plchess));
        return plchess;
    }

    private GridPane initializePlayers(GridPane grid) {
        plchess = player2.arrangePlayerPieces(grid);
        return player1.arrangePlayerPieces(plchess);
    }

    private void handleMouseClick(javafx.scene.input.MouseEvent event, GridPane plchess) {
        int eventX = (int) Math.floor((float) event.getX() / Square.edge);
        int eventY = (int) Math.floor((float) event.getY() / Square.edge);
        if (selectedPiece!=null){
            Rectangle currentRec = (Rectangle) plchess.getChildren().get(selectedPiece.getP().getY()*8+selectedPiece.getP().getX());
            lowlightAccessibleSquare(plchess,selectedPiece.getP().getX(), selectedPiece.getP().getY());

            //clearSelectedPieceEffect
            if (accessibleSquares!=null){
                for (Square sqr :accessibleSquares){
                    int pieceX=selectedPiece.getP().getX();
                    int pieceY=selectedPiece.getP().getY();

                    int SquareX =  sqr.getP().getX();
                    int SquareY = sqr.getP().getY();

                    if (SquareX ==eventX && SquareY ==eventY){

                        sqr.setPiece(selectedPiece);
                        Node pieceForm = selectedPiece.createShape();
                        // is it useful to add point to piece??
                        chessBoard.getSquares()[pieceY][pieceX].setPiece(null);
                        //think of move than this
                        selectedPiece.setP(new Point(SquareX, SquareY));

                        chessBoard.getSquares()[SquareY][SquareX].setPiece(selectedPiece);
                        //selectedPiece.move(SquareX, SquareY);
                        chessBoard.removeItem(pieceX, pieceY);
                        if (sqr.getPiece()!=null){
                            chessBoard.removeItem(sqr.getP().getX(),sqr.getP().getY());
                        }
                        sqr.setPiece(selectedPiece);
                        plchess.add(selectedPiece.createShape(), SquareX, SquareY);
                        currentPlayer = (currentPlayer==player1)?player2:player1;

                    }
                    Rectangle rec = (Rectangle) plchess.getChildren().get((SquareY)*8+SquareX);
                    rec.setFill( ( sqr.getP().getX() + sqr.getP().getY() ) % 2 == 0 ? Color.WHITE : Color.BROWN);
                    rec.setStrokeWidth(0);
                }
            }
            selectedPiece = null;
        }

        if (currentPlayer!=null){
            ArrayList<Piece> pieces = currentPlayer.getPiecelist();

            for (Piece piece :pieces){
                //les points ne sont pas les memes
                int pieceX=piece.getP().getX();
                int pieceY=piece.getP().getY();
                if (pieceX==eventX && pieceY==eventY){

                    String s =  (piece.getFacadeDir()==1)?"top":"down";
                    System.out.println("2-yes" + s+"\n--------------------------------");
                    accessibleSquares = piece.accessibleSquares(chessBoard);

                    Rectangle currentRec = (Rectangle) plchess.getChildren().get((eventY)*8+eventX);
                    selectedPiece = (Piece) piece;
                    DropShadow dropShadow = new DropShadow();
                    dropShadow.setOffsetX(1);
                    dropShadow.setOffsetY(1);

                    if(accessibleSquares!=null){

                        dropShadow.setColor(Color.GREEN);
                    }else {
                        dropShadow.setColor(Color.RED);
                    }

                    // Apply the DropShadow effect to the rectangle
                    currentRec.setEffect(dropShadow);
                    currentRec.setScaleX(0.9);
                    currentRec.setScaleY(0.9);

                    if (accessibleSquares!=null){
                        for (Square sqr :accessibleSquares){

                            int SquareX =  sqr.getP().getX();
                            int SquareY = sqr.getP().getY();

                            System.out.println("SquareX :   "+SquareX+ "---- > SquareY ----->" + SquareY);

                            Rectangle rec = (Rectangle) plchess.getChildren().get((SquareY)*8+SquareX);

                            rec.setFill(Color.rgb(144, 238, 144));
                            rec.setScaleX(0.95);
                            rec.setScaleY(0.95);
                            rec.setStroke(Color.GRAY);
                            rec.setStrokeWidth(3);

                        }
                    }
                    else{
                        System.out.println("piece is null");
                    }
                    break;
                }
            }
        }


        /*
        System.out.println("\nStart Click Handling ---------------------");
        int eventX = (int) Math.floor((float) event.getX() / Square.edge);
        int eventY = (int) Math.floor((float) event.getY() / Square.edge);

        handleSelectedPiece(eventX, eventY, plchess);
        handleAccessibleSquares(plchess);
        System.out.println("End Click Handling -----------------------");
        */
    }

    private void handleSelectedPiece(int eventX, int eventY, GridPane plchess) {
        if (selectedPiece != null) {
            clearSelectedPieceEffect(plchess);
            moveSelectedPiece(eventX, eventY, plchess);
            selectedPiece = null;
        } else {
            selectPiece(eventX, eventY, plchess);
        }
    }

    private void clearSelectedPieceEffect(GridPane plchess) {
        if (accessibleSquares != null) {
            for (Square sqr : accessibleSquares) {
                lowlightAccessibleSquare(plchess,sqr.getP().getX(), sqr.getP().getY());
                clearSquareEffect(plchess, sqr.getP().getX(), sqr.getP().getY());
            }
        }
    }

    private void clearSquareEffect(GridPane plchess, int x, int y) {
        Rectangle rec = (Rectangle) plchess.getChildren().get(y * 8 + x);
        rec.setFill((x + y) % 2 == 0 ? Color.WHITE : Color.BROWN);
        rec.setStrokeWidth(0);
    }

    private void moveSelectedPiece(int eventX, int eventY, GridPane plchess) {
        if (accessibleSquares != null) {
            for (Square sqr : accessibleSquares) {
                if (sqr.getP().getX() == eventX && sqr.getP().getY() == eventY) {
                    updatePiecePosition(selectedPiece, eventX, eventY);
                    updateChessBoard(selectedPiece, eventX, eventY);
                    switchPlayerTurn();
                    break;
                }
            }
        }
    }

    private void updatePiecePosition(Piece piece, int x, int y) {
        piece.setP(new Point(x, y));
    }

    private void updateChessBoard(Piece piece, int x, int y) {
        chessBoard.getSquares()[y][x].setPiece(piece);
        plchess.add(piece.createShape(), x, y);
    }

    private void switchPlayerTurn() {
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
    }

    private void selectPiece(int eventX, int eventY, GridPane plchess) {
        for (Piece piece : currentPlayer.getPiecelist()) {
            if (piece.getP().getX() == eventX && piece.getP().getY() == eventY) {
                highlightSelectedPiece(piece, plchess);
                selectedPiece = piece;
                accessibleSquares = piece.accessibleSquares(chessBoard);
                break;
            }
        }
    }

    private void highlightSelectedPiece(Piece piece, GridPane plchess) {
        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetX(1);
        dropShadow.setOffsetY(1);
        Rectangle currentRec = (Rectangle) plchess.getChildren().get(piece.getP().getY() * 8 + piece.getP().getX());
        currentRec.setEffect(dropShadow);
    }

    private void handleAccessibleSquares(GridPane plchess) {
        if (accessibleSquares != null) {
            for (Square sqr : accessibleSquares) {
                highlightAccessibleSquare(plchess, sqr.getP().getX(), sqr.getP().getY());
            }
        }
    }

    private void highlightAccessibleSquare(GridPane plchess, int x, int y) {
        Rectangle rec = (Rectangle) plchess.getChildren().get(y * 8 + x);
        rec.setFill(Color.rgb(144, 238, 144));
        rec.setStroke(Color.GRAY);
        rec.setStrokeWidth(3);
    }
    private void lowlightAccessibleSquare(GridPane plchess, int x, int y) {
        Rectangle rec = (Rectangle) plchess.getChildren().get(y * 8 + x);
        // Apply the DropShadow effect to the rectangle
        rec.setEffect(null);
        rec.setScaleX(1);
        rec.setScaleY(1);
    }

}

 /*
import chessboard.ChessBoard;
import chessboard.Square;
import def.Player;
import def.Point;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import pieces.Piece;

import java.util.ArrayList;


public class Game {

    private Piece selectedPiece = null;

    ChessBoard chessBoard =  new ChessBoard();
    //Player player1 = Player.getPlayer1();
    Player player2 = Player.getPlayer2();
    Player player1 = Player.getPlayer1();
    // if this case do not use too uch memory
    Player currentPlayer = player1;
    ArrayList<Square>  AcesSquares;
    GridPane plchess;

    public String title = " chess game ";


    public GridPane init(){
        GridPane gridchess =  chessBoard.createChessBoard();

        ArrayList<Square> accesSquares = new ArrayList<Square>();
        //Think of method to get recatangle index like I want 8 8 creat array  8 8

        // we can not modify gride like this
        plchess = player2.arrangePlayerPieces(gridchess);
        plchess = player1.arrangePlayerPieces(plchess);

        chessBoard.includePieces( player1.getPiecelist());
        chessBoard.includePieces(player2.getPiecelist());

        Square[][] squares= chessBoard.getSquares();

        GridPane finalPlchess = plchess;
        plchess.setOnMouseClicked(event -> handleMouseClick(event, finalPlchess));
        return finalPlchess;
    }




    private void handleMousePressed(javafx.scene.input.MouseEvent event ) {
        // Record the initial position of the mouse
        initialX = event.getSceneX();
        initialY = event.getSceneY();
    }
    private void handleMouseClick(javafx.scene.input.MouseEvent event, GridPane plchess) {
        System.out.println("\ndebut------------------------------------------------------");
        int eventX= (int)Math.floor((float)event.getX()/Square.edge);
        int eventY= (int)Math.floor((float)event.getY()/Square.edge);


        if (selectedPiece!=null){


            System.out.println("selctedPieceX " + selctedPieceX +",selctedPieceY "+selctedPieceY );

            Rectangle currentRec = (Rectangle) plchess.getChildren().get(selctedPieceY*8+selctedPieceX);

            // Apply the DropShadow effect to the rectangle
            currentRec.setEffect(null);
            currentRec.setScaleX(1);
            currentRec.setScaleY(1);

            if (AcesSquares!=null){
                for (Square sqr :AcesSquares){

                    int pieceX=selectedPiece.getP().getX();
                    int pieceY=selectedPiece.getP().getY();

                    int SquareX =  sqr.getP().getX();
                    int SquareY = sqr.getP().getY();

                    if (SquareX ==eventX && SquareY ==eventY){
                        //sqr.setPiece(selectedPiece);
                        Node pieceForm = selectedPiece.createShape();
                        // is it useful to add point to piece??

                        chessBoard.getSquares()[pieceY][pieceX].setPiece(null);
                        //think of move than this
                        selectedPiece.setP(new Point(SquareX, SquareY));

                        ArrayList<Piece> pieces = currentPlayer.getPiecelist();

                        for (Piece piece :pieces){
                            if (piece.getP().getX()==pieceX && piece.getP().getY()==pieceY){
                                piece = selectedPiece;
                            }
                        }

                        chessBoard.getSquares()[SquareY][SquareX].setPiece(selectedPiece);

                        System.out.println("added selected piece" +". chessBoard.getSquares()["+SquareY+"]["+SquareX+"]\n"+
                                "sqr.getP().getX()="+sqr.getP().getX()+ "sqr.getP().getY()="+sqr.getP().getY()+"\n"+
                                "selectedPiece x="+selectedPiece.getP().getX()+"selectedPiece y= "+selectedPiece.getP().getY()
                        );




                        System.out.println("pieceX, pieceY =====" +pieceX+" "+ pieceY);
                        selectedPiece.move(SquareX, SquareY);

                        chessBoard.removeItem(pieceX, pieceY);

                        sqr.setPiece(selectedPiece);

                        plchess.add(selectedPiece.createShape(), SquareX, SquareY);

                        currentPlayer = (currentPlayer==player1)?player2:player1;
                    }
                    Rectangle rec = (Rectangle) plchess.getChildren().get((SquareY)*8+SquareX);
                    rec.setFill( ( sqr.getP().getX() + sqr.getP().getY() ) % 2 == 0 ? Color.WHITE : Color.BROWN);
                    rec.setStrokeWidth(0);
                }
            }
            selectedPiece = null;
        }


        if (currentPlayer!=null){
            ArrayList<Piece> pieces = currentPlayer.getPiecelist();

            for (Piece piece :pieces){
                //les points ne sont pas les memes
                int pieceX=piece.getP().getX();
                int pieceY=piece.getP().getY();
                if (pieceX==eventX && pieceY==eventY){

                    String s =  (piece.getFacadeDir()==1)?"top":"down";
                    System.out.println("2-yes" + s+"\n--------------------------------");
                    AcesSquares = piece.accessibleSquares(chessBoard);

                    Rectangle currentRec = (Rectangle) plchess.getChildren().get((eventY)*8+eventX);
                    selectedPiece = (Piece) piece;
                    DropShadow dropShadow = new DropShadow();
                    dropShadow.setOffsetX(1);
                    dropShadow.setOffsetY(1);

                    if(AcesSquares!=null){

                        dropShadow.setColor(Color.GREEN);
                    }else {
                        dropShadow.setColor(Color.RED);
                    }

                    // Apply the DropShadow effect to the rectangle
                    currentRec.setEffect(dropShadow);
                    currentRec.setScaleX(0.9);
                    currentRec.setScaleY(0.9);

                    if (AcesSquares!=null){
                        for (Square sqr :AcesSquares){

                            int SquareX =  sqr.getP().getX();
                            int SquareY = sqr.getP().getY();

                            System.out.println("SquareX :   "+SquareX+ "---- > SquareY ----->" + SquareY);

                            Rectangle rec = (Rectangle) plchess.getChildren().get((SquareY)*8+SquareX);

                            rec.setFill(Color.rgb(144, 238, 144));
                            rec.setScaleX(0.95);
                            rec.setScaleY(0.95);
                            rec.setStroke(Color.GRAY);
                            rec.setStrokeWidth(3);

                        }
                    }
                    else{
                        System.out.println("piece is null");
                    }
                    break;
                }
            }
        }

        System.out.println("finnnnnnnnnnnnnnnn------------------------------------------------------");
    }
}
*/