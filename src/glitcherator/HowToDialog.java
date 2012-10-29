package glitcherator;

import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;

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
