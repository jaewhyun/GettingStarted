/**
 * Welcome Tool for PDE
 *
 * ##Copyright (c) 2018 Jae Won Hyun##
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
 * @author   ##Jae Won Hyun##
 * @modified ##06/04/2018##
 * @version  ##1.0.0##
 */

/**
 *
 * Referenced: WebFrame.java from the Processing repository.
 *
 */

package template.tool;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Desktop;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URI;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.html.*;

import processing.app.Platform;
import processing.app.ui.Toolkit;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class WFrame extends JFrame {
	JEditorPane editorPane;
	HTMLEditorKit editorkit;
	
	boolean ready;
	
	public WFrame(int width, int height, Container panel) throws IOException {

		setSize(width, height);
		this.setResizable(false);
		editorPane = new JEditorPane();
		editorPane.setEditable(false);
		
		editorPane.setPreferredSize(new Dimension(width, height));
		
		Container containerPanel = getContentPane();
		containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.PAGE_AXIS));
		containerPanel.add(editorPane);
		if(panel != null) 
			containerPanel.add(panel);
		
		Toolkit.registerWindowCloseKeys(getRootPane(), new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handleClose();
			}
		});

		Toolkit.setIcon(this);
		
		editorPane.setContentType("text/html");
		
		editorkit = new HTMLEditorKit();
		editorkit.setAutoFormSubmission(false);
		editorPane.setEditorKit(editorkit);
		
		editorPane.addHyperlinkListener(new HyperlinkListener() {
			@Override
			public void hyperlinkUpdate(HyperlinkEvent e) {
				if(e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
					handleLink(e.getURL().toExternalForm());
				}
			}
		});
	}
	
	public void setFile(URL urllink) {
		try {
			editorPane.setPage(urllink);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void handleLink(String link){
		try {
			openthislink(link);
		} catch(Exception e) {
			 e.printStackTrace();
		}
	}
	
	public void openthislink(String url) throws Exception {
		Desktop.getDesktop().browse(new URI(url));
	}
	
	public void handleClose() {
		dispose();
	}
}