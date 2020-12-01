package utils;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class Utils {
	
	public static BufferedImage readImage( Object obj, String imageFileName) {
		//imagem auxiliar
		BufferedImage bi = null;
		
		URL imageSrc = null;
		imageSrc = obj.getClass().getClassLoader().getResource(imageFileName);
		try {
			bi = ImageIO.read(imageSrc);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Image couldn't be read");
		}
		
		return bi;
	}
}
