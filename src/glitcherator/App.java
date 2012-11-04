package glitcherator;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JToolBar;
import javax.swing.event.ChangeListener;

import com.apple.eawt.AboutHandler;
import com.apple.eawt.AppEvent.AboutEvent;
import com.apple.eawt.AppEvent.PreferencesEvent;
import com.apple.eawt.Application;
import com.apple.eawt.PreferencesHandler;

public class App {

	public static final String INIT_IMAGE = "/resources/start.jpg";
	public static final String ABOUT_ICON = "/resources/g32.png";
	public static final String HOW_TO = "/resources/howto.html";
	public static final Integer BASE_WIDTH = 1000;
	public static final Integer BASE_HEIGHT = 800;
	
	public static JFrame frame;
	public static ResourceBundle bundle;
	
	private static HashMap<String, Component> appComponents = new HashMap<String, Component>();
	
	public App() {
		frame = new JFrame();
		frame.setName("Glitcherator");
		frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(BASE_WIDTH, BASE_HEIGHT));
		frame.setLayout(new BorderLayout(4,4));
		appComponents.put("app", frame);

		Preferences prefs = Preferences.userNodeForPackage(glitcherator.App.class);
		Locale locale =  new Locale(prefs.get(GlitchPrefsFrame.APP_LOCALE_KEY, GlitchPrefsFrame.APP_LOCALE_VAL));
		bundle = ResourceBundle.getBundle("resources.UI", locale);
	}
	
	public static void main(String[] args) {
		System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Glitcherator");
		System.setProperty("apple.laf.useScreenMenuBar", "true");
		
		App app = new App();
		app.initialize();
		app.setAboutHandler();
		app.setPreferencesHandler();
	}

	private void initialize() {
		GlitchPanel glitch = new GlitchPanel();
		glitch.setName("Glitchpanel");
		appComponents.put(glitch.getName(), glitch);
		GlitchActionListener gbh = new GlitchActionListener();
		
		JToolBar toolBar = createToolBar(gbh);
		frame.add(toolBar, BorderLayout.PAGE_START);
		
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu(bundle.getString("menu.file"));
		menuBar.add(fileMenu);
		
		JMenuItem open = new JMenuItem(bundle.getString("menu.file.open"));
		open.setName("Open");
		open.setActionCommand("open");
		open.addActionListener(gbh);
		fileMenu.add(open);
		
		JMenuItem save = new JMenuItem(bundle.getString("menu.file.save"));
		save.setName("Save");
		save.setActionCommand("save");
		save.addActionListener(gbh);
		fileMenu.add(save);
		
		JMenu helpMenu = new JMenu(bundle.getString("menu.help"));
		menuBar.add(helpMenu);
		
		JMenuItem help = new JMenuItem("How to glitch");
		help.setName("How");
		help.setActionCommand("How");
		help.addActionListener(gbh);
		helpMenu.add(help);
		
		frame.setJMenuBar(menuBar);
		
		StringBuffer title = new StringBuffer();
		title.append(Glitcherator.VERSION);
		title.append(" - ");
		title.append(glitch.getGlitch().getFilename());
		title.append(" - y_a_v_a");
		frame.setTitle(title.toString());
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setName("ScrollPane");
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setViewportView(glitch);
		
		JLabel statusBar = new JLabel("Statusbar");
		statusBar.setName("Statusbar");
		statusBar.setToolTipText("Information about last process.");
		statusBar.setFont(new Font(statusBar.getFont().getName(), Font.PLAIN, 10));
		statusBar.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		statusBar.setBorder(BorderFactory.createEmptyBorder(0, 10, 4, 0));
		statusBar.setHorizontalTextPosition(JLabel.LEFT);
		appComponents.put(statusBar.getName(), statusBar);
		frame.add(statusBar, BorderLayout.SOUTH);
		
		frame.add(scrollPane, BorderLayout.CENTER);
		frame.pack();
		frame.setVisible(true);	
	}

	private JToolBar createToolBar(GlitchActionListener gbh) {
		JToolBar toolBar = new JToolBar(JToolBar.HORIZONTAL);
		toolBar.setName("ToolBar");
		toolBar.setMargin(new Insets(4, 4, 4, 4));
		toolBar.setFloatable(false);
		
		JButton bttn = new JButton(bundle.getString("button.refresh"));
		bttn.setName("Refresh");
		bttn.setActionCommand("refresh");
		bttn.setToolTipText(bundle.getString("button.refresh.tooltip"));
		bttn.addActionListener(gbh);
		
		ChangeListener cl = new GlitchChangeListener();
		JSlider sizeSlider = new JSlider(JSlider.HORIZONTAL, 0, 1024, 512);
		sizeSlider.setName("SizeSlider");
		sizeSlider.setToolTipText(bundle.getString("slider.size.tooltip"));
		sizeSlider.addChangeListener(cl);
		sizeSlider.setMajorTickSpacing(64);
		sizeSlider.setSnapToTicks(true);
		sizeSlider.setPaintTicks(true);
		
		JSlider amountSlider = new JSlider(JSlider.HORIZONTAL, 0, 32, 4);
		amountSlider.setName("AmountSlider");
		amountSlider.setToolTipText(bundle.getString("slider.amount.tooltip"));
		amountSlider.addChangeListener(cl);
		amountSlider.setMajorTickSpacing(2);
		amountSlider.setSnapToTicks(true);

		JSlider hexSlider = new JSlider(JSlider.HORIZONTAL, 0, 255, 94); // 94 = 5e hex
		hexSlider.setName("HexSlider");
		hexSlider.setToolTipText(bundle.getString("slider.hex.tooltip"));
		hexSlider.addChangeListener(cl);
		hexSlider.setMajorTickSpacing(1);
		hexSlider.setSnapToTicks(true);
		
		JLabel hexVal = new JLabel("5e");
		hexVal.setName("hexVal");
		hexVal.setPreferredSize(new Dimension(20, 0));
		appComponents.put(hexVal.getName(), hexVal);
		
		toolBar.add(bttn);
		toolBar.addSeparator();
		toolBar.add(sizeSlider);
		toolBar.addSeparator();
		toolBar.add(amountSlider);
		toolBar.addSeparator();
		toolBar.add(hexSlider);
		toolBar.add(hexVal);
		
		return toolBar;
	}

	/**
	 * Create about page
	 */
	private void setAboutHandler() {
		Application a = Application.getApplication();
		a.setAboutHandler(new AboutHandler() {
			
			@Override
			public void handleAbout(AboutEvent e) {
				ImageIcon icon = new ImageIcon(getClass().getResource(App.ABOUT_ICON));
				
				StringBuffer sb = new StringBuffer();
				sb.append(bundle.getString("about.version"));
				sb.append(" ");
				sb.append(Glitcherator.VERSION);
				sb.append(bundle.getString("about.by"));
				
				JOptionPane.showMessageDialog(null, sb, bundle.getString("about.title"),
						JOptionPane.INFORMATION_MESSAGE, icon);
			}
		});
	}
	
	private void setPreferencesHandler() {
		Application a = Application.getApplication();
		a.setPreferencesHandler(new PreferencesHandler() {
			@Override
			public void handlePreferences(PreferencesEvent arg0) {
				GlitchPrefsFrame prefs = new GlitchPrefsFrame("Prefs");
				prefs.setVisible(true);
			}
		});
	}

	static HashMap<String, Component> getAppComponents() {
		return appComponents;
	}
}
