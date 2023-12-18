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
public class Queen extends Piece {

    public Queen(Player player, int x, int y) {
        super(player, x, y);
    }

    @Override
    public void move(int x, int y) {
        // Implement queen-specific move logic if needed
    }

    @Override
    public ArrayList<Square> accessibleSquares(ChessBoard chsBoard) {
        int pieceX = this.getP().getX();
        int pieceY = this.getP().getY();

        ArrayList<Square> accessibleSquares = new ArrayList<>();

        // Check horizontally
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                for (int i = 1; i < chsBoard.getSquares().length; i++) {
                    int newX = pieceX + i * dx;
                    int newY = pieceY + i * dy;

                    if (newX >= 0 && newX < chsBoard.getSquares()[0].length &&
                            newY >= 0 && newY < chsBoard.getSquares().length) {
                        Square square = chsBoard.getSquares()[newY][newX];
                        if (square.getPiece() == null) {
                            accessibleSquares.add(square);
                        }
                        // You may add logic to check if the square contains an opponent's piece
                        else {
                            break; // Stop if there's a piece in the way
                        }
                    } else {
                        break; // Stop if out of bounds
                    }
                }
            }
        }

        return accessibleSquares;
    }

    @Override
    public boolean canBeat() {
        // Implement queen-specific beat logic here
        return false;
    }

    @Override
    public boolean isKingSafe() {
        return false;
    }

    @Override
    public Node createShape() {
        if(this.player.getPl()==Pl.BottomPlayer) {
            ImageView img1 = new ImageView(new Image("pieces/images/queen.png"));
            img1.setFitHeight(Math.floor((float) Square.edge * 0.8));
            img1.setFitWidth(Math.floor((float) Square.edge * 0.8));

            return img1;
        }
        if(this.player.getPl()==Pl.TopPlayer ) {
            Circle queenShape = new Circle(20, Color.CHARTREUSE);
            return queenShape;
        }
        return null;
    }
}
