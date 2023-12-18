package pieces;

import chessboard.ChessBoard;
import chessboard.Square;
import def.Pl;
import def.Player;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
public class Rook extends Piece {

    public Rook(Player player, int x, int y) {
        super(player, x, y);
    }

    @Override
    public void move(int x, int y) {
        // Implement rook-specific move logic if needed
    }

    @Override
    public ArrayList<Square> accessibleSquares(ChessBoard chsBoard) {
        int pieceX = this.getP().getX();
        int pieceY = this.getP().getY();

        ArrayList<Square> accessibleSquares = new ArrayList<>();

        // Check horizontally
        for (int x = pieceX + 1; x < chsBoard.getSquares()[0].length; x++) {
            Square square = chsBoard.getSquares()[pieceY][x];
            if (square.getPiece() == null) {
                accessibleSquares.add(square);
            } else {
                break; // Stop if there's a piece in the way
            }
        }

        for (int x = pieceX - 1; x >= 0; x--) {
            Square square = chsBoard.getSquares()[pieceY][x];
            if (square.getPiece() == null) {
                accessibleSquares.add(square);
            } else {
                break; // Stop if there's a piece in the way
            }
        }

        // Check vertically
        for (int y = pieceY + 1; y < chsBoard.getSquares().length; y++) {
            Square square = chsBoard.getSquares()[y][pieceX];
            if (square.getPiece() == null) {
                accessibleSquares.add(square);
            } else {
                break; // Stop if there's a piece in the way
            }
        }

        for (int y = pieceY - 1; y >= 0; y--) {
            Square square = chsBoard.getSquares()[y][pieceX];
            if (square.getPiece() == null) {
                accessibleSquares.add(square);
            } else {
                break; // Stop if there's a piece in the way
            }
        }

        return accessibleSquares;
    }

    @Override
    public boolean canBeat() {
        // Implement rook-specific beat logic here
        return false;
    }

    @Override
    public boolean isKingSafe() {
        return false;
    }

    @Override
    public Node createShape(){
        if(this.player.getPl() == Pl.BottomPlayer){
            ImageView img1 = new ImageView(new Image("pieces/images/bishop.png"));
            img1.setFitHeight(Math.floor((float)Square.edge*0.8));
            img1.setFitWidth(Math.floor((float)Square.edge*0.8));
            return img1;
        }
        if(this.player.getPl() == Pl.TopPlayer ){
            Circle rookShape = new Circle(20, Color.CHARTREUSE);
            return rookShape;
        }
        return null;
    }
}








