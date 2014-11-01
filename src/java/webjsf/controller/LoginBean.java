package webjsf.controller;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class LoginBean {

    private String usuario;

    private String senha;

    //GETTERS e SETTERS
    public String login() {
        if ("admin".equals(usuario) && !"123".equals(senha)) {
            //Mensagens de validação
            FacesMessage message = new FacesMessage("O usuário não existe ou a senha é inválida.");
            FacesContext.getCurrentInstance().addMessage(null, message);
            return null;
        }

        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", usuario);

        //Navega até a página lista-contatos utilizando um redirect.
        return "lista-contatos?faces-redirect=true";
    }

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "login?faces-redirect=true";
    }

    public String getUsuario() {
        return usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    
    
    
}
