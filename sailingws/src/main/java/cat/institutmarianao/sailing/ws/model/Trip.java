package cat.institutmarianao.sailing.ws.model;

import java.io.Serializable;
import java.util.List;

import org.hibernate.annotations.Formula;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
@JsonIgnoreProperties({ "hibernateLazyInitializer", "tracking" })
@Entity
@Table(name = "trips")
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

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, updatable = false)
	@NotNull(message = "ID cannot be null")
	@JsonProperty("id")
	private Long id;

	@Column(name = "client_username", length = 25, nullable = false)
	@NotEmpty(message = "Client username is required")
	@NotNull(message = "Client username cannot be null")
	@JsonProperty("client")
	@Size(max = 25, message = "Client username cannot be longer than 25 characters")
	private String client;

	@Column(name = "places", nullable = false)
	@NotNull(message = "Places cannot be null")
	@JsonProperty("places")
	private int places;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "departure_id", referencedColumnName = "id", nullable = false)
	@JsonProperty("departure")
	@JsonManagedReference
	private Departure departure;

	@OneToMany(mappedBy = "trip", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	@Singular("track")
	@JsonProperty("tracking")
	@JsonBackReference
	@JsonIgnore
	private List<Action> tracking;

	@Enumerated(EnumType.STRING)
	@Formula("(SELECT CASE a.type " + "  WHEN 'BOOKING' THEN 'RESERVED' " + "  WHEN 'RESCHEDULING' THEN 'RESCHEDULED' "
			+ "  WHEN 'CANCELLATION' THEN 'CANCELLED' " + "  WHEN 'DONE' THEN 'DONE' " + "  ELSE NULL END "
			+ "FROM actions a WHERE a.trip_id = id AND a.date = " + "  (SELECT MAX(last.date) FROM actions last "
			+ "   WHERE last.trip_id = a.trip_id))")
	@Setter(AccessLevel.NONE)
	@JsonProperty("status")
	@JsonIgnore
	private Status status;
}
