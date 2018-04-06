package edu.ben.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

@Controller
public class FileUploadController extends BaseController {

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String handleFormUpload(@RequestParam("file") MultipartFile file) throws IOException {

		if (!file.isEmpty()) {

			BufferedImage src = ImageIO.read(new ByteArrayInputStream(file.getBytes()));

			String name = file.getOriginalFilename();
			String extension = getFileExtension(file);

			// check file extension
			if (extension.equals("jpg") || extension.equals("jpeg") || extension.equals("png")
					|| extension.equals("gif")) {

				// String path = new
				// File("HoneyCombv3/src/main/webapp/resources/images").getAbsolutePath();

				String path = "C:/Users/" + System.getProperty("user.name")
						+ "/git/honeycombv4/HoneyCombv3/src/main/webapp/resources/images";

				path += File.separator + name;

				System.out.println("path: " + path);

				File imageFile = new File(path);

				ImageIO.write(src, extension, imageFile);
			}
		}

		return "redirect:home";
	}

	private static String getFileExtension(MultipartFile file) {

		String fileName = file.getOriginalFilename();

		if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {

			return fileName.substring(fileName.lastIndexOf(".") + 1);

		} else {

			return "";

		}
	}

	// /**
	// * Upload single file using Spring Controller
	// */
	// @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	// public @ResponseBody String uploadFileHandler(@RequestParam("name") String
	// name,
	// @RequestParam("file") MultipartFile file) {
	//
	// if (!file.isEmpty()) {
	// try {
	// byte[] bytes = file.getBytes();
	//
	// // Creating the directory to store file
	// String rootPath = System.getProperty("C:/HoneyComb");
	// File dir = new File(rootPath + File.separator + "images");
	//
	// if (!dir.exists())
	// dir.mkdirs();
	//
	// // Create the file on server
	// File serverFile = new File(dir.getAbsolutePath() + File.separator + name);
	// BufferedOutputStream stream = new BufferedOutputStream(new
	// FileOutputStream(serverFile));
	// stream.write(bytes);
	// stream.close();
	//
	// return "You successfully uploaded file=" + name;
	// } catch (Exception e) {
	// return "You failed to upload " + name + " => " + e.getMessage();
	// }
	// } else {
	// return "You failed to upload " + name + " because the file was empty.";
	// }
	// }

}
