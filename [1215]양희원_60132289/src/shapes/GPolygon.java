package shapes;

import java.awt.Polygon;

import constants.GConstants.EDrawingType;

public class GPolygon extends GShape {
	private static final long serialVersionUID = 1L;
	private Polygon polygon;
	public GPolygon() {
		super(EDrawingType.NP, new Polygon());
		this.polygon = (Polygon)this.getShape();
	}
	public void setOrigin(int x, int y) {
		this.polygon.addPoint(x, y);
		this.polygon.addPoint(x, y);
	}
	public void setPoint(int x, int y) {
		px = x;
		py = y;
	}
	public void addPoint(int x, int y) {
		this.polygon.addPoint(x, y);
	}
	public void resize(int x, int y) {
		if (this.getCurrentEAnchor() == null) {
			for(int i = 0; i<this.polygon.npoints-1;i++){
			this.polygon.xpoints[this.polygon.npoints-1] = x;
			this.polygon.ypoints[this.polygon.npoints-1] = y;
			}
			return;
		}
		switch (this.getCurrentEAnchor()) {
		case NN:
			break;
		case NE:
			break;
		case NW:
			break;
		case SS:
			break;
		case SE:
			for(int i = 0; i<this.polygon.npoints;i++){
			this.polygon.xpoints[this.polygon.npoints-1] = x;
			this.polygon.ypoints[this.polygon.npoints-1] = y;
			}
			break;
		case SW:
			break;
		case EE:
			break;
		case WW:
			break;
		default:
			break;
		}
	}
	public void move(int x, int y) {
		for (int i=0; i<this.polygon.npoints; i++) {
			this.polygon.xpoints[i] += x - px;
			this.polygon.ypoints[i] += y - py;
		}
		this.polygon.invalidate();
		px = x;
		py = y;
	}
}
