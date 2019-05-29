package xml.booking.managers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xml.booking.model.Image;
import xml.booking.repositories.ImageRepository;

/**
* Generated by Spring Data Generator on 29/05/2019
*/
@Component
public class ImageManager {

	private ImageRepository imageRepository;

	@Autowired
	public ImageManager(ImageRepository imageRepository) {
		this.imageRepository = imageRepository;
	}

}
