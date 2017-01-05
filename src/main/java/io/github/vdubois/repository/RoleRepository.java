package io.github.vdubois.repository;

import io.github.vdubois.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Set;

/**
 * Created by vdubois on 04/01/17.
 */
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findOneByName(String name);

    Set<Role> findByNameIn(Collection<String> names);
}
