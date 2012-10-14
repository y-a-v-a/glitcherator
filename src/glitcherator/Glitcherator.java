package glitcherator;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;

import org.apache.commons.codec.binary.Hex;

public class Glitcherator {

	static final String VERSION = "Glitcherator 0.1";

	private long ctime;

	private BufferedImage image;

	private BufferedImage def_img;

	private String filename;

	private byte[] imageRaw;

	public Glitcherator() {
		this.ctime = new Date().getTime();
	}

	public Glitcherator(String filename) {
		this();
		this.filename = filename;
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
		this.def_img = null;
		
		char[] myChar = Hex.encodeHex(imageRaw, true);
		int max = myChar.length;
		Long x = new Long(Math.round(Math.random() * max));
		int k = Integer.parseInt(x.toString());

		// stay within bounds
		if ((k + 512) > max) {
			k -= 512;
		}

		for (int i = k; i < (k + 512); i += 2) {
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
		this.setDefImg(bi);
		bais = null;
		
		return this;
	}

	/**
	 * Export image to new file
	 * 
	 * @param String
	 *            target
	 * @return Glitcherator
	 */
	public Glitcherator export(String target) {
		try {
			File outputfile = new File(target);
			ImageIO.write(this.getDefImg(), "jpg", outputfile);
		} catch (IOException e) {
			System.out.println("Exception: " + e.getMessage());
		}
		return this;
	}

	public void refresh() {
		Runtime r = Runtime.getRuntime();
		r.gc();

		this.ctime = new Date().getTime();
		this.build();
	}

	public int getImgWidth() {
		return this.image.getWidth();
	}

	public int getImgHeight() {
		return this.image.getHeight();
	}

	public BufferedImage getDefImg() {
		return def_img;
	}

	private void setDefImg(BufferedImage bi) {
		this.def_img = bi;
	}

	public long getCtime() {
		return ctime;
	}

	public void setCtime(long ctime) {
		this.ctime = ctime;
	}
	
	public String getFilename() {
		return filename;
	}

	public String toString() {
		return "Cannot convert image to string";
	}


}
