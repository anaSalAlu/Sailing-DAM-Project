package cat.institutmarianao.sailing.ws.model;

import java.io.Serializable;
import java.util.List;

import org.hibernate.annotations.Formula;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;
import lombok.experimental.SuperBuilder;

/* Lombok */
@Data
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Trip implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final int MAX_DESCRIPTION = 500;

	public static final String RESERVED = "RESERVED";
	public static final String RESCHEDULED = "RESCHEDULED";
	public static final String CANCELLED = "CANCELLED";
	public static final String DONE = "DONE";

	public enum Status {
		RESERVED, RESCHEDULED, CANCELLED, DONE
	}

	/* Lombok */
	@EqualsAndHashCode.Include
	private Long id;

	private Client client;

	private int places;

	private Departure departure;
	
	/* Lombok */
	@Singular("track") 
	private List<Action> tracking;

	/* JPA */
	@Enumerated(EnumType.STRING) // Stored as string
	/* Hibernate */
	@Formula("(SELECT CASE a.type "
			+ "  WHEN '" + Action.BOOKING + "' THEN '" + Trip.RESERVED + "' " 
			+ "  WHEN '" + Action.RESCHEDULING + "' THEN '" + Trip.RESCHEDULED + "' "
			+ "  WHEN '" + Action.CANCELLATION + "' THEN '" + Trip.CANCELLED + "' "
			+ "  WHEN '" + Action.DONE + "' THEN '" + Trip.DONE + "' "
			+ "  ELSE NULL END "
			+ "FROM actions a WHERE a.trip_id = id AND a.date = "
			+ "  (SELECT MAX(last.date) FROM actions last "
			+ "   WHERE last.trip_id = a.trip_id)"
			+ ")")
	// Lombok
	@Setter(AccessLevel.NONE)
	private Status status;
	
}
