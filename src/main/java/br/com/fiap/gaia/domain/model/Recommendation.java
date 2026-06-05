package br.com.fiap.gaia.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;

@Entity
@Table(name = "recommendations")
public class Recommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Field field;

    private String priority;
    private String title;
    private String reason;
    private String action;
    private OffsetDateTime createdAt = OffsetDateTime.now();

    protected Recommendation() {
    }

    public Recommendation(Field field, String priority, String title, String reason, String action) {
        this.field = field;
        this.priority = priority;
        this.title = title;
        this.reason = reason;
        this.action = action;
    }

    public Long getId() {
        return id;
    }

    public Field getField() {
        return field;
    }

    public String getPriority() {
        return priority;
    }

    public String getTitle() {
        return title;
    }

    public String getReason() {
        return reason;
    }

    public String getAction() {
        return action;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }
}
