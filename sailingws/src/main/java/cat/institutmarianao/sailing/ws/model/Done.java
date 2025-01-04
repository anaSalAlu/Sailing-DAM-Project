package cat.institutmarianao.sailing.ws.model;

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
@Table(name = "actions")
@DiscriminatorValue(Action.DONE)
public class Done extends Action {
	private static final long serialVersionUID = 1L;

	@Column(name = "comments")
	private String comments;
	
	@Override
	public String getInfo() {
		return this.comments;
	}
}
