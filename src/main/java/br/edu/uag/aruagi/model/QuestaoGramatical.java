package br.edu.uag.aruagi.model;
// Generated 09/08/2014 12:29:58 by Hibernate Tools 3.6.0

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
 * QuestaoGramatical generated by hbm2java
 */
@Entity
@Table(name = "questao_gramatical", schema = "public"
)
public class QuestaoGramatical implements java.io.Serializable {

    private int id;
    private PessoaGramatical pessoaGramatical;
    private TempoVerbal tempoVerbal;
    private PalavraLatim palavraLatim;
    private NivelQuestao nivelQuestao;
    private boolean status;
    private String enunciado;
    private String titulo;
    private Integer usuario;

    public QuestaoGramatical() {
    }

    public QuestaoGramatical(int id, PessoaGramatical pessoaGramatical, TempoVerbal tempoVerbal, PalavraLatim palavraLatim, NivelQuestao nivelQuestao, boolean status) {
        this.id = id;
        this.pessoaGramatical = pessoaGramatical;
        this.tempoVerbal = tempoVerbal;
        this.palavraLatim = palavraLatim;
        this.nivelQuestao = nivelQuestao;
        this.status = status;
    }

    public QuestaoGramatical(int id, PessoaGramatical pessoaGramatical, TempoVerbal tempoVerbal, PalavraLatim palavraLatim, NivelQuestao nivelQuestao, boolean status, String enunciado, String titulo, Integer usuario) {
        this.id = id;
        this.pessoaGramatical = pessoaGramatical;
        this.tempoVerbal = tempoVerbal;
        this.palavraLatim = palavraLatim;
        this.nivelQuestao = nivelQuestao;
        this.status = status;
        this.enunciado = enunciado;
        this.titulo = titulo;
        this.usuario = usuario;
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
    @JoinColumn(name = "pessoa_gramatical", nullable = false)
    public PessoaGramatical getPessoaGramatical() {
        return this.pessoaGramatical;
    }

    public void setPessoaGramatical(PessoaGramatical pessoaGramatical) {
        this.pessoaGramatical = pessoaGramatical;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tempo_verbal", nullable = false)
    public TempoVerbal getTempoVerbal() {
        return this.tempoVerbal;
    }

    public void setTempoVerbal(TempoVerbal tempoVerbal) {
        this.tempoVerbal = tempoVerbal;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "palavra_latim", nullable = false)
    public PalavraLatim getPalavraLatim() {
        return this.palavraLatim;
    }

    public void setPalavraLatim(PalavraLatim palavraLatim) {
        this.palavraLatim = palavraLatim;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "nivel", nullable = false)
    public NivelQuestao getNivelQuestao() {
        return this.nivelQuestao;
    }

    public void setNivelQuestao(NivelQuestao nivelQuestao) {
        this.nivelQuestao = nivelQuestao;
    }

    @Column(name = "status", nullable = false)
    public boolean isStatus() {
        return this.status;
    }

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
    public Integer getUsuario() {
        return this.usuario;
    }

    public void setUsuario(Integer usuario) {
        this.usuario = usuario;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + this.id;
        hash = 37 * hash + (this.pessoaGramatical != null ? this.pessoaGramatical.hashCode() : 0);
        hash = 37 * hash + (this.tempoVerbal != null ? this.tempoVerbal.hashCode() : 0);
        hash = 37 * hash + (this.palavraLatim != null ? this.palavraLatim.hashCode() : 0);
        hash = 37 * hash + (this.nivelQuestao != null ? this.nivelQuestao.hashCode() : 0);
        hash = 37 * hash + (this.status ? 1 : 0);
        hash = 37 * hash + (this.enunciado != null ? this.enunciado.hashCode() : 0);
        hash = 37 * hash + (this.titulo != null ? this.titulo.hashCode() : 0);
        hash = 37 * hash + (this.usuario != null ? this.usuario.hashCode() : 0);
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
        final QuestaoGramatical other = (QuestaoGramatical) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.pessoaGramatical != other.pessoaGramatical && (this.pessoaGramatical == null || !this.pessoaGramatical.equals(other.pessoaGramatical))) {
            return false;
        }
        if (this.tempoVerbal != other.tempoVerbal && (this.tempoVerbal == null || !this.tempoVerbal.equals(other.tempoVerbal))) {
            return false;
        }
        if (this.palavraLatim != other.palavraLatim && (this.palavraLatim == null || !this.palavraLatim.equals(other.palavraLatim))) {
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
        if (this.usuario != other.usuario && (this.usuario == null || !this.usuario.equals(other.usuario))) {
            return false;
        }
        return true;
    }

}
