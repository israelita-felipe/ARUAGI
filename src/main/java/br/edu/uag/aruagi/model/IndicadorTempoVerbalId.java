package br.edu.uag.aruagi.model;
// Generated 09/08/2014 12:29:58 by Hibernate Tools 3.6.0

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * IndicadorTempoVerbalId generated by hbm2java
 */
@Embeddable
public class IndicadorTempoVerbalId implements java.io.Serializable {

    private int tempoVerbal;
    private int pessoaGramatical;

    public IndicadorTempoVerbalId() {
    }

    public IndicadorTempoVerbalId(int tempoVerbal, int pessoaGramatical) {
        this.tempoVerbal = tempoVerbal;
        this.pessoaGramatical = pessoaGramatical;
    }

    @Column(name = "tempo_verbal", nullable = false)
    public int getTempoVerbal() {
        return this.tempoVerbal;
    }

    public void setTempoVerbal(int tempoVerbal) {
        this.tempoVerbal = tempoVerbal;
    }

    @Column(name = "pessoa_gramatical", nullable = false)
    public int getPessoaGramatical() {
        return this.pessoaGramatical;
    }

    public void setPessoaGramatical(int pessoaGramatical) {
        this.pessoaGramatical = pessoaGramatical;
    }

    @Override
    public boolean equals(Object other) {
        if ((this == other)) {
            return true;
        }
        if ((other == null)) {
            return false;
        }
        if (!(other instanceof IndicadorTempoVerbalId)) {
            return false;
        }
        IndicadorTempoVerbalId castOther = (IndicadorTempoVerbalId) other;

        return (this.getTempoVerbal() == castOther.getTempoVerbal())
                && (this.getPessoaGramatical() == castOther.getPessoaGramatical());
    }

    @Override
    public int hashCode() {
        int result = 17;

        result = 37 * result + this.getTempoVerbal();
        result = 37 * result + this.getPessoaGramatical();
        return result;
    }

}
