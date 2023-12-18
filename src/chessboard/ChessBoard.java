package chessboard;

import java.util.ArrayList;
import def.Point;
import javafx.scene.layout.GridPane;
import pieces.*;


public class ChessBoard {
    private static final int BOARD_SIZE = 8;
    private GridPane chessboard = new GridPane();

    private Square[][] squares = new Square[BOARD_SIZE][BOARD_SIZE];

    public GridPane getChessboard() {
        return chessboard;
    }

    public Square[][] getSquares() {
        return squares;
    }

    public GridPane createChessBoard() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                squares[row][col] = new Square(new Point(col, row));
                this.chessboard.add(squares[row][col].getShape(), col, row);
            }
        }
        return chessboard;
    }

    public ArrayList<Square> emptySquares() {
        ArrayList<Square> emptySqr = new ArrayList<>();
        for (Square[] row : squares) {
            for (Square sqr : row) {
                if (sqr.piece == null) {
                    emptySqr.add(sqr);
                }
            }
        }
        return emptySqr;
    }

    public void removeItem(int rowIndex, int colIndex) {
        javafx.collections.ObservableList<javafx.scene.Node> children = this.chessboard.getChildren();
        javafx.scene.Node nodeToRemove = null;
        for (javafx.scene.Node node : children) {
            Integer row = GridPane.getRowIndex(node);
            Integer col = GridPane.getColumnIndex(node);
            if (row != null && col != null && row == colIndex && col == rowIndex && !(node instanceof javafx.scene.shape.Rectangle)) {
                nodeToRemove = node;
                break;
            }
        }
        if (nodeToRemove != null) {
            children.remove(nodeToRemove);
        }
    }

    public void includePieces(ArrayList<Piece> pieces) {
        for (Piece piece : pieces) {
            squares[piece.getP().getY()][piece.getP().getX()].setPiece(piece);
        }
    }
}
