package br.com.fiap.gaia.service;

import br.com.fiap.gaia.domain.model.ClimateObservation;
import br.com.fiap.gaia.domain.model.Field;
import br.com.fiap.gaia.domain.model.SatelliteObservation;
import java.time.OffsetDateTime;

public interface ClimateDataProvider {
    SatelliteObservation satelliteObservationFor(Field field, OffsetDateTime observedAt);

    ClimateObservation climateObservationFor(Field field, OffsetDateTime observedAt);
}
