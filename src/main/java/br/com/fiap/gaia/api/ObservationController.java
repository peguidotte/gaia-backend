package br.com.fiap.gaia.api;

import br.com.fiap.gaia.api.dto.ObservationRequest;
import br.com.fiap.gaia.api.dto.ObservationResponse;
import br.com.fiap.gaia.service.ObservationService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class ObservationController {

    private final ObservationService observationService;

    public ObservationController(ObservationService observationService) {
        this.observationService = observationService;
    }

    @GetMapping("/observations")
    public List<ObservationResponse> findAll() {
        return observationService.findAll().stream().map(ObservationResponse::from).toList();
    }

    @PostMapping("/observations")
    public ResponseEntity<ObservationResponse> create(@RequestBody @Valid ObservationRequest request) {
        ObservationResponse response = ObservationResponse.from(observationService.create(request));
        return ResponseEntity.created(URI.create("/observations/" + response.id())).body(response);
    }

    @GetMapping("/fields/{fieldId}/observations")
    public List<ObservationResponse> findByField(@PathVariable Long fieldId) {
        return observationService.findByField(fieldId).stream().map(ObservationResponse::from).toList();
    }
}
