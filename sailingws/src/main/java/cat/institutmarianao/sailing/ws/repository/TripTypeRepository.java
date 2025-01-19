package cat.institutmarianao.sailing.ws.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cat.institutmarianao.sailing.ws.model.TripType;
import cat.institutmarianao.sailing.ws.model.TripType.Category;

public interface TripTypeRepository extends JpaRepository<TripType, Long> {

	@Query("SELECT tt FROM TripType tt WHERE " + "(:category IS NULL OR tt.category = :category) AND "
			+ "(:priceFrom IS NULL OR tt.price >= :priceFrom) AND " + "(:priceTo IS NULL OR tt.price <= :priceTo) AND "
			+ "(:maxPlacesFrom IS NULL OR tt.maxPlaces >= :maxPlacesFrom) AND "
			+ "(:maxPlacesTo IS NULL OR tt.maxPlaces <= :maxPlacesTo) AND "
			+ "(:durationFrom IS NULL OR tt.duration >= :durationFrom) AND "
			+ "(:durationTo IS NULL OR tt.duration <= :durationTo)")
	List<TripType> findAllByFilters(@Param("category") Category category, @Param("priceFrom") Double priceFrom,
			@Param("priceTo") Double priceTo, @Param("maxPlacesFrom") Integer maxPlacesFrom,
			@Param("maxPlacesTo") Integer maxPlacesTo, @Param("durationFrom") Integer durationFrom,
			@Param("durationTo") Integer durationTo);

}
