package cat.institutmarianao.sailing.ws.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cat.institutmarianao.sailing.ws.model.Trip;
import cat.institutmarianao.sailing.ws.model.Trip.Status;
import cat.institutmarianao.sailing.ws.model.TripType.Category;

public interface TripRepository extends JpaRepository<Trip, Long> {

	@Query("SELECT t FROM Trip t " +
		       "JOIN t.departure d " +
		       "JOIN d.tripType tp " +
		       "WHERE " +
		       "( :category IS NULL OR tp.category = :category ) AND " +
		       "( :status IS NULL OR t.status = :status ) AND " +
		       "( :clientUsername IS NULL OR t.client LIKE %:clientUsername% ) AND " +
		       "( :from IS NULL OR d.date >= :from ) AND " +
		       "( :to IS NULL OR d.date <= :to ) " +
		       "ORDER BY tp.category ASC ")
		List<Trip> findAllByFilters(
		       @Param("category") Category category, 
		       @Param("status") Status status, 
		       @Param("clientUsername") String clientUsername, 
		       @Param("from") Date from, 
		       @Param("to") Date to);
	
//	@Query("SELECT t FROM Trip t " +
//			"JOIN t.departure d" +
//		    "JOIN d.tripType tt" +
//		    "WHERE (:category IS NULL OR tt.category = :category) AND" +
//		    "(:status IS NULL OR t.status = :status) AND" +
//		    "(:clientUsername IS NULL OR t.clientUsername = :clientUsername) AND" +
//		    "(:durationFrom IS NULL OR t.duration >= :durationFrom ) AND " +
//			"(:durationTo IS NULL OR t.duration <= :durationTo )")
//		List<Trip> findAllByFilters(
//		       @Param("category") Category category, 
//		       @Param("status") Status status, 
//		       @Param("clientUsername") String clientUsername, 
//		       @Param("from") Date from, 
//		       @Param("to") Date to);
}
