package br.com.fiap.gaia.domain.model;

import br.com.fiap.gaia.domain.vo.FieldArea;
import br.com.fiap.gaia.domain.vo.GeoPoint;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;

@Entity
@Table(name = "fields")
public class Field {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String crop;

    @Embedded
    private FieldArea area;

    @Embedded
    private GeoPoint centroid;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Farm farm;

    private OffsetDateTime createdAt = OffsetDateTime.now();

    protected Field() {
    }

    public Field(String name, String crop, FieldArea area, GeoPoint centroid, Farm farm) {
        this.name = name;
        this.crop = crop;
        this.area = area;
        this.centroid = centroid;
        this.farm = farm;
    }

    public void update(String name, String crop, FieldArea area, GeoPoint centroid, Farm farm) {
        this.name = name;
        this.crop = crop;
        this.area = area;
        this.centroid = centroid;
        this.farm = farm;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCrop() {
        return crop;
    }

    public FieldArea getArea() {
        return area;
    }

    public GeoPoint getCentroid() {
        return centroid;
    }

    public Farm getFarm() {
        return farm;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }
}
