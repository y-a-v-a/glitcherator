package glitcherator;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

public class GlitchLicenseDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9104705890661094599L;

	public GlitchLicenseDialog() {
		super();
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setName("ScrollPane");
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		JTextPane license = new JTextPane();
		license.setContentType("text/html");
		license.setFont(new Font(license.getFont().getName(), Font.PLAIN, 10));
		license.setEditable(false);
		try {
			license.setPage(getClass().getResource(App.LICENSE));
		} catch (IOException e) {
			license.setText("Unable to open License file");
		}
		license.setAlignmentX(0.5f);
		license.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));
		
		scrollPane.setViewportView(license);
		
		setLayout((LayoutManager) new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		add(Box.createRigidArea(new Dimension(0, 10)));
		add(scrollPane);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("License");
		setLocationRelativeTo(null);
		
		add(Box.createRigidArea(new Dimension(0, 50)));
		
		JButton close = new JButton("Close");
		close.setName("Close");
		close.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                dispose();
            }
        });

        close.setAlignmentX(0.5f);
        add(close);
        setSize(800, 600);
	}

}