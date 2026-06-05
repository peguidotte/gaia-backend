package br.com.fiap.gaia.config;

import br.com.fiap.gaia.api.dto.FarmRequest;
import br.com.fiap.gaia.api.dto.FieldRequest;
import br.com.fiap.gaia.domain.model.Field;
import br.com.fiap.gaia.domain.model.Farm;
import br.com.fiap.gaia.repository.FarmRepository;
import br.com.fiap.gaia.service.FarmService;
import br.com.fiap.gaia.service.FieldService;
import br.com.fiap.gaia.service.ObservationService;
import br.com.fiap.gaia.service.RecommendationService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DemoDataSeeder {

    @Bean
    CommandLineRunner seedDemoData(
            FarmRepository farmRepository,
            FarmService farmService,
            FieldService fieldService,
            ObservationService observationService,
            RecommendationService recommendationService
    ) {
        return args -> {
            if (farmRepository.count() > 0) {
                return;
            }

            Farm farm = farmService.create(new FarmRequest("Fazenda Boa Esperanca", "Ana Guidotte", "Ribeirao Preto", "SP"));
            Field soyField = fieldService.create(new FieldRequest("Talhao Norte", "Soja", 42.5, -21.1775, -47.8103, farm.getId()));
            Field cornField = fieldService.create(new FieldRequest("Talhao Sul", "Milho", 28.0, -21.1840, -47.8050, farm.getId()));

            observationService.generateDemoPair(soyField.getId());
            observationService.generateDemoPair(cornField.getId());
            recommendationService.generate(soyField.getId());
            recommendationService.generate(cornField.getId());
        };
    }
}
