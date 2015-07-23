/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.uag.aruagi.control.util.listeners;

import br.edu.uag.aruagi.control.util.hibernate.FacesContextUtil;
import br.edu.uag.aruagi.control.util.hibernate.HibernateUtil;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import org.hibernate.Session;

/**
 * Classe que "ouve" as fases do JSF e gerencia a sessão conforme necessidade
 *
 * @author Israel Araújo
 */
public class HibernatePhaseListener implements PhaseListener {

    /**
     * Abrindo sessao na restauração da visao
     *
     * @param fase
     */
    @Override
    public void beforePhase(PhaseEvent fase) {
        //if (fase.getPhaseId().equals(PhaseId.RESTORE_VIEW)) {
            Session session = FacesContextUtil.getRequestSession();
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            FacesContextUtil.setRequestSession(session);
        //}
    }

    /**
     * commitando alterações na ultima fase
     *
     * @param fase
     */
    @Override
    public void afterPhase(PhaseEvent fase) {
        //if (fase.getPhaseId().equals(PhaseId.RENDER_RESPONSE)) {
            Session session = FacesContextUtil.getRequestSession();
            try {
                session.getTransaction().commit();
            } catch (Exception e) {
                if (session.getTransaction().isActive()) {
                    session.getTransaction().rollback();
                }
            }finally{
                session.close();
            }
        //}
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.ANY_PHASE;
    }

}
