/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package axisView;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 *
 * @author David Rush
 */
public class AxisView extends JFrame {
  private static final long serialVersionUID=1L;
  private static final boolean USE_NETWORK_TABLE=true;

  private PicturePanel pp; //  = new Pictures(); // It's a JPanel
  private JButton refreshButton = new JButton("Refresh");

  private static final int TEAM=4188;

  public AxisView(final String path) throws java.io.IOException {
    System.out.println("path = "+path);
    pp = new PicturePanel(path,10,10,100,100);
    this.add(BorderLayout.CENTER,pp); // Add the pp JPanel to this JFrame
    this.add(BorderLayout.SOUTH,refreshButton);
    refreshButton.addActionListener(new ActionListener() {
      int count=1;
      public void actionPerformed(ActionEvent ae) {
        refresh();
      }
    });
  }

  private static int count=1;
  private static final String TABLE_NAME = "vision";
  private void refresh() {
    int centerx=0, centery=0, width=100, height=100;
    if (USE_NETWORK_TABLE) {
      try {
        NetworkTable table = NetworkTable.getTable("vision");
        centerx = table.getInt("centerx");
        centery = table.getInt("centery");
        width   = table.getInt("width");
        height  = table.getInt("height");
      } catch (Exception ex) {
        System.err.println("Exception trying to read NetworkTable.");
        centerx=-1;
      }
    } else {
      if (count%3==0) {
        centerx=10;
        centery=10;
      } else if (count%3==1) {
        centerx=90;
        centery=90;
      } else {
        centerx=120;
        centery=20;
      }
    }
    if (centerx>=0) {
      System.out.printf("centerx=%3d, centery=%3d, width=%3d, height=%3d\n"
          ,centerx,centery,width,height);
      try {
        pp.refresh(centerx,centery,width,height);
      } catch (Exception ex) {
        ex.printStackTrace(System.err);
      }
    }
    count++;
  }

//  private void drawBox(int x, int y, int width, int height) {
//    pp.drawBox(x,y,width,height);
//  }

  public static void main(String[] args) {
    if (args.length<1) {
      usage();
    }
    NetworkTable.setTeam(4188);
    System.out.println("args[0] = "+args[0]);
    try {
      String url = args[0];
      AxisView x = new AxisView(url);
      SwingConsole.run(x,320,320);
    } catch (java.io.IOException ex) {
      ex.printStackTrace();
    }
  }

  private static void usage() {
    System.err.println("Usage: java PictureView image_file_name");
    System.exit(1);
  }
}
