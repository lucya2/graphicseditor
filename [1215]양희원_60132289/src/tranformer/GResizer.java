package tranformer;

import java.awt.Graphics2D;

import shapes.GShape;

public class GResizer extends GTransformer {

	public GResizer(GShape shape) {
		super(shape);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initTransforming(int x, int y, Graphics2D g2d) {
		this.getShape().setPoint(x, y);
		this.getShape().draw(g2d);
	}

	@Override
	public void keepTransforming(int x, int y, Graphics2D g2d) {
		this.getShape().draw(g2d);
		this.getShape().resize(x, y);;
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
