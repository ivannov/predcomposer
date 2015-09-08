package com.nosoftskills.predcomposer.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.Objects;

@Entity
@XmlRootElement
public class Prediction implements Serializable {

    private static final long serialVersionUID = -5531972294380467582L;

    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Version
	private int version;

	@ManyToOne
	private User byUser;

	@ManyToOne
	private Game forGame;

    private String predictedResult;

    public Prediction() {
    }

    public Prediction(User byUser, Game forGame, String predictedResult) {
        this.byUser = byUser;
        this.forGame = forGame;
        this.predictedResult = predictedResult;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @XmlTransient
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public User getByUser() {
        return byUser;
    }

    public void setByUser(User byUser) {
        this.byUser = byUser;
    }

    public Game getForGame() {
        return forGame;
    }

    public void setForGame(Game forGame) {
        this.forGame = forGame;
    }

    public String getPredictedResult() {
        return predictedResult;
    }

    public void setPredictedResult(String predictedResult) {
        this.predictedResult = predictedResult;
    }

    @Override
    public String toString() {
        return "Prediction{" +
                "id=" + id +
                ", byUser=" + byUser +
                ", forGame=" + forGame +
                ", predictedResult='" + predictedResult + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Prediction))
            return false;
        Prediction that = (Prediction) o;
        return Objects.equals(byUser, that.byUser) &&
                Objects.equals(forGame, that.forGame) &&
                Objects.equals(predictedResult, that.predictedResult);
    }

    @Override
    public int hashCode() {
        return Objects.hash(byUser, forGame, predictedResult);
    }
}