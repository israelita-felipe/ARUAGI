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
 * PalavrasClassificadas generated by hbm2java
 */
@Entity
@Table(name = "palavras_classificadas", schema = "public"
)
public class PalavrasClassificadas implements java.io.Serializable {
    
    private PalavrasClassificadasId id;
    private PalavraLatim palavraLatim;
    private ClassificacaoGramatical classificacaoGramatical;
    private Integer usuario;
    private Boolean status;
    
    public PalavrasClassificadas() {
    }
    
    public PalavrasClassificadas(PalavrasClassificadasId id, PalavraLatim palavraLatim, ClassificacaoGramatical classificacaoGramatical) {
        this.id = id;
        this.palavraLatim = palavraLatim;
        this.classificacaoGramatical = classificacaoGramatical;
    }
    
    public PalavrasClassificadas(PalavrasClassificadasId id, PalavraLatim palavraLatim, ClassificacaoGramatical classificacaoGramatical, Integer usuario) {
        this.id = id;
        this.palavraLatim = palavraLatim;
        this.classificacaoGramatical = classificacaoGramatical;
        this.usuario = usuario;
    }
    
    @EmbeddedId
    @AttributeOverrides({
        @AttributeOverride(name = "palavraLatim", column = @Column(name = "palavra_latim", nullable = false)),
        @AttributeOverride(name = "classificacao", column = @Column(name = "classificacao", nullable = false))})
    public PalavrasClassificadasId getId() {
        return this.id;
    }
    
    public void setId(PalavrasClassificadasId id) {
        this.id = id;
    }
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "palavra_latim", nullable = false, insertable = false, updatable = false)
    public PalavraLatim getPalavraLatim() {
        return this.palavraLatim;
    }
    
    public void setPalavraLatim(PalavraLatim palavraLatim) {
        this.id.setPalavraLatim(palavraLatim.getId());
        this.palavraLatim = palavraLatim;
    }
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "classificacao", nullable = false, insertable = false, updatable = false)
    public ClassificacaoGramatical getClassificacaoGramatical() {
        return this.classificacaoGramatical;
    }
    
    public void setClassificacaoGramatical(ClassificacaoGramatical classificacaoGramatical) {
        this.id.setClassificacao(classificacaoGramatical.getId());
        this.classificacaoGramatical = classificacaoGramatical;
    }
    
    @Column(name = "usuario")
    public Integer getUsuario() {
        return this.usuario;
    }
    
    public void setUsuario(Integer usuario) {
        this.usuario = usuario;
    }
    
    public void setStatus(boolean status) {
        this.status = status;
    }
    
    @Column(name = "status")
    public Boolean getStatus() {
        return this.status;
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 97 * hash + (this.palavraLatim != null ? this.palavraLatim.hashCode() : 0);
        hash = 97 * hash + (this.classificacaoGramatical != null ? this.classificacaoGramatical.hashCode() : 0);
        hash = 97 * hash + (this.usuario != null ? this.usuario.hashCode() : 0);
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
        final PalavrasClassificadas other = (PalavrasClassificadas) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if (this.palavraLatim != other.palavraLatim && (this.palavraLatim == null || !this.palavraLatim.equals(other.palavraLatim))) {
            return false;
        }
        if (this.classificacaoGramatical != other.classificacaoGramatical && (this.classificacaoGramatical == null || !this.classificacaoGramatical.equals(other.classificacaoGramatical))) {
            return false;
        }
        if (this.usuario != other.usuario && (this.usuario == null || !this.usuario.equals(other.usuario))) {
            return false;
        }
        return true;
    }
    
}
