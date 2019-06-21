package xml.booking.managers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import xml.booking.dto.MessageDTO;
import xml.booking.model.Message;
import xml.booking.model.User;
import xml.booking.repositories.MessageRepository;

/**
* Generated by Spring Data Generator on 08/06/2019
*/
@Component
public class MessageManager {
	
	private MessageRepository messageRepository;

	@Autowired
	public MessageManager(MessageRepository messageRepository) {
		this.messageRepository = messageRepository;
	}

	public Page<MessageDTO> getAllMessages(Pageable page) {
		return messageRepository.findAll(page).map(new Function<Message, MessageDTO>() {
			@Override
			public MessageDTO apply(Message message) {
				MessageDTO messageDTO = new MessageDTO(message);
				return messageDTO;
			}
		});
	}
	
	public MessageDTO findOne(Long id) {
		Optional<Message> message = messageRepository.findById(id);
		if(message.isPresent()) {
			return new MessageDTO(message.get());
		}
		return null;
		
	}
	
	public Message save(MessageDTO messageDTO, User user) {
		if(messageDTO.getId() == null || !messageRepository.findById(messageDTO.getId()).isPresent()) {
			Message message = new Message();
			LocalDateTime ldt = LocalDateTime.now().plusDays(1);
			DateTimeFormatter formmat1 = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
			long millis = ldt.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
			message.setDate(millis);
			message.setUser(user);
			message.setValue(messageDTO.getValue());
			Message saved = messageRepository.save(message);
			return saved;
		}
		return null;
	}
	
	public Page<MessageDTO> findByUserId(Pageable page , Long id) {
		return messageRepository.findByUserId(page,id).map(new Function<Message, MessageDTO>() {
			@Override
			public MessageDTO apply(Message message) {
				MessageDTO messageDTO = new MessageDTO(message);
				return messageDTO;
			}
		});
	}
	
	public List<Message> getMessagesReservation(Long reservationId) {
		System.out.println(reservationId);
		
		return this.messageRepository.findReservationMessages(reservationId);
		
	}
}
