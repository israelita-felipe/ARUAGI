/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.uag.aruagi.control.bean;

/**
 *
 * @author Israel Araújo
 */
public class AcessibilidadeController {

    private int fontSize = 12;
    private boolean acessible = false;

    /**
     * Creates a new instance of AcessibilidadeController
     */
    public AcessibilidadeController() {
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public void up() {
        if (this.fontSize < 25) {
            this.fontSize++;
        }
    }

    public void down() {
        if (this.fontSize > 8) {
            this.fontSize--;
        }
    }

    public void setContraste(){
        this.acessible = !this.acessible;
    }
    public void reset() {
        this.fontSize = 12;
    }

    public boolean getAcessible() {
        return acessible;
    }

    public void setAcessible(boolean acessible) {
        this.acessible = acessible;
    }
}
