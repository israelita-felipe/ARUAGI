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
 * Videos generated by hbm2java
 */
@Entity
@Table(name = "videos", schema = "public"
)
public class Videos implements java.io.Serializable {

    private int id;
    private String link;
    private String descricao;
    private Integer usuario;
    private Boolean status;
    private List<VideosPostagem> videosPostagems = new ArrayList<VideosPostagem>();

    public Videos() {
    }

    public Videos(int id, String link) {
        this.id = id;
        this.link = link;
    }

    public Videos(int id, String link, String descricao, Integer usuario, List<VideosPostagem> videosPostagems) {
        this.id = id;
        this.link = link;
        this.descricao = descricao;
        this.usuario = usuario;
        this.videosPostagems = videosPostagems;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "link", nullable = false)
    public String getLink() {
        return this.link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Column(name = "descricao")
    public String getDescricao() {
        return this.descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Column(name = "usuario",updatable = true)
    public Integer getUsuario() {
        return this.usuario;
    }

    public void setUsuario(Integer usuario) {
        this.usuario = usuario;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "videos", targetEntity = VideosPostagem.class)
    @Fetch(FetchMode.SUBSELECT)
    public List<VideosPostagem> getVideosPostagems() {
        return this.videosPostagems;
    }

    public void setVideosPostagems(List<VideosPostagem> videosPostagems) {
        this.videosPostagems = videosPostagems;
    }

    @Column(name = "status")
    public Boolean getStatus() {
        return this.status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + getId();
        hash = 41 * hash + (getLink() != null ? getLink().hashCode() : 0);
        hash = 41 * hash + (getDescricao() != null ? getDescricao().hashCode() : 0);
        hash = 41 * hash + (getUsuario() != null ? getUsuario().hashCode() : 0);
        hash = 41 * hash + (getStatus() != null ? getStatus().hashCode() : 0);
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
        final Videos other = (Videos) obj;
        if (getId() != other.getId()) {
            return false;
        }
        if ((getLink() == null) ? (other.getLink() != null) : !getLink().equals(other.getLink())) {
            return false;
        }
        if ((getDescricao() == null) ? (other.getDescricao() != null) : !getDescricao().equals(other.getDescricao())) {
            return false;
        }
        if (getUsuario() != other.getUsuario() && (getUsuario() == null || !getUsuario().equals(other.getUsuario()))) {
            return false;
        }
        if (getStatus() != other.getStatus() && (getStatus() == null || !getStatus().equals(other.getStatus()))) {
            return false;
        }
        return true;
    }

}
