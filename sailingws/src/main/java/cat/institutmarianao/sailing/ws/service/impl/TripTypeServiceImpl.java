package cat.institutmarianao.sailing.ws.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import cat.institutmarianao.sailing.ws.exception.ForbiddenException;
import cat.institutmarianao.sailing.ws.exception.NotFoundException;
import cat.institutmarianao.sailing.ws.model.TripType;
import cat.institutmarianao.sailing.ws.model.TripType.Category;
import cat.institutmarianao.sailing.ws.repository.TripTypeRepository;
import cat.institutmarianao.sailing.ws.security.JwtUtils;
import cat.institutmarianao.sailing.ws.service.TripTypeService;
import jakarta.validation.constraints.NotNull;

@Validated
@Service
public class TripTypeServiceImpl implements TripTypeService {

	@Autowired
	private TripTypeRepository tripTypeRepository;

	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@Override
	public List<TripType> findAll(Category category, Double priceFrom, Double priceTo, Integer maxPlacesFrom,
			Integer maxPlacesTo, Integer durationFrom, Integer durationTo) {		
		
		return tripTypeRepository.findAllByFilters(category, priceFrom, priceTo, maxPlacesFrom, maxPlacesTo, durationFrom, durationTo);
	}

	@Override
	public TripType getById(@NotNull Long id) {
		
		return tripTypeRepository.findById(id).orElseThrow(() -> new NotFoundException(messageSource.getMessage("error.NotFound.resource.by.id", new String[] { "Id", id.toString() }, LocaleContextHolder.getLocale())));
	}

}
