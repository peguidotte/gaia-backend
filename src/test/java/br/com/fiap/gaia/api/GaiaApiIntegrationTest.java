package br.com.fiap.gaia.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class GaiaApiIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void createsFarmFieldObservationsAndRecommendation() throws Exception {
        String farmLocation = mockMvc.perform(post("/farms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "Fazenda Primavera",
                                  "ownerName": "Marina Silva",
                                  "city": "Campinas",
                                  "state": "SP"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andReturn()
                .getResponse()
                .getHeader("Location");

        Long farmId = Long.valueOf(farmLocation.substring(farmLocation.lastIndexOf('/') + 1));

        String fieldLocation = mockMvc.perform(post("/fields")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "Talhao Leste",
                                  "crop": "Cafe",
                                  "hectares": 18.5,
                                  "latitude": -22.90,
                                  "longitude": -47.06,
                                  "farmId": %d
                                }
                                """.formatted(farmId)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.farmId").value(farmId))
                .andReturn()
                .getResponse()
                .getHeader("Location");

        Long fieldId = Long.valueOf(fieldLocation.substring(fieldLocation.lastIndexOf('/') + 1));

        mockMvc.perform(post("/observations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "fieldId": %d,
                                  "type": "SATELLITE",
                                  "observedAt": "2026-06-05T10:00:00Z"
                                }
                                """.formatted(fieldId)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.type").value("SATELLITE"))
                .andExpect(jsonPath("$.producerMessage", notNullValue()));

        mockMvc.perform(post("/observations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "fieldId": %d,
                                  "type": "CLIMATE",
                                  "observedAt": "2026-06-05T11:00:00Z"
                                }
                                """.formatted(fieldId)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.type").value("CLIMATE"));

        mockMvc.perform(get("/fields/{fieldId}/observations", fieldId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

        mockMvc.perform(post("/recommendations/generate").param("fieldId", String.valueOf(fieldId)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.priority", notNullValue()))
                .andExpect(jsonPath("$.action", notNullValue()));
    }
}
