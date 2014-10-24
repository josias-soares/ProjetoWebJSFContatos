/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webjsf.controller;

import java.sql.SQLException;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import webjsf.dao.ContatoDao;
import webjsf.modelo.Contato;

/**
 *
 * @author josias
 */
@ManagedBean
public class CadastroContatoBean {
    
    private Contato contato = new Contato();
    
    /** 
     * A anotação @ManagedProperty("#{param.id}") recuperará o parâmetro com nome id 
     * e setará no atributo id do Managed Bean
     */
    @ManagedProperty("#{param.id}")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Contato getContato() {
        return contato;
    }

    public void setContato(Contato contato) {
        this.contato = contato;
    }
    
    public String grava(){ 
        
         if(contato.getNome() == null) {
            FacesMessage message = new FacesMessage("O nome deve ser definido.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
        
        if(contato.getDataNascimento() == null){
            FacesMessage message = new FacesMessage("A data de nascimento deve ser definida.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }

        if(contato.getNome() == null || contato.getDataNascimento() == null){
            return null;
        }

        try {
            if(contato.getId() == 0 || contato.getId() == null ){
                new ContatoDao().adiciona(contato);
            }else{
                new ContatoDao().altera(contato);
            }            
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        
        return "lista-contatos";
    }
    /**
     * O método é anotado com  @PostConstruct que garantirá que o 
     * método será invocado após a criação do Managed Bean pelo JSF. 
     * Neste método verificamos se estamos recebendo o id, caso ele seja diferente de nulo, 
     * carregamos o contato através do método ContatoDao.buscarPorId.
     */
    @PostConstruct
    public void init(){
        if(id != null){
            this.contato = new ContatoDao().buscarPorId(id);
        }
    }

}