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

/**
 * Usuario generated by hbm2java
 */
@Entity
@Table(name = "usuario", schema = "public"
)
public class Usuario implements java.io.Serializable {

    private int id;
    private Usuario usuario;
    private NivelAcesso nivelAcesso;
    private String nome;
    private String senha;
    private String login;
    private String observacoes;
    private Boolean status;
    private Set usuarios = new HashSet(0);

    public Usuario() {
    }

    public Usuario(int id, Usuario usuario, NivelAcesso nivelAcesso, String nome, String senha, String login) {
        this.id = id;
        this.usuario = usuario;
        this.nivelAcesso = nivelAcesso;
        this.nome = nome;
        this.senha = senha;
        this.login = login;
    }

    public Usuario(int id, Usuario usuario, NivelAcesso nivelAcesso, String nome, String senha, String login, String observacoes, Boolean status, Set usuarios) {
        this.id = id;
        this.usuario = usuario;
        this.nivelAcesso = nivelAcesso;
        this.nome = nome;
        this.senha = senha;
        this.login = login;
        this.observacoes = observacoes;
        this.status = status;
        this.usuarios = usuarios;
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

    @ManyToOne
    @JoinColumn(name = "incluido_por", nullable = false)
    public Usuario getUsuario() {
        return this.usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @ManyToOne
    @JoinColumn(name = "nivel_acesso", nullable = false)
    public NivelAcesso getNivelAcesso() {
        return this.nivelAcesso;
    }

    public void setNivelAcesso(NivelAcesso nivelAcesso) {
        this.nivelAcesso = nivelAcesso;
    }

    @Column(name = "nome", nullable = false)
    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Column(name = "senha", nullable = false)
    public String getSenha() {
        return this.senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Column(name = "login", nullable = false)
    public String getLogin() {
        return this.login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Column(name = "observacoes")
    public String getObservacoes() {
        return this.observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    @Column(name = "status")
    public Boolean getStatus() {
        return this.status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "usuario", targetEntity = Usuario.class)
    public Set getUsuarios() {
        return this.usuarios;
    }

    public void setUsuarios(Set usuarios) {
        this.usuarios = usuarios;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + this.id;
        hash = 13 * hash + (this.nome != null ? this.nome.hashCode() : 0);
        hash = 13 * hash + (this.senha != null ? this.senha.hashCode() : 0);
        hash = 13 * hash + (this.login != null ? this.login.hashCode() : 0);
        hash = 13 * hash + (this.observacoes != null ? this.observacoes.hashCode() : 0);
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
        final Usuario other = (Usuario) obj;
        if (this.id != other.id) {
            return false;
        }
        if ((this.nome == null) ? (other.nome != null) : !this.nome.equals(other.nome)) {
            return false;
        }
        if ((this.senha == null) ? (other.senha != null) : !this.senha.equals(other.senha)) {
            return false;
        }
        if ((this.login == null) ? (other.login != null) : !this.login.equals(other.login)) {
            return false;
        }
        if ((this.observacoes == null) ? (other.observacoes != null) : !this.observacoes.equals(other.observacoes)) {
            return false;
        }
        return true;
    }
   
}
