package cat.institutmarianao.sailing.ws.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Schema(oneOf = { Client.class, Admin.class }, discriminatorProperty = "role")
@Data
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "role")
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
	protected String username;

	@Column(name = "password")
	@Nonnull
	@JsonIgnore
	protected String password;

	@Column(name = "role")
	@Nonnull
	@Enumerated(EnumType.STRING)
	@JsonProperty("role")
	protected Role role;

	// Mapa para manejar traducciones de 'role'
	private Map<String, String> roleTranslations = new HashMap<>();

	@JsonAnyGetter
	public Map<String, String> getRoleTranslations() {
		return roleTranslations;
	}

	@JsonAnySetter
	public void setRoleTranslations(String language, String translation) {
		roleTranslations.put(language, translation);
	}

	@JsonProperty("role_en")
	public String getRoleEn() {
		return roleTranslations.get("en");
	}

	@JsonProperty("role_es")
	public String getRoleEs() {
		return roleTranslations.get("es");
	}

	@JsonProperty("role_ca")
	public String getRoleCa() {
		return roleTranslations.get("ca");
	}

	// Mapa para manejar traducciones de 'user_info'
	private Map<String, String> userInfo = new HashMap<>();

	@JsonAnyGetter
	public Map<String, String> getUserInfo() {
		return userInfo;
	}

	@JsonAnySetter
	public void setUserInfo(String language, String translation) {
		userInfo.put(language, translation);
	}

	@JsonProperty("user_info_en")
	public String getUserInfoEn() {
		return userInfo.get("en");
	}

	@JsonProperty("user_info_es")
	public String getUserInfoEs() {
		return userInfo.get("es");
	}

	@JsonProperty("user_info_ca")
	public String getUserInfoCa() {
		return userInfo.get("ca");
	}

	public boolean isAdmin() {
		return false;
	}
}
