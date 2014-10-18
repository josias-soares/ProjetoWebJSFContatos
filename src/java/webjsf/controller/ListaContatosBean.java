/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webjsf.controller;

import java.sql.SQLException;
import java.util.List;
import javax.faces.bean.ManagedBean;
import webjsf.modelo.Contato;
import webjsf.dao.ContatoDao;

/**
 *
 * @author josias
 */
@ManagedBean
public class ListaContatosBean {
    public List<Contato> getContatos() throws SQLException{
        return new ContatoDao().getLista();
    } 
    
    public void remove(Contato contato){
        new ContatoDao().deleta(contato);
    }


}
