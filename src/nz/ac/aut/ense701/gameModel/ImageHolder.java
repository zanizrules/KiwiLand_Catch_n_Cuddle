package nz.ac.aut.ense701.gameModel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class ImageHolder {
    private String imageLocation;
    private ImageIcon imageIcon;
    private int imgWidth;
    private int imgHeight;
    Image scalledImg;



    public void resizeImage(){
        BufferedImage img = null;
        try{
            img = ImageIO.read(new File(imageLocation));

        } catch (IOException e){
            e.printStackTrace();
        }

        scalledImg = img.getScaledInstance(imgWidth, imgHeight, Image.SCALE_SMOOTH);

    }

    //Used to set the dimensions of the image that will be used, currently in a gridSquare the best dimensions seem to be
    //50x50
    public void setImageDimensions(int height, int width){
        this.imgHeight = height;
        this.imgWidth = width;
    }

    public void setImageLocation(String imageLoc){
        this.imageLocation = imageLoc;
        resizeImage();
        imageIcon = new ImageIcon(scalledImg);
    }

    public String getImageLocation(){
        return this.imageLocation;
    }

    public ImageIcon getImage(){
        return this.imageIcon;
    }


}
