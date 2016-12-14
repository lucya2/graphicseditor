package shapes;

import java.awt.Rectangle;

import constants.GConstants.EDrawingType;

public class GRectangle extends GShape {
	private static final long serialVersionUID = 1L;
	private Rectangle rectangle;
	public GRectangle() {
		super(EDrawingType.TP, new Rectangle(0, 0, 0, 0));
		this.rectangle = (Rectangle)this.getShape();
	}
	public void setOrigin(int x, int y) {
		this.rectangle.setLocation(x, y);
	}
	public void setPoint(int x, int y) {
		this.px = x;
		this.py = y;
	}
	public void move(int x, int y) {
		this.rectangle.x += x - px;
		this.rectangle.y += y - py;
		this.setPoint(x, y);
	}
	public void addPoint(int x, int y) {
	}

	public void resize(int x, int y) {
		if (this.getCurrentEAnchor() == null) {
			this.rectangle.width = x - this.rectangle.x;
			this.rectangle.height = y - this.rectangle.y;
			return;
		}
		switch (this.getCurrentEAnchor()) {
		case NN:
			this.rectangle.height += this.rectangle.y - y;
			this.rectangle.y = y;
			break;
		case NE:
			this.rectangle.height +=  this.rectangle.y - y;
			this.rectangle.width = x - this.rectangle.x;
			this.rectangle.y = y;
			break;
		case NW:
			this.rectangle.height += this.rectangle.y - y;
			this.rectangle.width += this.rectangle.x - x;
			this.rectangle.y = y;
			this.rectangle.x = x;
			break;
		case SS:
			this.rectangle.height = y - this.rectangle.y;	
			break;
		case SE:
			this.rectangle.width = x - this.rectangle.x;
			this.rectangle.height = y - this.rectangle.y;	
			break;
		case SW:
			this.rectangle.height = y- this.rectangle.y;
			this.rectangle.width += this.rectangle.x - x;
			this.rectangle.x = x;
			break;
		case EE:
			this.rectangle.width =  x - this.rectangle.x;
			break;
		case WW:
			this.rectangle.width += this.rectangle.x - x;
			this.rectangle.x = x;
			break;
		default:
			break;
		}
		this.setPoint(x, y);
	}
}
