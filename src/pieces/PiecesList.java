package pieces;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PiecesList {

    private List<Piece> pieces;

    public PiecesList() {
        this.pieces = new ArrayList<>();
    }

    public void addPiece(Piece piece) {
        pieces.add(piece);
    }

    public void removePiece(Piece piece) {
        pieces.remove(piece);
    }

    public void movePiece(Piece piece, int newX, int newY) {
        piece.move(newX, newY);
    }

    // Add any other methods you need...

    public void displayPieces() {
        System.out.println("Current Pieces:");
        for (Piece piece : pieces) {
            System.out.println(piece);
        }
        System.out.println();
    }

    // Example of a method to remove pieces based on some condition
    public void removePiecesWithFacadeDir(int facadeDir) {
        Iterator<Piece> iterator = pieces.iterator();
        while (iterator.hasNext()) {
            Piece piece = iterator.next();
            if (piece.getFacadeDir() == facadeDir) {
                iterator.remove();
            }
        }
    }
}
