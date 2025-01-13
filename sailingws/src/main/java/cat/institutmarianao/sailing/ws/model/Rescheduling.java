package cat.institutmarianao.sailing.ws.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
	
	@Column(name = "old_date")
	private Date oldDate;
	
	@Column(name = "old_departure")
	private Date oldDeparture;
	
	@Column(name = "new_date")
	private Date newDate;

	@Column(name = "new_departure")
	private Date newDeparture;
	
	@Override
	public String getInfo() {
		return this.reason;
	}
}
