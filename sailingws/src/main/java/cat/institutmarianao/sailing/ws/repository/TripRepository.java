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
import jakarta.validation.constraints.NotNull;


public interface TripRepository  extends JpaRepository<Trip, Long> {

	@Query("SELECT t FROM Trip t JOIN t.tripType tt WHERE " +
	           "(:category IS NULL OR tt.category = :category) AND " +
	           "(:status IS NULL OR t.status = :status) AND " +
	           "(:clientUsername IS NULL OR t.client = :clientUsername) AND " +
	           "(:from IS NULL OR t.date >= :from) AND " +
	           "(:to IS NULL OR t.date <= :to)")
	    List<Trip> findAllByFilters(@Param("category") TripType.Category category,
	                                @Param("status") Trip.Status status,
	                                @Param("clientUsername") String clientUsername,
	                                @Param("from") Date from,
	                                @Param("to") Date to);

	    @Query("SELECT t FROM Trip t WHERE t.client = :clientUsername")
	    List<Trip> findByClientUsername(@Param("clientUsername") @NotNull String clientUsername);

}
