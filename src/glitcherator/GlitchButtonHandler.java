package glitcherator;

import java.awt.Component;
import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FilenameFilter;

public class GlitchButtonHandler implements ActionListener {
	private HowToDialog htp = null;

	@Override
	public void actionPerformed(ActionEvent e) {
		Component[] cs = App.frame.getContentPane().getComponents();
		GlitchPanel gp = null;
		try {
			for (int i = 0; i < cs.length; i++) {
				if (cs[i].getName() == "Glitchpanel") {
					gp = (GlitchPanel) cs[i];
				}
			}
			if (gp == null) {
				throw new Exception("Cannot find panel!");
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
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
			if (htp == null) {
				htp = new HowToDialog();
			}
			htp.setVisible(true);
		}
	}

}
