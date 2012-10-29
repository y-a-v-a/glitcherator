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

	private BufferedImage originalImage;

	private BufferedImage definiteImage;

	private String fileName;

	private byte[] imageAsByteArray;

	public Glitcherator() {
		this.ctime = new Date().getTime();
	}

	public Glitcherator(String filename) {
		this();
		this.fileName = filename;
		this.load();
	}

	private void load() {
		// first read original file
		try {
			this.originalImage = ImageIO.read(new File(this.fileName));
		} catch (IOException e) {
			System.out.println("Exception: " + e.getMessage());
		}

		// then read it raw in bytes
		try {
			File file = new File(this.fileName);
			FileImageInputStream fis = new FileImageInputStream(file);
			this.imageAsByteArray = new byte[(int) file.length()];
			fis.read(imageAsByteArray);

		} catch (Exception e) {
			System.out.println("io error");
		}

	}

	public Glitcherator build() {
		this.definiteImage = null;
		
		char[] pseudoBuffer = Hex.encodeHex(imageAsByteArray, true);
		int positionLimit = pseudoBuffer.length;
		int glitchPosition = (int) Math.round(Math.random() * positionLimit);
		
		// generate a value for replacement
		int exp = (int) Math.round(Math.random() * 10);
		int chunkSize = (int) Math.pow(2, exp);

		// stay within bounds
		if ((glitchPosition + chunkSize) > positionLimit) {
			glitchPosition -= chunkSize;
		}

		for (int i = glitchPosition; i < (glitchPosition + chunkSize); i += 2) {
			pseudoBuffer[i] = '5';
			pseudoBuffer[i + 1] = 'e';
		}
		byte[] newImageByteArray = null;

		try {
			newImageByteArray = Hex.decodeHex(pseudoBuffer);
		} catch (Exception e) {
			System.out.println("decoding mishap");
		}

		ByteArrayInputStream bais = new ByteArrayInputStream(newImageByteArray);

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
			File outputFile = new File(target);
			ImageIO.write(this.getDefImg(), "jpg", outputFile);
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
		return this.originalImage.getWidth();
	}

	public int getImgHeight() {
		return this.originalImage.getHeight();
	}

	public BufferedImage getDefImg() {
		return definiteImage;
	}

	private void setDefImg(BufferedImage bi) {
		this.definiteImage = bi;
	}

	public long getCtime() {
		return ctime;
	}

	public void setCtime(long ctime) {
		this.ctime = ctime;
	}
	
	public String getFilename() {
		return fileName;
	}

	public String toString() {
		return "Cannot convert image to string";
	}


}
