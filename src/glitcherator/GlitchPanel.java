package glitcherator;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

// is a panel
public class GlitchPanel extends JPanel {

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
	
	public void refreshGlitch() {
		this.glitch.build();
	}

	/**
	 * override from JPanel
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		refreshGlitch();
		this.setPreferredSize(new Dimension(this.glitch.getImgWidth(),
				this.glitch.getImgHeight() ));

		int left = (this.getWidth() - this.glitch.getImgWidth()) / 2; // posistion centered
		g.drawImage(this.glitch.getDefImg(), left, 0, null);
	}

	/**
	 * @return Glitcherator
	 */
	public Glitcherator getGlitch() {
		return glitch;
	}

	/**
	 * @param glitch
	 */
	public void setGlitch(Glitcherator glitch) {
		this.glitch = glitch;
	}

}
