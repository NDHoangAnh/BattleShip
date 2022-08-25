package BatteShip;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.Queue;

public class Creator extends JLabel implements MouseListener, MouseMotionListener, ActionListener   {
    public JFrame frame;
    public Clip clip;
    public JButton ready;
    public JButton auto;
    public JButton back;
    public JLabel shipContainer;
    public Map map;
    public Map playerMap, computerMap;

    public static boolean isHard;
    public static boolean isPlaySound;
    public static final Color TURQUOISE_MID_T2 	= new Color(72, 209, 204,180);

    private ImageIcon createBackground;
    private ImageIcon imgshipContainer;

    public Ship[] ship;
    public static boolean[] isNgang;
    public static boolean[] isNgangPlayer;
    public static boolean[] isNgangComputer;
    public static boolean[][] M = new boolean[11][11]; // đánh dấu ô đã có tàu

    private static int xLeft; // tọa độ cua map trên frame
    private static int yUp; // tọa độ của map trên frame
    private static int xRight;
    private static int yDown;
    public static int[] xS; // đánh dấu tọa độ của ship khi chưa vào map
    public static int[] yS;
    private static int xStart; // tọa độ điểm bắt đầu khi kéo thả
    private static int yStart; // tọa độ điểm bắt đầu khi kéo thả
    private int X, Y; // biến dùng để get tọa độ khi di chuyển
    public static int[] lengShip;

    public static ArrayList<String> A; // aray lưu tọa độ tàu của người chơi
    public static ArrayList<String> B; // lưu tọa độ tàu của computer

    public Creator(int w, int h,  String gameMode, boolean playSound) {

        super();
        this.setSize(w, h);
        createBackground=new ImageIcon(loadImage("/img/creator_bg.jpg", w, h));
       this.setIcon(createBackground);


        if (gameMode == "easy")
            isHard = false;
        else
            isHard = true;
        isPlaySound = playSound;


        A = new ArrayList<String>();
        B = new ArrayList<String>();
        A.add("");
        B.add("");

        xS = new int[6];
        yS = new int[6];
        isNgang = new boolean[6];
        lengShip = new int[6];
        ship = new Ship[6];
        for (int i=1;i<=5;i++){
            lengShip[i]=i;
        }
//        lengShip[1]=3;

        for(int i=1;i<=5;i++){
            ship[i]= new Ship(lengShip[i],50*lengShip[i],50,true);
            A.add(String.valueOf(10000*lengShip[i]));
            B.add(String.valueOf(10000*lengShip[i]));
        }



        for (int i = 1; i <= 5; i++) {
            ship[i].addMouseListener(this);
            ship[i].addMouseMotionListener(this);
        }
        build();
        init();


        map=new Map(560,560);
        map.setOpaque(false);
        map.setLocation(140,40);
        this.add(map);


        frame = new JFrame("Battle Ship");
        frame.setIconImage(loadImage("/img/logo.png",90,90));
        frame.setSize(w, h);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.add(this);

        playSound("/sound/sound.wav");
        if (!isPlaySound) clip.stop();

        xLeft = map.getX(); // lấy tọa độ map để căn góc :v
        xRight = xLeft + 560;
        yUp = map.getY();
        yDown = yUp + 560;

    }

    public void init(){
        for(int i=1;i<=10;i++){
            for(int j=1;j<=10;j++){
                M[i][j]=false;
            }
            if (i <= 5&&i>=1) {
                xS[i] = ship[i].getX();
                yS[i] = ship[i].getY();
                isNgang[i] = true;
            }
        }
    }

    public void build(){
        ready = new JButton("READY");
        ready.setBounds(140,  640, 260, 120);
        ready.setFont(new Font("Snap ITC", Font.TRUETYPE_FONT, 30));
        ready.setBackground(Color.GREEN);
        ready.setFocusable(false);
        ready.setForeground(new Color(72, 209, 204));
        ready.setBorder(new LineBorder(TURQUOISE_MID_T2,2));
        ready.setOpaque(false);
        ready.setActionCommand("ready");
        ready.addActionListener(this);
        this.add(ready);

        auto = new JButton("AUTO");
        auto.setBounds(440,  640, 260, 120);
        auto.setFont(new Font("Snap ITC", Font.TRUETYPE_FONT, 30));
        auto.setBackground(Color.GREEN);
        auto.setFocusable(false);
        auto.setForeground(new Color(72, 209, 204));
        auto.setBorder(new LineBorder(TURQUOISE_MID_T2,2));
        auto.setOpaque(false);
        auto.setActionCommand("random");
        auto.addActionListener(this);
        this.add(auto);

        back = new JButton("BACK");
        back.setBounds(740,  640, 260, 120);
        back.setFont(new Font("Snap ITC", Font.TRUETYPE_FONT, 30));
        back.setBackground(Color.GREEN);
        back.setFocusable(false);
        back.setForeground(new Color(72, 209, 204));
        back.setBorder(new LineBorder(TURQUOISE_MID_T2,2));
        back.setOpaque(false);
        this.add(back);


        shipContainer = new JLabel();
        createBackground=new ImageIcon(loadImage("/img/ShipContainer.png", 260, 320));
        shipContainer.setIcon(createBackground);
        shipContainer.setBackground(new Color(0,0,0,200));
        shipContainer.setSize(260,320);
        shipContainer.setLocation(back.getX(),back.getY()-40-shipContainer.getHeight());

        ship[1].setLocation(shipContainer.getX()+200,shipContainer.getY()+5);
        ship[2].setLocation(shipContainer.getX()+150,shipContainer.getY()+65);
        ship[3].setLocation(shipContainer.getX()+100,shipContainer.getY()+125);
        ship[4].setLocation(shipContainer.getX()+45,shipContainer.getY()+185);
        ship[5].setLocation(shipContainer.getX(),shipContainer.getY()+245);
       for(int i=1;i<=5;i++){
           this.add(ship[i]);
       }

//
        this.add(shipContainer);

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

    // đặt thử tàu thứ tmp vào vị trí x,y ứng với ô i,j
    public boolean put(int tmp, int x, int y, int i, int j,boolean isNgang) {
        int leng = lengShip[tmp];
        //tàu ngang
        if(isNgang){
            if ((x + leng * 50 > xRight && leng > 1) || (leng == 1 && x > xRight)) {
                ship[tmp].setLocation(xStart, yStart);

                if (xStart >= xLeft && xStart <= xRight && yStart >= yUp && yStart <= yDown) {
                    int u = (xStart - xLeft) / 56 + 1;
                    int v = (yStart - yUp) / 56 + 1;
                    for (int q = 0; q < leng; q++) {
                        M[u + q][v] = true;
                    }
                }
                 return false;
            }

            for (int t = 0; t < leng; t++) {
                if (M[i + t][j] == true) {
                    ship[tmp].setLocation(xStart, yStart);

                    if (xStart >= xLeft && xStart <= xRight && yStart >= yUp && yStart <= yDown) {
                        int u = (xStart - xLeft) / 56 + 1;
                        int v = (yStart - yUp) / 56 + 1;
                        for (int q = 0; q < leng; q++) {
                            M[u + q][v] = true;
                        }
                    }
                    return false;
                }
            }
            ship[tmp].setLocation(x-7 , y);
            for (int t = 0; t < leng; t++) {
                M[i + t][j] = true;
            }

        }
        //tàu dọc
        else{
            if ((y + leng * 50 > yDown && leng > 1) || (leng == 1 && y > yDown)) {
                ship[tmp].setLocation(xStart, yStart);

                if (xStart >= xLeft && xStart <= xRight && yStart >= yUp && yStart <= yDown) {
                    int u = (xStart - xLeft) / 56 + 1;
                    int v = (yStart - yUp) / 56 + 1;
                    for (int q = 0; q < leng; q++) {
                        M[u][v+q] = true;
                    }
                }
                 return false;
            }

            for (int t = 0; t < leng; t++) {
                if (M[i][j+t] == true) {
                    ship[tmp].setLocation(xStart, yStart);

                    if (xStart >= xLeft && xStart <= xRight && yStart >= yUp && yStart <= yDown) {
                        int u = (xStart - xLeft) / 56 + 1;
                        int v = (yStart - yUp) / 56 + 1;
                        for (int q = 0; q < leng; q++) {
                            M[u][v+q] = true;
                        }
                    }
                    return false;
                }
            }

                ship[tmp].setLocation(x-7, y);
            for (int t = 0; t < leng; t++) {
                M[i][j+t] = true;
            }


        }
        int v = (Integer.parseInt(A.get(tmp))) / 10000;
        A.set(tmp, "" + (v * 10000 + i * 100 + j));

        return true;

    }

    public boolean putForRandom(int tmp, int x, int y, int i, int j, int leng,boolean isForPlayer,boolean isNgang) {
        if(isNgang){
            if (x + 50 * leng > xRight && leng > 1)
                return false;
            for (int t = 0; t < leng; t++) {
                if (M[i + t][j] == true)
                    return false;
            }

            for (int t = 0; t < leng; t++) {
                M[i + t][j] = true;
            }
        }else{
            if (y + 50 * leng > yDown && leng > 1)
                return false;
            for (int t = 0; t < leng; t++) {
                if (M[i][j+t] == true)
                    return false;
            }

            for (int t = 0; t < leng; t++) {
                M[i][j+t] = true;
            }
        }

        ship[tmp].setLocation(x-7, y);

        if (isForPlayer) {
            int v = (Integer.parseInt(A.get(tmp))) / 10000;
            A.set(tmp, "" + (v * 10000 + i * 100 + j));
        } else {
            int v = (Integer.parseInt(B.get(tmp))) / 10000;
            B.set(tmp, "" + (v * 10000 + i * 100 + j));
        }

        return true;
    }

    public void setRandom(boolean isForPlayer) {
        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= 10; j++) {
                M[i][j] = false;
            }
        }
        Random rd = new Random();
        Queue<JLabel> Q = new LinkedList<>();
        Queue<Boolean> P = new LinkedList<>();

        for (int i = 1; i <= 5; i++) {
            ship[i].setLocation(xS[i], yS[i]);
            boolean k= rd.nextBoolean();
            isNgang[i]=k;
            P.add(isNgang[i]);
            ship[i].rotateShip(lengShip[i],50*lengShip[i],50,!isNgang[i]);
            Q.add(ship[i]);
        }
        int cnt = 1;
        while (!Q.isEmpty()) {
            int i = rd.nextInt(10);
            int j = rd.nextInt(10);
            int x = xLeft + 56 * i + 10;
            int y = yUp + 56 * j + 5;
            boolean k=P.peek();
            xStart = xS[cnt];
            yStart = yS[cnt];

            boolean is = true;
            if (!isForPlayer)
                is = false;

            boolean c = putForRandom(cnt, x, y, i + 1, j + 1, lengShip[cnt], is,k);
            if (c) {
                cnt++;
                Q.remove();
                P.remove();
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
       if(SwingUtilities.isLeftMouseButton(e)){
           if (xStart >= xLeft && xStart <= xRight && yStart >= yUp && yStart <= yDown) {
               int i = (xStart - xLeft) / 56 + 1;
               int j = (yStart - yUp) / 56 + 1;
               int tmp = 0;
               for (int k = 1; k <= 5; k++) {
                   if (e.getSource() == ship[k]) {
                       tmp = k;
                       break;
                   }
               }
               int leng = lengShip[tmp];
               boolean k=isNgang[tmp];
               for (int t = 0; t < leng; t++) {
                   if(k){
                       M[i + t][j] = false;
                   }else{
                       M[i][j+t]=false;
                   }

               }
           }

           int xNew = e.getX() + e.getComponent().getX() - X;
           int yNew = e.getY() + e.getComponent().getY() - Y;
           e.getComponent().setLocation(xNew, yNew);
       }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (SwingUtilities.isRightMouseButton(e)){
        int tmp = 0;
        for (int k = 1; k <= 5; k++) {
            if (e.getSource() == ship[k]) {
                tmp = k;
                break;
            }
        }
            if (ship[tmp].getX() < xLeft || ship[tmp].getX() > xRight || ship[tmp].getY() < yUp || ship[tmp].getY() > yDown) {
                return;}
            int i = (ship[tmp].getX() - xLeft) / 56;
            int j = (ship[tmp].getY()- yUp) / 56;
            boolean checkrotate=true;
            if(isNgang[tmp]){
                for(int t=1;t<lengShip[tmp];t++){
                    if(j+1+t>10){
                        checkrotate=false;
                        break;
                    }
                    if(M[i+1][j+t+1]){
                        checkrotate=false;
                        break;
                    }
                }

            }else{
                for (int t=1;t<lengShip[tmp];t++){
                    if(i+1+t>10){
                        checkrotate=false;
                        break;
                    }
                    if(M[i+t+1][j+1]){
                        checkrotate=false;
                        break;
                    }
                }
            }
            if(checkrotate){
                ship[tmp].rotateShip(lengShip[tmp],50*lengShip[tmp],50,isNgang[tmp]);
                if(isNgang[tmp]){
                    for(int t=1;t<lengShip[tmp];t++){
                        M[i+1+t][j+1]=false;
                        M[i+1][j+1+t]=true;
                    }
                }else{
                    for(int t=1;t<lengShip[tmp];t++){
                        M[i+1][j+1+t]=false;
                        M[i+1+t][j+1]=true;
                    }
                }
                isNgang[tmp]=!isNgang[tmp];

            }
        }
  }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
        X = e.getX();
        Y = e.getY();
        xStart = e.getComponent().getX();
        yStart = e.getComponent().getY();

    }

    // Nếu ship nằm ngoài map -> trả về vị trí cũ
    @Override
    public void mouseReleased(MouseEvent e) {
            int xNew = e.getX() + e.getComponent().getX() - X;
            int yNew = e.getY() + e.getComponent().getY() - Y;
            if (xNew < xLeft || xNew > xRight || yNew < yUp || yNew > yDown) {
                e.getComponent().setLocation(xStart, yStart);
                return;
            }

            int i = (xNew - xLeft) / 56;
            int j = (yNew - yUp) / 56;
            xNew = xLeft + i * 56 +10;
            yNew = yUp + j * 56 +3; // tàu sẽ ở ô i+1 và j+1
            int tmp = 0;
            for (int k = 1; k <= 5; k++) {
                if (e.getSource() == ship[k]) {
                    tmp = k;
                    break;
                }
            }
            if(SwingUtilities.isLeftMouseButton(e)){
                put(tmp, xNew, yNew, i + 1, j + 1,isNgang[tmp]);
            }

        // TODO Auto-generated method stub
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            if ("random".equals(e.getActionCommand())) {
                setRandom(true);
            }
        if ("ready".equals(e.getActionCommand())){
            isNgangPlayer=new boolean[6];
            isNgangComputer=new boolean[6];
                boolean c = isReady();
                if (!c)
                    return;
                playerMap = getFinalMap();
                for (int i=1;i<=5;i++){
                    isNgangPlayer[i]=isNgang[i];
                }
                setRandom(false);
                computerMap = getFinalMap();
                for (int i=1;i<=5;i++){
                    isNgangComputer[i]=isNgang[i];
                }
                goToPlay();
            }
        }

    private boolean isReady() {
        for (int i = 1; i <= 5; i++) {
            int x = ship[i].getX();
            int y = ship[i].getY();
            if (x < xLeft || x > xRight || y < yUp || y > yDown)
                return false;
        }
        return true;
    }

    private Map getFinalMap() {
        Map s = new Map(560, 560);
        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= 10; j++) {
                s.isShip[j][i] = M[i][j];
            }
        }
        return s;
    }

    public void goToPlay() {
        this.setVisible(false);
        frame.remove(this);
        new PlayGame(1220, 820, playerMap, computerMap, isHard, A, B, isNgangPlayer,isNgangComputer );
        frame.setVisible(false);
    }

    private void playSound(String link) {
        try {
            URL url = this.getClass().getResource(link);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
            clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
