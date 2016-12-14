package frames;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.Stack;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import constants.GConstants;
import constants.GConstants.EAnchors;
import constants.GConstants.EDrawingType;
import shapes.GShape;
import tranformer.GDrawer;
import tranformer.GMover;
import tranformer.GResizer;
import tranformer.GRotator;
import tranformer.GTransformer;

public class GDrawingPanel extends JPanel {
	// attributes
	private static final long serialVersionUID = 1L;
	// object states
	private enum EState {idle, drawingTP, drawingNP, transforming};
	private EState eState;
	// components
	private Stack<Vector<GShape>> ddo;
	private Stack<Vector<GShape>> undo;
	private Vector<GShape> shapeVector;
	public Vector<GShape> getShapeVector() { return this.shapeVector; }
	public void setShapeVector(Vector<GShape> shapeVector) {
		this.shapeVector = shapeVector;
		repaint();
	}
	private MouseEventHandler mouseEventHandler;
	// associative attributes
	private GShape selectedShape;
	public void setSelectedShape(GShape selectedShape) {
		this.selectedShape = selectedShape;
	}	
	// working objects;
	private GShape currentShape;
	private GTransformer currentTransformer;
	private boolean bDirty;
	public boolean isDirty() {return bDirty;}
	public void setDirty(boolean bDirty) {this.bDirty = bDirty;}
	
	public GDrawingPanel() {
		super();
		// attributes
		this.eState = EState.idle;
		// components
		this.shapeVector = new Vector<GShape>();		
		ddo = new Stack<Vector<GShape>>();
		undo = new Stack<Vector<GShape>>();
		//ddo.add((Vector<GShape>) shapeVector.clone());
		//undo.add((Vector<GShape>) shapeVector.clone());
		this.mouseEventHandler = new MouseEventHandler();
		this.addMouseListener(mouseEventHandler);
		this.addMouseMotionListener(mouseEventHandler);
		// working variables
		this.selectedShape = null;
		this.currentShape = null;
		this.currentTransformer = null;
		this.bDirty = false;
	}
	public void initialize() {
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		for (GShape shape: this.shapeVector) {
			shape.draw((Graphics2D)g);
		}
	}
	public void initPanel() {
		shapeVector.clear();
		repaint();
		this.bDirty = false;
	}
	private void resetSelected() {
		for (GShape shape: this.shapeVector) {
			shape.setSelected(false);
		}
		this.repaint();
	}
	private void initTransforming(int x, int y) {
		if (this.currentShape == null) {
			this.currentShape= this.selectedShape.clone();
			this.currentTransformer = new GDrawer(this.currentShape);
		} else if (this.currentShape.getCurrentEAnchor() == EAnchors.MM) {
			this.currentTransformer = new GMover(this.currentShape);
		} else if (this.currentShape.getCurrentEAnchor() == EAnchors.RR) {
			this.currentTransformer = new GRotator(this.currentShape);
		} else {			
			this.currentTransformer = new GResizer(this.currentShape);
		}
		this.resetSelected();
		Graphics2D g2D = (Graphics2D)this.getGraphics();
		g2D.setXORMode(this.getBackground());
		this.currentTransformer.initTransforming(x, y, g2D);
	}
	private void keepTransforming(int x, int y) {
		Graphics2D g2D = (Graphics2D)this.getGraphics();
		g2D.setXORMode(this.getBackground());
		this.currentTransformer.keepTransforming(x, y, g2D);
	}
	private void continueTransforming(int x, int y) {
		Graphics2D g2D = (Graphics2D)this.getGraphics();
		g2D.setXORMode(this.getBackground());
		this.currentTransformer.continueTransforming(x, y, g2D);
	}
	@SuppressWarnings("unchecked")
	private void finishTransforming(int x, int y) {
		Graphics2D g2D = (Graphics2D)this.getGraphics();
		g2D.setXORMode(this.getBackground());
		this.currentTransformer.finishTransforming(x, y, g2D);
		
		if (this.currentTransformer.getClass().getSimpleName().equals("GDrawer")) {
			this.shapeVector.add(this.currentShape);
		}
		undo.add((Vector<GShape>) shapeVector.clone());
		ddo.clear();
		this.currentShape.setSelected(true);
		this.repaint();
		this.bDirty = true;
	}
	@SuppressWarnings("unchecked")
	public void ddo() {
		if(ddo.size() != 0) {
			undo.add((Vector<GShape>)shapeVector.clone());
			shapeVector = ddo.pop();
			repaint();
		}
	}
	@SuppressWarnings("unchecked")
	public void undo() {
		if(undo.size() != 0) {
			ddo.add((Vector<GShape>)shapeVector.clone());
			shapeVector = undo.pop();
			repaint();		
		}
	}
	private GShape onShape(int x, int y) {
		for (GShape shape: this.shapeVector) {
			GConstants.EAnchors eAnchor = shape.contnains(x, y);
			if (eAnchor != null)
				return shape;
		}
		return null;
	}
	private void changeCursor(GShape shape) {
		if (shape == null) {
			this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			return;
		}
		this.setCursor(shape.getCurrentEAnchor().getCursor());
	}
	public void resetSelections() {
		for(GShape shape : shapeVector) {
			shape.setSelected(false);
		}
		repaint();
	}
	class MouseEventHandler 
		implements MouseInputListener, MouseMotionListener {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() == 1) {
				mouse1Clicked(e);
			} else if (e.getClickCount() == 2) {
				mouse2Clicked(e);
			}
 		}
		private void mouse1Clicked(MouseEvent e) {
			if (eState == EState.idle) {
				currentShape = onShape(e.getX(), e.getY());
				if (currentShape == null) {
					if (selectedShape.geteDrawingType()==EDrawingType.NP) {
						initTransforming(e.getX(), e.getY());
						eState = EState.drawingNP;
					}
				}
			} else if (eState == EState.drawingNP) {	
				continueTransforming(e.getX(), e.getY());			
			}
		}
		private void mouse2Clicked(MouseEvent e) {
			if (eState == EState.drawingNP) {		
				finishTransforming(e.getX(), e.getY());
				eState = EState.idle;
			}			
		}
		@Override
		public void mousePressed(MouseEvent e) {
			if (eState == EState.idle) {
				currentShape = onShape(e.getX(), e.getY());
				if (currentShape == null) {
					if (selectedShape.geteDrawingType()==EDrawingType.TP) {
						initTransforming(e.getX(), e.getY());
						eState = EState.drawingTP;
					}
				} else {
					initTransforming(e.getX(), e.getY());
					eState = EState.transforming;
				}
			}	
		}
		@Override
		public void mouseReleased(MouseEvent e) {
			if (eState == EState.drawingTP) {		
				finishTransforming(e.getX(), e.getY());
				eState = EState.idle;
			} else if (eState == EState.transforming) {
				finishTransforming(e.getX(), e.getY());
				eState = EState.idle;
			} 
		}
		@Override
		public void mouseMoved(MouseEvent e) {
			if (eState == EState.drawingNP) {
				keepTransforming(e.getX(), e.getY());
			} else if (eState == EState.idle) {
				GShape shape = onShape(e.getX(), e.getY());
				changeCursor(shape);
			}
		}		
		@Override
		public void mouseDragged(MouseEvent e) {
			if (eState == EState.drawingTP) {
				keepTransforming(e.getX(), e.getY());
			} else if (eState == EState.transforming) {
				keepTransforming(e.getX(), e.getY());				
			}
		}
		@Override
		public void mouseEntered(MouseEvent arg0) {
		}
		@Override
		public void mouseExited(MouseEvent arg0) {
		}
	}

}
