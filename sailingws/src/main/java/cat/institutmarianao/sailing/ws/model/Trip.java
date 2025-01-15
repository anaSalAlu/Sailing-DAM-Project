package cat.institutmarianao.sailing.ws.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.annotations.Formula;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonBackReference;
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

	// El campo id es la clave primaria de la tabla
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, updatable = false)
	@NotNull
	@JsonProperty("id") // Nombre del campo en el JSON
	private Long id;

	// Nombre del cliente (se traducirá)
	@Column(name = "client_username", length = 25, nullable = false)
	@NotEmpty
	@NotNull
	@JsonProperty("client") // Nombre del campo en el JSON
	private String client;

	// Mapa para manejar traducciones de 'status'
	private Map<String, String> statusTranslations = new HashMap<>();

	@JsonAnyGetter
	public Map<String, String> getStatusTranslations() {
		return statusTranslations;
	}

	@JsonAnySetter
	public void setStatusTranslations(String language, String translation) {
		statusTranslations.put(language, translation);
	}

	@JsonProperty("status_en")
	public String getStatusEn() {
		return statusTranslations.get("en");
	}

	@JsonProperty("status_es")
	public String getStatusEs() {
		return statusTranslations.get("es");
	}

	@JsonProperty("status_ca")
	public String getStatusCa() {
		return statusTranslations.get("ca");
	}

	@Column(name = "places", nullable = false)
	@NotNull
	@JsonProperty("places") // Nombre del campo en el JSON
	private int places;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "departure_id", referencedColumnName = "id", nullable = false)
	@NotNull
	@JsonProperty("departure") // Nombre del campo en el JSON
	@JsonManagedReference // Evita la serialización recursiva y maneja la relación correctamente
	private Departure departure;

	@OneToMany(mappedBy = "trip", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@Singular("track")
	@JsonProperty("tracking") // Nombre del campo en el JSON
	@JsonBackReference // Evita la serialización recursiva en el lado de la lista de acciones
	private List<Action> tracking;

	@Enumerated(EnumType.STRING)
	@Formula("(SELECT CASE a.type " + "  WHEN 'BOOKING' THEN 'RESERVED' " + "  WHEN 'RESCHEDULING' THEN 'RESCHEDULED' "
			+ "  WHEN 'CANCELLATION' THEN 'CANCELLED' " + "  WHEN 'DONE' THEN 'DONE' " + "  ELSE NULL END "
			+ "FROM actions a WHERE a.trip_id = id AND a.date = " + "  (SELECT MAX(last.date) FROM actions last "
			+ "   WHERE last.trip_id = a.trip_id))")
	@Setter(AccessLevel.NONE)
	@JsonProperty("status") // Nombre del campo en el JSON
	private Status status;
}
