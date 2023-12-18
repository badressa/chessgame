package chessboard;

import def.Point;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import pieces.Piece;

public class Square {
    Piece piece;
    private Point p;
    private Rectangle r;
    public static int edge=60;



    public Square(Point p) {
        this.r =new Rectangle(edge, edge, (p.getX() + p.getY() ) % 2 == 0 ? Color.WHITE : Color.BROWN);
        this.p = p;
    }

    public Square(Piece piece, Point p) {
        piece.setP(p);
        this.piece = piece;
        this.p = p;
    }

    public Piece getPiece() {
        return piece;
    }
    public Rectangle getShape(){
        return this.r;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public Point getP() {
        return p;
    }
}

