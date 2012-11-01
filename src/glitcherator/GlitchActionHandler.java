package glitcherator;

import java.awt.Component;
import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FilenameFilter;

import javax.swing.JScrollPane;
import javax.swing.JViewport;

public class GlitchActionHandler implements ActionListener {
	private HowToDialog howToDialog = null;

	@Override
	public void actionPerformed(ActionEvent e) {
		Component[] cs = App.frame.getContentPane().getComponents();
		JScrollPane jsp = null;
		try {
			for (int i = 0; i < cs.length; i++) {
				if (cs[i].getName() == "ScrollPane") {
					jsp = (JScrollPane) cs[i];
				}
			}
			if (jsp == null) {
				throw new Exception("Cannot find panel!");
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		JViewport jvp = (JViewport) jsp.getComponent(0);
		GlitchPanel gp = (GlitchPanel) jvp.getComponent(0);
		
		if (e.getActionCommand() == "Save") {
			String file = "img" + gp.getGlitch().getCtime() + ".jpg";
			FileDialog fd = new FileDialog(App.frame, null, FileDialog.SAVE);
			fd.setTitle("Save Glitched file...");
			fd.setAlwaysOnTop(true);
			fd.setFile(file);
			fd.setVisible(true);
			String filename = fd.getDirectory() + fd.getFile();
			gp.getGlitch().export(filename);
		}

		if (e.getActionCommand() == "Refresh") {
			gp.getGlitch().refresh();
			App.frame.repaint();
		}

		if (e.getActionCommand() == "Open") {
			FileDialog fd = new FileDialog(App.frame, null, FileDialog.LOAD);
			fd.setFilenameFilter(new FilenameFilter() {
				public boolean accept(File dir, String name) {
					return (name.endsWith(".jpg")); // only jpeg for now
				}
			});
			fd.setVisible(true);
			String filename = fd.getDirectory() + fd.getFile();
			gp.loadNewGlitch(filename);
		}
		if(e.getActionCommand() == "How") {
			// cache panel
			if (howToDialog == null) {
				howToDialog = new HowToDialog();
			}
			howToDialog.setVisible(true);
		}
	}
	
}
