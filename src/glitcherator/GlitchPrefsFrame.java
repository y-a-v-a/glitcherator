package glitcherator;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Locale;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

public class GlitchPrefsFrame extends JFrame {

	private static final long serialVersionUID = 6689879634112896433L;

	private Preferences prefs = Preferences.userNodeForPackage(glitcherator.App.class);
	
	public static final String SAVE_PATH_KEY = "save_path";
	public static final String SAVE_PATH_VAL = System.getProperty("user.dir");
	public static final String APP_LOCALE_KEY = "app_locale";
	public static final String APP_LOCALE_VAL = Locale.getDefault().toString();
	
	public GlitchPrefsFrame(String name) {
		super(name);
		initPrefs();
		pack();
	}

	private void initPrefs() {		
		JPanel mainPane = new JPanel();
		mainPane.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));
		
		JLabel language = new JLabel(App.bundle.getString("prefs.language"));
		mainPane.add(language);
		
		ActionListener l = new PrefActionListener();

		String[] labels = { "nl_NL", "en_US" };
		JComboBox jcb = new JComboBox(labels);
		jcb.setSelectedItem(prefs.get(APP_LOCALE_KEY, APP_LOCALE_VAL));
		jcb.setActionCommand("changeLang");
		jcb.addActionListener(l);
		mainPane.add(jcb);
		
		JLabel path = new JLabel(App.bundle.getString("prefs.path"));
		mainPane.add(path);
		
		JTextField jtf = new JTextField(prefs.get(SAVE_PATH_KEY, SAVE_PATH_VAL));
		jtf.setColumns(12);
		jtf.setActionCommand("setPath");
		PrefCaretListener cl = new PrefCaretListener();
		jtf.addCaretListener(cl);
		mainPane.add(jtf);
		
		add(mainPane);
	}
	
	class PrefActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String command = e.getActionCommand();
			JComboBox jcb = (JComboBox) e.getSource();
			if (command == "changeLang") {
				String locale = (String) jcb.getSelectedItem();
				prefs.put(APP_LOCALE_KEY, locale);
			}
		}
	}
	
	class PrefCaretListener implements CaretListener {

		@Override
		public void caretUpdate(CaretEvent e) {
			JTextField s = (JTextField) e.getSource();
			String path = s.getText();
			File f = new File(path);
			boolean exists = f.exists();
			if (!exists) {
				s.setBorder(BorderFactory.createLineBorder(Color.RED));
			}
			if (exists) {
				s.setBorder(BorderFactory.createLineBorder(Color.GREEN));
				prefs.put(SAVE_PATH_KEY, path);
				try {
					prefs.flush();
				} catch (BackingStoreException e1) {
					// silently fail
					System.out.println("could not store prefs");
				}
			}
			
		}
		
	}
}
