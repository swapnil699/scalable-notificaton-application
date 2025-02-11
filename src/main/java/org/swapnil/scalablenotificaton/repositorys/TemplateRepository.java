package org.swapnil.scalablenotificaton.repositorys;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.swapnil.scalablenotificaton.Models.Template;

import java.util.Optional;

@Repository
public interface TemplateRepository extends JpaRepository<Template, Long> {
    // Find a template by name
    Optional<Template> findByName(String name);
}
