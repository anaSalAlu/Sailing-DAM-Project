package cat.institutmarianao.sailing.ws.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import cat.institutmarianao.sailing.ws.model.Action;
import cat.institutmarianao.sailing.ws.model.Trip;
import cat.institutmarianao.sailing.ws.model.Trip.Status;
import cat.institutmarianao.sailing.ws.model.TripType.Category;
import jakarta.validation.constraints.NotNull;

@Validated
@Service
public interface TripService {

	List<Trip> findAll(Category category, Status status, String clientUsername, Date from, Date to);

	Trip getById(@NotNull Long id);

	List<Trip> findAllByClientUsername(@NotNull String clientUsername);

	Trip save(@NotNull Trip trip);

	Action saveAction(Action action);
	
}
