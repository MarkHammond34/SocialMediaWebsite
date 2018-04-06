package edu.ben.util;

import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

public class FileUploadUtil {

	public static String uploadImage(MultipartFile file) throws IOException, IllegalArgumentException {

		if (!file.isEmpty()) {

			BufferedImage src = ImageIO.read(new ByteArrayInputStream(file.getBytes()));

			String name = file.getOriginalFilename();
			String extension = getFileExtension(file);

			// check file extension
			if (extension.equals("jpg") || extension.equals("jpeg") || extension.equals("png")
					|| extension.equals("gif")) {

				String path = "C:/Users/" + System.getProperty("user.name")
						+ "/git/honeycombv4/HoneyCombv3/src/main/webapp/resources/images";

				path += File.separator + name;

				System.out.println("path: " + path);

				File imageFile = new File(path);

				ImageIO.write(src, extension, imageFile);

				return name;
			} else {
				throw new IllegalArgumentException(
						"file is not an acceptable image type (use png, jpg, jpeg, or gif).");
			}
		}
		throw new IllegalArgumentException("file is empty");

	}

	private static String getFileExtension(MultipartFile file) {

		String fileName = file.getOriginalFilename();

		if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {

			return fileName.substring(fileName.lastIndexOf(".") + 1);

		} else {

			return "";

		}
	}

}
