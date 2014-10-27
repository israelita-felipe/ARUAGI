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
 * LinksQuestao generated by hbm2java
 */
@Entity
@Table(name = "links_questao", schema = "public"
)
public class LinksQuestao implements java.io.Serializable {

    private LinksQuestaoId id;
    private ListaQuestao listaQuestao;
    private Postagem postagem;
    private Integer usuario;
    private Boolean status;

    @Column(name = "status")
    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public LinksQuestao() {
    }

    public LinksQuestao(LinksQuestaoId id, ListaQuestao listaQuestao, Postagem postagem) {
        this.id = id;
        this.listaQuestao = listaQuestao;
        this.postagem = postagem;
    }

    public LinksQuestao(LinksQuestaoId id, ListaQuestao listaQuestao, Postagem postagem, Integer usuario) {
        this.id = id;
        this.listaQuestao = listaQuestao;
        this.postagem = postagem;
        this.usuario = usuario;
    }

    @EmbeddedId
    @AttributeOverrides({
        @AttributeOverride(name = "listaQuestoes", column = @Column(name = "lista_questoes", nullable = false)),
        @AttributeOverride(name = "postagem", column = @Column(name = "postagem", nullable = false))})
    public LinksQuestaoId getId() {
        return this.id;
    }

    public void setId(LinksQuestaoId id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "lista_questoes", nullable = false, insertable = false, updatable = false)
    public ListaQuestao getListaQuestao() {
        return this.listaQuestao;
    }

    public void setListaQuestao(ListaQuestao listaQuestao) {
        this.listaQuestao = listaQuestao;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "postagem", nullable = false, insertable = false, updatable = false)
    public Postagem getPostagem() {
        return this.postagem;
    }

    public void setPostagem(Postagem postagem) {
        this.postagem = postagem;
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
        int hash = 3;
        hash = 53 * hash + (getId() != null ? getId().hashCode() : 0);
        hash = 53 * hash + (getListaQuestao() != null ? getListaQuestao().hashCode() : 0);
        hash = 53 * hash + (getPostagem() != null ? getPostagem().hashCode() : 0);
        hash = 53 * hash + (getUsuario() != null ? getUsuario().hashCode() : 0);
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
        final LinksQuestao other = (LinksQuestao) obj;
        if (getId() != other.getId() && (getId() == null || !getId().equals(other.getId()))) {
            return false;
        }
        if (getListaQuestao() != other.getListaQuestao() && (getListaQuestao() == null || !getListaQuestao().equals(other.getListaQuestao()))) {
            return false;
        }
        if (getPostagem() != other.getPostagem() && (getPostagem() == null || !getPostagem().equals(other.getPostagem()))) {
            return false;
        }
        if (getUsuario() != other.getUsuario() && (getUsuario() == null || !getUsuario().equals(other.getUsuario()))) {
            return false;
        }
        return true;
    }

}
