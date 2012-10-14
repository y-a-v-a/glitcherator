package glitcherator;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.apple.eawt.AboutHandler;
import com.apple.eawt.AppEvent.AboutEvent;
import com.apple.eawt.Application;

public class App {

	static final String INIT_IMAGE = "/resources/start.jpg";
	static final String ABOUT_ICON = "/resources/g32.png";
	static final String HOW_TO = "/resources/howto.html";
	static final Integer BASE_WIDTH = 1000;
	static final Integer BASE_HEIGHT = 800;
	
	public static JFrame frame;
	
	public static GlitchPanel glitch;
	
	public App() {
		frame = new JFrame();
		frame.setName("Glitcherator");
		frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Glitcherator");
		System.setProperty("apple.laf.useScreenMenuBar", "true");
				
		App app = new App();
		app.setAboutHandler();
		
		glitch = new GlitchPanel();
		glitch.setName("Glitchpanel");
		GlitchButtonHandler gbh = new GlitchButtonHandler();
		
		JPanel panel = new JPanel();
		panel.setName("Panel");
		Dimension minimumSize = new Dimension(App.BASE_WIDTH, App.BASE_HEIGHT);
		panel.setPreferredSize(minimumSize);
		
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		
		JMenuItem open = new JMenuItem("Open");
		open.setName("Open");
		open.addActionListener(gbh);
		fileMenu.add(open);
		
		JMenuItem save = new JMenuItem("Save");
		save.setName("Save");
		save.addActionListener(gbh);
		fileMenu.add(save);
		
		JMenu helpMenu = new JMenu("Help");
		menuBar.add(helpMenu);
		
		JMenuItem help = new JMenuItem("How to glitch");
		help.setName("How");
		help.setActionCommand("How");
		help.addActionListener(gbh);
		helpMenu.add(help);
		
		frame.setJMenuBar(menuBar);
		
		JButton bttn = new JButton("Refresh");
		bttn.setName("Refresh");
		bttn.setToolTipText("Refresh image");
		bttn.addActionListener(gbh);
		
		panel.add(bttn);
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

	private void setAboutHandler() {
		Application a = Application.getApplication();
		a.setAboutHandler(new AboutHandler() {
			
			@Override
			public void handleAbout(AboutEvent e) {
				ImageIcon icon = new ImageIcon(getClass().getResource(App.ABOUT_ICON));
				
				StringBuffer sb = new StringBuffer();
				sb.append("Glitcherator version ");
				sb.append(Glitcherator.VERSION);
				sb.append("\nby y-a-v-a.org");
				
				JOptionPane.showMessageDialog(null, sb, "About", JOptionPane.INFORMATION_MESSAGE, icon);
			}
		});
	}
}
