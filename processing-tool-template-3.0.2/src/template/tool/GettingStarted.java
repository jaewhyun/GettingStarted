/**
 * you can put a one sentence description of your tool here.
 *
 * ##copyright##
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General
 * Public License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA  02111-1307  USA
 *
 * @author   ##author##
 * @modified ##date##
 * @version  ##tool.prettyVersion##
 */

package template.tool;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Container;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.Dimension;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.imageio.*;

import processing.app.Base;
import processing.app.tools.Tool;
import processing.app.ui.Editor;

import java.io.*;
import java.util.*;

import org.imgscalr.*;

// when creating a tool, the name of the main class which implements Tool must
// be the same as the value defined for project.name in your build.properties

public class GettingStarted implements Tool, ActionListener {
  Base base;
  private JFrame currentframe;
  JLabel imageArea;
  int pos = 0;

  public String getMenuTitle() {
    return "Getting Started";
  }


  public void init(Base base) {
    // Store a reference to the Processing application itself
    this.base = base;
  }


  public void run() {
    // Get the currently active Editor to run the Tool on it
    Editor editor = base.getActiveEditor();
    createWalkthrough();
    // Fill in author.name, author.url, tool.prettyVersion and
    // project.prettyName in build.properties for them to be auto-replaced here.
    System.out.println("Getting Started 1.0.0 by Jae Hyun");
  }
  
  public void createWalkthrough() {
	  if(currentframe != null) {
			currentframe.setVisible(true);
			return;
		}
	  
	  currentframe = new JFrame("Getting Started");
	  
	  JPanel imagePanel = new JPanel(new BorderLayout());
	  imagePanel.setBorder(BorderFactory.createEmptyBorder(80, 10, 10, 10));
	  
	  imageArea = new JLabel();
	  imagePanel.add(imageArea);
	  
	  JButton previousButton = new JButton("Previous");
	  previousButton.addActionListener(this);
	  previousButton.setActionCommand("Previous");
	  
	  JButton nextButton = new JButton("Next");
	  nextButton.addActionListener(this);
	  nextButton.setActionCommand("Next");
	  
	  JPanel panelButtons = new JPanel();
	  panelButtons.setLayout(new BoxLayout(panelButtons, BoxLayout.LINE_AXIS));
	  panelButtons.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
	  panelButtons.add(Box.createRigidArea(new Dimension(10, 0)));
	  panelButtons.add(Box.createHorizontalGlue());
	  panelButtons.add(previousButton);
	  panelButtons.add(nextButton);
	  
	  currentframe.setSize(439, 548);
	  currentframe.setResizable(false);
	  
	  Container pane = currentframe.getContentPane();
	  pane.add(imagePanel, BorderLayout.CENTER);
	  currentframe.add(panelButtons, BorderLayout.PAGE_END);
	  
	  currentframe.setVisible(true);

	  currentframe.requestFocusInWindow();
  }		
  
  public Boolean displayImage(int index) {
	 try {
		 File[] imagesList = getImages();
		  File imageName = imagesList[index];
		  ImageIcon icon = new ImageIcon(imageName.getAbsolutePath());
		  if(imageName.getAbsolutePath().toString().toLowerCase().endsWith(".gif")) {
			  ImageIcon icon = new ImageIcon(imageName.getAbsolutePath());
			  Dimension imageDimension = new Dimension(icon.getIconWidth(), icon.getIconHeight());
			  Dimension labelDimension = new Dimension(imageArea.getWidth(), imageArea.getHeight());
			  
			  double scaleFactor = Math.min(1d, getScaledFactorToFit(imageDimension, labelDimension));
			  int scaleWidth = (int) Math.round(icon.getIconWidth() * scaleFactor);
			  int scaleHeight = (int) Math.round(icon.getIconHeight() * scaleFactor);
			  
			  Image image = icon.getImage().getScaledInstance(scaleWidth, scaleHeight, Image.SCALE_DEFAULT);
			  imageArea.setIcon(new ImageIcon(image));
		  } else {
			  BufferedImage origImage;
			  origImage = ImageIO.read(imageName);
			  Dimension imageDimension = new Dimension(origImage.getWidth(), origImage.getHeight());
			  Dimension labelDimension = new Dimension(imageArea.getWidth(), imageArea.getHeight());
			  double scaleFactor = Math.min(1d, getScaledFactorToFit(imageDimension, labelDimension));
			  int scaleWidth = (int) Math.round(origImage.getWidth() * scaleFactor);
			  int scaleHeight = (int) Math.round(origImage.getHeight() * scaleFactor);
			  
			  origImage = Scalr.resize(origImage, Scalr.Method.ULTRA_QUALITY, scaleWidth, scaleHeight, Scalr.OP_ANTIALIAS);
			  imageArea.setIcon(new ImageIcon(origImage));
		  }
	 } catch (IOException ex) {
		 return false;
	 }
	 
	 return true;
  }
  
  
  public double getScaleFactor(int iMasterSize, int iTargetSize) {
	    double dScale = 1;
	    if (iMasterSize > iTargetSize) {
	        dScale = (double) iTargetSize / (double) iMasterSize;
	    } else {
	        dScale = (double) iTargetSize / (double) iMasterSize;
	    }
	    return dScale;
  }
  
  public double getScaledFactorToFit(Dimension original, Dimension toFit) {
	    double dScale = 1d;
	    if (original != null && toFit != null) {
	        double dScaleWidth = getScaleFactor(original.width, toFit.width);
	        double dScaleHeight = getScaleFactor(original.height, toFit.height);
	        dScale = Math.min(dScaleHeight, dScaleWidth);
	    }
	    return dScale;
  }
  
  public File[] getImages() {
	  File folder = new File("/data/img/");
	  File[] listofImages = folder.listFiles();
	  return listofImages;
  }
  
  @Override 
  public void actionPerformed(ActionEvent e) {
		if(base == null) {
			return;
		}

		String selected = e.getActionCommand();
		
		if(selected.equals("Previous")) {
			pos = pos - 1;
			if(pos < 0) {
				pos = 0;
			}
			
			displayImage(pos);
		} else if(selected.equals("Next")) {
			pos = pos + 1;
			if(pos >= getImages().length) {
				pos = getImages().length - 1;
			}
			
			displayImage(pos);
		}
  }
}
