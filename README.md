<p align="center">
  <img src="./assets/Gaia_Typo.png" alt="Gaia" width="420" />
</p>

<p align="center">
  <img alt="Java" src="https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" />
  <img alt="Spring Boot" src="https://img.shields.io/badge/Spring%20Boot-3-6DB33F?style=for-the-badge&logo=springboot&logoColor=white" />
  <img alt="PostgreSQL" src="https://img.shields.io/badge/PostgreSQL-16-4169E1?style=for-the-badge&logo=postgresql&logoColor=white" />
  <img alt="Docker" src="https://img.shields.io/badge/Docker-Compose-2496ED?style=for-the-badge&logo=docker&logoColor=white" />
  <img alt="Maven" src="https://img.shields.io/badge/Maven-3-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white" />
  <img alt="Swagger" src="https://img.shields.io/badge/Swagger-OpenAPI-85EA2D?style=for-the-badge&logo=swagger&logoColor=black" />
</p>

# Gaia Backend MVP

API REST para monitoramento agrícola e recomendações acionáveis do Gaia, uma plataforma de inteligência agrícola baseada em observação espacial.

## Integrantes

| Nome | RM |
|---|---|
| Leonardo Correa de Mello | RM 555573 |
| Felipe Soares Xavier | RM 556931 |
| Pedro Visconti Guidotte | RM 556630 |
| Herbert de Sousa Vilela | RM 555701 |
| Gabriel Figueira Flora | RM 556476 |

## Motivação do projeto

Decisões agrícolas dependem de informações ambientais confiáveis: condição da vegetação, comportamento climático, riscos próximos à propriedade e evolução do talhão ao longo do tempo. O problema é que esses dados costumam ficar espalhados em sistemas diferentes, com linguagem técnica e baixa orientação para a tomada de decisão do produtor.

O Gaia foi pensado para resolver esse ponto: transformar observações espaciais e ambientais em entendimento prático. A proposta não é criar apenas um painel com números brutos, nem uma aplicação de clima genérica. O objetivo é interpretar sinais relevantes e responder, de forma simples, duas perguntas centrais:

- O que está acontecendo na propriedade ou no talhão?
- O que merece atenção e qual ação deve ser priorizada?

Este backend representa o primeiro recorte técnico dessa visão. Ele modela propriedades rurais, talhões, observações satelitais, observações climáticas e recomendações agrícolas. A API usa dados simulados de forma determinística para demonstrar o fluxo completo sem depender de serviços externos, mantendo a entrega simples de executar e fácil de avaliar.

O foco estratégico do MVP é **monitoramento da saúde da vegetação e apoio à decisão por meio de observação espacial**. Por isso, a API não expõe apenas dados técnicos como NDVI ou chuva acumulada: ela também gera mensagens e recomendações em linguagem compreensível para produtores, como inspeção de vegetação, acompanhamento do talhão ou avaliação de irrigação.

## Arquitetura e fluxo

A arquitetura segue uma separação em camadas para demonstrar organização, testabilidade e desacoplamento:

- **Controllers REST** recebem as requisições HTTP e expõem os recursos da API.
- **DTOs** separam o contrato externo da API das entidades persistidas.
- **Services** concentram regras de aplicação, orquestração do fluxo e injeção de dependências.
- **Interfaces de domínio** definem contratos para pontuação de risco, geração de recomendações e fornecimento de dados simulados.
- **Entidades JPA** representam o modelo persistido no PostgreSQL.
- **Value Objects** encapsulam conceitos de domínio como coordenada, área, índice de vegetação e pontuação de risco.
- **Repositories JPA** cuidam da persistência sem expor detalhes de banco para a API.

O modelo de domínio foi estruturado para evidenciar conceitos de POO:

- `Observation` é uma classe abstrata para observações ambientais.
- `SatelliteObservation` e `ClimateObservation` especializam a observação por herança.
- O método `producerMessage()` é polimórfico e gera mensagens específicas para cada tipo de observação.
- `RecommendationEngine`, `ObservationScoringService` e `ClimateDataProvider` são interfaces injetadas por dependência.

### Fluxo funcional

```mermaid
flowchart TD
    A["Cliente REST / Swagger / Postman"] --> B["Controllers REST"]
    B --> C["Validação dos DTOs"]
    C --> D["Services de aplicação"]
    D --> E["Repositories JPA"]
    E --> F["PostgreSQL"]

    D --> G["ClimateDataProvider"]
    G --> H["Observações simuladas determinísticas"]
    H --> I["SatelliteObservation"]
    H --> J["ClimateObservation"]

    I --> K["ObservationScoringService"]
    J --> K
    K --> L["RiskScore"]

    D --> M["RecommendationEngine"]
    L --> M
    M --> N["Recommendation"]
    N --> E
```

### Fluxo de uso esperado

```mermaid
sequenceDiagram
    participant User as Cliente
    participant API as Gaia API
    participant DB as PostgreSQL
    participant Provider as Dados simulados
    participant Engine as Motor de recomendação

    User->>API: Criar propriedade rural
    API->>DB: Persistir Farm
    DB-->>API: Farm criada

    User->>API: Criar talhão vinculado à propriedade
    API->>DB: Persistir Field
    DB-->>API: Field criado

    User->>API: Criar observação SATELLITE ou CLIMATE
    API->>Provider: Gerar dados ambientais determinísticos
    Provider-->>API: Observação com risco calculável
    API->>DB: Persistir Observation

    User->>API: Gerar recomendação do talhão
    API->>DB: Buscar histórico de observações
    API->>Engine: Interpretar risco e contexto
    Engine-->>API: Recomendação acionável
    API->>DB: Persistir Recommendation
    API-->>User: Ação recomendada para o produtor
```

### Recursos da API

| Recurso | Responsabilidade |
|---|---|
| `/farms` | Cadastro e manutenção de propriedades rurais. |
| `/fields` | Cadastro e manutenção de talhões vinculados a propriedades. |
| `/observations` | Registro de observações satelitais e climáticas simuladas. |
| `/fields/{fieldId}/observations` | Consulta do histórico ambiental de um talhão. |
| `/recommendations/generate` | Geração de recomendação agrícola a partir das observações. |
| `/fields/{fieldId}/recommendations` | Consulta de recomendações já geradas para um talhão. |

## Como rodar

Pré-requisitos:

- Java 21
- Maven 3
- Docker Desktop

Suba o banco PostgreSQL:

```bash
docker compose up -d
```

Rode a API:

```bash
mvn spring-boot:run
```

Acesse:

- API: http://localhost:8080
- Swagger: http://localhost:8080/swagger-ui.html
- Health check: http://localhost:8080/actuator/health

Execute os testes automatizados:

```bash
mvn test
```

Também há uma coleção Postman no arquivo `gaia-api.postman_collection.json`, com uma sequência pronta para testar o fluxo completo da API.
