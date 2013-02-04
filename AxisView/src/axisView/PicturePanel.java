package axisView;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import javax.imageio.ImageIO;
import java.io.InputStream;
import java.awt.image.BufferedImage;

class PicturePanel extends JPanel {

  private static final long serialVersionUID=1L;

  private BufferedImage image;
  private String urlString;
  private URL url = null;
  private int centerX, centerY, width, height;
  //private Image displayBgImage;
  //private int displayWidth, displayHeight;

  //public PicturePanel(String filename) throws java.io.IOException {
    //image = ImageIO.read(new FileImageInputStream(new File(filename)));
  //}

  public PicturePanel(String urlString, int x, int y, int width, int height) throws java.io.IOException {
    this.urlString = urlString;
    java.net.Authenticator.setDefault(
      new java.net.Authenticator() {
        protected java.net.PasswordAuthentication getPasswordAuthentication() {
          return new java.net.PasswordAuthentication("FRC","FRC".toCharArray());
        }
      }
    );
    this.url = new URL(this.urlString);
    System.out.println("Authority: "+this.url.getAuthority());
    refresh(x,y,width,height);
  }

  public void refresh(int x, int y, int width, int height) throws java.io.IOException {
    if (true) {
      this.image = ImageIO.read(this.url);
    } else {
      InputStream is = this.url.openStream();
      this.image = ImageIO.read(is);
    }
    this.centerX = x;
    this.centerY = y;
    this.width = width;
    this.height = height;
    repaint();
  }


  java.awt.Color overlayColor = java.awt.Color.RED;

  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.drawImage(image,0,0,null); // draw image
    g.setColor(overlayColor);
    int left = centerX-width/2;
    int top  = centerY-height/2;
    g.drawRect(left,top,width,height);
  }

}

