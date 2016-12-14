package menus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import constants.GConstants;
import constants.GConstants.EFileMenuItem;
import frames.GDrawingPanel;
import shapes.GShape;

public class GFileMenu extends JMenu {
	private static final long serialVersionUID = 1L;
	// association
	private GDrawingPanel drawingPanel;
	private File file;

	public GFileMenu() {
		super(GConstants.FILEMENU_TITLE);
		ActionHandler actionHandler = new ActionHandler();
		for (EFileMenuItem eMenuItem : EFileMenuItem.values()) {
			JMenuItem menuItem = new JMenuItem(eMenuItem.getText());
			menuItem.addActionListener(actionHandler);
			menuItem.setActionCommand(eMenuItem.name());
			this.add(menuItem);
		}
		file = null;
	}

	private class ActionHandler implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if (event.getActionCommand().equals(EFileMenuItem.newFile.name())) {
				newFile();
			} else if (event.getActionCommand().equals(EFileMenuItem.open.name())) {
				open();
			} else if (event.getActionCommand().equals(EFileMenuItem.save.name())) {
				save();
			} else if (event.getActionCommand().equals(EFileMenuItem.saveAs.name())) {
				saveAs();
			} else if (event.getActionCommand().equals(EFileMenuItem.exit.name())) {
				exit();
			}
		}
	}

	public void initialize(GDrawingPanel drawingPanel) {
		this.drawingPanel = drawingPanel;
	}

	@SuppressWarnings("unchecked")
	private void inputStream() {
		try {
			ObjectInputStream inputStream = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)));
			drawingPanel.setShapeVector((Vector<GShape>) inputStream.readObject());
			inputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void outputStream() {
		try {
			ObjectOutputStream outputStream = new ObjectOutputStream(
					new BufferedOutputStream(new FileOutputStream(file)));
			outputStream.writeObject(drawingPanel.getShapeVector());
			outputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private int showOpenDialog() {
		JFileChooser fileChooser = new JFileChooser(new File("."));
		int reply;
		FileNameExtensionFilter filter = new FileNameExtensionFilter("GraphicEditor", "gps");
		fileChooser.setFileFilter(filter);
		reply = fileChooser.showOpenDialog(null);
		file = fileChooser.getSelectedFile();
		return reply;
	}

	private int showSaveDialog() {
		JFileChooser fileChooser = new JFileChooser(new File("."));
		FileNameExtensionFilter filter = new FileNameExtensionFilter("GraphicEditor", "gps");
		fileChooser.setFileFilter(filter);
		int reply = fileChooser.showSaveDialog(null);
		File renameFile = fileChooser.getSelectedFile();
		String extension = ".gps";
		if (renameFile != null) {
			if (renameFile.getName().contains(extension))
				file = new File(renameFile.getName());
			else
				file = new File(renameFile.getName() + extension);
		}
		return reply;
	}

	private void saveOrNot() {
		int reply = 0;
		if (drawingPanel.isDirty()) {
			reply = JOptionPane.showConfirmDialog(null, "저장하시겠습니까?");
			if (reply == JOptionPane.OK_OPTION) {
				showSaveDialog();
				drawingPanel.setDirty(false);
				outputStream();
			}
		}
	}

	public void newFile() {
		saveOrNot();
		drawingPanel.initPanel();
	}

	public void open() {
		int reply;
		saveOrNot();
		reply = showOpenDialog();
		if (reply == JOptionPane.OK_OPTION) {
			inputStream();
		}
	}

	public void save() {
		int reply = JOptionPane.OK_OPTION;
		if (drawingPanel.isDirty()) {
			if (file == null) {
				reply = showSaveDialog();
			}
			if (reply == JOptionPane.OK_OPTION) {
				drawingPanel.setDirty(false);
				outputStream();
			}
		}
	}

	public void saveAs() {
		int reply = showSaveDialog();
		if (reply == JOptionPane.OK_OPTION) {
			outputStream();
		}
	}

	public void exit() {
		int reply = JOptionPane.OK_OPTION;
		if (drawingPanel.isDirty()) {
			reply = JOptionPane.showConfirmDialog(null, "저장하시겠습니까?");
			if (reply == JOptionPane.OK_OPTION) {
				if (file == null) {
					reply = showSaveDialog();
				}
				drawingPanel.setDirty(false);
				outputStream();
			}
		}
		System.exit(1);
	}

}
