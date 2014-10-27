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
 * QuestaoTraduzFrase generated by hbm2java
 */
@Entity
@Table(name = "questao_traduz_frase", schema = "public"
)
public class QuestaoTraduzFrase implements java.io.Serializable, InterfaceQuestao {

    private int id;
    private FraseLatim fraseLatim;
    private FrasePortugues frasePortugues;
    private NivelQuestao nivelQuestao;
    private boolean status;
    private String enunciado;
    private String titulo;
    private Integer usuario;

    public QuestaoTraduzFrase() {
    }

    public QuestaoTraduzFrase(int id, FraseLatim fraseLatim, NivelQuestao nivelQuestao, boolean status) {
        this.id = id;
        this.fraseLatim = fraseLatim;
        this.nivelQuestao = nivelQuestao;
        this.status = status;
    }

    public QuestaoTraduzFrase(int id, FraseLatim fraseLatim, NivelQuestao nivelQuestao, boolean status, String enunciado, String titulo, Integer usuario) {
        this.id = id;
        this.fraseLatim = fraseLatim;
        this.nivelQuestao = nivelQuestao;
        this.status = status;
        this.enunciado = enunciado;
        this.titulo = titulo;
        this.usuario = usuario;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "frase_latim", nullable = false)
    public FraseLatim getFraseLatim() {
        return this.fraseLatim;
    }

    public void setFraseLatim(FraseLatim fraseLatim) {
        this.fraseLatim = fraseLatim;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "nivel", nullable = false)
    @Override
    public NivelQuestao getNivelQuestao() {
        return this.nivelQuestao;
    }

    @Override
    public void setNivelQuestao(NivelQuestao nivelQuestao) {
        this.nivelQuestao = nivelQuestao;
    }

    @Column(name = "status", nullable = false)
    @Override
    public boolean getStatus() {
        return this.status;
    }

    @Override
    public void setStatus(boolean status) {
        this.status = status;
    }

    @Column(name = "enunciado")
    public String getEnunciado() {
        return this.enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    @Column(name = "titulo")
    public String getTitulo() {
        return this.titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    @Column(name = "usuario")
    @Override
    public Integer getUsuario() {
        return this.usuario;
    }

    @Override
    public void setUsuario(Integer usuario) {
        this.usuario = usuario;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "traducao")
    public FrasePortugues getFrasePortugues() {
        return this.frasePortugues;
    }

    public void setFrasePortugues(FrasePortugues frasePortugues) {
        this.frasePortugues = frasePortugues;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + this.id;
        hash = 17 * hash + (this.fraseLatim != null ? this.fraseLatim.hashCode() : 0);
        hash = 17 * hash + (this.frasePortugues != null ? this.frasePortugues.hashCode() : 0);
        hash = 17 * hash + (this.nivelQuestao != null ? this.nivelQuestao.hashCode() : 0);
        hash = 17 * hash + (this.status ? 1 : 0);
        hash = 17 * hash + (this.enunciado != null ? this.enunciado.hashCode() : 0);
        hash = 17 * hash + (this.titulo != null ? this.titulo.hashCode() : 0);
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
        final QuestaoTraduzFrase other = (QuestaoTraduzFrase) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.fraseLatim != other.fraseLatim && (this.fraseLatim == null || !this.fraseLatim.equals(other.fraseLatim))) {
            return false;
        }
        if (this.frasePortugues != other.frasePortugues && (this.frasePortugues == null || !this.frasePortugues.equals(other.frasePortugues))) {
            return false;
        }
        if (this.nivelQuestao != other.nivelQuestao && (this.nivelQuestao == null || !this.nivelQuestao.equals(other.nivelQuestao))) {
            return false;
        }
        if (this.status != other.status) {
            return false;
        }
        if ((this.enunciado == null) ? (other.enunciado != null) : !this.enunciado.equals(other.enunciado)) {
            return false;
        }
        if ((this.titulo == null) ? (other.titulo != null) : !this.titulo.equals(other.titulo)) {
            return false;
        }
        return true;
    }
}
