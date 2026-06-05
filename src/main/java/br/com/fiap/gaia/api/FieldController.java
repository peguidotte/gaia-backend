package br.com.fiap.gaia.api;

import br.com.fiap.gaia.api.dto.FieldRequest;
import br.com.fiap.gaia.api.dto.FieldResponse;
import br.com.fiap.gaia.service.FieldService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fields")
public class FieldController {

    private final FieldService fieldService;

    public FieldController(FieldService fieldService) {
        this.fieldService = fieldService;
    }

    @GetMapping
    public List<FieldResponse> findAll() {
        return fieldService.findAll().stream().map(FieldResponse::from).toList();
    }

    @GetMapping("/{id}")
    public FieldResponse findById(@PathVariable Long id) {
        return FieldResponse.from(fieldService.findById(id));
    }

    @PostMapping
    public ResponseEntity<FieldResponse> create(@RequestBody @Valid FieldRequest request) {
        FieldResponse response = FieldResponse.from(fieldService.create(request));
        return ResponseEntity.created(URI.create("/fields/" + response.id())).body(response);
    }

    @PutMapping("/{id}")
    public FieldResponse update(@PathVariable Long id, @RequestBody @Valid FieldRequest request) {
        return FieldResponse.from(fieldService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        fieldService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
