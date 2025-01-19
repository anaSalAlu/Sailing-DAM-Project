package cat.institutmarianao.sailing.ws.model;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.annotations.Formula;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
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
@Entity
@Table(name = "departures")
public class Departure implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String TZ = "Europe/Madrid";

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // ID generado automáticamente
	@Column(name = "id", nullable = false, updatable = false)
	@NotNull(message = "ID cannot be null")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "trip_type_id", nullable = false)
	@NotNull(message = "Trip type cannot be null")
	private TripType tripType;

	@Temporal(TemporalType.DATE)
	@Column(name = "date", nullable = false)
	@NotNull(message = "Date cannot be null")
	private Date date;

	@Temporal(TemporalType.TIME)
	@Column(name = "departure", nullable = false)
	@NotNull(message = "Departure time cannot be null")
	private Date departure;

	/* Hibernate: */
	@Formula("(SELECT COALESCE(SUM(t.places), 0) " + "FROM trips t INNER JOIN actions a ON a.trip_id = t.id "
			+ "WHERE a.type <> '" + Action.CANCELLATION + "' AND " + "t.departure_id = id AND "
			+ "a.date = (SELECT MAX(last.date) FROM actions last WHERE last.trip_id = a.trip_id) " + ")")
	@Setter(AccessLevel.NONE)
	private int bookedPlaces;
}