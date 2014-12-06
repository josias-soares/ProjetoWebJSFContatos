/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webjsf.controller;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIInput;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import webjsf.model.Contato;
import webjsf.model.ContatoRepository;
import webjsf.model.Telefone;

/**
 *
 * @author josias
 */
@ManagedBean
@ViewScoped
public class CadastroContatoBean  implements Serializable{

    private Contato contato = new Contato();

    private Telefone telefoneSelecionado = novoTelefone();

    @PostConstruct
    public void init() {
        String idParameter = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
        if (idParameter != null) {
            EntityManager em = getEntityManager();
            this.contato = new ContatoRepository(em).buscaPorId(Long.valueOf(idParameter));
        }
    }

    public Contato getContato() {
        return contato;
    }

    public void setContato(Contato contato) {
        this.contato = contato;
    }

    private EntityManager getEntityManager() {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) ec.getRequest();
        EntityManager manager = (EntityManager) request.getAttribute("EntityManager");

        return manager;
    }

    public String grava() {
        EntityManager manager = getEntityManager();

        new ContatoRepository(manager).adiciona(contato);

        return "lista-contatos?faces-redirect=true";
    }

    public void emailChanged(ValueChangeEvent event) {
        String oldEmailValue = (String) event.getOldValue();
        String newEmailValue = (String) event.getNewValue();

        System.out.println(oldEmailValue);
        System.out.println(newEmailValue);

        if (newEmailValue != null && !newEmailValue.equals(oldEmailValue)) {
            EntityManager manager = getEntityManager();
            Contato contato = new ContatoRepository(manager).buscaPorEmail(newEmailValue);
            Long id = (Long) ((UIInput) event.getComponent().findComponent("id")).getValue();

            if (contato != null && !contato.getId().equals(id) && newEmailValue.equals(contato.getEmail())) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, String.format("Email já cadastrado para o contato %s.", contato.getNome()), null);
                FacesContext.getCurrentInstance().addMessage(null, message);
            }
        }
    }

    public void removeTelefone(Telefone telefone) {
        contato.getTelefones().remove(telefone);
    }

    public void editaTelefone(Telefone telefone) {
        telefoneSelecionado = telefone;
    }

    public Telefone novoTelefone() {
        telefoneSelecionado = new Telefone();
        return telefoneSelecionado;
    }

    public void adicionaTelefone() {
        if (telefoneSelecionado.getNumero() == null) {
            return;
        }
        contato.adicionaTelefone(telefoneSelecionado);

        novoTelefone();
    }

    public Telefone getTelefoneSelecionado() {
        return telefoneSelecionado;
    }

    public void setTelefoneSelecionado(Telefone telefoneSelecionadoNoForm) {
        this.telefoneSelecionado = telefoneSelecionadoNoForm;
    }

    public Telefone.Tipo[] getTiposTelefone() {
        return Telefone.Tipo.values();
    }
}