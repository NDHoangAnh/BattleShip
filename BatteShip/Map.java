package BatteShip;

import javax.swing.*;
import java.awt.*;


public class Map extends JPanel {
    public JButton mapPiece[][];
    public boolean isShip[][];

    public Map(int w, int h) {
        super();
        mapPiece = new JButton[11][11];
        isShip = new boolean[11][11];
        init();


        this.setSize(w, h);
        this.setLayout(new GridLayout(10, 10)); // tạo GridLayout cho Map

        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= 10; j++) {
                mapPiece[i][j] = new JButton(); // chưa có action
                mapPiece[i][j].setBackground(Color.decode("#114D73"));
                mapPiece[i][j].setOpaque(false);
                this.add(mapPiece[i][j]);
            }
        }
    }

    // mặc định ban đầu không có tàu
    public void init() {
        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= 10; j++) {
                isShip[i][j] = false;
            }
        }
    }


}
