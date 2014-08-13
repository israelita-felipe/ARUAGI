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
 * IndicadorPessoaGramatical generated by hbm2java
 */
@Entity
@Table(name = "indicador_pessoa_gramatical", schema = "public"
)
public class IndicadorPessoaGramatical implements java.io.Serializable {

    private IndicadorPessoaGramaticalId id;
    private PessoaGramatical pessoaGramatical;
    private TempoVerbal tempoVerbal;
    private String descricao;
    private Integer usuario;
    private Boolean status;

    public IndicadorPessoaGramatical() {
    }

    public IndicadorPessoaGramatical(IndicadorPessoaGramaticalId id, PessoaGramatical pessoaGramatical, TempoVerbal tempoVerbal, String descricao) {
        this.id = id;
        this.pessoaGramatical = pessoaGramatical;
        this.tempoVerbal = tempoVerbal;
        this.descricao = descricao;
    }

    public IndicadorPessoaGramatical(IndicadorPessoaGramaticalId id, PessoaGramatical pessoaGramatical, TempoVerbal tempoVerbal, String descricao, Integer usuario) {
        this.id = id;
        this.pessoaGramatical = pessoaGramatical;
        this.tempoVerbal = tempoVerbal;
        this.descricao = descricao;
        this.usuario = usuario;
    }

    @EmbeddedId
    @AttributeOverrides({
        @AttributeOverride(name = "tempoVerbal", column = @Column(name = "tempo_verbal", nullable = false)),
        @AttributeOverride(name = "pessoaGramatical", column = @Column(name = "pessoa_gramatical", nullable = false))})
    public IndicadorPessoaGramaticalId getId() {
        return this.id;
    }

    public void setId(IndicadorPessoaGramaticalId id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pessoa_gramatical", nullable = false, insertable = false, updatable = false)
    public PessoaGramatical getPessoaGramatical() {
        return this.pessoaGramatical;
    }

    public void setPessoaGramatical(PessoaGramatical pessoaGramatical) {
        this.id.setPessoaGramatical(pessoaGramatical.getId());
        this.pessoaGramatical = pessoaGramatical;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tempo_verbal", nullable = false, insertable = false, updatable = false)
    public TempoVerbal getTempoVerbal() {
        return this.tempoVerbal;
    }

    public void setTempoVerbal(TempoVerbal tempoVerbal) {
        this.id.setTempoVerbal(tempoVerbal.getId());
        this.tempoVerbal = tempoVerbal;
    }

    @Column(name = "descricao", nullable = false)
    public String getDescricao() {
        return this.descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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
        hash = 53 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 53 * hash + (this.pessoaGramatical != null ? this.pessoaGramatical.hashCode() : 0);
        hash = 53 * hash + (this.tempoVerbal != null ? this.tempoVerbal.hashCode() : 0);
        hash = 53 * hash + (this.descricao != null ? this.descricao.hashCode() : 0);
        hash = 53 * hash + (this.usuario != null ? this.usuario.hashCode() : 0);
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
        final IndicadorPessoaGramatical other = (IndicadorPessoaGramatical) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if (this.pessoaGramatical != other.pessoaGramatical && (this.pessoaGramatical == null || !this.pessoaGramatical.equals(other.pessoaGramatical))) {
            return false;
        }
        if (this.tempoVerbal != other.tempoVerbal && (this.tempoVerbal == null || !this.tempoVerbal.equals(other.tempoVerbal))) {
            return false;
        }
        if ((this.descricao == null) ? (other.descricao != null) : !this.descricao.equals(other.descricao)) {
            return false;
        }
        if (this.usuario != other.usuario && (this.usuario == null || !this.usuario.equals(other.usuario))) {
            return false;
        }
        return true;
    }

}
