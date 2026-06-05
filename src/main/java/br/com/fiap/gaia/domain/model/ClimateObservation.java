package br.com.fiap.gaia.domain.model;

import br.com.fiap.gaia.domain.vo.RiskScore;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.time.OffsetDateTime;

@Entity
@DiscriminatorValue("CLIMATE")
public class ClimateObservation extends Observation {

    private Double rainfallMm;
    private Double temperatureCelsius;
    private Double humidityPercent;

    protected ClimateObservation() {
    }

    public ClimateObservation(Field field, OffsetDateTime observedAt, Double rainfallMm, Double temperatureCelsius, Double humidityPercent, RiskScore riskScore) {
        super(field, observedAt, climateSummary(rainfallMm, temperatureCelsius), riskScore);
        this.rainfallMm = rainfallMm;
        this.temperatureCelsius = temperatureCelsius;
        this.humidityPercent = humidityPercent;
    }

    @Override
    public String producerMessage() {
        return "Chuva de %.1f mm, temperatura de %.1f C e umidade de %.0f%%.".formatted(rainfallMm, temperatureCelsius, humidityPercent);
    }

    private static String climateSummary(Double rainfallMm, Double temperatureCelsius) {
        if (rainfallMm < 5 && temperatureCelsius > 31) {
            return "Condicao seca com calor elevado";
        }
        if (rainfallMm > 40) {
            return "Chuva intensa recente";
        }
        return "Condicao climatica estavel";
    }

    public Double getRainfallMm() {
        return rainfallMm;
    }

    public Double getTemperatureCelsius() {
        return temperatureCelsius;
    }

    public Double getHumidityPercent() {
        return humidityPercent;
    }
}
