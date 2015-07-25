/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.uag.aruagi.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author root
 * @param <TipoQuestao>
 * @param <TipoPergunta>
 * @param <TipoResposta>
 */
public abstract class Gabarito<TipoQuestao, TipoPergunta, TipoResposta> {

    private final List<Correcao> correcao;

    public Gabarito(int nCorrecoes) {
        this.correcao = new ArrayList(nCorrecoes);
        for (int i = 0; i < nCorrecoes; i++) {
            this.correcao.add(null);
        }
    }

    public List<Correcao> getCorrecao() {
        return correcao;
    }
    
    public abstract Correcao find(TipoQuestao questao);

    /**
     * classe abstrata guarda as correções
     */
    public abstract class Correcao {

        private final TipoPergunta correto;
        private final TipoResposta resposta;

        public Correcao(TipoPergunta correto, TipoResposta resposta) {
            this.correto = correto;
            this.resposta = resposta;
        }

        public TipoPergunta getCorreto() {
            return correto;
        }

        public TipoResposta getResposta() {
            return resposta;
        }

        public abstract boolean corrige();
    }

}
