package cat.institutmarianao.sailing.ws.model;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.annotations.Formula;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/* Lombok */
@Data
@NoArgsConstructor
@SuperBuilder

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Departure implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String TZ = "Europe/Madrid";

	/* Lombok: */
	@EqualsAndHashCode.Include
	protected Long id;

	private TripType tripType;

	private Date date;

	private Date departure;

	/* Hibernate: */
	@Formula("(SELECT COALESCE(SUM(t.places), 0) " + "FROM trips t INNER JOIN actions a ON a.trip_id = t.id "
			+ "WHERE a.type <> '" + Action.CANCELLATION + "' AND " + "t.departure_id = id AND "
			+ "a.date = (SELECT MAX(last.date) FROM actions last WHERE last.trip_id = a.trip_id) " + ")")

	/* Lombok */
	@Setter(AccessLevel.NONE)

	private int bookedPlaces;
}
