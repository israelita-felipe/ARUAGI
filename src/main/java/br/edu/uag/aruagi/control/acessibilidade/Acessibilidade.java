/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.uag.aruagi.control.acessibilidade;

/**
 *
 * @author Israel Araújo
 */
public class Acessibilidade {

    public String fontCollor;
    public String fontFamilly;
    public String backgroudCollor;
    public String activeCSSStyle;

    public int fontSize;

    public Acessibilidade() {
    }

    public String getFontCollor() {
        return fontCollor;
    }

    public void setFontCollor(String fontCollor) {
        this.fontCollor = fontCollor;
    }

    public String getFontFamilly() {
        return fontFamilly;
    }

    public void setFontFamilly(String fontFamilly) {
        this.fontFamilly = fontFamilly;
    }

    public String getBackgroudCollor() {
        return backgroudCollor;
    }

    public void setBackgroudCollor(String backgroudCollor) {
        this.backgroudCollor = backgroudCollor;
    }

    public String getActiveCSSStyle() {
        return activeCSSStyle;
    }

    public void setActiveCSSStyle(String activeCSSStyle) {
        this.activeCSSStyle = activeCSSStyle;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

}
