/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.uag.aruagi.model;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author root
 * @param <T>
 */
public class Gabarito<T> {

    private final List<Correcao> correcao;

    public Gabarito() {
        this.correcao = new LinkedList<Correcao>();
    }

    public abstract class Correcao {

        private final T correto;
        private final T resposta;

        public Correcao(T correto, T resposta) {
            this.correto = correto;
            this.resposta = resposta;
        }

        public abstract boolean corrige();
    }
}
