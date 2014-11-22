/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webjsf.controller;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import webjsf.dao.ContatoDao;
import webjsf.dao.ListaContatosDataModel;
import webjsf.modelo.Contato;


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

    public List<Contato> buscaPorNome(String nome) throws SQLException {
        return new ContatoDao().buscarPorNome(nome);
    }

    public void remove(Contato contato) throws SQLException {
        new ContatoDao().deleta(contato);
    }
}
