package alr.images;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ImageService {

	private final List<ImageModel> images;

	public ImageService() {
		this.images = new ArrayList<>();
	}

	public void pushImage(ImageModel image) {
		images.add(image);
	}

	public List<ImageModel> getImages() {
		return images;
	}
}
