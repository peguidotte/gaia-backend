package br.com.fiap.gaia.api;

import br.com.fiap.gaia.api.dto.FarmRequest;
import br.com.fiap.gaia.api.dto.FarmResponse;
import br.com.fiap.gaia.service.FarmService;
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
@RequestMapping("/farms")
public class FarmController {

    private final FarmService farmService;

    public FarmController(FarmService farmService) {
        this.farmService = farmService;
    }

    @GetMapping
    public List<FarmResponse> findAll() {
        return farmService.findAll().stream().map(FarmResponse::from).toList();
    }

    @GetMapping("/{id}")
    public FarmResponse findById(@PathVariable Long id) {
        return FarmResponse.from(farmService.findById(id));
    }

    @PostMapping
    public ResponseEntity<FarmResponse> create(@RequestBody @Valid FarmRequest request) {
        FarmResponse response = FarmResponse.from(farmService.create(request));
        return ResponseEntity.created(URI.create("/farms/" + response.id())).body(response);
    }

    @PutMapping("/{id}")
    public FarmResponse update(@PathVariable Long id, @RequestBody @Valid FarmRequest request) {
        return FarmResponse.from(farmService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        farmService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
