package pieces;

import chessboard.ChessBoard;
import chessboard.Square;
import def.Player;
import def.Point;
import def.Pl;
import javafx.scene.Node;

import java.util.ArrayList;

public abstract class Piece implements ISecureKing {
    protected Point p;

    public Player getPlayer() {
        return player;
    }

    protected Player player;
    protected int facadeDir;

    Piece(Player player, int x, int y) {
        this.p = new Point(x, y);
        this.player = player;
        this.facadeDir = (this.player != null && this.player.getPl() != null && this.player.getPl().equals(Pl.TopPlayer)) ? 1 : -1;
    }

    public void move(int x, int y) {
        this.p.setXY(x, y);
    }

    public boolean canMove() {
        isKingSafe();
        canBeat();
        return false;
    }

    public abstract boolean canBeat();

    public int getFacadeDir() {
        return facadeDir;
    }

    @Override
    public abstract boolean isKingSafe();

    public abstract Node createShape();

    public Point getP() {
        return p;
    }

    public void setP(Point p) {
        this.p = p;
    }

    public abstract ArrayList<Square> accessibleSquares(ChessBoard chsBoard);

    @Override
    public String toString() {
        return this.getClass().getName() + this.player.getPl() + p.toString() + "\n";
    }


    public boolean isKingSafe(ChessBoard chessBoard) {
        int kingX = player.getKing().getP().getX();
        int kingY = player.getKing().getP().getY();

        // Check if there's an opponent's pawn threatening the king diagonally
        int opponentFacadeDir = (player.getPl() == Pl.TopPlayer) ? -1 : 1;

        // Check top-left diagonal
        if (isValidSquare(chessBoard, kingX - 1, kingY + opponentFacadeDir) &&
                chessBoard.getSquares()[kingY + opponentFacadeDir][kingX - 1].getPiece() instanceof Pawn &&
                chessBoard.getSquares()[kingY + opponentFacadeDir][kingX - 1].getPiece().getPlayer() != player) {
            return false;
        }

        // Check top-right diagonal
        if (isValidSquare(chessBoard, kingX + 1, kingY + opponentFacadeDir) &&
                chessBoard.getSquares()[kingY + opponentFacadeDir][kingX + 1].getPiece() instanceof Pawn &&
                chessBoard.getSquares()[kingY + opponentFacadeDir][kingX + 1].getPiece().getPlayer() != player) {
            return false;
        }

        return true; // King is safe
    }

    private boolean isValidSquare(ChessBoard chessBoard, int x, int y) {
        return x >= 0 && x < 8 && y >= 0 && y < 8;
    }
}
