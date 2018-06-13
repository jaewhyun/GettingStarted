/**
 * Welcome Tool for PDE
 *
 * Copyright (c) 2018 Jae Won Hyun
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
 * @author   Jae Won Hyun
 * @modified 06/04/2018
 * @version  1.0.0
 */

package template.tool;

import java.awt.Dimension;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.io.File;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import java.net.URL;

import javax.swing.text.html.HTMLEditorKit;

import processing.app.Base;
import processing.app.Sketch;
import processing.app.tools.Tool;
import processing.app.ui.Editor;
import processing.app.ui.WebFrame;

import java.io.*;
import java.util.*;

// when creating a tool, the name of the main class which implements Tool must
// be the same as the value defined for project.name in your build.properties

public class GettingStarted implements Tool, ActionListener {
  Base base;
  WFrame currentframe;
  JButton tryitButton;
  JButton previousButton;
  JButton nextButton;

  int pos = 0;
  String[] htmlArray = new String[] {
		  "/data/static/0.html",
		  "/data/static/1.html",
		  "/data/static/2.html",
		  "/data/static/3.html",
		  "/data/static/4.html",
		  "/data/static/5.html",
		  "/data/static/6.html",
		  "/data/static/7.html",
		  "/data/static/8.html",
		  "/data/static/9.html",
		  "/data/static/10.html",
		  "/data/static/11.html",
		  "/data/static/12.html",
		  "/data/static/13.html"};
		  

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
		  	displayhtml(0);
			currentframe.setVisible(true);
			return;
	  }
	  
	  JComponent panel = Box.createHorizontalBox();
	  panel.setBackground(new Color(245, 245, 245));
//	  panel.add(Box.createHorizontalGlue());
	  
	  tryitButton = new JButton("Try It!");
	  tryitButton.addActionListener(this);
	  tryitButton.setActionCommand("Try It!");
	  tryitButton.setEnabled(false);
	  
	  previousButton = new JButton("Previous");
	  previousButton.addActionListener(this);
	  previousButton.setActionCommand("Previous");
	  
	  nextButton = new JButton("Next");
	  nextButton.addActionListener(this);
	  nextButton.setActionCommand("Next");

	  JPanel panelButtons = new JPanel();
	  panelButtons.setLayout(new BoxLayout(panelButtons, BoxLayout.LINE_AXIS));
	  panelButtons.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
	  panelButtons.add(Box.createRigidArea(new Dimension(10, 0)));
	  panelButtons.add(tryitButton);
	  panelButtons.add(Box.createHorizontalGlue());
	  panelButtons.add(previousButton);
	  panelButtons.add(nextButton);
	  
	  panel.add(panelButtons);
	  
	  try {
		  currentframe = new WFrame(439, 570, panel);
		  currentframe.setVisible(true);
		  currentframe.requestFocusInWindow();
		  displayhtml(pos);
		  previousButton.setEnabled(false);
	  } catch(IOException e) {
		  e.printStackTrace();
	  }
	  
  }		
  
  public void displayhtml(int index) {
	  URL htmlfile = getIndexFile(index);
	  currentframe.setFile(htmlfile);
  }
 
  public URL getIndexFile(int index) {
	  String filename = htmlArray[index];
	  java.net.URL htmlURL = getClass().getResource(filename);
	  
	  return htmlURL;
  }
  
  @Override 
  public void actionPerformed(ActionEvent e) {
		if(base == null) {
			return;
		}

		String selected = e.getActionCommand();
		
		if(selected.equals("Try It!")) {
			Editor editor = base.getActiveEditor();
			if(pos == 1) {
				String drawEllipse = "ellipse(50, 50, 80, 80);";
				editor.setText(drawEllipse);
			} else if(pos == 3) {
				String aniEllipse = "void setup() {"+"\n"+
									" size(480, 120);\n"+
									"}\n"+
									"\n"+
									"void draw() {"+"\n"+
									" if(mousePressed) {"+"\n"+
									"   fill(0);\n"+
									" } else {\n" +
									"   fill(255);\n"+
									" }\n"+
									" ellipse(mouseX, mouseY, 80, 80);\n"+
									"}\n";
				
				editor.setText(aniEllipse);
			} else if(pos == 9) {
				String idErrors = "void setup() {"+"\n"+
									" size(200, 200);\n"+
									"}\n"+
									"\n"+
									"void draw() {"+"\n"+
									" int x = 50\n"+
									" ellipse(300, 200, 50, 50;\n"+
									"}\n";
				editor.setText(idErrors);
			}
		}
		
		if(selected.equals("Previous")) {
			pos = pos - 1;
			nextButton.setEnabled(true);
			
			if(pos == 1 || pos == 3 || pos == 9) {
				tryitButton.setEnabled(true);
			} else {
				tryitButton.setEnabled(false);
			}
			
			if(pos < 0 || pos == 0) {
				pos = 0;
				previousButton.setEnabled(false);
			} 

			displayhtml(pos);
		} else if(selected.equals("Next")) {
			pos = pos + 1;
			if(pos == htmlArray.length - 1) {
				nextButton.setEnabled(false);
			} 
			
			if(pos == 1 || pos == 3 || pos == 9) {
				tryitButton.setEnabled(true);
			} else {
				tryitButton.setEnabled(false);
			}
			
			previousButton.setEnabled(true);
			
			if(pos >= htmlArray.length) {
				pos = htmlArray.length - 1;
			}
			
			displayhtml(pos);
		}
  }
}
