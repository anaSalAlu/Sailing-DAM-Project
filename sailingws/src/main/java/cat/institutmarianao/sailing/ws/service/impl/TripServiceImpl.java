package cat.institutmarianao.sailing.ws.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import cat.institutmarianao.sailing.ws.exception.NotFoundException;
import cat.institutmarianao.sailing.ws.model.Action;
import cat.institutmarianao.sailing.ws.model.Trip;
import cat.institutmarianao.sailing.ws.model.Trip.Status;
import cat.institutmarianao.sailing.ws.model.TripType.Category;
import cat.institutmarianao.sailing.ws.repository.TripRepository;
import cat.institutmarianao.sailing.ws.service.TripService;
import jakarta.validation.constraints.NotNull;

@Validated
@Service
public class TripServiceImpl implements TripService {

	@Autowired
	private TripRepository tripRepository;

	@Autowired
	private MessageSource messageSource;

	@Override
	public List<Trip> findAll(Category category, Status status, String clientUsername, Date from, Date to) {
		return tripRepository.findAllByFilters(category, status, clientUsername, from, to);
	}

	@Override
	public Trip getById(@NotNull Long id) {
		return tripRepository.findById(id).orElseThrow(() -> new NotFoundException(messageSource.getMessage("error.NotFound.resource.by.id", new String[] { "Id", id.toString() }, LocaleContextHolder.getLocale())));
	}

	@Override
	public List<Trip> findAllByClientUsername(@NotNull String clientUsername) {
		return tripRepository.findByClientUsername(clientUsername);
	}

	@Override
	public Trip save(@NotNull Trip trip) {
		return tripRepository.save(trip);
	}

	@Override
	public Action saveAction(Action action) {
		return null;
	}
	
	

}
