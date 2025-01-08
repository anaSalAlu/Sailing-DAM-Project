package cat.institutmarianao.sailing.ws.model;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/* Lombok */
@Data

@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

public class TripType implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final int MIN_DEPARTURE_HOUR = 6;
	public static final int MAX_DEPARTURE_HOUR = 14;
	public static final String GROUP = "GROUP";
	public static final String PRIVATE = "PRIVATE";

	public enum Category {
		GROUP, PRIVATE
	}

	@EqualsAndHashCode.Include
	private Long id;
	private String title;

	private Category category;

	private String description;

	private double price;

	private String departures;

	private int duration;

	private int maxPlaces;
}
