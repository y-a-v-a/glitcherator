package glitcherator;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class App {

	public static JFrame frame;
	
	public static GlitchPanel glitch;
	
	public App() {
		frame = new JFrame();
		frame.setName("Glitcherator");
		frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Glitcherator");
		
		new App();
		glitch = new GlitchPanel();
		glitch.setName("Glitchpanel");
		GlitchButtonHandler gbh = new GlitchButtonHandler();
		
		JPanel panel = new JPanel();
		panel.setName("Panel");
		Dimension minimumSize = new Dimension(1000,800);
		panel.setPreferredSize(minimumSize);
		
		JButton bttn = new JButton("Refresh");
		bttn.setName("Refresh");
		bttn.setToolTipText("Refresh image");
		bttn.addActionListener(gbh);
		JButton bttn2 = new JButton("Save");
		bttn2.setName("Save");
		bttn2.addActionListener(gbh);
		JButton bttn3 = new JButton("Open");
		bttn3.setName("Open");
		bttn3.addActionListener(gbh);
		
		panel.add(bttn);
		panel.add(bttn2);
		panel.add(bttn3);
		frame.add(panel);
		
		StringBuffer title = new StringBuffer();
		title.append(Glitcherator.VERSION);
		title.append(" - ");
		title.append(glitch.getGlitch().getFilename());
		title.append(" - y_a_v_a");
		frame.setTitle(title.toString());
		
		panel.add(glitch);
		frame.setContentPane(panel);


		frame.pack();
		frame.setVisible(true);
	}
}
