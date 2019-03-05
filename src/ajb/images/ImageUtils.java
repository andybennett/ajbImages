package ajb.images;

import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.imageio.ImageIO;

import ajb.colours.ColourUtils;

public class ImageUtils {

	public static BufferedImage create(double width, double height) {

		GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice()
				.getDefaultConfiguration();
		BufferedImage img = gc.createCompatibleImage((int) width, (int) height, BufferedImage.TYPE_INT_ARGB);

		Graphics2D gr = (Graphics2D) img.getGraphics();

		Map<?, ?> desktopHints = (Map<?, ?>) Toolkit.getDefaultToolkit()
				.getDesktopProperty("awt.font.desktophints");

		if (desktopHints != null) {
			gr.setRenderingHints(desktopHints);
		}

		gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);		
		
		return img;

	}

	public BufferedImage getImage(String image) {

		BufferedImage result = null;

		try {

			ImageIO.read(this.getClass().getResourceAsStream(image));

		} catch (IOException e) {

			e.printStackTrace();

		}

		return result;

	}

	public static void save(BufferedImage image, String ext, String fileName) {

		File file = new File(fileName + "." + ext);

		try {

			ImageIO.write(image, ext, file);

		} catch (IOException e) {

			System.out.println("Write error for " + file.getPath() + ": " + e.getMessage());

		}

	}
	
	public static int countNonAlphaPixels(BufferedImage image) {

		int count = 0;

		for (int x = 0; x < image.getWidth(); x++) {

			for (int y = 0; y < image.getHeight(); y++) {

				if (image.getRGB(x, y) > 0) {

					count++;

				}
			}
		}
		
		return count;
	}	

	public static BufferedImage drawArea(Area area) {
		
		BufferedImage img = ImageUtils.create(area.getBounds2D().getMaxX(), area.getBounds2D().getMaxY());

		Graphics2D gr = (Graphics2D) img.getGraphics();

		gr.setColor(ColourUtils.gray);

		gr.fill(area);

		gr.dispose();		
		
		return img;		
		
	}
	
	public static Area createAreaFromImage(BufferedImage image) {

		Area result = new Area();
		
		for (int x = 0; x < image.getWidth(); x++) {

			for (int y = 0; y < image.getHeight(); y++) {

				if (image.getRGB(x, y) > 0) {

					result.add(new Area(new Rectangle2D.Double(x, y, 1, 1)));

				}
			}
		}
		
		return result;
	}	
}
