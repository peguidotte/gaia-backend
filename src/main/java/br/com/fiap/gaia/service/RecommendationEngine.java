package br.com.fiap.gaia.service;

import br.com.fiap.gaia.domain.model.Field;
import br.com.fiap.gaia.domain.model.Observation;
import br.com.fiap.gaia.domain.model.Recommendation;
import java.util.List;

public interface RecommendationEngine {
    Recommendation generate(Field field, List<Observation> observations);
}
