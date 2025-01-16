package cat.institutmarianao.sailing.ws.service;

import java.util.List;

import cat.institutmarianao.sailing.ws.model.TripType;
import cat.institutmarianao.sailing.ws.model.TripType.Category;
import jakarta.validation.constraints.NotNull;

public interface TripTypeService {

	List<TripType> findAll(Category category, Double priceFrom, Double priceTo, Integer maxPlacesFrom,
			Integer maxPlacesTo, Integer durationFrom, Integer durationTo);

	TripType getById(@NotNull Long id);
}
