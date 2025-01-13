package cat.institutmarianao.sailing.ws.model;

import java.io.Serializable;

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
@DiscriminatorValue(User.ADMIN)
public class Admin extends User implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Override
	public String getInfo() {
		return "";
	}
	
	@Override
	public boolean isAdmin() {
		return true;
	}
}
