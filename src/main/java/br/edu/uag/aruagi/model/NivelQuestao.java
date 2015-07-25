package br.edu.uag.aruagi.model;
// Generated 09/08/2014 12:29:58 by Hibernate Tools 3.6.0

import java.util.ArrayList;
import java.util.List;
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
 * NivelQuestao generated by hbm2java
 */
@Entity
@Table(name = "nivel_questao", schema = "public"
)
public class NivelQuestao implements java.io.Serializable {

    private int id;
    private String descricao;
    private String observacoes;
    private Integer usuario;
    private List questaoTraduzPalavras = new ArrayList();
    private List questaoGramaticals = new ArrayList();
    private List questaoTraduzFrases = new ArrayList();
    private List questaoLacunas = new ArrayList();
    private List questaoDeclinacaos = new ArrayList();
    private Boolean status;

    @Column(name = "status")
    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public NivelQuestao() {
    }

    public NivelQuestao(int id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public NivelQuestao(int id, String descricao, String observacoes, Integer usuario, List questaoTraduzPalavras, List questaoGramaticals, List questaoTraduzFrases, List questaoLacunas, List questaoDeclinacaos) {
        this.id = id;
        this.descricao = descricao;
        this.observacoes = observacoes;
        this.usuario = usuario;
        this.questaoTraduzPalavras = questaoTraduzPalavras;
        this.questaoGramaticals = questaoGramaticals;
        this.questaoTraduzFrases = questaoTraduzFrases;
        this.questaoLacunas = questaoLacunas;
        this.questaoDeclinacaos = questaoDeclinacaos;
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

    @Column(name = "observacoes")
    public String getObservacoes() {
        return this.observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    @Column(name = "usuario")
    public Integer getUsuario() {
        return this.usuario;
    }

    public void setUsuario(Integer usuario) {
        this.usuario = usuario;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "nivelQuestao", targetEntity = QuestaoTraduzPalavra.class)
    @Fetch(FetchMode.SUBSELECT)
    public List<QuestaoTraduzPalavra> getQuestaoTraduzPalavras() {
        return this.questaoTraduzPalavras;
    }

    public void setQuestaoTraduzPalavras(List questaoTraduzPalavras) {
        this.questaoTraduzPalavras = questaoTraduzPalavras;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "nivelQuestao", targetEntity = QuestaoGramatical.class)
    @Fetch(FetchMode.SUBSELECT)
    public List getQuestaoGramaticals() {
        return this.questaoGramaticals;
    }

    public void setQuestaoGramaticals(List questaoGramaticals) {
        this.questaoGramaticals = questaoGramaticals;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "nivelQuestao", targetEntity = QuestaoTraduzFrase.class)
    @Fetch(FetchMode.SUBSELECT)
    public List getQuestaoTraduzFrases() {
        return this.questaoTraduzFrases;
    }

    public void setQuestaoTraduzFrases(List questaoTraduzFrases) {
        this.questaoTraduzFrases = questaoTraduzFrases;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "nivelQuestao", targetEntity = QuestaoLacuna.class)
    @Fetch(FetchMode.SUBSELECT)
    public List getQuestaoLacunas() {
        return this.questaoLacunas;
    }

    public void setQuestaoLacunas(List questaoLacunas) {
        this.questaoLacunas = questaoLacunas;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "nivelQuestao", targetEntity = QuestaoDeclinacao.class)
    @Fetch(FetchMode.SUBSELECT)
    public List getQuestaoDeclinacaos() {
        return this.questaoDeclinacaos;
    }

    public void setQuestaoDeclinacaos(List questaoDeclinacaos) {
        this.questaoDeclinacaos = questaoDeclinacaos;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + this.id;
        hash = 97 * hash + (this.descricao != null ? this.descricao.hashCode() : 0);
        hash = 97 * hash + (this.observacoes != null ? this.observacoes.hashCode() : 0);
        hash = 97 * hash + (this.usuario != null ? this.usuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
       return true;
    }

}
