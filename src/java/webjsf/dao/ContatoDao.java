
package webjsf.dao;

import java.sql.Connection;
import java.sql.Date;
import webjsf.modelo.Contato;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import webjsf.db.ConnectionFactory;

public class ContatoDao {
    private Connection connection;

    public ContatoDao() {
        this.connection = new ConnectionFactory().getConnection();
    }
    
    public void adiciona(Contato contato) throws SQLException{
        String sql = "INSERT INTO CONTATOS "
                + "(ID, NOME, EMAIL, ENDERECO, DATANASCIMENTO) VALUES (?,?,?,?,?)";
        
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            
            stmt.setLong(1, getProximoId());
            stmt.setString(2, contato.getNome());
            stmt.setString(3, contato.getEmail());
            stmt.setString(4, contato.getEndereco());
            stmt.setDate(5, new Date(contato.getDataNascimento().getTimeInMillis()));
            
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally{
            connection.close();
        }
    }
    
    public List<Contato> getLista() throws SQLException {
       
        try {
            List<Contato> contatos = new ArrayList<Contato>();
            PreparedStatement stmt = connection.prepareStatement("select * from contatos");

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                Contato contato = new Contato();
                contato.setId(rs.getLong("id"));
                contato.setNome(rs.getString("nome"));
                contato.setEmail(rs.getString("email"));
                contato.setEndereco(rs.getString("endereco"));

                Calendar data = Calendar.getInstance();
                data.setTime(rs.getDate("dataNascimento"));
                contato.setDataNascimento(data);

                contatos.add(contato);
            }
            rs.close();
            stmt.close();

            return contatos;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally{
            connection.close();
        }
    }
    
    public Long getProximoId() throws SQLException {
        String sql = "select max(id) from contatos ";

        try {
                
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet set = st.executeQuery();
                
            if (set.next()) {
                return set.getLong(1) + 1;
            }
        } finally {
            //connection.close();
        }
        return 1L;
    }
    
    public Contato buscarPorId(Long id){
        String sql = "select * from contatos "
                + " where id = ? ";

        try {
            
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setLong(1, id);
 
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                Contato contato = new Contato();
                contato.setId(rs.getLong("id"));
                contato.setNome(rs.getString("nome"));
                contato.setEmail(rs.getString("email"));
                contato.setEndereco(rs.getString("endereco"));

                if (rs.getDate("dataNascimento") != null) {
                    Calendar dataNascimento = Calendar.getInstance();
                    dataNascimento.setTime(rs.getDate("dataNascimento"));

                    contato.setDataNascimento(dataNascimento);
                }
                return contato;
            }
        } catch (SQLException e) {
            System.out.println("Erro de sql:" + e.getMessage());
        }
        return null;
    }

    public void deleta(Contato contato) {
        
    }

    public void altera(Contato contato) {
        
    }

    
}
