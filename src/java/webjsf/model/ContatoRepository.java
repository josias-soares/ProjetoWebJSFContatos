/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webjsf.model;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 *
 * @author josias
 */ 
public class ContatoRepository {
    private EntityManager em;
    
    public ContatoRepository(EntityManager em){
        this.em = em;
    }
    
    public void adiciona(Contato contato){
        if (contato.getId() == null){
            em.persist(contato);
        } else {
            em.merge(contato);
        }
    }
    
    public void remove(Contato contato){
        contato = em.find(Contato.class, contato.getId());
        em.remove(contato);
    }
    
    public Contato buscaPorEmail(String email){
        List<Contato> resultList = em.createQuery("from Contato c where c.email = :email", Contato.class).setParameter("email", email).getResultList();
        
        if (!resultList.isEmpty()){
            return resultList.iterator().next();
        }
        
        return null;
    }
    
    public Contato buscaPorId(Long id){
        return em.find(Contato.class, id);
    }
    
    public List<Contato> buscaTodos(){
        return em.createQuery("from Contato c", Contato.class).getResultList();
    }
    
    public int getTotal(){
        return em.createQuery("from Contato c", Contato.class).getMaxResults();
    }

    public List<Contato> getLista(int first, int pageSize) {
        TypedQuery<Contato> query = em.createQuery("from Contato c", Contato.class);
        query.setFirstResult(first);
        query.setMaxResults(pageSize);

        return query.getResultList();
    }
    
    
    
}
