/**
 * Getting Started Tool for PDE
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

package jwh.gettingstarted;

import java.awt.Dimension;
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

public class GettingStarted implements Tool, ActionListener {
  Base base;
  WFrame currentFrame;
  JButton tryitButton;
  JButton previousButton;
  JButton nextButton;
  
  // variable to indicate whether a new editor has been opened before
  boolean open = false;
  int openLocation = 0;
  
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
		  "/data/static/13.html",
		  "/data/static/14.html",
		  "/data/static/15.html"};
  HashMap<Integer, String> tutorialCode = new HashMap<Integer, String>();

  public String getMenuTitle() {
    return "Getting Started";
  }


  public void init(Base base) {
    this.base = base;
  }


  public void run() {
    createWalkthrough();
    System.out.println("Getting Started 1.1.0 by Jae Hyun");
  }
  
  /*
   * Adding buttons to the frame
   */
  public void createWalkthrough() {
	  // if the frame had been opened before
	  if(currentFrame != null) {
		  	displayhtml(0);
		  	pos = 0;
			currentFrame.setVisible(true);
			return;
	  }
	  
	  JComponent panel = Box.createHorizontalBox();
	  panel.setBackground(new Color(245, 245, 245));
	  
	  // adding Try It button to populate tutorial code into a new editor
	  tryitButton = new JButton("Try It!");
	  tryitButton.addActionListener(this);
	  tryitButton.setActionCommand("Try It!");
	  tryitButton.setEnabled(false);
	  
	  // adding Previous button to return to the previous frame
	  previousButton = new JButton("Previous");
	  previousButton.addActionListener(this);
	  previousButton.setActionCommand("Previous");
	  
	  // adding Next button to flip to the next frame
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
//	  panelButtons.setBackground(Color.WHITE);
	 
	  panel.add(panelButtons);
	  
	  try {
		  currentFrame = new WFrame(439, 570, panel);
		  currentFrame.setVisible(true);
		  currentFrame.requestFocusInWindow();
		  setTutorialCode();
		  displayhtml(pos);
		  previousButton.setEnabled(false);
	  } catch(IOException e) {
		  e.printStackTrace();
	  }
	  
  }		
  
  /*
   * Retrieving html file
   */
  public URL getIndexFile(int index) {
	  String filename = htmlArray[index];
	  java.net.URL htmlURL = getClass().getResource(filename);
	  
	  return htmlURL;
  }
  
  /*
   * displying the html file
   */
  public void displayhtml(int index) {
	  URL htmlfile = getIndexFile(index);
	  currentFrame.setFile(htmlfile);
  }
 
  /*
   * Setting Try It codes
   */
  public void setCode(Editor editorInput) {
	  if(tutorialCode.containsKey(pos)) {
		  editorInput.setText(tutorialCode.get(pos));
	  }
  }
  
  /*
   * Saving Tutorial Code into Map
   */
  public void setTutorialCode() {
	  String drawEllipse = "ellipse(50, 50, 80, 80);";
	  tutorialCode.put(1, drawEllipse);
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
	  tutorialCode.put(3, aniEllipse);
	  String idErrors = "void setup() {"+"\n"+
				" size(200, 200);\n"+
				"}\n"+
				"\n"+
				"void draw() {"+"\n"+
				" int x = 50\n"+
				" ellipse(300, 200, 50, 50;\n"+
				"}\n";
	  tutorialCode.put(10, idErrors);
  }
  
  @Override 
  public void actionPerformed(ActionEvent e) {
		if(base == null) {
			return;
		}

		String selected = e.getActionCommand();
		
		if(selected.equals("Try It!")) {
			if(open == false) {
				base.handleNew();
				openLocation = base.getEditors().size() - 1;
			}
			// indicates that a new editor is now open
			open = true;
			
			Editor editor;
			// if the the editor number is lower than the actual number of editors open rn,
			// then retrieve that editor to populate the example text
			if(openLocation < base.getEditors().size()) {
				editor = base.getEditors().get(openLocation);
				
			// if the editor number is higher than the actual number of editors open rn,
			// then open a new editor to populate the example text and remember that location instead
			} else {
				base.handleNew();
				openLocation = base.getEditors().size() - 1;
				editor = base.getEditors().get(openLocation);
			}
			
			setCode(editor);
			editor.requestFocus();
		}
		
		// enabling and abling buttons
		if(selected.equals("Previous")) {
			pos = pos - 1;
			nextButton.setEnabled(true);
			
			if(pos == 1 || pos == 3 || pos == 10) {
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
			
			if(pos == 1 || pos == 3 || pos == 10) {
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
