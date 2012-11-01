package glitcherator;

import java.awt.Component;

import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JViewport;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class GlitchChangeListener implements ChangeListener {

	@Override
	public void stateChanged(ChangeEvent e) {
		Component[] cs = App.frame.getContentPane().getComponents();
		JScrollPane jsp = null;
		try {
			for (int i = 0; i < cs.length; i++) {
				if (cs[i].getName() == "ScrollPane") {
					jsp = (JScrollPane) cs[i];
				}
			}
			if (jsp == null) {
				throw new Exception("Cannot find panel!");
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		JViewport jvp = (JViewport) jsp.getComponent(0);
		GlitchPanel gp = (GlitchPanel) jvp.getComponent(0);

		JSlider source = (JSlider) e.getSource();
		if (source.getName() == "SizeSlider") {
			if (((source.getValue() % 64) == 0) && !source.getValueIsAdjusting()) {
				gp.getGlitch().setChunkSize(source.getValue());
				App.frame.repaint();
			}
		} else if (source.getName() == "AmountSlider") {
			if (((source.getValue() % 2) == 0) && !source.getValueIsAdjusting()) {
				gp.getGlitch().setChunkAmount(source.getValue());
				App.frame.repaint();
			}
		} else if (source.getName() == "HexSlider") {
			if (((source.getValue() % 1) == 0) && !source.getValueIsAdjusting()) {
				StringBuffer s = new StringBuffer();
				s.append(Integer.toHexString(source.getValue()));
				if(s.length() == 1) {
					s.append("0");
				}
				gp.getGlitch().setHexValue(s.toString());
				App.frame.repaint();
			}
		}
	}

}
