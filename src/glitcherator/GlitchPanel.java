package glitcherator;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

// is a panel
public class GlitchPanel extends JPanel {
	
	private static final int TOP_MARGIN = 10;

	// has an image
	private Glitcherator glitch;

	private static final long serialVersionUID = 7824240316897863142L;

	public GlitchPanel() {
		String filename = getClass().getResource(App.INIT_IMAGE).getFile();
		this.glitch = new Glitcherator(filename);
	}

	public void loadNewGlitch(String filename) {
		this.glitch = null;
		this.glitch = new Glitcherator(filename);
		this.repaint();
	}
	
	/**
	 * override from JPanel
	 */
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);

		int imageWidth = this.glitch.getImgWidth();
		int imageHeight = this.glitch.getImgHeight();
		
		this.setPreferredSize(new Dimension(imageWidth, imageHeight));

		int panelWidth = this.getWidth();
		int positionFromLeft = (panelWidth - imageWidth) / 2;
		graphics.drawImage(this.glitch.getDefImg(), positionFromLeft, TOP_MARGIN, null);
	}

	/**
	 * @return Glitcherator
	 */
	public Glitcherator getGlitch() {
		return glitch;
	}
}
