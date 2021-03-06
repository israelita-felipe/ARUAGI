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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * AtividadeQuestao generated by hbm2java
 */
@Entity
@Table(name = "atividade_questao", schema = "public"
)
public class AtividadeQuestao implements java.io.Serializable {

    private int id;
    private TipoQuestao tipoQuestao;
    private boolean status;
    private String titulo;
    private Integer usuario;
    private String descricao;
    private Set listaQuestaos = new HashSet(0);

    public AtividadeQuestao() {
    }

    public AtividadeQuestao(int id, TipoQuestao tipoQuestao, boolean status) {
        this.id = id;
        this.tipoQuestao = tipoQuestao;
        this.status = status;
    }

    public AtividadeQuestao(int id, TipoQuestao tipoQuestao, boolean status, String titulo, Integer usuario, String descricao, Set listaQuestaos) {
        this.id = id;
        this.tipoQuestao = tipoQuestao;
        this.status = status;
        this.titulo = titulo;
        this.usuario = usuario;
        this.descricao = descricao;
        this.listaQuestaos = listaQuestaos;
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tipo_questao", nullable = false)
    public TipoQuestao getTipoQuestao() {
        return this.tipoQuestao;
    }

    public void setTipoQuestao(TipoQuestao tipoQuestao) {
        this.tipoQuestao = tipoQuestao;
    }

    @Column(name = "status", nullable = false)
    public boolean isStatus() {
        return this.status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Column(name = "titulo")
    public String getTitulo() {
        return this.titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    @Column(name = "usuario")
    public Integer getUsuario() {
        return this.usuario;
    }

    public void setUsuario(Integer usuario) {
        this.usuario = usuario;
    }

    @Column(name = "descricao")
    public String getDescricao() {
        return this.descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "atividadeQuestao", targetEntity = ListaQuestao.class)
    @Fetch(FetchMode.SUBSELECT)
    public Set getListaQuestaos() {
        return this.listaQuestaos;
    }

    public void setListaQuestaos(Set listaQuestaos) {
        this.listaQuestaos = listaQuestaos;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.id;
        hash = 29 * hash + (this.tipoQuestao != null ? this.tipoQuestao.hashCode() : 0);
        hash = 29 * hash + (this.status ? 1 : 0);
        hash = 29 * hash + (this.titulo != null ? this.titulo.hashCode() : 0);
        hash = 29 * hash + (this.usuario != null ? this.usuario.hashCode() : 0);
        hash = 29 * hash + (this.descricao != null ? this.descricao.hashCode() : 0);
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
        final AtividadeQuestao other = (AtividadeQuestao) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.tipoQuestao != other.tipoQuestao && (this.tipoQuestao == null || !this.tipoQuestao.equals(other.tipoQuestao))) {
            return false;
        }
        if (this.status != other.status) {
            return false;
        }
        if ((this.titulo == null) ? (other.titulo != null) : !this.titulo.equals(other.titulo)) {
            return false;
        }
        if (this.usuario != other.usuario && (this.usuario == null || !this.usuario.equals(other.usuario))) {
            return false;
        }
        if ((this.descricao == null) ? (other.descricao != null) : !this.descricao.equals(other.descricao)) {
            return false;
        }
        return true;
    }

}
