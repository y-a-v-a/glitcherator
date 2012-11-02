package glitcherator;

import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class GlitchChangeListener implements ChangeListener {

	@Override
	public void stateChanged(ChangeEvent e) {
		GlitchPanel gp = (GlitchPanel) App.getAppComponents().get("Glitchpanel");

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
				String value = s.toString();
				JLabel hexVal = (JLabel) App.getAppComponents().get("hexVal");
				hexVal.setText(value);
				gp.getGlitch().setHexValue(value);
				App.frame.repaint();
			}
		}
	}

}
