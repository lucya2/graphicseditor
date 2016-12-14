package tranformer;

import java.awt.Graphics2D;

import shapes.GShape;

abstract public class GTransformer {
	private GShape shape;
	protected GShape getShape() { return this.shape; }
	
	public GTransformer(GShape shape) {
		this.shape = shape;
	}
	abstract public void initTransforming(int x, int y, Graphics2D g2D);
	abstract public void keepTransforming(int x, int y, Graphics2D g2D);
	abstract public void finishTransforming(int x, int y, Graphics2D g2D);
	abstract public void continueTransforming(int x, int y, Graphics2D g2D);
}

