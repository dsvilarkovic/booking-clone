package xml.booking.managers;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import xml.booking.dto.CodeBookDTO;
import xml.booking.model.AccommodationCategory;
import xml.booking.repositories.AccommodationCategoryRepository;

/**
* Generated by Spring Data Generator on 07/06/2019
*/
@Component
public class AccommodationCategoryManager {

	private AccommodationCategoryRepository accommodationCategoryRepository;

	@Autowired
	public AccommodationCategoryManager(AccommodationCategoryRepository accommodationCategoryRepository) {
		this.accommodationCategoryRepository = accommodationCategoryRepository;
	}

	
	public Page<CodeBookDTO> getAllAccommodationCategories(Pageable page) {
		return accommodationCategoryRepository.findByDeleted(page,false).map(new Function<AccommodationCategory, CodeBookDTO>() {
			@Override
			public CodeBookDTO apply(AccommodationCategory category) {
				CodeBookDTO codeBookDTO = new CodeBookDTO(category);
				return codeBookDTO;
			}
		});
	}
	
	public List<CodeBookDTO> getAllAccommodationCategories() {
		List<AccommodationCategory> accommodationCategoryList = accommodationCategoryRepository.findByDeleted(false);
		List<CodeBookDTO> dtoList = new ArrayList<CodeBookDTO>();
		
		for(AccommodationCategory act : accommodationCategoryList) {
			CodeBookDTO codeBookDTO = new CodeBookDTO(act);
			dtoList.add(codeBookDTO);
		}
			
		return dtoList;
	}
	
	
	public CodeBookDTO findOne(Long id) {
		AccommodationCategory category = accommodationCategoryRepository.findByDeletedAndId(false, id);
		return (category == null)? null: new CodeBookDTO(category) ;
	}
	
	public CodeBookDTO save(CodeBookDTO codeBook) {
		if(codeBook.getId() == null || !accommodationCategoryRepository.findById(codeBook.getId()).isPresent()) {
			AccommodationCategory saved = accommodationCategoryRepository.save(new AccommodationCategory(codeBook));
			codeBook.setId(saved.getId());
			return codeBook;
		}
		return null;
	}
	
	public boolean remove(Long id) {
		AccommodationCategory category = accommodationCategoryRepository.findByDeletedAndId(false, id);
		if(category!= null) {
			category.setDeleted(true);
			return (accommodationCategoryRepository.save(category) == null)?false:true;
		}
		return false;
	}
	
	public boolean update(CodeBookDTO codeBook, Long id) {
		AccommodationCategory category = accommodationCategoryRepository.findByDeletedAndId(false, id);
		if(category!= null) {
			category.setName(codeBook.getName());
			accommodationCategoryRepository.save(category);
			return true;
		}
		
		return false;
	}
	
	public CodeBookDTO findCategoryByAccommodationUnitId(Long accommodationUnitId) {
		AccommodationCategory category = accommodationCategoryRepository.findCategoryByAccommodationUnitId(accommodationUnitId);		
		return (category == null)? null: new CodeBookDTO(category) ;
	}
}
