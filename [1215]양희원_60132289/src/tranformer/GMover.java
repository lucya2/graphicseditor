package tranformer;

import java.awt.Graphics2D;

import shapes.GShape;

public class GMover extends GTransformer {

	public GMover(GShape shape) {
		super(shape);
	}

	@Override
	public void initTransforming(int x, int y, Graphics2D g2d) {
		// TODO Auto-generated method stub
		this.getShape().setPoint(x, y);
		this.getShape().draw(g2d);
	}

	@Override
	public void keepTransforming(int x, int y, Graphics2D g2d) {
		this.getShape().draw(g2d);
		this.getShape().move(x, y);
		this.getShape().draw(g2d);
	}

	@Override
	public void finishTransforming(int x, int y, Graphics2D g2d) {
		// TODO Auto-generated method stub

	}

	@Override
	public void continueTransforming(int x, int y, Graphics2D g2d) {
		// TODO Auto-generated method stub

	}

}
