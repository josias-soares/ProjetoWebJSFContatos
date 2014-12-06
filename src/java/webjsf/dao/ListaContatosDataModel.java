/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webjsf.dao;

import java.util.List;
import java.util.Map;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import webjsf.model.Contato;
import webjsf.model.ContatoRepository;


public class ListaContatosDataModel extends LazyDataModel<Contato> {

    @Override
    public List<Contato> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
            EntityManager em = getEntityManager();
            ContatoRepository repository = new ContatoRepository(em);
            setRowCount(repository.getTotal());
            setPageSize(pageSize);

            return repository.getLista(first, pageSize);
    }

    private EntityManager getEntityManager() {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) ec.getRequest();
        EntityManager manager = (EntityManager) request.getAttribute("EntityManager");

        return manager;
    }        
    
}

