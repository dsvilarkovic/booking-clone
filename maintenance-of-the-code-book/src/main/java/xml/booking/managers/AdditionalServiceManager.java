package xml.booking.managers;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import xml.booking.dto.CodeBookDTO;
import xml.booking.model.AdditionalService;
import xml.booking.repositories.AdditionalServiceRepository;

/**
* Generated by Spring Data Generator on 07/06/2019
*/
@Component
public class AdditionalServiceManager {

	private AdditionalServiceRepository additionalServiceRepository;

	@Autowired
	public AdditionalServiceManager(AdditionalServiceRepository additionalServiceRepository) {
		this.additionalServiceRepository = additionalServiceRepository;
	}
	
	public Page<CodeBookDTO> getAllAdditionalServices(Pageable page) {
		return additionalServiceRepository.findByDeleted(page,false).map(new Function<AdditionalService, CodeBookDTO>() {
			@Override
			public CodeBookDTO apply(AdditionalService service) {
				CodeBookDTO codeBookDTO = new CodeBookDTO(service);
				return codeBookDTO;
			}
		});
	}
	
	public CodeBookDTO findOne(Long id) {
		AdditionalService service = additionalServiceRepository.findByDeletedAndId(false, id);
		return (service == null) ? null : new CodeBookDTO(service);
	}
	
	public CodeBookDTO save(CodeBookDTO codeBook) {
		if(codeBook.getId()== null || !additionalServiceRepository.findById(codeBook.getId()).isPresent()) {
			AdditionalService service = additionalServiceRepository.save(new AdditionalService(codeBook));
			codeBook.setId(service.getId());
			return codeBook;
		}
		return null;
	}
	
	public boolean remove(Long id) {
		AdditionalService service = additionalServiceRepository.findByDeletedAndId(false, id);
		if(service!= null) {
			service.setDeleted(true);
			return (additionalServiceRepository.save(service) == null)?false:true;
		}
		return false;
	}
	
	public boolean update(CodeBookDTO codeBook, Long id) {
		AdditionalService service = additionalServiceRepository.findByDeletedAndId(false, id);
		if(service!= null) {
			service.setName(codeBook.getName());
			additionalServiceRepository.save(service);
			return true;
		}
		
		return false;
	}

}
