package glitcherator;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class App {

	private String imagename;

	public static JFrame frame;
	
	public static void main(String[] args) {
		System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Glitcherator");
		
		App app = new App();
		app.imagename = args[0];
		Glitcherator glitch = new Glitcherator(app.imagename);
		
		frame = new JFrame("Glitcherator");
		frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		Dimension minimumSize = new Dimension(1000,800);
		panel.setPreferredSize(minimumSize);
		
		JButton bttn = new JButton("Refresh");
		bttn.setToolTipText("Refresh image");
		bttn.addActionListener(glitch);
		JButton bttn2 = new JButton("Save");
		bttn2.addActionListener(glitch);
		JButton bttn3 = new JButton("Open");
		bttn3.addActionListener(glitch);
		
		panel.add(bttn);
		panel.add(bttn2);
		panel.add(bttn3);
		frame.add(panel);
		
		StringBuffer title = new StringBuffer();
		title.append(Glitcherator.VERSION);
		title.append(" - ");
		title.append(app.imagename);
		title.append(" - y_a_v_a");
		frame.setTitle(title.toString());
				
		
		glitch.build();
		
		panel.add(glitch);
		frame.setContentPane(panel);


		frame.pack();
		frame.setVisible(true);
	}
}
