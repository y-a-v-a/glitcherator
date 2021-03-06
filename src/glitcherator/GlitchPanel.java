package glitcherator;

import java.awt.Dimension;
import java.awt.Graphics;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JPanel;

// is a panel
public class GlitchPanel extends JPanel {
	
	private static final int TOP_MARGIN = 10;

	// has an image
	private Glitcherator glitch;

	private static final long serialVersionUID = 7824240316897863142L;

	public GlitchPanel() {
		InputStream filename = getClass().getResourceAsStream(App.INIT_IMAGE);
		try {
			this.glitch = new Glitcherator(App.INIT_IMAGE, filename);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
