/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.uag.aruagi.control.util.filtros;

import br.edu.uag.aruagi.control.bean.UsuarioSessionController;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Israel Araújo
 */
public class AdminFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        /**
         * capturando o usuario do managedBean
         */
        UsuarioSessionController user = (UsuarioSessionController) ((HttpServletRequest) request).getSession().getAttribute("usuarioSessionController");
        if (user != null && user.isLogged()) {
            if (!user.isAdmin()) {
                String contextPath = ((HttpServletRequest) request).getContextPath();
                /**
                 * redirecionando o usuario para a pagina home
                 */
                ((HttpServletResponse) response).sendRedirect(contextPath + "/private/homePrivate.uag");
            } else {
                /**
                 * caso esteja logado continue
                 */
                chain.doFilter(request, response);
            }
        } else {
            /**
             * caso esteja logado continue
             */
            chain.doFilter(request, response);
        }

    }

    @Override
    public void destroy() {
    }

}
