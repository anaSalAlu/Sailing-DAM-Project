package cat.institutmarianao.sailing.ws.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/* Lombok */
@Data
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue(Action.RESCHEDULING)
public class Rescheduling extends Action {
	private static final long serialVersionUID = 1L;

	@Column(name = "reason")
	private String reason;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "old_date")
	private Date oldDate;
	
	@Temporal(TemporalType.TIME)
	@Column(name = "old_departure")
	private Date oldDeparture;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "new_date")
	private Date newDate;

	@Temporal(TemporalType.TIME)
	@Column(name = "new_departure")
	private Date newDeparture;
	
	@Override
	public String getInfo() {
		return this.reason;
	}
}
