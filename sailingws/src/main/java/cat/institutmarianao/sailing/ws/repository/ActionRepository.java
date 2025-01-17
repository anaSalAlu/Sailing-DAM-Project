package cat.institutmarianao.sailing.ws.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import cat.institutmarianao.sailing.ws.model.Action;

public interface ActionRepository extends JpaRepository<Action, Long> {


}
