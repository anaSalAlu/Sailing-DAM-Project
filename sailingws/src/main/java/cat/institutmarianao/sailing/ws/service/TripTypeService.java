package cat.institutmarianao.sailing.ws.service;

import java.util.List;

import cat.institutmarianao.sailing.ws.model.User;
import cat.institutmarianao.sailing.ws.model.User.Role;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public interface TripTypeService {

	List<TripType> findAll(Category category, Double priceFrom, Double priceTo, Integer maxPlacesFrom, Integer maxPlacesTo, Integer durationFrom, Integer durationTo);

	TripType getById(@NotNull Long id);
}
