package menus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import constants.GConstants;
import constants.GConstants.EEditMenuItem;
import frames.GDrawingPanel;
import shapes.GShape;

public class GEditMenu extends JMenu {
	private static final long serialVersionUID = 1L;
	// association
	private GDrawingPanel drawingPanel;
	private Vector<GShape> temps;
	private Vector<GShape> saves;

	public GEditMenu() {
		super(GConstants.EDITMENU_TITLE);
		temps = new Vector<GShape>();
		saves = new Vector<GShape>();
		ActionHandler actionHandler = new ActionHandler();
		for (GConstants.EEditMenuItem eMenuItem : GConstants.EEditMenuItem.values()) {
			JMenuItem menuItem = new JMenuItem(eMenuItem.getText());
			menuItem.addActionListener(actionHandler);
			menuItem.setActionCommand(eMenuItem.name());
			this.add(menuItem);
		}
	}
	private class ActionHandler implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if (event.getActionCommand().equals(EEditMenuItem.cut.name())) {
				cut();
			} else if (event.getActionCommand().equals(EEditMenuItem.copy.name())) {
				copy();
			} else if (event.getActionCommand().equals(EEditMenuItem.paste.name())) {
				paste();
			} else if (event.getActionCommand().equals(EEditMenuItem.delete.name())) {
				delete();
			} else if (event.getActionCommand().equals(EEditMenuItem.ddo.name())) {
				ddo();
			} else if (event.getActionCommand().equals(EEditMenuItem.undo.name())) {
				undo();
			} else if (event.getActionCommand().equals(EEditMenuItem.group.name())) {
				group();
			} else if (event.getActionCommand().equals(EEditMenuItem.ungroup.name())) {
				ungroup();
			} 
		}
	}

	public void initialize(GDrawingPanel drawingPanel) {
		this.drawingPanel = drawingPanel;
	}

	public void ddo() {
		drawingPanel.ddo();
	}

	public void undo() {
		drawingPanel.undo();
	}

	public void cut() {
		saves.clear();
		for (GShape s : this.drawingPanel.getShapeVector()) {
			temps.add(s);
		}
		for (GShape s : temps) {
			if (s.isSelected()) {
				saves.add((GShape) s.clone());
				this.drawingPanel.getShapeVector().remove(s);
			}
		}
		drawingPanel.repaint();
	}

	public void paste() {
		drawingPanel.resetSelections();
		for (GShape s : saves) {
			s.setSelected(true);
			this.drawingPanel.getShapeVector().add((GShape) s.clone());
		}
		drawingPanel.repaint();
	}

	public void copy() {
		saves.clear();
		for (GShape s : this.drawingPanel.getShapeVector()) {
			if (s.isSelected()) {
				saves.add((GShape) s.clone());
			}
		}
	}

	public void delete() {
		for (GShape s : this.drawingPanel.getShapeVector()) {
			temps.add(s);
		}
		for (GShape s : temps) {
			if (s.isSelected()) {
				this.drawingPanel.getShapeVector().remove(s);
			}
		}
		drawingPanel.repaint();
	}

	public void group() {
	}
	public void ungroup() {
	}
}
