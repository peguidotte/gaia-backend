package br.com.fiap.gaia.domain;

import br.com.fiap.gaia.domain.vo.GeoPoint;
import br.com.fiap.gaia.domain.vo.RiskScore;
import br.com.fiap.gaia.domain.vo.VegetationIndex;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ValueObjectTest {

    @Test
    void validatesGeoPointRange() {
        assertThatThrownBy(() -> new GeoPoint(-91.0, -47.0))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void classifiesVegetationStatus() {
        assertThat(new VegetationIndex(0.72).status()).isEqualTo("Vegetacao saudavel");
        assertThat(new VegetationIndex(0.50).status()).isEqualTo("Vegetacao estavel");
        assertThat(new VegetationIndex(0.30).status()).isEqualTo("Vegetacao sob atencao");
    }

    @Test
    void classifiesRiskLevel() {
        assertThat(new RiskScore(20).level()).isEqualTo("BAIXO");
        assertThat(new RiskScore(55).level()).isEqualTo("MEDIO");
        assertThat(new RiskScore(85).level()).isEqualTo("ALTO");
    }
}
