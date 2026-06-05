package br.com.fiap.gaia.repository;

import br.com.fiap.gaia.domain.model.Field;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FieldRepository extends JpaRepository<Field, Long> {
    List<Field> findByFarmId(Long farmId);
}
