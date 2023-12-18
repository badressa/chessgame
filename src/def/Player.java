package def;

import pieces.*;
import java.util.ArrayList;
import javafx.scene.layout.GridPane;



public class Player {

    private static final int BOARD_SIZE = 8;
    private int rowStart;
    public ArrayList<Piece> getPiecelist() {
        return piecelist;
    }

    public void setPiecelist(ArrayList<Piece> piecelist) {
        this.piecelist = piecelist;
    }

    ArrayList<Piece> piecelist = new ArrayList<Piece>();


    ArrayList<Piece> piecebeatenlist = new ArrayList<Piece>();
    //1 list pieces aat first then loook at the rest 
    //think about function or boolean to know if in square or beaten
    //think of limitation of two players pattern
    private static Player player1;
    private static Player player2;

    private Pl pl;

    public Pl getPl() {
        return pl;
    }

    public void setPl(Pl pl) {
        this.pl = pl;
    }
    public void init(){

    }

    private String name;
    private Player(Pl player,String name) {
        this.pl = player;
        this.name = name;
    }


    public static Player getPlayer1() {
        if (player1 == null) {
            player1 = new Player(Pl.TopPlayer, "Player 1");
        }
        return player1;
    }
    public static Player getPlayer2() {
        if (player2 == null) {
            player2 = new Player(Pl.BottomPlayer,"Player 2");
        }
        return player2;
    }
    public GridPane arrangePlayerPieces(GridPane chessboard) {
        int rowFloor = (this.pl == Pl.TopPlayer) ? 0 : 6;

        for (int row = rowFloor; row < rowFloor + 2; row++) {
            for (int col = 0; col < 8; col++) {
                // Check if the cell is empty before adding a new piece
                //if (chessboard.getChildren().stream().noneMatch(node -> GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col)) {
                    if ( (row==0 || row ==7) ) {
                        if ((col==0 || col==7 )){
                            Rook rook = new Rook(this, row, col);
                            //the square x x receives rook ext ... Square
                            piecelist.add(rook);
                            chessboard.add(rook.createShape(), col, row);
                        }

                        if ((col==0+1 || col==7-1 )){
                            Knight knight = new Knight(this, col, row);
                            piecelist.add(knight);
                            chessboard.add(knight.createShape(), col, row);
                        }

                        if ((col==0+2 || col==7-2 )){
                            Bishop bishop = new Bishop(this, col, row);
                            piecelist.add(bishop);
                            chessboard.add(bishop.createShape(), col, row);

                        }
                        if (col==3) {

                            Queen queen = new Queen(this, col, row);
                            piecelist.add(queen);
                            chessboard.add(queen.createShape(), col, row);
                        }
                        if (col==4) {
                            King king = new King(this, col, row);
                            piecelist.add(king);
                            chessboard.add(king.createShape(), col, row);
                        }
                    }
                    if (row == 1 || row ==6) {
                        Pawn pawn = new Pawn(this, col, row);
                        piecelist.add(pawn);
                        chessboard.add(pawn.createShape(), col, row);
                    }
               // }
            }
        }
        return chessboard;
    }
    public String toString(){
        String s="";
        for(Piece piece: piecelist ){
            s+= piece.toString();
        }
        return s;
    }
    public Piece getKing() {
        for (Piece piece : piecelist) {
            if (piece instanceof King) {
                return piece;
            }
        }
        return null; // If the king is not found
    }
}


