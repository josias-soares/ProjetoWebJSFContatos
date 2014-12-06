/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webjsf.jsf.filter;

import java.io.IOException;
import java.rmi.ServerException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**
 *
 * @author josias
 */
@WebFilter("/*")
public class JpaFilter implements  Filter{
    
    private EntityManagerFactory factory;
    
    @Override
    public void init(FilterConfig fc) throws ServletException {
        this.factory = Persistence.createEntityManagerFactory("ContatosPU");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        EntityManager manager = this.factory.createEntityManager();
        
        request.setAttribute("EntityManager", manager);
        manager.getTransaction().begin();
        
        try{
            chain.doFilter(request, response);
            manager.getTransaction().commit();
        }catch (Exception e){
            manager.getTransaction().rollback();
            throw new ServerException(e.getMessage());
        }finally {
            manager.close();
        }
    }

    @Override
    public void destroy() {
        this.factory.close();
    }
    
}
