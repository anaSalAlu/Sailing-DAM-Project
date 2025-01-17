package cat.institutmarianao.sailing.ws.service;

import cat.institutmarianao.sailing.ws.model.Action;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public interface ActionService {

	Action save(@NotNull @Valid Action action);
	
	boolean existsById(@NotNull Long id);
}
