package shapes;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Vector;

import constants.GConstants;

public class GSelection extends GRectangle {
	private static final long serialVersionUID = 1L;
	public Vector<GShape> shapes;

	public void setShapes(Vector<GShape> shapes) {this.shapes = shapes;}
	public void init(Vector<GShape> shapes) {
		this.shapes = shapes;
	}
	
	public void draw(Graphics g) {
		Graphics2D g2D = (Graphics2D)g;
		float dashes[] = {GConstants.DEFAULT_DASH_OFFSET};
		BasicStroke dashedLineStroke = new BasicStroke(GConstants.DEFAULT_DASHEDLINE_WIDTH,
	            BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 10, dashes, 0);
		g2D.setStroke(dashedLineStroke);
		g2D.draw(shape);
	}
	
	public void selectShapes() {
		for(GShape shapeVector: shapes)  {
			if(shape.contains(shapeVector.getBounds())) {
				shapeVector.setSelected(true);
			}
		}
	}
	
	public GShape newClone() {
		return new GSelection();
	}
}
