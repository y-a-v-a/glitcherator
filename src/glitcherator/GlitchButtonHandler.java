package glitcherator;

import java.awt.Component;
import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

public class GlitchButtonHandler implements ActionListener {

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
			StringBuffer sb = new StringBuffer();
			try {
				String source = getClass().getResource(App.HOW_TO).getFile();
				FileReader fr = new FileReader(source);
				BufferedReader in = new BufferedReader(fr);
				
				String str;
				while ((str = in.readLine()) != null) {
					sb.append(str);
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}

			JLabel jl = new JLabel();
			jl.setText(sb.toString());
			jl.setAlignmentX(0.5f);
			jl.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));
			
			final JDialog jd = new JDialog();
			jd.setLayout((LayoutManager) new BoxLayout(jd.getContentPane(), BoxLayout.Y_AXIS));
			jd.add(Box.createRigidArea(new Dimension(0, 10)));
			jd.add(jl);
			jd.setModalityType(ModalityType.APPLICATION_MODAL);
			jd.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			jd.setTitle("How to glitch...");
			jd.setLocationRelativeTo(null);
			
			jd.add(Box.createRigidArea(new Dimension(0, 50)));
			
			JButton close = new JButton("Close");
			close.setName("Close");
			close.addActionListener(new ActionListener() {

	            public void actionPerformed(ActionEvent event) {
	                jd.dispose();
	            }
	        });

	        close.setAlignmentX(0.5f);
	        jd.add(close);
	        jd.setSize(400, 300);
	        jd.setVisible(true);
			
		}
	}

}
