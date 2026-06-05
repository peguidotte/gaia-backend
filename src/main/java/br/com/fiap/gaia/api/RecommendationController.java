package br.com.fiap.gaia.api;

import br.com.fiap.gaia.api.dto.RecommendationResponse;
import br.com.fiap.gaia.service.RecommendationService;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class RecommendationController {

    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @PostMapping("/recommendations/generate")
    public ResponseEntity<RecommendationResponse> generate(@RequestParam Long fieldId) {
        RecommendationResponse response = RecommendationResponse.from(recommendationService.generate(fieldId));
        return ResponseEntity.created(URI.create("/fields/" + fieldId + "/recommendations")).body(response);
    }

    @GetMapping("/fields/{fieldId}/recommendations")
    public List<RecommendationResponse> findByField(@PathVariable Long fieldId) {
        return recommendationService.findByField(fieldId).stream().map(RecommendationResponse::from).toList();
    }
}
