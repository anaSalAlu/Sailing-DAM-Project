package cat.institutmarianao.sailing.ws.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cat.institutmarianao.sailing.ws.model.Trip;
import cat.institutmarianao.sailing.ws.model.Trip.Status;
import cat.institutmarianao.sailing.ws.model.TripType;
import cat.institutmarianao.sailing.ws.model.TripType.Category;

public interface TripTypeRepository extends JpaRepository<TripType, Long> {

	@Query("SELECT tt FROM TripType tt WHERE " + "(:category IS NULL OR tt.category = :category) AND "
			+ "(:price IS NULL OR tt.price = :price) AND "
			+ "(:departureTime IS NULL OR tt.departures LIKE %:departureTime%)")
	List<TripType> findAllByFilters(@Param("category") Category category, @Param("price") Double price,
			@Param("departureTime") String departureTime);

	List<Trip> findAllByFilters(Category category, Status status, String clientUsername, Date from, Date to);
}
