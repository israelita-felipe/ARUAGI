package br.edu.uag.aruagi.model;
// Generated 09/08/2014 12:29:58 by Hibernate Tools 3.6.0

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * TempoVerbal generated by hbm2java
 */
@Entity
@Table(name = "tempo_verbal", schema = "public"
)
public class TempoVerbal implements java.io.Serializable {

    private int id;
    private String descricao;
    private Integer usuario;
    private Boolean status;
    private Set indicadorTempoVerbals = new HashSet(0);
    private Set indicadorPessoaGramaticals = new HashSet(0);
    private Set radicals = new HashSet(0);
    private Set questaoGramaticals = new HashSet(0);

    public TempoVerbal() {
    }

    public TempoVerbal(int id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public TempoVerbal(int id, String descricao, Integer usuario, Boolean status, Set indicadorTempoVerbals, Set indicadorPessoaGramaticals, Set radicals, Set questaoGramaticals) {
        this.id = id;
        this.descricao = descricao;
        this.usuario = usuario;
        this.status = status;
        this.indicadorTempoVerbals = indicadorTempoVerbals;
        this.indicadorPessoaGramaticals = indicadorPessoaGramaticals;
        this.radicals = radicals;
        this.questaoGramaticals = questaoGramaticals;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Column(name = "status")
    public Boolean getStatus() {
        return this.status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "tempoVerbal", targetEntity = IndicadorTempoVerbal.class)
    @Fetch(FetchMode.SUBSELECT)
    public Set getIndicadorTempoVerbals() {
        return this.indicadorTempoVerbals;
    }

    public void setIndicadorTempoVerbals(Set indicadorTempoVerbals) {
        this.indicadorTempoVerbals = indicadorTempoVerbals;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "tempoVerbal", targetEntity = IndicadorPessoaGramatical.class)
    @Fetch(FetchMode.SUBSELECT)
    public Set getIndicadorPessoaGramaticals() {
        return this.indicadorPessoaGramaticals;
    }

    public void setIndicadorPessoaGramaticals(Set indicadorPessoaGramaticals) {
        this.indicadorPessoaGramaticals = indicadorPessoaGramaticals;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "tempoVerbal", targetEntity = Radical.class)
    @Fetch(FetchMode.SUBSELECT)
    public Set getRadicals() {
        return this.radicals;
    }

    public void setRadicals(Set radicals) {
        this.radicals = radicals;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "tempoVerbal", targetEntity = QuestaoGramatical.class)
    @Fetch(FetchMode.SUBSELECT)
    public Set getQuestaoGramaticals() {
        return this.questaoGramaticals;
    }

    public void setQuestaoGramaticals(Set questaoGramaticals) {
        this.questaoGramaticals = questaoGramaticals;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + this.id;
        hash = 53 * hash + (this.descricao != null ? this.descricao.hashCode() : 0);
        hash = 53 * hash + (this.usuario != null ? this.usuario.hashCode() : 0);
        hash = 53 * hash + (this.status != null ? this.status.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {        
        return true;
    }

}
