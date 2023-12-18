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


// ... (same package as other pieces)

public class Knight extends Piece {

    public Knight(Player player, int x, int y) {
        super(player, x, y);
    }

    @Override
    public void move(int x, int y) {
        // Implement knight-specific move logic if needed
    }

    @Override
    public ArrayList<Square> accessibleSquares(ChessBoard chsBoard) {
        int pieceX = this.getP().getX();
        int pieceY = this.getP().getY();

        ArrayList<Square> accessibleSquares = new ArrayList<>();

        // Check L-shaped moves
        int[] dx = {2, 1, -1, -2, -2, -1, 1, 2};
        int[] dy = {1, 2, 2, 1, -1, -2, -2, -1};

        for (int i = 0; i < dx.length; i++) {
            int newX = pieceX + dx[i];
            int newY = pieceY + dy[i];

            if (newX >= 0 && newX < chsBoard.getSquares()[0].length &&
                    newY >= 0 && newY < chsBoard.getSquares().length) {
                Square square = chsBoard.getSquares()[newY][newX];
                if (square.getPiece() == null) {
                    accessibleSquares.add(square);
                }
                // You may add logic to check if the square contains an opponent's piece
            }
        }

        return accessibleSquares;
    }

    @Override
    public boolean canBeat() {
        // Implement knight-specific beat logic here
        return false;
    }

    @Override
    public boolean isKingSafe() {
        return false;
    }

    @Override
    public Node createShape() {
        if(this.player.getPl()==Pl.BottomPlayer) {
            ImageView img1 = new ImageView(new Image("pieces/images/knight.png"));
            img1.setFitHeight(Math.floor((float) Square.edge * 0.8));
            img1.setFitWidth(Math.floor((float) Square.edge * 0.8));

            return img1;
        }
        if(this.player.getPl()==Pl.TopPlayer ) {
            Circle knightShape = new Circle(7, Color.CHARTREUSE);
            return knightShape;
        }
        return null;
    }
}

