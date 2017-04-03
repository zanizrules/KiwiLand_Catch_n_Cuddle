package nz.ac.aut.ense701.gameModel;

import javax.swing.*;


public class Image {
    private String imageLocation;
    private ImageIcon imageIcon;

    public void setImageLocation(String imageLoc){
        this.imageLocation = imageLoc;
        //Wont work until assets are properly assigned
        this.imageIcon = new ImageIcon(this.getClass().getResource(imageLoc));

    }

    public String getImageLocation(){
        return this.imageLocation;
    }

    public ImageIcon getImage(){
        return this.imageIcon;
    }


}
