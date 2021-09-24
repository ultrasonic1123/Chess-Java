
package chinese.chess;


import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

class CChessPanel extends JPanel implements MouseListener, MouseMotionListener{
    
  final static int orgX = 60, orgY = 40, side = 67;
  private Point fromColRow;
  private Point movingPieceXY;
  private Image movingPieceImage;
  CCBoard brd = new CCBoard();

    public CChessPanel(CCBoard brd) {
        this.brd = brd;
        addMouseListener(this);
        addMouseMotionListener(this);
    }
  //--start drawing
  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    drawGrid(g);
    drawPieces(g);
    if(movingPieceImage != null){
       g.drawImage(movingPieceImage, movingPieceXY.x, movingPieceXY.y, null);
    }
    
  }
  private void drawPieces(Graphics g) {
    for (Piece p : brd.getPieces()) {
            if (fromColRow != null && fromColRow.x == p.getCol() && fromColRow.y == p.getRow()) {
        continue; 
      }
      Image img = chineseChess.keyNameValueImage.get(p.getImageName());
      g.drawImage(img, orgX + side * p.getCol() - side/2, 
                       orgY + side * p.getRow() - side/2, this);
    }
  }
  private void drawGrid(Graphics g) {    
    for (int i = 0; i < CCBoard.COLS; i++) {
      g.drawLine(orgX + i * side, orgY,
                 orgX + i * side, orgY + 4 * side);
      g.drawLine(orgX + i * side, orgY + 5 * side,
                 orgX + i * side, orgY + 9 * side);
    }
    for (int i = 0; i < CCBoard.ROWS; i++) {
      g.drawLine(orgX,              orgY + i * side,
                 orgX + 8 * side, orgY + i * side);
    }
    for (int i = 0; i < 2; i++) {
      g.drawLine(orgX + 3 * side, orgY + i * 7 * side,
                 orgX + 5 * side, orgY + (2 + i * 7) * side);
      g.drawLine(orgX + 5 * side, orgY + i * 7 * side,
                 orgX + 3 * side, orgY + (2 + i * 7) * side);
      g.drawLine(orgX + 8 * i * side, orgY + 4 * side,
                 orgX + 8 * i * side, orgY + 5 * side);
    }
    for (int i = 0; i < 2; i++) {
      drawStarAt(g, 1 + i * 6, 2);
      drawStarAt(g, 1 + i * 6, 7);
      drawHalfStarAt(g, 0, 3, false);
      drawHalfStarAt(g, 0, 6, false);
      drawHalfStarAt(g, 8, 3, true);
      drawHalfStarAt(g, 8, 6, true);
    }
    for (int i = 0; i < 3; i++) {
      drawStarAt(g, 2 + i * 2, 3);
      drawStarAt(g, 2 + i * 2, 6);
    }
    int margin = side/15;
    g.drawRect(orgX - margin, orgY - margin, (CCBoard.COLS - 1)* side + 2 * margin, (CCBoard.ROWS - 1)* side + 2 * margin);
    
  }
   private void drawHalfStarAt(Graphics g, int col, int row, 
                              boolean left) {
    int gap = side / 9;
    int bar = side / 4;
    int hSign = left ? -1 : 1; //(hozirontal)
    int tipX = orgX + col * side + hSign * gap;
    for (int i = 0; i < 2; i++) {
      int vSign = -1 + i * 2; //(vertical)
      int tipY = orgY + row * side + vSign * gap;  
      g.drawLine(tipX, tipY, tipX + hSign * bar , tipY);
      g.drawLine(tipX, tipY, tipX, tipY + vSign * bar);
    }
  }
    private void drawStarAt(Graphics g, int col, int row) {
    drawHalfStarAt(g, col, row, true);
    drawHalfStarAt(g, col, row, false);
  }
    private Point coordToColRow(Point xy) {
        return new Point((xy.x - orgX + side/2)/side,(xy.y - orgX + side/2)/side );
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mousePressed(MouseEvent e) {
       fromColRow = coordToColRow(e.getPoint());
       Piece movingPiece = brd.pieceAt(fromColRow.x, fromColRow.y);
       movingPieceImage = chineseChess.keyNameValueImage.get(movingPiece.imageName);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(fromColRow == null){
            return;
        }
        Point toColRow = coordToColRow(e.getPoint());
        if(brd.validMove(fromColRow.x, fromColRow.y, toColRow.x, toColRow.y)){
            brd.movePiece(fromColRow.x, fromColRow.y, toColRow.x, toColRow.y);
            if(brd.BlackIsChecked()){
                System.out.println("Black King is checked!");
                if(brd.isRescue(brd.BlackIsCheckedby()) || brd.existingMove(brd.blackKingAt())){
                    System.out.println("yeahhhh still alive");
                    JOptionPane.showMessageDialog(null, "Black-King is checked! ");
                }
                else{
                    int option = JOptionPane.showConfirmDialog(null, "Do you want to play a new game?","Player 1 WIN", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(option == JOptionPane.YES_OPTION) {
                       JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
                        frame.dispose();
                        new chineseChess();
                    } else{
                        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
                        new JframeStart();
                    }
                    System.out.println("Checkmate!");
                }
            }
            if(brd.RedIsChecked()){
                if(brd.isRescue(brd.RedIsCheckedby()) || brd.existingMove(brd.redKingAt())){
                    System.out.println("yeahhhh im still alive");
                    System.out.println("Red King is checked!");
                    JOptionPane.showMessageDialog(this, "Red-King is checked! ");
                }
                else {
                    JOptionPane.showConfirmDialog(this, "Do you want to play a new game?","Player 2 WIN", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    System.out.println("Checkmate!");
                    int option = JOptionPane.showConfirmDialog(this, "Do you want to play a new game?","Player 1 WIN", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(option == JOptionPane.YES_OPTION) {
                       JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
                        frame.dispose();
                        new chineseChess();
                    } else{
                        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
                        new JframeStart();
                    }
                  
                }
            }
            System.out.println(brd);
           
        }
        
            fromColRow = null;
            movingPieceXY = null;
            movingPieceImage = null;
            repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
       
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }
//mouse move--
    @Override
    public void mouseDragged(MouseEvent e) {
        //System.out.println(e.getPoint());
        Point mouseTip = e.getPoint();
        movingPieceXY = new Point(mouseTip.x - side/2, mouseTip.y - side/2);
        repaint();
        
    }

    @Override
    public void mouseMoved(MouseEvent e) { 
    }
   
    
}