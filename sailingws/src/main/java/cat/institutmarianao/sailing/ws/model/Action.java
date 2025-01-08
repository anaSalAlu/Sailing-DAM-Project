package cat.institutmarianao.sailing.ws.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

/* Swagger */
@Schema(oneOf = { Booking.class, Rescheduling.class, Cancellation.class, Done.class }, discriminatorProperty = "type")
/* Lombok */
@Data
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "actions")
public abstract class Action implements Serializable {

	private static final long serialVersionUID = 1L;

	/* Values for type - MUST be constants */
	public static final String BOOKING = "BOOKING";
	public static final String RESCHEDULING = "RESCHEDULING";
	public static final String CANCELLATION = "CANCELLATION";
	public static final String DONE = "DONE";

	public enum Type {
		BOOKING, RESCHEDULING, CANCELLATION, DONE
	}

	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "id")
	@NotEmpty
	@JsonProperty("id")

	protected Long id;

	@NonNull
	@Enumerated(EnumType.STRING)
	@Column(name = "type", length = 31)
	@NotBlank
	@JsonProperty("type")
	protected Type type;

	@NonNull
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "performer_username", referencedColumnName = "username")
	@NotBlank
	@JsonProperty("performer_username")
	protected User performer;

	@NonNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonProperty("date")
	protected Date date = new Date();

	@NonNull
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "trip_id", referencedColumnName = "id")
	@NotBlank
	@JsonProperty("trip")
	protected Trip trip;

	public String getInfo() {
		return "";
	}

}
