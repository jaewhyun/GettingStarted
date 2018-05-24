/*
 *
 * Reference: WebFrame.java from Processing repository.
 *
 */

package template.tool;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Desktop;
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
		
//		URL fileUrl = file.toURI().toURL();
//		requestContentHeight(width, fileUrl);
		this.setSize(width, height);
		editorPane = new JEditorPane();
		
//		editorPane.setPage(fileUrl);
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
		
		System.out.println("we're here1");
		
		File filelocation = new File("/Users/jaewonhyun/Desktop/1.html");
		URL filefile = filelocation.toURI().toURL();
		editorPane.setPage(filefile);
		editorPane.setContentType("text/html");
		editorkit = new HTMLEditorKit();
		editorkit.setAutoFormSubmission(false);
		editorPane.setEditorKit(editorkit);
//		editorKit = (HTMLEditorKit) editorPane.getEditorKit();
		System.out.println("we're here2");
//		editorKit.setAutoFormSubmission(false);
		
		editorPane.addHyperlinkListener(new HyperlinkListener() {
			@Override
			public void hyperlinkUpdate(HyperlinkEvent e) {
				if(e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
					handleLink(e.getURL().toExternalForm());
				}
			}
		});
		
		System.out.println("finished establishing");
	}
	
	public void setFile(int width, File file) {
		try {
			URL fileUrl = file.toURI().toURL();
			requestContentHeight(width, fileUrl);
			editorPane.setPage(fileUrl);
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
	
	void requestContentHeight(final int width, final URL url) {
		new Thread(new Runnable() {
			public void run() {
				final JEditorPane dummy = new JEditorPane();
				dummy.addPropertyChangeListener("page", new PropertyChangeListener() {
					@Override
					public void propertyChange(PropertyChangeEvent e) {
						int high = dummy.getPreferredSize().height;
						editorPane.setPreferredSize(new Dimension(width, high));
						pack();
						setLocationRelativeTo(null);
						ready = true;
					}
				});
				try {
					dummy.setPage(url);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				dummy.setSize(width, Short.MAX_VALUE);
			}
		}).start();
	}
	
	public void handleClose() {
		dispose();
	}
}