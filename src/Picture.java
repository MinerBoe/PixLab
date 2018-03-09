import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.text.*;
import java.util.*;
import java.util.List; // resolves problem with java.awt.List and java.util.List

/**
 * A class that represents a picture. This class inherits from SimplePicture and
 * allows the student to add functionality to the Picture class.
 * 
 * @author Barbara Ericson ericson@cc.gatech.edu
 */
public class Picture extends SimplePicture {
	///////////////////// constructors //////////////////////////////////

	/**
	 * Constructor that takes no arguments
	 */
	public Picture() {
		/*
		 * not needed but use it to show students the implicit call to super()
		 * child constructors always call a parent constructor
		 */
		super();
	}

	/**
	 * Constructor that takes a file name and creates the picture
	 * 
	 * @param fileName
	 *            the name of the file to create the picture from
	 */
	public Picture(String fileName) {
		// let the parent class handle this fileName
		super(fileName);
	}

	/**
	 * Constructor that takes the width and height
	 * 
	 * @param height
	 *            the height of the desired picture
	 * @param width
	 *            the width of the desired picture
	 */
	public Picture(int height, int width) {
		// let the parent class handle this width and height
		super(width, height);
	}

	/**
	 * Constructor that takes a picture and creates a copy of that picture
	 * 
	 * @param copyPicture
	 *            the picture to copy
	 */
	public Picture(Picture copyPicture) {
		// let the parent class do the copy
		super(copyPicture);
	}

	/**
	 * Constructor that takes a buffered image
	 * 
	 * @param image
	 *            the buffered image to use
	 */
	public Picture(BufferedImage image) {
		super(image);
	}

	////////////////////// methods ///////////////////////////////////////

	/**
	 * Method to return a string with information about this picture.
	 * 
	 * @return a string with information about the picture such as fileName,
	 *         height and width.
	 */
	public String toString() {
		String output = "Picture, filename " + getFileName() + " height " + getHeight() + " width " + getWidth();
		return output;

	}

	/** Method to set the blue to 0 */
	public void zeroBlue() {
		Pixel[][] pixels = this.getPixels2D();
		for (Pixel[] rowArray : pixels) {
			for (Pixel pixelObj : rowArray) {
				pixelObj.setBlue(0);
			}
		}
	}

	/** Method to set the green and red to 0 */
	public void keepOnlyBlue() {

		Pixel[][] pixels = this.getPixels2D();
		for(Pixel[] rowArray : pixels) {
			for (Pixel pixelObj : rowArray) {
				pixelObj.setRed(0);
				pixelObj.setGreen(0);
			}
		}

	}

	/** Method to negate the image*/
	public void negate() {

		Pixel[][] pixels = this.getPixels2D();
		for(Pixel[] rowArray : pixels) {
			for (Pixel pixelObj : rowArray) {
				pixelObj.setRed(255-pixelObj.getRed());
				pixelObj.setGreen(255-pixelObj.getGreen());
				pixelObj.setBlue(255-pixelObj.getBlue());
			}
		}

	}

	/** Method to grayscale the image*/
	public void grayscale() {
		int i = 0;
		Pixel[][] pixels = this.getPixels2D();
		for(Pixel[] rowArray : pixels) {
			for (Pixel pixelObj : rowArray) {
				i = (pixelObj.getBlue() + pixelObj.getRed() + pixelObj.getGreen()) /3;
				pixelObj.setRed(i);
				pixelObj.setGreen(i);
				pixelObj.setBlue(i);
			}
		}

	}

	/** Method to fix the water of the image*/
	public void fixUnderwater() {

		Pixel[][] pixels = this.getPixels2D();
		for(Pixel[] rowArray : pixels) {
			for (Pixel pixelObj : rowArray) {
				
				pixelObj.setRed(pixelObj.getRed()*4);
				
			}
		}

	}


	/**
	 * Method that mirrors the picture around a vertical mirror in the center of
	 * the picture from left to right
	 */
	public void mirrorVertical() {
		Pixel[][] pixels = this.getPixels2D();
		Pixel leftPixel = null;
		Pixel rightPixel = null;
		int width = pixels[0].length;
		for (int row = 0; row < pixels.length; row++) {
			for (int col = 0; col < width / 2; col++) {
				leftPixel = pixels[row][col];
				rightPixel = pixels[row][width - 1 - col];
				rightPixel.setColor(leftPixel.getColor());
			}
		}
	}

	/** Mirror just part of a picture of a temple */
	public void mirrorTemple() {
		int mirrorPoint = 276;
		Pixel leftPixel = null;
		Pixel rightPixel = null;
		int count = 0;
		Pixel[][] pixels = this.getPixels2D();

		// loop through the rows
		for (int row = 27; row < 97; row++) {
			// loop from 13 to just before the mirror point
			for (int col = 13; col < mirrorPoint; col++) {

				leftPixel = pixels[row][col];
				rightPixel = pixels[row][mirrorPoint - col + mirrorPoint];
				rightPixel.setColor(leftPixel.getColor());
				count++;
			}
		}
		System.out.println(count);
	}


	/** Mirror the arms on the snowman */
	public void mirrorArms() {

		Pixel topPixel = null;
		Pixel bottomPixel = null;
		Pixel[][] pixels = this.getPixels2D();

		// Mirror left arm
		for(int row = 157; row < 191; row++) {

			for(int col = 103; col < 170; col++) {

				topPixel = pixels[row][col];
				bottomPixel = pixels[row+74][col-7];
				bottomPixel.setColor(topPixel.getColor());

			}
		}

		// Mirror right arm
		for(int row = 171; row < 196; row++) {

			for(int col = 239; col < 294; col++) {

				topPixel = pixels[row][col];
				bottomPixel = pixels[row+74][col+6];
				bottomPixel.setColor(topPixel.getColor());

			}
		}


	}

	/** Mirror just the gull on the beach */
	public void mirrorGull() {

		Pixel originalPixel = null;
		Pixel mirroredPixel = null;

		Pixel[][] pixels = this.getPixels2D();
		for(int row = 235; row < 321; row++) {

			for(int col = 238; col < 345; col++) {

				originalPixel = pixels[row][col];
				mirroredPixel = pixels[row-2][col-109];
				mirroredPixel.setColor(originalPixel.getColor());

			}
		}


	}

	/**
	 * copy from the passed fromPic to the specified startRow and startCol in
	 * the current picture
	 * 
	 * @param fromPic
	 *            the picture to copy from
	 * @param startRow
	 *            the start row to copy to
	 * @param startCol
	 *            the start col to copy to
	 */
	public void copy(Picture fromPic, int startRow, int startCol) {
		Pixel fromPixel = null;
		Pixel toPixel = null;
		Pixel[][] toPixels = this.getPixels2D();
		Pixel[][] fromPixels = fromPic.getPixels2D();
		for (int fromRow = 0, toRow = startRow; fromRow < fromPixels.length
				&& toRow < toPixels.length; fromRow++, toRow++) {
			for (int fromCol = 0, toCol = startCol; fromCol < fromPixels[0].length
					&& toCol < toPixels[0].length; fromCol++, toCol++) {
				fromPixel = fromPixels[fromRow][fromCol];
				toPixel = toPixels[toRow][toCol];
				toPixel.setColor(fromPixel.getColor());
			}
		}
	}

	public void copyPart(Picture fromPic, int startRow, int startCol, int imageStartRow, int imageStartCol, int imageEndRow, int imageEndCol) {
		Pixel fromPixel = null;
		Pixel toPixel = null;
		Pixel[][] toPixels = this.getPixels2D();
		Pixel[][] fromPixels = fromPic.getPixels2D();
		for (int fromRow = imageStartRow, toRow = startRow; fromRow < imageEndRow
				&& toRow < toPixels.length; fromRow++, toRow++) {
			for (int fromCol = imageStartCol, toCol = startCol; fromCol < imageEndCol
					&& toCol < toPixels[0].length; fromCol++, toCol++) {
				fromPixel = fromPixels[fromRow][fromCol];
				toPixel = toPixels[toRow][toCol];
				toPixel.setColor(fromPixel.getColor());
			}
		}
	}

	/** Method to create a collage of several pictures */
	public void createCollage() {
		Picture flower1 = new Picture("flower1.jpg");
		Picture flower2 = new Picture("flower2.jpg");
		this.copyPart(flower1, 0, 0, 25, 50, 100, 100);
		this.copyPart(flower2, 100, 0, 25, 50, 100, 100);
		this.copyPart(flower1, 200, 0, 25, 50, 100, 100);
		Picture flowerNoBlue = new Picture(flower2);
		flowerNoBlue.zeroBlue();
		this.copyPart(flowerNoBlue, 300, 0, 25, 50, 100, 100);
		this.copyPart(flower1, 400, 0, 25, 50, 100, 100);
		this.copyPart(flower2, 500, 0, 25, 50, 100, 100);
		this.mirrorVertical();
		this.write("collage.jpg");
	}

	/** Method to create a collage of several pictures */
	public void myCollage() {
		Picture seagull = new Picture("seagull.jpg");
		Picture smallSeagull = seagull.scale(0.25, 0.25);
		this.copy(smallSeagull, 0, 0);
		Picture seagullNoBlue = new Picture(smallSeagull);
		seagullNoBlue.zeroBlue();
		Picture seagullNegate = new Picture(smallSeagull);
		seagullNegate.negate();
		Picture seagullGrayscale = new Picture(smallSeagull);
		seagullGrayscale.grayscale();

		this.copy(seagullNegate, 100, 0);
		this.copy(seagullGrayscale, 200, 0);
		this.copy(seagullNoBlue, 300, 0);
		this.mirrorVertical();
		this.write("myCollage.jpg");
	}


	/**
	 * Method to show large changes in color
	 * 
	 * @param edgeDist
	 *            the distance for finding edges
	 */
	public void edgeDetection(int edgeDist) {
		Pixel leftPixel = null;
		Pixel rightPixel = null;
		Pixel topPixel = null;
		Pixel bottomPixel = null;
		Pixel[][] pixels = this.getPixels2D();
		Color rightColor = null;
		Color bottomColor = null;

		for (int row = 0; row < pixels.length - 1; row++) {
			for (int col = 0; col < pixels[0].length; col++) {
				topPixel = pixels[row][col];
				bottomPixel = pixels[row + 1][col];
				bottomColor = bottomPixel.getColor();
				if (topPixel.colorDistance(bottomColor) > edgeDist)
					topPixel.setColor(Color.BLACK);
				else
					topPixel.setColor(Color.WHITE);
			}
		}
	}

	public void edgeDetection2(int edgeDist) {
		Pixel leftPixel = null;
		Pixel rightPixel = null;
		Pixel topPixel = null;
		Pixel bottomPixel = null;
		Pixel[][] pixels = this.getPixels2D();
		Color rightColor = null;
		Color bottomColor = null;

		for (int row = 0; row < pixels.length - 1; row++) {
			for (int col = 0; col < pixels[0].length-1; col++) {
				topPixel = pixels[row][col+1];
				bottomPixel = pixels[row + 1][col];
				bottomColor = bottomPixel.getColor();
				if (topPixel.colorDistance(bottomColor) > edgeDist)
					topPixel.setColor(Color.BLACK);
				else
					topPixel.setColor(Color.WHITE);
			}
		}
	}

	/*
	 * Main method for testing - each class in Java can have a main method
	 */
	public static void main(String[] args) {
		Picture beach = new Picture("beach.jpg");
		beach.explore();
		beach.zeroBlue();
		beach.explore();
	}

} // this } is the end of class Picture, put all new methods before this
