package nz.ac.aut.ense701.gameModel;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * Created by michelemiller on 3/04/17.
 */
public class TestGUI extends JFrame {


        ImageIcon image = new ImageIcon("assets/kiwi.png");
        JFrame frame = new JFrame();
        JLabel label = new JLabel(image);



        public TestGUI(){
            label.setIcon(image);
            System.out.println("Testing");
            URL url = getClass().getResource("//assets//kiwi.ping");
            System.out.println("Testing");
            /*
            File f = new File(url.getPath());

            if(f.exists()){
                System.out.println("File Exists");
            }
            */
            if(image == null){
                System.out.println("No Image is Saved");
            }else{
                System.out.println("Yes there is an image");
            }
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setTitle("Image Checker");
            frame.setSize(500,500);
            frame.add(label, BorderLayout.CENTER);
            frame.setVisible(true);



        }
}
