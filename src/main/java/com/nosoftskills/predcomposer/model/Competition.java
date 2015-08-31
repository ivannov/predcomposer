package com.nosoftskills.predcomposer.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Version;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Competition implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Version
	private int version;

	@Column(nullable = false)
	private String name;

	private String description;

	@OneToMany(mappedBy = "competition", cascade = CascadeType.ALL)
	private Set<Game> games = new HashSet<>();

    public Competition() {
    }

    public Competition(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Game> getGames() {
        return games;
    }

    public void setGames(Set<Game> games) {
        this.games = games;
    }

    @Override
    public String toString() {
        return "Competition{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", games=" + games +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Competition))
            return false;
        Competition that = (Competition) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description);
    }
}