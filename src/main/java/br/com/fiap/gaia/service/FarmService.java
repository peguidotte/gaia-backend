package br.com.fiap.gaia.service;

import br.com.fiap.gaia.api.dto.FarmRequest;
import br.com.fiap.gaia.domain.model.Farm;
import br.com.fiap.gaia.repository.FarmRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FarmService {

    private final FarmRepository farmRepository;

    public FarmService(FarmRepository farmRepository) {
        this.farmRepository = farmRepository;
    }

    @Transactional(readOnly = true)
    public List<Farm> findAll() {
        return farmRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Farm findById(Long id) {
        return farmRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Propriedade nao encontrada: " + id));
    }

    @Transactional
    public Farm create(FarmRequest request) {
        return farmRepository.save(new Farm(request.name(), request.ownerName(), request.city(), request.state().toUpperCase()));
    }

    @Transactional
    public Farm update(Long id, FarmRequest request) {
        Farm farm = findById(id);
        farm.update(request.name(), request.ownerName(), request.city(), request.state().toUpperCase());
        return farm;
    }

    @Transactional
    public void delete(Long id) {
        Farm farm = findById(id);
        farmRepository.delete(farm);
    }
}
