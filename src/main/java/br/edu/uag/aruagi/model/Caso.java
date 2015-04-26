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
 * Caso generated by hbm2java
 */
@Entity
@Table(name = "caso", schema = "public"
)
public class Caso implements java.io.Serializable {

    private int id;
    private String descricao;
    private Integer usuario;
    private Boolean status;
    private Set questaoDeclinacaos = new HashSet(0);
    private Set casoAplicados = new HashSet(0);

    public Caso() {
    }

    public Caso(int id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public Caso(int id, String descricao, Integer usuario, Boolean status,Set questaoDeclinacaos, Set casoAplicados) {
        this.id = id;
        this.descricao = descricao;
        this.usuario = usuario;
        this.status = status;
        this.questaoDeclinacaos = questaoDeclinacaos;
        this.casoAplicados = casoAplicados;
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

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "caso", targetEntity = QuestaoDeclinacao.class)
    @Fetch(FetchMode.SUBSELECT)
    public Set getQuestaoDeclinacaos() {
        return this.questaoDeclinacaos;
    }

    public void setQuestaoDeclinacaos(Set questaoDeclinacaos) {
        this.questaoDeclinacaos = questaoDeclinacaos;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "caso", targetEntity = CasoAplicado.class)
    @Fetch(FetchMode.SUBSELECT)
    public Set getCasoAplicados() {
        return this.casoAplicados;
    }

    public void setCasoAplicados(Set casoAplicados) {
        this.casoAplicados = casoAplicados;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this.id;
        hash = 59 * hash + (this.descricao != null ? this.descricao.hashCode() : 0);
        hash = 59 * hash + (this.usuario != null ? this.usuario.hashCode() : 0);
        hash = 59 * hash + (this.status != null ? this.status.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {        
        return true;
    }

    @Override
    public String toString() {
        return String.valueOf(getId());
    }
}
