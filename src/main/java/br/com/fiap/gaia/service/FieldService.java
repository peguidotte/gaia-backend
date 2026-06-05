package br.com.fiap.gaia.service;

import br.com.fiap.gaia.api.dto.FieldRequest;
import br.com.fiap.gaia.domain.model.Farm;
import br.com.fiap.gaia.domain.model.Field;
import br.com.fiap.gaia.domain.vo.FieldArea;
import br.com.fiap.gaia.domain.vo.GeoPoint;
import br.com.fiap.gaia.repository.FieldRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FieldService {

    private final FieldRepository fieldRepository;
    private final FarmService farmService;

    public FieldService(FieldRepository fieldRepository, FarmService farmService) {
        this.fieldRepository = fieldRepository;
        this.farmService = farmService;
    }

    @Transactional(readOnly = true)
    public List<Field> findAll() {
        return fieldRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Field findById(Long id) {
        return fieldRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Talhao nao encontrado: " + id));
    }

    @Transactional
    public Field create(FieldRequest request) {
        Farm farm = farmService.findById(request.farmId());
        return fieldRepository.save(toEntity(request, farm));
    }

    @Transactional
    public Field update(Long id, FieldRequest request) {
        Field field = findById(id);
        Farm farm = farmService.findById(request.farmId());
        field.update(request.name(), request.crop(), new FieldArea(request.hectares()), new GeoPoint(request.latitude(), request.longitude()), farm);
        return field;
    }

    @Transactional
    public void delete(Long id) {
        Field field = findById(id);
        fieldRepository.delete(field);
    }

    private Field toEntity(FieldRequest request, Farm farm) {
        return new Field(request.name(), request.crop(), new FieldArea(request.hectares()), new GeoPoint(request.latitude(), request.longitude()), farm);
    }
}
