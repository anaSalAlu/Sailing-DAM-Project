package cat.institutmarianao.sailing.ws.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cat.institutmarianao.sailing.ws.model.TripType;
import cat.institutmarianao.sailing.ws.model.TripType.Category;


public interface TripTypeRepository  extends JpaRepository<TripType, Long> {

	@Query("SELECT t FROM TripType t WHERE " +
			"( :category IS NULL OR t.category = :category ) AND " +
			"( :priceFrom IS NULL OR t.price >= :priceFrom ) AND " +
			"( :priceTo IS NULL OR t.price <= :priceTo ) AND " +
			"( :maxPlacesFrom IS NULL OR t.maxPlaces >= :maxPlacesFrom ) AND " +
			"( :maxPlacesTo IS NULL OR t.maxPlaces <= :maxPlacesTo ) AND " +
			"( :durationFrom IS NULL OR t.duration >= :durationFrom ) AND " +
			"( :durationTo IS NULL OR t.duration <= :durationTo )")
	List<TripType> findAllByFilters(
			@Param("category") Category category, 
			@Param("priceFrom") Double priceFrom, 
			@Param("priceTo") Double priceTo, 
			@Param("maxPlacesFrom") Integer maxPlacesFrom, 
			@Param("maxPlacesTo") Integer maxPlacesTo, 
			@Param("durationFrom") Integer durationFrom, 
			@Param("durationTo") Integer durationTo);
}
