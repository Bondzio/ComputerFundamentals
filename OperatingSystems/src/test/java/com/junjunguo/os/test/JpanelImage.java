package com.junjunguo.os.test;

/**
 * This file is part of OperatingSystems.
 * <p/>
 * Created by <a href="http://junjunguo.com">GuoJunjun</a> on 24/02/16.
 */


import javax.swing.*;
import java.awt.*;

public class JpanelImage extends Canvas {

    public void paint(Graphics g) {

        Toolkit t = Toolkit.getDefaultToolkit();
//        Image   i = t.getImage("com/junjunguo/os/p2/images/floor.gif");//////
        Image   i = t.getImage("/Users/Junjun/Repository/NTNU/ComputerFundamentals/OperatingSystems/src/main/java/com/junjunguo/os/p2/images/floor.gif");
        g.drawImage(i, 120, 100, this);

    }

    public static void main(String[] args) {
        JpanelImage m = new JpanelImage();
        JFrame      f = new JFrame();
        f.add(m);
        f.setSize(400, 400);
        f.setVisible(true);
    }

}
