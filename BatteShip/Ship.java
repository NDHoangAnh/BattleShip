package BatteShip;

import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Ship extends JLabel {
    public Image shipImage; // ảnh tàu nằm ngang

    public Ship(int length, int w, int h, boolean isNgang) {
        super();
        if(isNgang){
            this.setSize(w,h);
        }
        else{
            this.setSize(h,w);
        }

        if (length == 5) {
            if(isNgang){
                shipImage = loadImage("/img/5.png", w, h);
            }else{
                shipImage = loadImage("/img/5_v.png", h, w);
            }
        }
        if (length == 1) {
            if(isNgang){
                shipImage = loadImage("/img/1.png", w, h);
            }else{
                shipImage = loadImage("/img/1_v.png", h, w);
            }
        }
        if (length == 4) {
            if(isNgang){
                shipImage = loadImage("/img/4.png", w, h);
            }else{
                shipImage = loadImage("/img/4_v.png", h, w);
            }
        }
        if (length == 3) {
            if(isNgang){
                shipImage = loadImage("/img/3.png", w, h);
            }else{
                shipImage = loadImage("/img/3_v.png", h, w);
            }
        }
        if (length == 2) {
            if(isNgang){
                shipImage = loadImage("/img/2.png", w, h);
            }else{
                shipImage = loadImage("/img/2_v.png", h, w);
            }
        }
        this.setIcon(new ImageIcon(shipImage));

    }

    public void rotateShip(int length, int w, int h,boolean isNgang){
        if(isNgang){
            this.setSize(h,w);
            this.setIcon(new ImageIcon(loadImage("/img/"+length+"_v.png",h,w)));
        }
        else{
            this.setSize(w,h);
            this.setIcon(new ImageIcon(loadImage("/img/"+length+".png",w,h)));
        }

    }



    private Image loadImage(String s, int w, int h) {
        BufferedImage i = null; // doc anh duoi dang Buffered Image
        try {
            i = ImageIO.read(MainMenu.class.getResource(s));
        } catch (Exception e) {
            System.out.println("Duong dan anh k hop le!");
        }

        Image dimg = i.getScaledInstance(w, h, Image.SCALE_SMOOTH); // thay doi kich thuoc anh
        return dimg;

    }
}
