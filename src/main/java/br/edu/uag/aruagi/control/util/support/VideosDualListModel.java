/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.uag.aruagi.control.util.support;

import br.edu.uag.aruagi.model.Videos;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author israel
 */
public class VideosDualListModel implements Serializable {

    private List<Videos> sources = new ArrayList<Videos>();
    private List<Videos> target = new ArrayList<Videos>();
    private Videos selected;

    public VideosDualListModel() {
    }

    public Videos getSelected() {
        return selected;
    }

    public void setTarget(List<Videos> target) {
        this.target = target;
    }

    public void setSources(List<Videos> sources) {
        this.sources = sources;
    }

    public void setSelected(Videos selected) {
        this.selected = selected;
    }

    public boolean add() {
        if (this.sources.remove(selected)) {
            return this.target.add(selected);
        }
        return false;
    }

    public boolean remove() {
        if (this.target.remove(selected)) {
            return this.sources.add(selected);
        }
        return false;
    }

    public List<Videos> getSources() {
        return sources;
    }

    public List<Videos> getTarget() {
        return target;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (this.sources != null ? this.sources.hashCode() : 0);
        hash = 97 * hash + (this.target != null ? this.target.hashCode() : 0);
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
        final VideosDualListModel other = (VideosDualListModel) obj;
        if (this.sources != other.sources && (this.sources == null || !this.sources.equals(other.sources))) {
            return false;
        }
        if (this.target != other.target && (this.target == null || !this.target.equals(other.target))) {
            return false;
        }
        return true;
    }

}
