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

public class Pawn extends Piece  {

    private boolean firstMove = true;
    private int stepLimit = 2;

    public Pawn(Player player, int x, int y) {
        super(player, x, y);
    }

    @Override
    public void move(int x, int y) {
        this.p.setY(this.p.getY() + this.facadeDir * y);
        if (firstMove){
            this.stepLimit=1;
        }
    }

    @Override
    public ArrayList<Square> accessibleSquares(ChessBoard chsBoard) {
        int pieceX = this.getP().getX();
        int pieceY = this.getP().getY();

        ArrayList<Square> accessibleSquares = new ArrayList<>();

        // Simplified logic for accessible squares based on direction
        int direction = this.facadeDir;

        // Check forward squares
        for (int dy = 1; dy <= stepLimit; dy++) {
            int newY = pieceY + dy * direction;
            if (newY >= 0 && newY < chsBoard.getSquares().length) {
                Square square = chsBoard.getSquares()[newY][pieceX];
                if (square.getPiece() == null) {
                    accessibleSquares.add(square);
                } else {
                    break; // Stop if there's a piece in the way
                }
            }
        }

        // Check diagonal squares for beating
        int[] diagonalOffsets = { -1, 1 };
        for (int dx : diagonalOffsets) {
            int newX = pieceX + dx;
            int newY = pieceY + direction;
            if (newY >= 0 && newY < chsBoard.getSquares().length && newX >= 0 && newX < chsBoard.getSquares()[0].length) {
                Square square = chsBoard.getSquares()[newY][newX];
                if (square.getPiece() != null && square.getPiece().getPlayer() != this.player) {
                    accessibleSquares.add(square);
                }
            }
        }

        return accessibleSquares;
    }

    @Override
    public boolean canBeat() {
        // Implement pawn-specific beat logic here
        return false;
    }

    @Override
    public boolean isKingSafe() {
        return false;
    }
    public boolean isKingSafe(ChessBoard chsBoard) {

        return false;
    }

    /*@Override
    public boolean isKingSafe(ChessBoard chsBoard) {
        // Get the king's position
        int kingX = player.getKing().getP().getX();
        int kingY = player.getKing().getP().getY();

        // Check if any accessible square puts the king in check
        for (Square square : accessibleSquares(chsBoard)) {
            if (square.getP().getX() == kingX && square.getP().getY() == kingY) {
                return false; // King is not safe
            }
        }

        return true; // King is safe
    }*/

    @Override
    public Node createShape() {
        if (this.player.getPl() == Pl.BottomPlayer) {
            ImageView img1 = new ImageView(new Image("pieces/images/pawn.png"));
            img1.setFitHeight(Math.floor((float) Square.edge * 0.8));
            img1.setFitWidth(Math.floor((float) Square.edge * 0.8));

            return img1;
        }
        if (this.player.getPl() == Pl.TopPlayer) {
            Circle pawnShape = new Circle(7, Color.CHARTREUSE);
            return pawnShape;
        }
        return null;
    }
}
