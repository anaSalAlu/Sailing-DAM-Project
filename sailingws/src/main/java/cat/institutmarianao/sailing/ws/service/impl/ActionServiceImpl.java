package cat.institutmarianao.sailing.ws.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import cat.institutmarianao.sailing.ws.exception.ForbiddenException;
import cat.institutmarianao.sailing.ws.model.Action;
import cat.institutmarianao.sailing.ws.repository.ActionRepository;
import cat.institutmarianao.sailing.ws.security.JwtUtils;
import cat.institutmarianao.sailing.ws.service.ActionService;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ActionServiceImpl implements ActionService{

	@Autowired
	private ActionRepository actionRepository;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@Override
	public Action save(@NotNull @Valid Action action) {
		if (!(jwtUtils.isAdmin() || jwtUtils.isClient())) 
			throw new ForbiddenException(messageSource.getMessage("error.Forbidden.users.find", null, LocaleContextHolder.getLocale()));
		
		if (actionRepository.existsById(action.getId()))
			throw new ValidationException (messageSource.getMessage("error.UserService.username.exists", new String[] { action.getId().toString() }, LocaleContextHolder.getLocale()));
		
		return actionRepository.saveAndFlush(action);
	}

	@Override
	public boolean existsById(@NotNull Long id) {
		return actionRepository.existsById(id);
	}

}
