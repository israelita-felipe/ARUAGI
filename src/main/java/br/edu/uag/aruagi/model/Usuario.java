package br.edu.uag.aruagi.model;
// Generated 09/08/2014 12:29:58 by Hibernate Tools 3.6.0

import br.edu.uag.aruagi.control.util.cript.SHA256;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
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
    private NivelAcesso nivelAcessoByIncluidoPor;
    private NivelAcesso nivelAcessoByNivelAcesso;
    private String nome;
    private String senha;
    private String login;
    private String observacoes;
    private Boolean status;
    private Set usuarios = new HashSet(0);

    public Usuario() {
    }

    public Usuario(int id, Usuario usuario, NivelAcesso nivelAcessoByIncluidoPor, NivelAcesso nivelAcessoByNivelAcesso, String nome, String senha, String login) {
        this.id = id;
        this.usuario = usuario;
        this.nivelAcessoByIncluidoPor = nivelAcessoByIncluidoPor;
        this.nivelAcessoByNivelAcesso = nivelAcessoByNivelAcesso;
        this.nome = nome;
        this.senha = senha;
        this.login = login;
    }

    public Usuario(int id, Usuario usuario, NivelAcesso nivelAcessoByIncluidoPor, NivelAcesso nivelAcessoByNivelAcesso, String nome, String senha, String login, String observacoes, Boolean status, Set usuarios) {
        this.id = id;
        this.usuario = usuario;
        this.nivelAcessoByIncluidoPor = nivelAcessoByIncluidoPor;
        this.nivelAcessoByNivelAcesso = nivelAcessoByNivelAcesso;
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
    @JoinColumn(name = "incluido_por", nullable = false, insertable = false, updatable = false)
    public NivelAcesso getNivelAcessoByIncluidoPor() {
        return this.nivelAcessoByIncluidoPor;
    }

    public void setNivelAcessoByIncluidoPor(NivelAcesso nivelAcessoByIncluidoPor) {
        this.nivelAcessoByIncluidoPor = nivelAcessoByIncluidoPor;
    }

    @ManyToOne
    @JoinColumn(name = "nivel_acesso", nullable = false)
    public NivelAcesso getNivelAcessoByNivelAcesso() {
        return this.nivelAcessoByNivelAcesso;
    }

    public void setNivelAcessoByNivelAcesso(NivelAcesso nivelAcessoByNivelAcesso) {
        this.nivelAcessoByNivelAcesso = nivelAcessoByNivelAcesso;
    }

    @Column(name = "nome", nullable = false)
    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Column(name = "senha", nullable = false, unique = true)
    public String getSenha() {
        return this.senha;
    }

    public void setSenha(String senha){
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

    @OneToMany(mappedBy = "usuario", targetEntity = Usuario.class)
    public Set getUsuarios() {
        return this.usuarios;
    }

    public void setUsuarios(Set usuarios) {
        this.usuarios = usuarios;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 61 * hash + this.id;
        hash = 61 * hash + (this.usuario != null ? this.usuario.hashCode() : 0);
        hash = 61 * hash + (this.nivelAcessoByIncluidoPor != null ? this.nivelAcessoByIncluidoPor.hashCode() : 0);
        hash = 61 * hash + (this.nivelAcessoByNivelAcesso != null ? this.nivelAcessoByNivelAcesso.hashCode() : 0);
        hash = 61 * hash + (this.nome != null ? this.nome.hashCode() : 0);
        hash = 61 * hash + (this.senha != null ? this.senha.hashCode() : 0);
        hash = 61 * hash + (this.login != null ? this.login.hashCode() : 0);
        hash = 61 * hash + (this.observacoes != null ? this.observacoes.hashCode() : 0);
        hash = 61 * hash + (this.status != null ? this.status.hashCode() : 0);
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
        if (this.usuario != other.usuario && (this.usuario == null || !this.usuario.equals(other.usuario))) {
            return false;
        }
        if (this.nivelAcessoByIncluidoPor != other.nivelAcessoByIncluidoPor && (this.nivelAcessoByIncluidoPor == null || !this.nivelAcessoByIncluidoPor.equals(other.nivelAcessoByIncluidoPor))) {
            return false;
        }
        if (this.nivelAcessoByNivelAcesso != other.nivelAcessoByNivelAcesso && (this.nivelAcessoByNivelAcesso == null || !this.nivelAcessoByNivelAcesso.equals(other.nivelAcessoByNivelAcesso))) {
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
        if (this.status != other.status && (this.status == null || !this.status.equals(other.status))) {
            return false;
        }
        return true;
    }

}
