package cat.institutmarianao.sailing.ws.service;

import java.util.Date;
import java.util.List;

import cat.institutmarianao.sailing.ws.model.Action;
import cat.institutmarianao.sailing.ws.model.Trip;
import cat.institutmarianao.sailing.ws.model.Trip.Status;
import cat.institutmarianao.sailing.ws.model.TripType.Category;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public interface TripService {

	List<Trip> findAll(Category category, Status status, String clientUsername, Date from, Date to);

	Trip getById(@NotNull Long id);
	
	List<Trip> getByUsername(@NotNull String username, Category category, Status status, Date from, Date to);

	Trip save(@NotNull @Valid Trip trip);

	Action saveAction(@NotNull @Valid Action action);

	boolean existsById(@NotNull Long id);
	
}
