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

	// instance fields
	private long ctime;

	private String fileName;

	// buffers
	private BufferedImage originalImage;

	private BufferedImage definiteImage = null;

	private byte[] imageAsByteArray;
	
	private char[] pseudoBuffer;
	
	// glitch dynamics
	private int chunkSize = 512;//(int) Math.pow(2, (int) Math.round(Math.random() * 10));

	private int chunkAmount = 4;
	
	private int[] chunkPositions = null;

	private String hexValue = "5e";

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
			this.definiteImage = ImageIO.read(new File(this.fileName));
		} catch (IOException e) {
			System.out.println("Exception: " + e.getMessage());
		}

		// then read it raw in bytes
		try {
			File file = new File(this.fileName);
			FileImageInputStream fis = new FileImageInputStream(file);
			this.imageAsByteArray = new byte[(int) file.length()];
			fis.read(imageAsByteArray);
			fis.close();

		} catch (Exception e) {
			System.out.println("io error");
		}
		initPseudoBuffer();
	}
	
	private void initPseudoBuffer() {		
		pseudoBuffer = Hex.encodeHex(this.imageAsByteArray, true);
	}

	public void build() {
		this.initPseudoBuffer();
		int amount = getChunkAmount();
		int[] positions = getChunkPositions(amount);
		
		for (int t = 0; t < amount; t++) {			
			// generate a value for replacement
			int chunkSize = getChunkSize();
	
			// stay within bounds
			if ((positions[t] + chunkSize) > pseudoBuffer.length) {
				positions[t] -= chunkSize;
			}
	
			for (int i = positions[t]; i < (positions[t] + chunkSize); i += 2) {
				pseudoBuffer[i] = this.hexValue.toCharArray()[0];
				pseudoBuffer[i + 1] = this.hexValue.toCharArray()[1];
			}
		}
		
		byte[] newImageByteArray = null;

		try {
			newImageByteArray = Hex.decodeHex(pseudoBuffer);
			System.out.println();
		} catch (Exception e) {
			System.out.println("decoding mishap");
		}

		ByteArrayInputStream bais = new ByteArrayInputStream(newImageByteArray);

		BufferedImage bi = null;
		try {
			bi = ImageIO.read(bais);
			this.definiteImage = null;
			this.setDefImg(bi);
		} catch (IOException e) {
			System.out.println("cannot read image?!");
			this.definiteImage = this.originalImage;
		}
		
		bais = null;
	}

	/**
	 * Export image to new file
	 * 
	 * @param String target
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
		this.definiteImage = null;
		Runtime r = Runtime.getRuntime();
		r.gc();
		this.ctime = new Date().getTime();
	}

	public int getImgWidth() {
		return this.originalImage.getWidth();
	}

	public int getImgHeight() {
		return this.originalImage.getHeight();
	}

	public BufferedImage getDefImg() {
		if (definiteImage == null) {
//			System.out.println("DEF rebuild...");
			this.build();
		}
//		System.out.println("DEF cache...");
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

	public int getChunkSize() {
		return chunkSize;
	}

	public void setChunkSize(int chunkSize) {
		this.definiteImage = null;
		this.chunkSize = chunkSize;
	}

	public void setChunkAmount(int value) {
		this.definiteImage = null;
		this.chunkAmount  = value;
	}
	
	private int getChunkAmount() {
		return this.chunkAmount;
	}
	
	private int[] getChunkPositions(int value) {
		this.chunkPositions = new int[value];
		for (int t = 0; t < value; t++) {
			int glitchPosition = (int) Math.round(Math.random() * this.pseudoBuffer.length);
			this.chunkPositions[t] = glitchPosition;
		}
		return this.chunkPositions;
	}

	public void setHexValue(String string) {
		this.definiteImage = null;
		this.hexValue = string;
	}

}
