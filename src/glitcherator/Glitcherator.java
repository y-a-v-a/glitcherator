package glitcherator;

import java.awt.Graphics2D;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.imageio.ImageIO;

public class Glitcherator {
	private static final String VERSION = "Glitcherator 0.1";
	
	private static final boolean DEBUG = true;
	
	private long ctime;
	
	private BufferedImage image;
	
	private BufferedImage def_img;
	
	private Map<String, String> debug = new HashMap<String, String>();
	
	private String filename;

	private Glitcherator() {
		this.ctime = new Date().getTime();
		this.debug.put("time", Long.toString(this.ctime));
	}
	
	public Glitcherator(String filename) {
		this();
		this.filename = filename;
		this.debug.put("file", this.filename);
		try {
		    this.image = ImageIO.read(new File(this.filename));
		} catch (IOException e) {
			System.out.println("Exception: " + e.getMessage());
		}
		doDebug();
	}
	
	public Glitcherator build() {
		WritableRaster wr = this.image.getRaster();
		double[] data = {2d, 3d, 4d };
		int i = 0;
		for (i = 0; i < 100; i++) {
			wr.setPixel(i, i, data);
		}
		//wr.setPixel(100, 100, data);

		java.util.Hashtable<String, String> properties = new java.util.Hashtable<String, String>();
		properties.put("comment", "Created with Glitcherator");

		ColorModel cm = this.image.getColorModel();
		BufferedImage bi = new BufferedImage(cm, wr, false, properties);
				
		this.def_img = bi;
		return this;
	}
	
	public Glitcherator show(JFrame frame) {
		StringBuffer title = new StringBuffer();
		title.append(Glitcherator.VERSION);
		title.append(" - ");
		title.append(this.filename);
		title.append(" - y_a_v_a");
		frame.setTitle(title.toString());
		
		frame.setBounds(0, 0, this.def_img.getWidth(), this.def_img.getHeight());	
		frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
		
		frame.setVisible(true);
		Graphics2D g = (Graphics2D) frame.getRootPane().getGraphics();
		g.drawImage(this.def_img, null, 0, 0);
		g.dispose();
		
		frame.setLocationRelativeTo(null);
		return this;
	}
	
	/**
	 * Export image to new file
	 * @param String target
	 * @return Glitcherator
	 */
	public Glitcherator export(String target) {
		try {
			String name = "img" + Long.toString(this.ctime) + ".jpg";
		    File outputfile = new File(target + name);
		    ImageIO.write(this.def_img, "jpg", outputfile);
		} catch (IOException e) {
		    System.out.println("Exception: " + e.getMessage());
		}
		return this;
	}
	
	/**
	 * write message to standard output
	 * @param String msg
	 */
	private void log(String msg) {
		if (Glitcherator.DEBUG) {
			System.out.println(msg);
		}
	}
	
	/**
	 * Generate String from debug Hashmap
	 */
	private void doDebug() {
		StringBuffer sb = new StringBuffer();
		Set<String> keys = this.debug.keySet();
		for (String key : keys) {
			String val = this.debug.get(key);
			sb.append(key);
			sb.append(" : ");
			sb.append(val);
			sb.append("\n");
		}
		this.log(sb.toString());
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "Cannot convert image to string";
	}

}
