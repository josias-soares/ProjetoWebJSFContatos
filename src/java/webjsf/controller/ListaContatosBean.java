/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webjsf.controller;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import webjsf.dao.ListaContatosDataModel;
import webjsf.model.Contato;
import webjsf.model.ContatoRepository;


@ViewScoped
@ManagedBean
public class ListaContatosBean implements Serializable {

    private ListaContatosDataModel dataModel;

    public ListaContatosDataModel getContatos() {
        if (dataModel == null) {
            dataModel = new ListaContatosDataModel();
        }

        return dataModel;
    }
    
    private EntityManager getEntityManager() {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) ec.getRequest();
        EntityManager manager = (EntityManager) request.getAttribute("EntityManager");

        return manager;
    }

    public String remove(Contato contato){
        EntityManager manager = getEntityManager();

        new ContatoRepository(manager).remove(contato);

        return "lista-contatos?faces-redirect=true";
    }
    


}
