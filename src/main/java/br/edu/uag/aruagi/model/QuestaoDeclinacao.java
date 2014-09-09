package br.edu.uag.aruagi.model;
// Generated 09/08/2014 12:29:58 by Hibernate Tools 3.6.0

import br.edu.uag.aruagi.control.interfaces.InterfaceQuestao;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * QuestaoDeclinacao generated by hbm2java
 */
@Entity
@Table(name = "questao_declinacao", schema = "public"
)
public class QuestaoDeclinacao implements java.io.Serializable, InterfaceQuestao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private int id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "declinacao", nullable = false)
    private Declinacao declinacao;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "palavra_latim", nullable = false)
    private PalavraLatim palavraLatim;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "caso", nullable = false)
    private Caso caso;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "nivel", nullable = false)
    private NivelQuestao nivelQuestao;
    @Column(name = "titulo")
    private String titulo;
    @Column(name = "enunciado")
    private String enunciado;
    @Column(name = "usuario")
    private Integer usuario;
    @Column(name = "status", nullable = false)
    private boolean status;
    
    public QuestaoDeclinacao() {
    }

    public QuestaoDeclinacao(int id, Declinacao declinacao, PalavraLatim palavraLatim, Caso caso, NivelQuestao nivelQuestao, boolean status) {
        this.id = id;
        this.declinacao = declinacao;
        this.palavraLatim = palavraLatim;
        this.caso = caso;
        this.nivelQuestao = nivelQuestao;
        this.status = status;
    }

    public QuestaoDeclinacao(int id, Declinacao declinacao, PalavraLatim palavraLatim, Caso caso, NivelQuestao nivelQuestao, String titulo, String enunciado, Integer usuario, boolean status) {
        this.id = id;
        this.declinacao = declinacao;
        this.palavraLatim = palavraLatim;
        this.caso = caso;
        this.nivelQuestao = nivelQuestao;
        this.titulo = titulo;
        this.enunciado = enunciado;
        this.usuario = usuario;
        this.status = status;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public Declinacao getDeclinacao() {
        return this.declinacao;
    }

    public void setDeclinacao(Declinacao declinacao) {
        this.declinacao = declinacao;
    }

    public PalavraLatim getPalavraLatim() {
        return this.palavraLatim;
    }

    public void setPalavraLatim(PalavraLatim palavraLatim) {
        this.palavraLatim = palavraLatim;
    }

    public Caso getCaso() {
        return this.caso;
    }

    public void setCaso(Caso caso) {
        this.caso = caso;
    }

    @Override
    public NivelQuestao getNivel() {
        return this.nivelQuestao;
    }

    @Override
    public void setNivel(NivelQuestao nivelQuestao) {
        this.nivelQuestao = nivelQuestao;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getEnunciado() {
        return this.enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    @Override
    public Integer getAutor() {
        return this.usuario;
    }

    @Override
    public void setAutor(Integer usuario) {
        this.usuario = usuario;
    }

    @Override
    public boolean getStatus() {
        return this.status;
    }

    @Override
    public void setStatus(boolean status) {
        this.status = status;
    }
    

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + this.id;
        hash = 53 * hash + (this.declinacao != null ? this.declinacao.hashCode() : 0);
        hash = 53 * hash + (this.palavraLatim != null ? this.palavraLatim.hashCode() : 0);
        hash = 53 * hash + (this.caso != null ? this.caso.hashCode() : 0);
        hash = 53 * hash + (this.nivelQuestao != null ? this.nivelQuestao.hashCode() : 0);
        hash = 53 * hash + (this.titulo != null ? this.titulo.hashCode() : 0);
        hash = 53 * hash + (this.enunciado != null ? this.enunciado.hashCode() : 0);
        hash = 53 * hash + (this.usuario != null ? this.usuario.hashCode() : 0);
        hash = 53 * hash + (this.status ? 1 : 0);
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
        final QuestaoDeclinacao other = (QuestaoDeclinacao) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.declinacao != other.declinacao && (this.declinacao == null || !this.declinacao.equals(other.declinacao))) {
            return false;
        }
        if (this.palavraLatim != other.palavraLatim && (this.palavraLatim == null || !this.palavraLatim.equals(other.palavraLatim))) {
            return false;
        }
        if (this.caso != other.caso && (this.caso == null || !this.caso.equals(other.caso))) {
            return false;
        }
        if (this.nivelQuestao != other.nivelQuestao && (this.nivelQuestao == null || !this.nivelQuestao.equals(other.nivelQuestao))) {
            return false;
        }
        if ((this.titulo == null) ? (other.titulo != null) : !this.titulo.equals(other.titulo)) {
            return false;
        }
        if ((this.enunciado == null) ? (other.enunciado != null) : !this.enunciado.equals(other.enunciado)) {
            return false;
        }
        if (this.usuario != other.usuario && (this.usuario == null || !this.usuario.equals(other.usuario))) {
            return false;
        }
        if (this.status != other.status) {
            return false;
        }
        return true;
    }

}
