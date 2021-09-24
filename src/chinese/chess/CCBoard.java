package chinese.chess;

import static chinese.chess.RANK.KING;
import java.util.HashSet;
import java.util.Set;

public class CCBoard {

    final static int ROWS = 10;
    final static int COLS = 9;
    private boolean isRedTurn = true;
    private Set<Piece> pieces = new HashSet<Piece>();
    Set<Piece> canCheckRedKingFuture = new HashSet<Piece>();
    Set<Piece> canCheckBlackKingFuture = new HashSet<Piece>();

    public Piece pieceAt(int col, int row) {
        for (Piece piece : pieces) {
            if (piece.getCol() == col && piece.getRow() == row) {
                return piece;
            }
        }
        return null;
    }

    public Piece blackKingAt() {
        for (Piece piece : pieces) {
            if (piece.getRank() == KING && piece.getIsRed() == false) {
                return piece;
            }
        }
        return null;
    }

    public Piece redKingAt() {
        for (Piece piece : pieces) {
            if (piece.getRank() == KING && piece.getIsRed() == true) {
                return piece;
            }
        }
        return null;
    }

    public boolean RedIsChecked() {
        for (Piece piece : pieces) {
            if (validMoveRegardnessTurn(piece.getCol(), piece.getRow(), redKingAt().getCol(), redKingAt().getRow())) {
                return true;
            }

        }
        return false;
    }

    public Piece RedIsCheckedby() {
        for (Piece piece : pieces) {
            if (validMoveRegardnessTurn(piece.getCol(), piece.getRow(), redKingAt().getCol(), redKingAt().getRow())) {
                return piece;
            }

        }
        return null;
    }

    public boolean BlackIsChecked() {
        for (Piece piece : pieces) {
            if (validMoveRegardnessTurn(piece.getCol(), piece.getRow(), blackKingAt().getCol(), blackKingAt().getRow())) {
                return true;
            }
        }
        return false;
    }

    public Piece BlackIsCheckedby() {
        for (Piece piece : pieces) {
            if (validMoveRegardnessTurn(piece.getCol(), piece.getRow(), blackKingAt().getCol(), blackKingAt().getRow())) {
                return piece;
            }

        }
        return null;
    }

    Set<Piece> dangerRedChecking() {
        for (Piece piece : pieces) {
            Piece redKing = redKingAt();
            Piece blacKing = blackKingAt();
            if (piece.getIsRed() == false) {
                switch (piece.getRank()) {
                    case ROOK:
                        if (isStraight(piece.getCol(), piece.getRow(), redKing.getCol(), redKing.getRow())
                                && numPieceBetween(piece.getCol(), piece.getRow(), redKing.getCol(), redKing.getRow()) == 1) {
                            if (piece.getCol() == redKing.getCol()) {//Vertical
                                int head = Math.min(piece.getRow(), redKing.getRow());
                                int tail = Math.max(piece.getRow(), redKing.getRow());
                                for (int row = head + 1; row < tail; row++) {
                                    if (pieceAt(piece.getCol(), row) != null) {
                                        canCheckRedKingFuture.add(pieceAt(piece.getCol(), row));
                                    }
                                }
                            } else {
                                int head = Math.min(piece.getCol(), redKing.getCol());
                                int tail = Math.max(piece.getCol(), redKing.getCol());
                                for (int col = head + 1; col < tail; col++) {
                                    if (pieceAt(col, piece.getRow()) != null) {
                                        canCheckRedKingFuture.add(pieceAt(col, piece.getRow()));
                                    }
                                }
                            }
                        }
                    case CANNON:

                        if (isStraight(piece.getCol(), piece.getRow(), redKing.getCol(), redKing.getRow())
                                && numPieceBetween(piece.getCol(), piece.getRow(), redKing.getCol(), redKing.getRow()) == 2) {
                            if (piece.getCol() == redKing.getCol()) {//Vertical
                                int head = Math.min(piece.getRow(), redKing.getRow());
                                int tail = Math.max(piece.getRow(), redKing.getRow());
                                for (int row = head + 1; row < tail; row++) {
                                    if (pieceAt(piece.getCol(), row) != null) {
                                        canCheckRedKingFuture.add(pieceAt(piece.getCol(), row));
                                    }
                                }
                            } else {
                                int head = Math.min(piece.getCol(), redKing.getCol());
                                int tail = Math.max(piece.getCol(), redKing.getCol());
                                for (int col = head + 1; col < tail; col++) {
                                    if (pieceAt(col, piece.getRow()) != null) {
                                        canCheckRedKingFuture.add(pieceAt(col, piece.getRow()));
                                    }
                                }
                            }
                        }
                    case KNIGHT:
                        if (Math.abs(piece.getCol() - redKing.getCol()) == 1 && Math.abs(piece.getRow() - redKing.getRow()) == 2) {
                            if (pieceAt(piece.getCol(), (piece.getRow() + redKing.getRow()) / 2) != null) {
                                canCheckRedKingFuture.add(pieceAt(piece.getCol(), (piece.getRow() + redKing.getRow()) / 2));
                            }
                        } else if (Math.abs(piece.getCol() - redKing.getCol()) == 2 && Math.abs(piece.getRow() - redKing.getRow()) == 1) {
                            if (pieceAt((piece.getCol() + redKing.getCol()) / 2, piece.getRow()) != null) {
                                canCheckRedKingFuture.add(pieceAt((piece.getCol() + redKing.getCol()) / 2, piece.getRow()));
                            }
                        }
                }
            }
        }
        return canCheckRedKingFuture;
    }

    Set<Piece> dangerBlackChecking() {
        for (Piece piece : pieces) {
            Piece blackKing = blackKingAt();
            if (piece.getIsRed() == true) {
                switch (piece.getRank()) {
                    case ROOK:
                        if (isStraight(piece.getCol(), piece.getRow(), blackKing.getCol(), blackKing.getRow())
                                && numPieceBetween(piece.getCol(), piece.getRow(), blackKing.getCol(), blackKing.getRow()) == 1) {
                            if (piece.getCol() == blackKing.getCol()) {//Vertical
                                int head = Math.min(piece.getRow(), blackKing.getRow());
                                int tail = Math.max(piece.getRow(), blackKing.getRow());
                                for (int row = head + 1; row < tail; row++) {
                                    if (pieceAt(piece.getCol(), row) != null) {
                                        canCheckRedKingFuture.add(pieceAt(piece.getCol(), row));
                                    }
                                }
                            } else {
                                int head = Math.min(piece.getCol(), blackKing.getCol());
                                int tail = Math.max(piece.getCol(), blackKing.getCol());
                                for (int col = head + 1; col < tail; col++) {
                                    if (pieceAt(col, piece.getRow()) != null) {
                                        canCheckRedKingFuture.add(pieceAt(col, piece.getRow()));
                                    }
                                }
                            }
                        }
                    case CANNON:

                        if (isStraight(piece.getCol(), piece.getRow(), blackKing.getCol(), blackKing.getRow())
                                && numPieceBetween(piece.getCol(), piece.getRow(), blackKing.getCol(), blackKing.getRow()) == 2) {
                            if (piece.getCol() == blackKing.getCol()) {//Vertical
                                int head = Math.min(piece.getRow(), blackKing.getRow());
                                int tail = Math.max(piece.getRow(), blackKing.getRow());
                                for (int row = head + 1; row < tail; row++) {
                                    if (pieceAt(piece.getCol(), row) != null) {
                                        canCheckRedKingFuture.add(pieceAt(piece.getCol(), row));
                                    }
                                }
                            } else {
                                int head = Math.min(piece.getCol(), blackKing.getCol());
                                int tail = Math.max(piece.getCol(), blackKing.getCol());
                                for (int col = head + 1; col < tail; col++) {
                                    if (pieceAt(col, piece.getRow()) != null) {
                                        canCheckRedKingFuture.add(pieceAt(col, piece.getRow()));
                                    }
                                }
                            }
                        }
                    case KNIGHT:
                        if (Math.abs(piece.getCol() - blackKing.getCol()) == 1 && Math.abs(piece.getRow() - blackKing.getRow()) == 2) {
                            if (pieceAt(piece.getCol(), (piece.getRow() + blackKing.getRow()) / 2) != null) {
                                canCheckRedKingFuture.add(pieceAt(piece.getCol(), (piece.getRow() + blackKing.getRow()) / 2));
                            }
                        } else if (Math.abs(piece.getCol() - blackKing.getCol()) == 2 && Math.abs(piece.getRow() - blackKing.getRow()) == 1) {
                            if (pieceAt((piece.getCol() + blackKing.getCol()) / 2, piece.getRow()) != null) {
                                canCheckRedKingFuture.add(pieceAt((piece.getCol() + blackKing.getCol()) / 2, piece.getRow()));
                            }
                        }
                }
            }
        }
        return canCheckBlackKingFuture;
    }

    boolean existingMove(Piece p) {
        boolean check = false;
        if (p.getIsRed() == true) {
            if (validMoveRegardnessTurn(p.getCol(), p.getRow(), p.getCol() + 1, p.getRow())) {
                for (Piece piece : pieces) {
                    if (piece.getIsRed() == false && validMoveRegardnessTurn(piece.getCol(), piece.getRow(), p.getCol() + 1, p.getRow())) {
                        check = true;
                    }
                }
            }
            if (validMoveRegardnessTurn(p.getCol(), p.getRow(), p.getCol() - 1, p.getRow())) {
                for (Piece piece : pieces) {
                    if (piece.getIsRed() == false && validMoveRegardnessTurn(piece.getCol(), piece.getRow(), p.getCol() - 1, p.getRow())) {
                        check = true;
                    }
                }
            }
            if (validMoveRegardnessTurn(p.getCol(), p.getRow(), p.getCol(), p.getRow() - 1)) {
                for (Piece piece : pieces) {
                    if (piece.getIsRed() == false && validMoveRegardnessTurn(piece.getCol(), piece.getRow(), p.getCol(), p.getRow() - 1)) {
                        check = true;
                    }
                }
            }
            if (validMoveRegardnessTurn(p.getCol(), p.getRow(), p.getCol(), p.getRow() + 1)) {
                for (Piece piece : pieces) {
                    if (piece.getIsRed() == false && validMoveRegardnessTurn(piece.getCol(), piece.getRow(), p.getCol() + 1, p.getRow())) {
                        check = true;
                    }
                }
            }
        }

        return check;
    }

    boolean isRescue(Piece p) {
        boolean yes = false;
        Piece k;
        if (p.getIsRed()) {
            k = blackKingAt();
        } else {
            k = redKingAt();
        }
        switch (p.getRank()) {
            case ROOK:
                int rescuePiece = 0;
                String name = "";
                if (p.getCol() == k.getCol()) {   //vertical check
                    for (Piece piece2 : pieces) {
                        if (piece2.getIsRed() == k.getIsRed() && k.getIsRed() == true) {//red King is checked
                            for (int i = k.getRow() + 1; i <= p.getRow(); i++) {
                                if (validMoveRegardnessTurn(piece2.getCol(), piece2.getRow(), p.getCol(), i)) {
                                    rescuePiece++;
                                    System.out.println(piece2.getImageName());
                                    name = piece2.getImageName();
                                    break;
                                }
                            }
                            if (rescuePiece > 1) {
                                yes = true;
                            }
                            if (rescuePiece == 1 && name != "rb") {
                                yes = true;
                                for (Piece a : dangerRedChecking()) {
                                    if (name == a.getImageName()) {
                                        yes = false;
                                    }
                                    canCheckRedKingFuture.clear();
                                    break;
                                }

                            }
                        } else if (piece2.getIsRed() == k.getIsRed() && k.getIsRed() == false)//black King is checked
                        {
                            System.out.println("b");
                            for (int i = p.getRow(); i < k.getRow(); i++) {
                                if (validMoveRegardnessTurn(piece2.getCol(), piece2.getRow(), p.getCol(), i)) {
                                    rescuePiece++;
                                    System.out.println(piece2.getImageName());
                                    name = piece2.getImageName();
                                    break;
                                }
                            }
                            if (rescuePiece > 1) {
                                yes = true;
                            }
                            if (rescuePiece == 1 && name != "bb") {
                                yes = true;
                                for (Piece a : dangerBlackChecking()) {
                                    if (name == a.getImageName()) {
                                        yes = false;
                                    }
                                    canCheckBlackKingFuture.clear();
                                    break;
                                }
                            }
                        }
                    }
                } else {  //horizontal check
                    for (Piece piece2 : pieces) {
                        if (piece2.getIsRed() == k.getIsRed()) {
                            if (p.getCol() < k.getCol()) { //leftside check
                                for (int i = p.getCol(); i < k.getCol(); i++) {
                                    if (validMoveRegardnessTurn(piece2.getCol(), piece2.getRow(), i, p.getRow())) {
                                        rescuePiece++;
                                        name = piece2.getImageName();
                                        System.out.println(piece2.getImageName());
                                    }
                                    break;
                                }
                                if (rescuePiece > 1) {
                                    yes = true;
                                }
                                if (rescuePiece == 1 && name != "bb" && name != "rb") {
                                    yes = true;
                                    if (RedIsChecked()) {
                                        for (Piece a : dangerRedChecking()) {
                                            if (name == a.getImageName()) {
                                                yes = false;
                                            }
                                            canCheckRedKingFuture.clear();
                                            break;
                                        }
                                    } else {
                                        for (Piece a : dangerBlackChecking()) {
                                            if (name == a.getImageName()) {
                                                yes = false;
                                            }
                                            canCheckBlackKingFuture.clear();
                                            break;
                                        }
                                    }
                                }
                            } else {                      //rightside check
                                for (int i = k.getCol() + 1; i <= p.getCol(); i++) {
                                    if (validMoveRegardnessTurn(piece2.getCol(), piece2.getRow(), i, p.getRow())) {
                                        yes = true;
                                        System.out.println(piece2.getImageName());
                                    }
                                    break;
                                }
                                if (rescuePiece > 1) {
                                    yes = true;
                                }
                                if (rescuePiece == 1 && name != "bb" && name != "rb") {
                                    yes = true;
                                    if (RedIsChecked()) {
                                        for (Piece a : dangerRedChecking()) {
                                            if (name == a.getImageName()) {
                                                yes = false;
                                            }
                                            canCheckRedKingFuture.clear();
                                            break;
                                        }
                                    } else {
                                        for (Piece a : dangerBlackChecking()) {
                                            if (name == a.getImageName()) {
                                                yes = false;
                                            }
                                            canCheckBlackKingFuture.clear();
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                System.out.println(yes);
                break;
            case KNIGHT:
                if (Math.abs(p.getCol() - k.getCol()) == 1) {
                    for (Piece piece2 : pieces) {
                        if (piece2.getIsRed() == k.getIsRed()) {
                            if (validMoveRegardnessTurn(piece2.getCol(), piece2.getRow(), p.getCol(), p.getRow())) {
                                return true;
                            } else {
                                if (validMoveRegardnessTurn(piece2.getCol(), piece2.getRow(), p.getCol(), (p.getRow() + k.getRow()) / 2)) {
                                    System.out.println("can rc");
                                    return true;
                                }
                            }
                        }
                    }
                } else {
                    for (Piece piece2 : pieces) {
                        if (piece2.getIsRed() == k.getIsRed()) {
                            if (validMoveRegardnessTurn(piece2.getCol(), piece2.getRow(), p.getCol(), p.getRow())) {
                                return true;
                            } else {
                                if (validMoveRegardnessTurn(piece2.getCol(), piece2.getRow(), (p.getCol() + k.getCol()) / 2, p.getRow())) {
                                    System.out.println("can rc");
                                    return true;
                                }
                            }
                        }
                    }
                }
                break;
            case CANNON:
                if (p.getCol() == k.getCol()) {   //vertical check
                    for (Piece piece2 : pieces) {
                        if (piece2.getIsRed() == k.getIsRed() && k.getIsRed() == true) {//red King is checked
                            for (int i = k.getRow() + 1; i <= p.getRow(); i++) {
                                if (validMoveRegardnessTurn(piece2.getCol(), piece2.getRow(), p.getCol(), i)) {

                                    System.out.println(piece2.getImageName());

                                    break;
                                }
                            }

                        } else if (piece2.getIsRed() == k.getIsRed() && k.getIsRed() == false)//black King is checked
                        {
                            System.out.println("b");
                            for (int i = p.getRow(); i < k.getRow(); i++) {
                                if (validMoveRegardnessTurn(piece2.getCol(), piece2.getRow(), p.getCol(), i)) {

                                    System.out.println(piece2.getImageName());

                                    break;
                                }
                            }
                        }
                    }
                } else {  //horizontal check
                    for (Piece piece2 : pieces) {
                        if (piece2.getIsRed() == k.getIsRed()) {
                            if (p.getCol() < k.getCol()) { //leftside check
                                for (int i = p.getCol(); i < k.getCol(); i++) {
                                    if (validMoveRegardnessTurn(piece2.getCol(), piece2.getRow(), i, p.getRow())) {

                                        System.out.println(piece2.getImageName());
                                    }
                                    break;
                                }

                            } else {                      //rightside check
                                for (int i = k.getCol() + 1; i <= p.getCol(); i++) {
                                    if (validMoveRegardnessTurn(piece2.getCol(), piece2.getRow(), i, p.getRow())) {
                                        yes = true;
                                        System.out.println(piece2.getImageName());
                                    }
                                    break;
                                }

                            }
                        }
                    }
                }
                System.out.println(yes);
                break;
        }
        return yes;
    }

    //boolean canEscape(){
    //    
    //}
    public void movePiece(int fromCol, int fromRow, int toCol, int toRow) {
        Piece movingP = pieceAt(fromCol, fromRow);
        Piece targetP = pieceAt(toCol, toRow);
        pieces.remove(movingP);
        pieces.remove(targetP);
        pieces.add(new Piece(toCol, toRow, movingP.getIsRed(), movingP.getRank(), movingP.getImageName()));
        isRedTurn = !isRedTurn;
    }

    private boolean outOfBoard(int col, int row) {
        return col < 0 || col > 8 || row < 0 || row > 9;
    }

    private boolean isStraight(int fromCol, int fromRow, int toCol, int toRow) {
        return fromCol == toCol || fromRow == toRow;
    }

    private boolean isDiagonal(int fromCol, int fromRow, int toCol, int toRow) {
        return Math.abs(fromCol - toCol) == Math.abs(fromRow - toRow);
    }

    private int steps(int fromCol, int fromRow, int toCol, int toRow) {
        if (fromCol == toCol) {
            return Math.abs(fromRow - toRow);
        } else if (fromRow == toRow) {
            return Math.abs(fromCol - toCol);
        } else if (isDiagonal(fromCol, fromRow, toCol, toRow)) {
            return Math.abs(fromRow - toRow);
        }
        return 0;//neither straight nor Daigonal
    }

    private boolean outOfPalace(int col, int row, boolean isRed) {
        if (isRed) {
            return col < 3 || col > 5 || row < 0 || row > 2;
        } else {
            return col < 3 || col > 5 || row < 7 || row > 9;
        }
    }

    private boolean selfSide(int row, boolean isRed) {
        return isRed ? row <= 4 : row >= 5;
    }

    private int numPieceBetween(int fromCol, int fromRow, int toCol, int toRow) {
        if (!isStraight(fromCol, fromRow, toCol, toRow) || steps(fromCol, fromRow, toCol, toRow) < 2) {
            return 0;
        }
        int cnt = 0, head, tail;
        if (fromCol == toCol) {//Vertical
            head = Math.min(fromRow, toRow);
            tail = Math.max(fromRow, toRow);
            for (int row = head + 1; row < tail; row++) {
                cnt += (pieceAt(fromCol, row) == null) ? 0 : 1;
            }
        } else {
            head = Math.min(fromCol, toCol);
            tail = Math.max(fromCol, toCol);
            for (int col = head + 1; col < tail; col++) {
                cnt += (pieceAt(col, fromRow) == null) ? 0 : 1;
            }
        }
        return cnt;
    }

    private boolean selfKilling(int fromCol, int fromRow, int toCol, int toRow, boolean isRed) {
        Piece target = pieceAt(toCol, toRow);
        return target != null && target.getIsRed() == isRed;
    }

    private boolean isValidGuardMove(int fromCol, int fromRow, int toCol, int toRow, boolean isRed) {
        if (outOfPalace(toCol, toRow, isRed)) {
            return false;
        }
        return isDiagonal(fromCol, fromRow, toCol, toRow) && steps(fromCol, fromRow, toCol, toRow) == 1;
    }

    private boolean isValidKingMove(int fromCol, int fromRow, int toCol, int toRow, boolean isRed) {
        if (outOfPalace(toCol, toRow, isRed)) {
            return false;
        }
        return isStraight(fromCol, fromRow, toCol, toRow) && steps(fromCol, fromRow, toCol, toRow) == 1;
    }

    private boolean isValidKnightMove(int fromCol, int fromRow, int toCol, int toRow) {
        if (Math.abs(fromCol - toCol) == 1 && Math.abs(fromRow - toRow) == 2) {
            return pieceAt(fromCol, (fromRow + toRow) / 2) == null;
        } else if (Math.abs(fromCol - toCol) == 2 && Math.abs(fromRow - toRow) == 1) {
            return pieceAt((fromCol + toCol) / 2, fromRow) == null;
        }
        return false;
    }

    private boolean isValidBishopMove(int fromCol, int fromRow, int toCol, int toRow, boolean isRed) {
        return selfSide(toRow, isRed)
                && pieceAt((fromCol + toCol) / 2, (fromRow + toRow) / 2) == null
                && isDiagonal(fromCol, fromRow, toCol, toRow)
                && steps(fromCol, fromRow, toCol, toRow) == 2;
    }

    private boolean isValidRookMove(int fromCol, int fromRow, int toCol, int toRow) {
        return isStraight(fromCol, fromRow, toCol, toRow)
                && numPieceBetween(fromCol, fromRow, toCol, toRow) == 0;
    }

    private boolean isValidCannonMove(int fromCol, int fromRow, int toCol, int toRow) {
        if (pieceAt(toCol, toRow) == null) {
            return isValidRookMove(fromCol, fromRow, toCol, toRow);
        }
        return numPieceBetween(fromCol, fromRow, toCol, toRow) == 1;
    }

    private boolean isValidPawnMove(int fromCol, int fromRow, int toCol, int toRow, boolean isRed) {
        if (steps(fromCol, fromRow, toCol, toRow) != 1 || isStraight(fromCol, fromRow, toCol, toRow) != true) {
            return false;
        }
        return isRed && toRow > fromRow || !isRed && toRow < fromRow
                || (!selfSide(fromRow, isRed) && (isRed && toRow >= fromRow || !isRed && toRow <= fromRow));
    }
//-------------------------------------------------

    boolean validMove(int fromC, int fromR, int toC, int toR) {
        if (fromC == toC && fromR == toR
                || outOfBoard(toC, toR)) {
            return false;
        }
        Piece p = pieceAt(fromC, fromR);
        if (p == null || p.getIsRed() != isRedTurn || selfKilling(fromC, fromR, toC, toR, p.getIsRed())) {
            return false;
        }
        boolean ok = false;
        switch (p.getRank()) {
            case GUARD:
                ok = isValidGuardMove(fromC, fromR, toC, toR, p.getIsRed());
                break;
            case KING:
                ok = isValidKingMove(fromC, fromR, toC, toR, p.getIsRed());
                break;
            case BISHOP:
                ok = isValidBishopMove(fromC, fromR, toC, toR, p.getIsRed());
                break;
            case KNIGHT:
                ok = isValidKnightMove(fromC, fromR, toC, toR);
                break;
            case ROOK:
                ok = isValidRookMove(fromC, fromR, toC, toR);
                break;
            case CANNON:
                ok = isValidCannonMove(fromC, fromR, toC, toR);
                break;
            case PAWN:
                ok = isValidPawnMove(fromC, fromR, toC, toR, p.getIsRed());
                break;
        }
        return ok;
    }

    boolean validMoveRegardnessTurn(int fromC, int fromR, int toC, int toR) {
        if (fromC == toC && fromR == toR
                || outOfBoard(toC, toR)) {
            return false;
        }
        Piece p = pieceAt(fromC, fromR);
        if (p == null || selfKilling(fromC, fromR, toC, toR, p.getIsRed())) {
            return false;
        }
        boolean ok = false;
        switch (p.getRank()) {
            case GUARD:
                ok = isValidGuardMove(fromC, fromR, toC, toR, p.getIsRed());
                break;
            case KING:
                ok = isValidKingMove(fromC, fromR, toC, toR, p.getIsRed());
                break;
            case BISHOP:
                ok = isValidBishopMove(fromC, fromR, toC, toR, p.getIsRed());
                break;
            case KNIGHT:
                ok = isValidKnightMove(fromC, fromR, toC, toR);
                break;
            case ROOK:
                ok = isValidRookMove(fromC, fromR, toC, toR);
                break;
            case CANNON:
                ok = isValidCannonMove(fromC, fromR, toC, toR);
                break;
            case PAWN:
                ok = isValidPawnMove(fromC, fromR, toC, toR, p.getIsRed());
                break;
        }
        return ok;
    }

    CCBoard() {//khởi tạo bàn cờ cơ bản.
        pieces.add(new Piece(4, 0, true, RANK.KING, "rb"));
        pieces.add(new Piece(4, 9, false, RANK.KING, "bb"));
        for (int i = 0; i < 5; i++) {
            pieces.add(new Piece(i * 2, 3, true, RANK.PAWN, "rz"));
            pieces.add(new Piece(i * 2, 6, false, RANK.PAWN, "bz"));
        }
        for (int i = 0; i < 2; i++) {

            pieces.add(new Piece(0 + i * 8, 0, true, RANK.ROOK, "rj"));
            pieces.add(new Piece(1 + i * 6, 0, true, RANK.KNIGHT, "rm"));
            pieces.add(new Piece(2 + i * 4, 0, true, RANK.BISHOP, "rx"));
            pieces.add(new Piece(3 + i * 2, 0, true, RANK.GUARD, "rs"));
            pieces.add(new Piece(1 + i * 6, 2, true, RANK.CANNON, "rp"));

            pieces.add(new Piece(0 + i * 8, 9, false, RANK.ROOK, "bj"));
            pieces.add(new Piece(1 + i * 6, 9, false, RANK.KNIGHT, "bm"));
            pieces.add(new Piece(2 + i * 4, 9, false, RANK.BISHOP, "bx"));
            pieces.add(new Piece(3 + i * 2, 9, false, RANK.GUARD, "bs"));
            pieces.add(new Piece(1 + i * 6, 7, false, RANK.CANNON, "bp"));
        }
    }

    public Set<Piece> getPieces() {
        return pieces;
    }

    @Override
    public String toString() {
        String brd = " ";
        for (int i = 0; i < 9; i++) {
            brd += " " + i + "";
        }
        brd += "\n";
        for (int row = 0; row < ROWS; row++) {
            brd += row + "" + " ";
            for (int col = 0; col < COLS; col++) {
                Piece p = pieceAt(col, row);
                if (p == null) {
                    brd += ". ";
                } else {
                    switch (p.getRank()) {
                        case ROOK:
                            brd += p.getIsRed() ? "R " : "r ";
                            break;
                        case KNIGHT:
                            brd += p.getIsRed() ? "N " : "n ";
                            break;
                        case BISHOP:
                            brd += p.getIsRed() ? "B " : "b ";
                            break;
                        case GUARD:
                            brd += p.getIsRed() ? "G " : "g ";
                            break;
                        case KING:
                            brd += p.getIsRed() ? "K " : "k ";
                            break;
                        case CANNON:
                            brd += p.getIsRed() ? "C " : "c ";
                            break;
                        case PAWN:
                            brd += p.getIsRed() ? "P " : "p ";
                            break;
                    }
                }
            }
            brd += "\n";
        }
        return brd;
    }
}
