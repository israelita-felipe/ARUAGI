package br.edu.uag.aruagi.model;
// Generated 09/08/2014 12:29:58 by Hibernate Tools 3.6.0

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * TraduzFrase generated by hbm2java
 */
@Entity
@Table(name = "traduz_frase", schema = "public"
)
public class TraduzFrase implements java.io.Serializable {

    private TraduzFraseId id;
    private FraseLatim fraseLatim;
    private FrasePortugues frasePortugues;
    private Integer usuario;

    public TraduzFrase() {
    }

    public TraduzFrase(TraduzFraseId id, FraseLatim fraseLatim, FrasePortugues frasePortugues) {
        this.id = id;
        this.fraseLatim = fraseLatim;
        this.frasePortugues = frasePortugues;
    }

    public TraduzFrase(TraduzFraseId id, FraseLatim fraseLatim, FrasePortugues frasePortugues, Integer usuario) {
        this.id = id;
        this.fraseLatim = fraseLatim;
        this.frasePortugues = frasePortugues;
        this.usuario = usuario;
    }

    @EmbeddedId
    @AttributeOverrides({
        @AttributeOverride(name = "fraseLatim", column = @Column(name = "frase_latim", nullable = false)),
        @AttributeOverride(name = "frasePortugues", column = @Column(name = "frase_portugues", nullable = false))})
    public TraduzFraseId getId() {
        return this.id;
    }

    public void setId(TraduzFraseId id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "frase_latim", nullable = false, insertable = false, updatable = false)
    public FraseLatim getFraseLatim() {
        return this.fraseLatim;
    }

    public void setFraseLatim(FraseLatim fraseLatim) {
        this.fraseLatim = fraseLatim;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "frase_portugues", nullable = false, insertable = false, updatable = false)
    public FrasePortugues getFrasePortugues() {
        return this.frasePortugues;
    }

    public void setFrasePortugues(FrasePortugues frasePortugues) {
        this.frasePortugues = frasePortugues;
    }

    @Column(name = "usuario")
    public Integer getUsuario() {
        return this.usuario;
    }

    public void setUsuario(Integer usuario) {
        this.usuario = usuario;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 37 * hash + (this.fraseLatim != null ? this.fraseLatim.hashCode() : 0);
        hash = 37 * hash + (this.frasePortugues != null ? this.frasePortugues.hashCode() : 0);
        hash = 37 * hash + (this.usuario != null ? this.usuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TraduzFrase other = (TraduzFrase) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if (this.fraseLatim != other.fraseLatim && (this.fraseLatim == null || !this.fraseLatim.equals(other.fraseLatim))) {
            return false;
        }
        if (this.frasePortugues != other.frasePortugues && (this.frasePortugues == null || !this.frasePortugues.equals(other.frasePortugues))) {
            return false;
        }
        if (this.usuario != other.usuario && (this.usuario == null || !this.usuario.equals(other.usuario))) {
            return false;
        }
        return true;
    }

}
