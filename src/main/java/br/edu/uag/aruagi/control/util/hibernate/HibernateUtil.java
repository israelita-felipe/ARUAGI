package br.edu.uag.aruagi.control.util.hibernate;

// IMPORTS
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

/**
 * FÁBRICA DE SESSÃO DO HIBERNATE
 *
 * @author Israel Araújo
 */
public class HibernateUtil {

    /**
     * ATRIBUTOS
     */
    private static final SessionFactory sessionFactory;

    /**
     * construtor estático da fábrica de sessões
     */
    static {
        try {
            Configuration configuration = new Configuration().configure();
            ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().
                    applySettings(configuration.getProperties()).buildServiceRegistry();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } catch (Exception ex) {
            throw new ExceptionInInitializerError(ex);
        }

    }

    /**
     * metodo estático que retorna um fábria de sessão
     *
     * @return
     */
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
