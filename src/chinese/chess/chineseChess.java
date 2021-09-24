
package chinese.chess;

import java.awt.Image;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class chineseChess {
    static Map<String, Image> keyNameValueImage = new HashMap<>();
    
    public chineseChess() {
    try {
            Set<String> imgNames = new HashSet<>(Arrays.asList(
          "bj", "bm", "bx", "bs", "bb", "bp", "bz",
          "rj", "rm", "rx", "rs", "rb", "rp", "rz"));
    for (String imgName : imgNames) { 
       Image img = ImageIO.read(getClass().getResource("/CCimage/"+ imgName + ".png")).getScaledInstance(CChessPanel.side, CChessPanel.side, Image.SCALE_SMOOTH);
       keyNameValueImage.put(imgName, img);
    }
        } catch (IOException ex) {
           ex.printStackTrace();
    }
    
    JFrame f = new JFrame("Chinese chess");
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.setSize(700, 800);
    f.setLocation(0, 0);
    CChessPanel ccPanel = new CChessPanel(new CCBoard());
    f.add(ccPanel);
    f.setVisible(true);

    }
     
}
