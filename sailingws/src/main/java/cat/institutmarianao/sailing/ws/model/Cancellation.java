package cat.institutmarianao.sailing.ws.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
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
@DiscriminatorValue(Action.CANCELLATION)
@Schema(description = "Cancellation action")
public class Cancellation extends Action {
	private static final long serialVersionUID = 1L;

	@Column(name = "reason")
	private String reason;

	@Override
	public String getInfo() {
		return this.reason;
	}
}
