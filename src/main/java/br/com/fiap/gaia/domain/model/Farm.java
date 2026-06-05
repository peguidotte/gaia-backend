package br.com.fiap.gaia.domain.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "farms")
public class Farm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String ownerName;
    private String city;
    private String state;
    private OffsetDateTime createdAt = OffsetDateTime.now();

    @OneToMany(mappedBy = "farm", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Field> fields = new ArrayList<>();

    protected Farm() {
    }

    public Farm(String name, String ownerName, String city, String state) {
        this.name = name;
        this.ownerName = ownerName;
        this.city = city;
        this.state = state;
    }

    public void update(String name, String ownerName, String city, String state) {
        this.name = name;
        this.ownerName = ownerName;
        this.city = city;
        this.state = state;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }
}
