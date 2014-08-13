/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.uag.aruagi.control.util.support;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Israel Araújo
 */
public class DateTime {

    public static Date getCurrentDate() {
        Calendar c = Calendar.getInstance();
        c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), 0, 0, 0);
        return c.getTime();
    }

}
