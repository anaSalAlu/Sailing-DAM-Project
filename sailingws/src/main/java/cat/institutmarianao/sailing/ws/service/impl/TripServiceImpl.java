package cat.institutmarianao.sailing.ws.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import cat.institutmarianao.sailing.ws.exception.ForbiddenException;
import cat.institutmarianao.sailing.ws.exception.NotFoundException;
import cat.institutmarianao.sailing.ws.model.Action;
import cat.institutmarianao.sailing.ws.model.Trip;
import cat.institutmarianao.sailing.ws.model.Trip.Status;
import cat.institutmarianao.sailing.ws.model.TripType.Category;
import cat.institutmarianao.sailing.ws.repository.ActionRepository;
import cat.institutmarianao.sailing.ws.repository.TripRepository;
import cat.institutmarianao.sailing.ws.security.JwtUtils;
import cat.institutmarianao.sailing.ws.service.TripService;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import jakarta.validation.constraints.NotNull;

@Validated
@Service
public class TripServiceImpl implements TripService {

	@Autowired
	private TripRepository tripRepository;

	@Autowired
	private ActionRepository actionRepository;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private JwtUtils jwtUtils;

	@Override
	public List<Trip> findAll(Category category, Status status, String clientUsername, Date from, Date to) {
		return tripRepository.findAllByFilters(category, status, clientUsername, from, to);
	}

	@Override
	public Trip getById(@NotNull Long id) {
		if (!jwtUtils.isAdmin()) {
			throw new ForbiddenException(
					messageSource.getMessage("error.Forbidden.users.find", null, LocaleContextHolder.getLocale()));
		}

		return tripRepository.findById(id)
				.orElseThrow(() -> new NotFoundException(messageSource.getMessage("error.NotFound.resource.by.id",
						new String[] { "Id", id.toString() }, LocaleContextHolder.getLocale())));
	}

	@Override
	public List<Trip> getByUsername(@NotNull String username, Category category, Status status, Date from, Date to) {
		if (!jwtUtils.isAdmin()) {
			throw new ForbiddenException(
					messageSource.getMessage("error.Forbidden.users.find", null, LocaleContextHolder.getLocale()));
		}

		return tripRepository.findAllByFilters(category, status, username, from, to);
	}

//	@Override
//	public Trip save(@NotNull @Valid Trip trip) {
//		if (!(jwtUtils.isAdmin() || jwtUtils.isClient())) {
//			throw new ForbiddenException(
//					messageSource.getMessage("error.Forbidden.users.find", null, LocaleContextHolder.getLocale()));
//		}
//
//		if (tripRepository.existsById(trip.getId())) {
//			throw new ValidationException(messageSource.getMessage("error.UserService.username.exists",
//					new String[] { trip.getId().toString() }, LocaleContextHolder.getLocale()));
//		}
//
//		return tripRepository.saveAndFlush(trip);
//	}

	@Override
	public boolean existsById(@NotNull Long id) {
		return tripRepository.existsById(id);
	}

//	@Override
//	public Action saveAction(@NotNull @Valid Action action) {
//		if (!(jwtUtils.isAdmin() || jwtUtils.isClient())) {
//			throw new ForbiddenException(
//					messageSource.getMessage("error.Forbidden.users.find", null, LocaleContextHolder.getLocale()));
//		}
//
//		if (actionRepository.existsById(action.getId())) {
//			throw new ValidationException(messageSource.getMessage("error.UserService.username.exists",
//					new String[] { action.getId().toString() }, LocaleContextHolder.getLocale()));
//		}
//
//		return actionRepository.saveAndFlush(action);
//	}

	@Override
	public Trip save(@NotNull @Valid Trip trip) {
		if (!(jwtUtils.isAdmin() || jwtUtils.isClient())) {
			throw new ForbiddenException(
					messageSource.getMessage("error.Forbidden.users.find", null, LocaleContextHolder.getLocale()));
		}

		if (tripRepository.existsById(trip.getId())) {
			throw new ValidationException(messageSource.getMessage("error.UserService.username.exists",
					new String[] { trip.getId().toString() }, LocaleContextHolder.getLocale()));
		}

		return tripRepository.saveAndFlush(trip);
	}

	@Override
	public Action saveAction(@NotNull @Valid Action action) {
		// Verifica si el trip existe
		Trip trip = tripRepository.findById(action.getTrip().getId()).orElseThrow();

		// Asocia el trip a la acción
		action.setTrip(trip);

		// Guarda la acción
		return actionRepository.saveAndFlush(action);
	}

}
