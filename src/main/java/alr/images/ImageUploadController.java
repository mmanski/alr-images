package alr.images;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ImageUploadController {

	@Autowired
	private ImageService imageService;

	@Autowired
	private SimpMessagingTemplate template;

	@MessageMapping("/imageMessages")
	@SendTo("/topic/images")
	public List<ImageModel> handleChangedDescription(ImageModel imageModelInput) {
		for (ImageModel imageModel : imageService.getImages()) {
			if (imageModel.getImagePath().equals(imageModelInput.getImagePath())) {
				imageModel.setImageDescription(imageModelInput.getImageDescription());
				break;
			}
		}
		return imageService.getImages();
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public ImageModel handleFileUpload(@RequestParam("file") MultipartFile file) {
		String name = UUID.randomUUID().toString();
		ImageModel imageModel = null;

		if (!file.isEmpty()) {
			try {
				File image = new File(name);
				Path path = Paths.get(ClassLoader.getSystemResource("static/img").getPath() + "/" + name);
				Files.write(path, file.getBytes());
				imageModel = new ImageModel(image);

				imageService.pushImage(imageModel);
				template.convertAndSend("/topic/images", imageService.getImages());

				return imageModel;

			} catch (IOException e) {
				return imageModel;
			}
		} else {
			return imageModel;
		}
	}

}
