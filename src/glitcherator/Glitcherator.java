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
	
	private int chunkSize = (int) Math.pow(2, (int) Math.round(Math.random() * 10));

	private int chunkAmount = 4;
	
	private int[] chunkPositions = new int[4];

	private String hexValue = "5e";

	private char[] pseudoBuffer;

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
		initPseudoBuffer();
		setChunkAmount(chunkAmount);
	}
	
	private void initPseudoBuffer() {		
		pseudoBuffer = Hex.encodeHex(this.imageAsByteArray, true);
	}

	public Glitcherator build() {
		this.definiteImage = null;
		initPseudoBuffer();

		for (int t = 0; t < this.chunkAmount; t++) {
			
			// generate a value for replacement
			int chunkSize = this.chunkSize;
	
			// stay within bounds
			if ((chunkPositions[t] + chunkSize) > pseudoBuffer.length) {
				chunkPositions[t] -= chunkSize;
			}
	
			for (int i = chunkPositions[t]; i < (chunkPositions[t] + chunkSize); i += 2) {
				pseudoBuffer[i] = this.hexValue.toCharArray()[0];
				pseudoBuffer[i + 1] = this.hexValue.toCharArray()[1];
			}
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

	public int getChunkSize() {
		return chunkSize;
	}

	public void setChunkSize(int chunkSize) {
		this.chunkSize = chunkSize;
	}

	public void setChunkAmount(int value) {
		this.chunkAmount  = value;
		this.chunkPositions = new int[value];
		for (int t = 0; t < this.chunkAmount; t++) {
			int glitchPosition = (int) Math.round(Math.random() * this.pseudoBuffer.length);
			this.chunkPositions[t] = glitchPosition;
		}
	}

	public void setHexValue(String string) {
		this.hexValue = string;
	}

}
