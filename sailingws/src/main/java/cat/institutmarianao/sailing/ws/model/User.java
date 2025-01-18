package cat.institutmarianao.sailing.ws.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Schema(oneOf = { Client.class, Admin.class }, discriminatorProperty = "role")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "role", discriminatorType = DiscriminatorType.STRING)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "role")
@JsonSubTypes({ @JsonSubTypes.Type(value = Client.class, name = "CLIENT"),
		@JsonSubTypes.Type(value = Admin.class, name = "ADMIN") })
public abstract class User implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String ADMIN = "ADMIN";
	public static final String CLIENT = "CLIENT";

	public enum Role {
		ADMIN, CLIENT
	}

	public static final int MIN_USERNAME = 2;
	public static final int MAX_USERNAME = 25;
	public static final int MIN_PASSWORD = 10;

	@EqualsAndHashCode.Include
	@Id
	@Column(name = "username")
	@Nonnull
	@JsonProperty("user_name")
	@NotNull
	@Size(min = MIN_USERNAME, max = MAX_USERNAME)
	@Schema(description = "Unique username of the user", example = "john_doe")
	protected String username;

	@Column(name = "password")
	@Nonnull
	@JsonProperty("password")
	@Size(min = MIN_PASSWORD, max = 255)
	@Schema(description = "Password of the user", example = "password123")
	protected String password;

	@Column(name = "role", insertable = false, updatable = false)
	@Nonnull
	@Enumerated(EnumType.STRING)
	@JsonProperty("role")
	@Schema(description = "Role of the user", allowableValues = { "ADMIN", "CLIENT" })
	protected Role role;

	@JsonIgnore
	public abstract String getInfo();

	@JsonIgnore
	public boolean isAdmin() {
		return false;
	}
}
