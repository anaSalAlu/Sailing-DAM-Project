package cat.institutmarianao.sailing.ws.model;

import java.io.Serializable;

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
@DiscriminatorValue(User.CLIENT)
public class Client extends User implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final int MIN_FULL_NAME = 3;
	public static final int MAX_FULL_NAME = 100;

	@Column(name = "full_name")
	protected String fullName;

	@Column(name = "phone")
	protected Integer phone;
	
	@Override
	public String getInfo() {
		return this.fullName+" ("+this.phone+")";
	}
	
}
