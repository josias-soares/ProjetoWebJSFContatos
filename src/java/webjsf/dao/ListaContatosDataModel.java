/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webjsf.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import webjsf.modelo.Contato;


public class ListaContatosDataModel extends LazyDataModel<Contato> {

    /**
     * Método que carrega a listagem de contatos de acordo com os parâmetros first (offset) e pageSize (limit)
     */
    @Override
    public List<Contato> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        try {
            ContatoDao dao = new ContatoDao();
            setRowCount(dao.getTotal());
            setPageSize(pageSize);

            return dao.getLista(first, pageSize);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

}
