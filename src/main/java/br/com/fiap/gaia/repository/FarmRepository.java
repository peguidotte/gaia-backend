package br.com.fiap.gaia.repository;

import br.com.fiap.gaia.domain.model.Farm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FarmRepository extends JpaRepository<Farm, Long> {
}
