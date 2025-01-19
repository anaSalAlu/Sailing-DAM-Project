package cat.institutmarianao.sailing.ws.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "actions")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXTERNAL_PROPERTY, property = "type")
@JsonSubTypes({ @JsonSubTypes.Type(value = Booking.class, name = "BOOKING"),
		@JsonSubTypes.Type(value = Rescheduling.class, name = "RESCHEDULING"),
		@JsonSubTypes.Type(value = Cancellation.class, name = "CANCELLATION"),
		@JsonSubTypes.Type(value = Done.class, name = "DONE") })
@Schema(oneOf = { Booking.class, Rescheduling.class, Cancellation.class, Done.class }, discriminatorProperty = "type")
public abstract class Action implements Serializable {

	private static final long serialVersionUID = 1L;

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
	@NotNull(message = "ID cannot be null")
	@JsonProperty("id")
	@Schema(example = "1")
	protected Long id;

	@NonNull
	@Enumerated(EnumType.STRING)
	@Column(name = "type", length = 31, insertable = false, updatable = false)
	@NotNull(message = "Action type is required")
	@JsonProperty("type")
	@Size(min = 3, max = 31, message = "Action type should be between 3 and 31 characters")
	protected Type type;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "performer_username", referencedColumnName = "username")
	@JsonProperty("performer_username")
	@Size(max = 25, message = "Performer username cannot be longer than 25 characters")
	protected User performer;

	@NonNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonProperty("date")
	@JsonIgnore
	protected Date date = new Date();

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "trip_id", referencedColumnName = "id")
	@NotNull(message = "Trip is required")
	@JsonProperty("trip")
	@JsonBackReference
	protected Trip trip;

	@JsonIgnore
	public String getInfo() {
		return "";
	}

}
