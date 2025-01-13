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

	/* Lombok: */
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // ID generado automáticamente
	private Long id;

	// Relación con la entidad TripType
	@ManyToOne(fetch = FetchType.LAZY) // Relación Many-to-One con TripType
	@JoinColumn(name = "trip_type_id", nullable = false)
	private TripType tripType;

	// Fecha de la salida
	@Column(name = "date", nullable = false)
	private Date date;

	// Hora de la salida
	@Column(name = "departure", nullable = false)
	private Date departure;

	/* Hibernate: */
	// Consulta personalizada para obtener las plazas reservadas
	@Formula("(SELECT COALESCE(SUM(t.places), 0) " + "FROM trips t INNER JOIN actions a ON a.trip_id = t.id "
			+ "WHERE a.type <> '" + Action.CANCELLATION + "' AND " + "t.departure_id = id AND "
			+ "a.date = (SELECT MAX(last.date) FROM actions last WHERE last.trip_id = a.trip_id) " + ")")
	@Setter(AccessLevel.NONE) // No queremos un setter para este campo, ya que es calculado
	private int bookedPlaces;
}