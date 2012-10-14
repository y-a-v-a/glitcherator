package glitcherator;

import java.awt.Graphics;

import javax.swing.JPanel;

// is a panel
public class GlitchPanel extends JPanel {
	
	// has an image
	private Glitcherator glitch;

	private static final long serialVersionUID = 7824240316897863142L;
	
	public GlitchPanel() {
		this.glitch = new Glitcherator("toosad2.jpg");
	}
	
	public void loadNewGlitch(String filename) {
		this.glitch = null;
		this.glitch = new Glitcherator(filename);
		this.repaint();
	}

	/**
	 * override from JPanel
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		this.glitch.build();

		// center image on pane
		Integer fromLeft = new Integer(0);
		fromLeft = Integer
				.parseInt(String.valueOf(((1000 - this.glitch.getImgWidth()) / 2)));
		this.setBounds(fromLeft, 40, this.glitch.getImgWidth(), this.glitch.getImgHeight());

		g.drawImage(this.glitch.getDefImg(), 0, 0, null);
	}

	public Glitcherator getGlitch() {
		return glitch;
	}
	
	public void setGlitch(Glitcherator glitch) {
		this.glitch = glitch;
	}
	
}
