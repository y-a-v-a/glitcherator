package glitcherator;

import java.awt.Component;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class GlitchChangeListener implements ChangeListener {

	@Override
	public void stateChanged(ChangeEvent e) {
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
		}
	}

}
