package xml.booking.managers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xml.booking.model.Comment;
import xml.booking.repositories.CommentRepository;

/**
* Generated by Spring Data Generator on 18/05/2019
*/
@Component
public class CommentManager {

	private CommentRepository commentRepository;

	@Autowired
	public CommentManager(CommentRepository commentRepository) {
		this.commentRepository = commentRepository;
	}

}
