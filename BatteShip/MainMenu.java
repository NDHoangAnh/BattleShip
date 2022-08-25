package BatteShip;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.border.LineBorder;

public class MainMenu implements ActionListener  {
    private JFrame frame;
    private JLabel menu1;
    private ImageIcon menuBackground;
    private JButton play;
    private JButton gameMode;
    private JButton gameRule;
    private JButton highScore;
    private JButton sound;
    private JButton exit;

    private Creator creator;

    private Clip clip;
    private static boolean isPlaySound ;

    public MainMenu(int w, int h, boolean playSound){
        frame = new JFrame("Battle Ship");
        isPlaySound = playSound;

        frame.setIconImage(loadImage("/img/logo.png", 90, 90));
        frame.setSize(w, h);

        menu1 = new JLabel();
        menu1.setSize(w, h);
        menuBackground=new ImageIcon(loadImage("/img/menu1_background.jpg", w, h));
        menu1.setIcon(menuBackground);

        play = new JButton("PLAY");
        play.setBounds(w / 2 - 130,  h / 5, 260, 50);
        play.setFont(new Font("Snap ITC", Font.BOLD, 30));
        play.setBackground(Color.GREEN);
        play.setBorder(new LineBorder(Color.GREEN));
        play.setFocusable(false);
        menu1.add(play);
        play.setActionCommand("Play");
        play.addActionListener(this);

        gameMode = new JButton("EASY");
        gameMode.setBounds(w / 2 - 130,  h / 5+60, 260, 50);
        gameMode.setFont(new Font("Snap ITC", Font.BOLD, 30));
        gameMode.setBackground(new Color(255, 102, 102));
        gameMode.setBorder(new LineBorder(new Color(255, 102, 102)));
        gameMode.setFocusable(false);
        menu1.add(gameMode);
        gameMode.setActionCommand("easy");
        gameMode.addActionListener(this);

        gameRule = new JButton("GAME RULE");
        gameRule.setBounds(w / 2 - 130,  h / 5+120, 260, 50);
        gameRule.setFont(new Font("Snap ITC", Font.BOLD, 30));
        gameRule.setBackground(Color.YELLOW);
        gameRule.setBorder(new LineBorder(Color.YELLOW));
        gameRule.setFocusable(false);
        menu1.add(gameRule);
        gameRule.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI("https://www.google.com/search?hl=fr&sxsrf=ALeKk03f4-0_FiE0HM0MRB2D8gbIOjgqrA%3A1597187736262&source=hp&ei=mCYzX5XhDJKblwS9_KagDA&q=How+to+use+google&oq=How+to+use+google&gs_lcp=CgZwc3ktYWIQAzIFCAAQywEyBQgAEMsBMgUIABDLATIFCAAQywEyBQgAEMsBMgUIABDLATIFCAAQywEyBQgAEMsBMgUIABDLATIFCAAQywE6BAgjECc6AggAOgIILjoFCC4QywE6BggjECcQEzoHCAAQChDLAToECAAQDVDRqAFY7vEBYOH2AWgEcAB4AIAB9wKIAcsWkgEIMy4xNy4wLjGYAQCgAQGqAQdnd3Mtd2l6&sclient=psy-ab&ved=0ahUKEwiVjaD9o5TrAhWSzYUKHT2-CcQQ4dUDCAc&uact=5"));
                } catch (URISyntaxException | IOException ex) {

                }
            }
        });

        highScore = new JButton("HIGHSCORE");
        highScore.setBounds(w / 2 - 130,  h / 5+180, 260, 50);
        highScore.setFont(new Font("Snap ITC", Font.BOLD, 30));
        highScore.setBackground(Color.GRAY);
        highScore.setBorder(new LineBorder(Color.GRAY));
        highScore.setFocusable(false);
        menu1.add(highScore);

        sound = new JButton();
        sound.setBounds(w / 2 - 130,  h / 5+240, 260, 50);
        sound.setFont(new Font("Snap ITC", Font.BOLD, 30));
        sound.setBackground(Color.ORANGE);
        sound.setBorder(new LineBorder(Color.ORANGE));
        sound.setFocusable(false);
        if (isPlaySound) {
            sound.setText("SOUND OFF");
            sound.setActionCommand("sound on");
        } else {
            sound.setText("SOUND ON");
            sound.setActionCommand("sound off");
        }
        sound.addActionListener(this);
        menu1.add(sound);


        exit = new JButton("EXIT");
        exit.setBounds(w / 2 - 130,  h / 5+300, 260, 50);
        exit.setFont(new Font("Snap ITC", Font.BOLD, 30));
        exit.setBackground(Color.RED);
        exit.setBorder(new LineBorder(Color.RED));
        exit.setFocusable(false);
        menu1.add(exit);
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit (0);

            }});

        frame.add(menu1);


        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);

        playSound("/sound/sound.wav");
        if (!isPlaySound){
            clip.stop();
        }
        else {
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("sound on".equals(e.getActionCommand())) {
            sound.setText("SOUND ON");
            sound.setActionCommand("sound off");
            isPlaySound = false;
            clip.stop();
        }

        if ("sound off".equals(e.getActionCommand())) {
            sound.setText("SOUND OFF");
            sound.setActionCommand("sound on");
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            isPlaySound = true;
        }

        if ("easy".equals(e.getActionCommand())) {
            gameMode.setText("HARD");
            gameMode.setActionCommand("hard");
        }

        if ("hard".equals(e.getActionCommand())) {
            gameMode.setText("EASY");
            gameMode.setActionCommand("easy");
        }

        if ("Play".equals(e.getActionCommand())) {
            this.clip.stop();
            creator = new Creator(1120, 820, gameMode.getActionCommand(),isPlaySound);
            creator.back.setActionCommand("back");
            creator.back.addActionListener(this);
            frame.setVisible(false);       }

        if ("back".equals(e.getActionCommand())) {
            creator.clip.stop();
            creator.frame.setVisible(false);

            creator = null;
            System.gc();
            frame.setVisible(true);
            if (isPlaySound) {
                clip.start();
            }
        }
    }

}
