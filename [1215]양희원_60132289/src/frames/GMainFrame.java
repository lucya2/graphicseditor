package frames;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

import constants.GConstants;
import constants.GConstants.EMainFrame;

// MainFrame specialize JFrame
public class GMainFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	// components
	private GMenuBar menuBar;
	private GToolBar toolBar;
	private GDrawingPanel drawingPanel;
	// constructor
	public GMainFrame() {
		// attribute initialization
		this.setTitle(GConstants.MAINFRAME_TITLE);
		this.setLocation(EMainFrame.X.getValue(), EMainFrame.Y.getValue());
		this.setSize(new Dimension(EMainFrame.W.getValue(), EMainFrame.H.getValue()));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// component creation & registration
		this.menuBar = new GMenuBar();
		this.setJMenuBar(menuBar);
		
		this.getContentPane().setLayout(new BorderLayout());
		this.toolBar = new GToolBar();
		this.add(toolBar, BorderLayout.NORTH);
		this.drawingPanel = new GDrawingPanel();
		this.add(drawingPanel, BorderLayout.CENTER);
	}	
	public void initialize() {
		// component initialization
		this.menuBar.initialize(this.drawingPanel);			
		this.toolBar.initialize(this.drawingPanel);		
		this.drawingPanel.initialize();		
	}
}
