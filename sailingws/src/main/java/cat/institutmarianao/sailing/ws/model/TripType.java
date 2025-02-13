package cat.institutmarianao.sailing.ws.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "trip_types")
@JsonIgnoreProperties({ "hibernateLazyInitializer" })
public class TripType implements Serializable {

	private static final long serialVersionUID = 1L;

	// Constantes para validaciones y categorías
	public static final int MIN_DEPARTURE_HOUR = 6;
	public static final int MAX_DEPARTURE_HOUR = 14;
	public static final String GROUP = "GROUP";
	public static final String PRIVATE = "PRIVATE";

	// Enum para la categoría de tipo de viaje
	public enum Category {
		GROUP, PRIVATE
	}

	// El campo id es la clave primaria de la tabla
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // ID generado automáticamente
	@EqualsAndHashCode.Include // Usado para la comparación en EqualsAndHashCode
	@JsonProperty("id")
	private Long id;

	// La categoría del viaje (GROUP o PRIVATE)
	@Enumerated(EnumType.STRING) // Guarda el valor como String en la base de datos
	@Column(name = "category", nullable = false)
	@JsonProperty("category")
	private Category category;

	// El precio del viaje
	@Column(name = "price", nullable = false)
	@JsonProperty("price")
	private double price;

	// Las horas de salida disponibles (por ejemplo, "9:30;13:30;17:30")
	@Column(name = "departures", length = 255)
	@JsonProperty("departures")
	private String departures;

	// Duración del viaje en horas
	@Column(name = "duration", nullable = false)
	@JsonProperty("duration")
	private int duration;

	// Número máximo de plazas disponibles para este tipo de viaje
	@Column(name = "max_places")
	@JsonProperty("max_places")
	private int maxPlaces;
}
