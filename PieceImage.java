import javax.swing.*;
import java.awt.*;

public class PieceImage
{
    private ImageIcon blueBiz, blueRam, blueSau, blueTor, blueXor, redBiz, redRam, redSau, redTor, redXor;
    private ImageIcon[][] boardLayout;

    // Author: Nick
    PieceImage()
    {
        //Images for the pieces
        blueBiz = new ImageIcon("resources/BlueBiz.png");
        blueBiz = scaleIcon(blueBiz, 70, 70);
        blueRam = new ImageIcon("resources/BlueRam.png");
        blueRam = scaleIcon(blueRam, 50, 70);
        blueSau = new ImageIcon("resources/BlueSau.png");
        blueSau = scaleIcon(blueSau, 70, 70);
        blueTor = new ImageIcon("resources/BlueTor.png");
        blueTor = scaleIcon(blueTor, 70, 70);
        blueXor = new ImageIcon("resources/BlueXor.png");
        blueXor = scaleIcon(blueXor, 70, 70);

        redBiz = new ImageIcon("resources/RedBiz.png");
        redBiz = scaleIcon(redBiz, 70, 70);
        redRam = new ImageIcon("resources/RedRam.png");
        redRam = scaleIcon(redRam, 50, 70);
        redSau = new ImageIcon("resources/RedSau.png");
        redSau = scaleIcon(redSau, 70, 70);
        redTor = new ImageIcon("resources/RedTor.png");
        redTor = scaleIcon(redTor, 70, 70);
        redXor = new ImageIcon("resources/RedXor.png");
        redXor = scaleIcon(redXor, 70, 70);

        //The layout for the board
        boardLayout = new ImageIcon[][]{
                {redTor, redBiz, redSau, redBiz, redXor},
                {redRam, redRam, redRam, redRam, redRam},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {blueRam, blueRam, blueRam, blueRam, blueRam},
                {blueXor, blueBiz, blueSau, blueBiz, blueTor},
        };
    }

    public ImageIcon[][] getBoardLayout()
    {
        return boardLayout;
    }

    //This function scales the image icon to size for the buttons
    private ImageIcon scaleIcon(ImageIcon icon, int width, int height) {
        Image img = icon.getImage();
        Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImg);
    }

    public ImageIcon getRedBiz()
    {
        return redBiz;
    }

    public ImageIcon getRedRam()
    {
        return redRam;
    }

    public ImageIcon getRedSau()
    {
        return redSau;
    }

    public ImageIcon getRedTor()
    {
        return redTor;
    }

    public ImageIcon getRedXor()
    {
        return redXor;
    }

    public ImageIcon getBlueBiz()
    {
        return blueBiz;
    }

    public ImageIcon getBlueRam()
    {
        return blueRam;
    }

    public ImageIcon getBlueSau()
    {
        return blueSau;
    }

    public ImageIcon getBlueTor()
    {
        return blueTor;
    }

    public ImageIcon getBlueXor()
    {
        return blueXor;
    }
}