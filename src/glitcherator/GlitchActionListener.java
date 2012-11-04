package glitcherator;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FilenameFilter;
import java.util.prefs.Preferences;

public class GlitchActionListener implements ActionListener {
	private HowToDialog howToDialog = null;

	@Override
	public void actionPerformed(ActionEvent e) {
		GlitchPanel gp = (GlitchPanel) App.getAppComponents().get("Glitchpanel");
		
		if (e.getActionCommand() == "save") {
			String file = "img" + gp.getGlitch().getCtime() + ".jpg";
			FileDialog fd = new FileDialog(App.frame, null, FileDialog.SAVE);
			fd.setTitle("Save Glitched file...");
			fd.setAlwaysOnTop(true);
			fd.setFile(file);
			fd.setVisible(true);
			String filename = fd.getDirectory() + fd.getFile();
			gp.getGlitch().export(filename);
		}

		if (e.getActionCommand() == "refresh") {
			gp.getGlitch().refresh();
			App.frame.repaint();
		}

		if (e.getActionCommand() == "open") {
			FileDialog fd = new FileDialog(App.frame, null, FileDialog.LOAD);
			Preferences prefs = Preferences.userNodeForPackage(glitcherator.App.class);
			fd.setDirectory(prefs.get(GlitchPrefsFrame.SAVE_PATH_KEY, GlitchPrefsFrame.SAVE_PATH_VAL));
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
