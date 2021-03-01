package life;

import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameField extends JPanel {

    Universe myWorld;
    int sideOfSquare;

    public GameField(Dimension preferredSize, int sideOfSquare, Universe myWorld) {
        super();
        this.myWorld = myWorld;
        this.sideOfSquare = sideOfSquare;
        setPreferredSize(preferredSize);


    }


    public void paintComponent(Graphics g) {
        if (myWorld != null && myWorld.getSize() > 0) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(new Color(7, 7, 7));

            for (int i = 1; i <= myWorld.getSize(); i++) {
                for (int j = 1; j <= myWorld.getSize(); j++) {
                    g2d.drawRect((i) * sideOfSquare, (j)* sideOfSquare, sideOfSquare, sideOfSquare);
                    if (myWorld.alive(i, j)) {
                        g2d.fillRect((i) * sideOfSquare, (j) * sideOfSquare, sideOfSquare, sideOfSquare);
                    }
                }
            }
        }
        setPreferredSize(new Dimension(getWidth(), getHeight()));


    }

}
