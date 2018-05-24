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

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.imageio.*;

import javax.swing.text.html.HTMLEditorKit;

import processing.app.Base;
import processing.app.tools.Tool;
import processing.app.ui.Editor;
import processing.app.ui.WebFrame;

import java.io.*;
import java.util.*;

import org.imgscalr.*;

// when creating a tool, the name of the main class which implements Tool must
// be the same as the value defined for project.name in your build.properties

public class GettingStarted implements Tool, ActionListener {
  Base base;
  WFrame currentframe;
//  private JFrame currentframe;
//  JLabel imageArea;
  int pos = 0;
  String[] htmlArray = new String[] {"/data/img/1.html"};

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
	  System.out.println("here");
	  if(currentframe != null) {
			currentframe.setVisible(true);
			return;
	  }
	  
	  JComponent panel = Box.createHorizontalBox();
	  
	  panel.setBackground(new Color(245, 245, 245));
	  panel.add(Box.createHorizontalGlue());
	  
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
	  
	  panel.add(panelButtons);
	  
//	  currentframe.setSize(439, 548);
//	  currentframe.setResizable(false);
//	  
//	  Container pane = currentframe.getContentPane();
//	  pane.add(imagePanel, BorderLayout.CENTER);
//	  currentframe.add(panelButtons, BorderLayout.PAGE_END);
//	  
//	  currentframe.setVisible(true);
//
//	  currentframe.requestFocusInWindow();
	  try {
		  System.out.println("establishing WFrame");
		  currentframe = new WFrame(439, 548, panel);
		  currentframe.setVisible(true);
		  currentframe.requestFocusInWindow();
	  } catch(IOException e) {
		  e.printStackTrace();
	  }
	  
  }		
  
  public void displayhtml(int index) {
	  File htmlfile = getIndexFile(index);
	  System.out.println(htmlfile.getAbsolutePath());
	  currentframe.setFile(439, htmlfile);
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
  
  public File getIndexFile(int index) {
	  String filename = htmlArray[index];
	  java.net.URL htmlURL = getClass().getResource(filename);
			  
	  File htmlfile = new File(htmlURL.getFile());
	  System.out.println(htmlfile.getAbsolutePath());
	  if(htmlfile.exists()) {
		  return htmlfile;
	  }
	  
	  return null;
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
			
			displayhtml(pos);
		} else if(selected.equals("Next")) {
			pos = pos + 1;
			if(pos >= htmlArray.length) {
				pos = htmlArray.length - 1;
			}
			
			displayhtml(pos);
		}
  }
}
