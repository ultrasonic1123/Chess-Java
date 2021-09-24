
package chinese.chess;
enum RANK{
    ROOK, // xe
    KNIGHT, // mã
    BISHOP, //tịnh
    GUARD, // sỹ
    KING, // tướng
    CANNON, //pháo
    PAWN // tốt
}

public class Piece {
    private int col;
    private int row;
    private boolean isRed;//true for RED, false for BLACK
    private RANK rank;
    String imageName;
    public int getCol(){
        return col;
    }
    
    public int getRow(){
        return row;
    }
    
    public RANK getRank(){
        return rank;
    }
    
    public boolean getIsRed(){
        return isRed;
    }
    
    public String getImageName(){
        return imageName;
    }
    
    public Piece(int col, int row, boolean isRed, RANK rank, String imageName){
        this.col = col;
        this.row = row;
        this.isRed = isRed;
        this.rank = rank;
        this.imageName = imageName;
    }
    
}
