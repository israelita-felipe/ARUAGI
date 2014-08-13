/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.uag.aruagi.control.util.support;

/**
 *
 * @author Israel Araújo
 */
public class StringManager {

    public static String prepareLinkVideo(String link) {
        return link.replaceFirst("watch", "").replaceFirst("=", "/").replaceFirst("[?]", "");
    }
}
