package glitcherator;

import java.awt.FileDialog;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.swing.JPanel;

import org.apache.commons.codec.binary.Hex;

public class Glitcherator extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	static final String VERSION = "Glitcherator 0.1";
	
	private static final boolean DEBUG = true;
	
	private long ctime;
	
	private BufferedImage image;
	
	private BufferedImage def_img;
	
	private Map<String, String> debug = new HashMap<String, String>();
	
	private String filename;
	
	private byte[] imageRaw;

	private Glitcherator() {
		this.ctime = new Date().getTime();
		this.debug.put("time", Long.toString(this.ctime));
	}
	
	public Glitcherator(String filename) {
		this();
		this.filename = filename;
		this.debug.put("file", this.filename);
		this.load();
	}
	
	private void load() {
		// first read original file
		try {
			this.image = ImageIO.read(new File(this.filename));
		} catch (IOException e) {
			System.out.println("Exception: " + e.getMessage());
		}
		
		// then read it raw in bytes
		try {
			File f = new File(this.filename);
			FileImageInputStream fis = new FileImageInputStream(f);
			this.imageRaw = new byte[(int) f.length()];
			fis.read(imageRaw);
			
		} catch (Exception e) {
			System.out.println("io error");
		}
		
	}
	
	public Glitcherator build() {
		char[] myChar = Hex.encodeHex(imageRaw, true);
		int max = myChar.length;
		Long x = new Long(Math.round(Math.random() * max));
		int k = Integer.parseInt(x.toString() );
		
		// stay within bounds
		if ((k + 512) > max) {
			k -= 512;
		}
		
		for(int i = k; i < (k + 512); i += 2) {
			myChar[i] = '5';
			myChar[i + 1] = 'e';
		}
		byte[] myArray = null;
		
		try {
			myArray = Hex.decodeHex(myChar);
		} catch (Exception e) {
			System.out.println("decoding mishap");
		}

		ByteArrayInputStream bais = new ByteArrayInputStream(myArray);
		
		BufferedImage bi = null;
		try {
			bi = ImageIO.read(bais);
		} catch (IOException e) {
			System.out.println("cannot read image?!");
		}
		this.def_img = bi;
		return this;
		
	}
	
	/**
	 * override from JPanel 
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		// center image on pane
		Integer fromLeft = new Integer(0);
		fromLeft = Integer.parseInt(String.valueOf(((1000 - this.getDefWidth()) / 2)));
		this.setBounds(fromLeft, 40, this.getDefWidth(), this.getDefHeight());
		
		g.drawImage(this.def_img, 0, 0, null);
	}
	
	public int getDefWidth() {
		return this.image.getWidth();
	}
	
	public int getDefHeight() {
		return this.image.getHeight();
	}
	
//	public Glitcherator show(JFrame frame) {
//		frame.setBounds(0, 0, this.def_img.getWidth(), this.def_img.getHeight() + 100);
//
//		JPanel jp = new JPanel();
//		jp.setBounds(0, 0, this.def_img.getWidth(), this.def_img.getHeight());
//		jp.setVisible(true);
//
//		frame.add(jp);
//		
//		Graphics2D g = (Graphics2D) jp.getGraphics();
//		g.drawImage(this.def_img, null, 0, 40);
//		g.dispose();
//		
//		frame.repaint(10000);
//
//		return this;
//	}
	
	/**
	 * Export image to new file
	 * @param String target
	 * @return Glitcherator
	 */
	public Glitcherator export(String target) {
		try {
			//String name = "img" + Long.toString(this.ctime) + ".jpg";
		    File outputfile = new File(target);//+ name);
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

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "Save") {
			String file = "img" + Long.toString(this.ctime) + ".jpg";
			FileDialog fd = new FileDialog(App.frame, null, FileDialog.SAVE);
			fd.setTitle("Save Glitched file...");
			fd.setAlwaysOnTop(true);
			fd.setFile(file);
			fd.setVisible(true);
			String filename = fd.getDirectory() + fd.getFile();
			this.export(filename);
		}
		if (e.getActionCommand() == "Refresh") {
			this.ctime = new Date().getTime();
			this.build();
			this.repaint();
		}
		if(e.getActionCommand() == "Open") {
			FileDialog fd = new FileDialog(App.frame, null, FileDialog.LOAD);
			fd.setVisible(true);
			String filename = fd.getDirectory() + fd.getFile();
			this.filename = filename;
			this.load();
			this.build();
			this.repaint();
		}
	}

}
