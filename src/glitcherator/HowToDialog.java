package glitcherator;

import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

public class HowToDialog extends JDialog {

	private static final long serialVersionUID = -2645033264885686180L;

	public HowToDialog() {
		super();
		StringBuffer sb = new StringBuffer();
		try {
			InputStream source = getClass().getResourceAsStream(App.HOW_TO);
			InputStreamReader isr = new InputStreamReader(source);
			BufferedReader in = new BufferedReader(isr);

			String str;
			while ((str = in.readLine()) != null) {
				sb.append(str);
			}
		} catch (IOException e1) {
			sb.append("Could not read 'How to' file.");
		}

		JLabel jl = new JLabel();
		jl.setText(sb.toString());
		jl.setAlignmentX(0.5f);
		jl.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));

		setLayout((LayoutManager) new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		add(Box.createRigidArea(new Dimension(0, 10)));
		add(jl);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("How to glitch...");
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
        setSize(400, 300);
        // visibility handled by GlitchActionHandler
	}
}
