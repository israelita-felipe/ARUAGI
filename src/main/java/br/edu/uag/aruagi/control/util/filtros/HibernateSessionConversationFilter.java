package br.edu.uag.aruagi.control.util.filtros;


import br.edu.uag.aruagi.control.util.hibernate.HibernateUtil;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StaleObjectStateException;
import org.hibernate.context.internal.ManagedSessionContext;

public class HibernateSessionConversationFilter  
        implements Filter {  
     
 
	    
  
    private SessionFactory sf;  
  
    public static final String HIBERNATE_SESSION_KEY = "hibernateSession";  
    public static final String END_OF_CONVERSATION_FLAG = "endOfConversation";  
  
    public void doFilter(ServletRequest request,  
                         ServletResponse response,  
                         FilterChain chain)  
            throws IOException, ServletException {  
  
        Session currentSession;  
  
        // Try to get a Hibernate Session from the HttpSession  
        HttpSession httpSession =  
                ((HttpServletRequest) request).getSession();  
        Session disconnectedSession =  
                (Session) httpSession.getAttribute(HIBERNATE_SESSION_KEY);  
  
        try {  
  
            // Start a new conversation or in the middle?  
            if (disconnectedSession == null) {  
                
                currentSession = sf.openSession();  
                currentSession.setFlushMode(FlushMode.NEVER);  
            } else {  
                
                currentSession = (Session) disconnectedSession;  
            }  
  
              
            ManagedSessionContext.bind(currentSession);  
  
            
            currentSession.beginTransaction();  
  
              
            chain.doFilter(request, response);  
  
              
            currentSession = ManagedSessionContext.unbind(sf);  
  
            // End or continue the long-running conversation?  
            if (request.getAttribute(END_OF_CONVERSATION_FLAG) != null ||  
                request.getParameter(END_OF_CONVERSATION_FLAG) != null) {  
  
                 
                currentSession.flush();  
  
                 
                currentSession.getTransaction().commit();  
  
                 
                currentSession.close();  
  
                 
                httpSession.setAttribute(HIBERNATE_SESSION_KEY, null);  
  
                 
  
            } else {  
  
                  
                currentSession.getTransaction().commit();  
  
                  
                httpSession.setAttribute(HIBERNATE_SESSION_KEY, currentSession);  
  
                  
            }  
  
        } catch (StaleObjectStateException staleEx) {  
            
            throw staleEx;  
        } catch (Throwable ex) {  
            // Rollback only  
            try {  
                if (sf.getCurrentSession().getTransaction().isActive()) {  
                    
                    sf.getCurrentSession().getTransaction().rollback();  
                }  
            } catch (Throwable rbEx) {  
                 
            } finally {  
                
                currentSession = ManagedSessionContext.unbind(sf);  
  
                 
                currentSession.close();  
  
                
                httpSession.setAttribute(HIBERNATE_SESSION_KEY, null);  
  
            }  
  
            // Let others handle it... maybe another interceptor for exceptions?  
            throw new ServletException(ex);  
        }  
  
    }  
  
    public void init(FilterConfig filterConfig) throws ServletException {  
         
        sf = HibernateUtil.getSessionFactory();  
    }  
  
    public void destroy() {}  
}  