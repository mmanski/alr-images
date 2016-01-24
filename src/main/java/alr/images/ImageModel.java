package alr.images;

import java.io.File;

public class ImageModel {

	private String imagePath;

	private String imageDescription = "Add description";

	public ImageModel() {

	}

	public ImageModel(File imageFile) {
		this.imagePath = imageFile.getPath();
	}

	public String getImagePath() {
		return imagePath;
	}

	public String getImageDescription() {
		return imageDescription;
	}

	public void setImageDescription(String imageDescription) {
		this.imageDescription = imageDescription;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

}
