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

    //atributos
    public String fontCollor;
    public String fontFamilly;
    public String backgroudCollor;
    public String activeCSSStyle;
    public int fontSize;

    /**
     * construtor
     */
    public Acessibilidade() {
    }

    /**
     * cor da fonte
     *
     * @return
     */
    public String getFontCollor() {
        return fontCollor;
    }

    /**
     * cor da fonte
     *
     * @param fontCollor
     */
    public void setFontCollor(String fontCollor) {
        this.fontCollor = fontCollor;
    }

    /**
     * tipo de fonte
     *
     * @return
     */
    public String getFontFamilly() {
        return fontFamilly;
    }

    /**
     * tipo de fonte
     *
     * @param fontFamilly
     */
    public void setFontFamilly(String fontFamilly) {
        this.fontFamilly = fontFamilly;
    }

    /**
     * cor de fundo
     *
     * @return
     */
    public String getBackgroudCollor() {
        return backgroudCollor;
    }

    /**
     * cor de fundo
     *
     * @param backgroudCollor
     */
    public void setBackgroudCollor(String backgroudCollor) {
        this.backgroudCollor = backgroudCollor;
    }

    /**
     * nome do estilo css padrão
     *
     * @return
     */
    public String getActiveCSSStyle() {
        return activeCSSStyle;
    }

    /**
     * nome do estilo css padrão
     *
     * @param activeCSSStyle
     */
    public void setActiveCSSStyle(String activeCSSStyle) {
        this.activeCSSStyle = activeCSSStyle;
    }

    /**
     * tamanho da fonte
     *
     * @return
     */
    public int getFontSize() {
        return fontSize;
    }

    /**
     * tamanho da fonte
     *
     * @param fontSize
     */
    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

}
