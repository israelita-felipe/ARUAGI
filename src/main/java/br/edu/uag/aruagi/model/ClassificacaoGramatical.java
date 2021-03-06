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
 * ClassificacaoGramatical generated by hbm2java
 */
@Entity
@Table(name = "classificacao_gramatical", schema = "public"
)
public class ClassificacaoGramatical implements java.io.Serializable {

    private int id;
    private String nome;
    private Integer usuario;
    private Boolean status;
    private Set palavrasClassificadases = new HashSet(0);

    public ClassificacaoGramatical() {
    }

    public ClassificacaoGramatical(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public ClassificacaoGramatical(int id, String nome, Integer usuario, Boolean status, Set palavrasClassificadases) {
        this.id = id;
        this.nome = nome;
        this.usuario = usuario;
        this.status = status;
        this.palavrasClassificadases = palavrasClassificadases;
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

    @Column(name = "nome", nullable = false)
    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "classificacaoGramatical", targetEntity = PalavrasClassificadas.class)
    @Fetch(FetchMode.SUBSELECT)
    public Set getPalavrasClassificadases() {
        return this.palavrasClassificadases;
    }

    public void setPalavrasClassificadases(Set palavrasClassificadases) {
        this.palavrasClassificadases = palavrasClassificadases;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + this.id;
        hash = 79 * hash + (this.nome != null ? this.nome.hashCode() : 0);
        hash = 79 * hash + (this.usuario != null ? this.usuario.hashCode() : 0);
        hash = 79 * hash + (this.status != null ? this.status.hashCode() : 0);
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
        final ClassificacaoGramatical other = (ClassificacaoGramatical) obj;
        if (this.id != other.id) {
            return false;
        }
        if ((this.nome == null) ? (other.nome != null) : !this.nome.equals(other.nome)) {
            return false;
        }
        if (this.usuario != other.usuario && (this.usuario == null || !this.usuario.equals(other.usuario))) {
            return false;
        }
        if (this.status != other.status && (this.status == null || !this.status.equals(other.status))) {
            return false;
        }
        return true;
    }

}
