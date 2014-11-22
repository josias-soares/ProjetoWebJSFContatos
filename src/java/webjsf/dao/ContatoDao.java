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

    public void adiciona(Contato contato) throws SQLException {
        String sql = "INSERT INTO CONTATOS "
                + "(ID, NOME, EMAIL, ENDERECO, DATANASCIMENTO) VALUES (?,?,?,?,?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, getProximoId());
            stmt.setString(2, contato.getNome());
            stmt.setString(3, contato.getEmail());
            stmt.setString(4, contato.getEndereco());
            stmt.setDate(5, new Date(contato.getDataNascimento().getTimeInMillis()));

            stmt.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    public List<Contato> getLista(int offset, int limit) throws SQLException {
        List<Contato> contatos = new ArrayList<>();
        String sql = "select * from contatos OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        try (PreparedStatement st = connection.prepareStatement(sql);) {
            st.setInt(1, offset);
            st.setInt(2, limit);
            try (ResultSet rs = st.executeQuery()) {

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
            }

            return contatos;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
   
    public int getTotal() throws SQLException {
        String sql = "select count(id) from contatos ";

        try (PreparedStatement st = connection.prepareStatement(sql)) {

            ResultSet set = st.executeQuery();

            if (set.next()) {
                return set.getInt(1);
            }
        }
        return 1;
    }

    public Long getProximoId() throws SQLException {
        String sql = "select max(id) from contatos ";

        try (PreparedStatement st = connection.prepareStatement(sql)) {

            ResultSet set = st.executeQuery();

            if (set.next()) {
                return set.getLong(1) + 1;
            }
        }
        return 1L;
    }

    public Contato buscarPorId(Long id) {
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
        String sql = "DELETE FROM CONTATOS WHERE ID = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, contato.getId());
            stmt.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void altera(Contato contato) {
        String sql = "UPDATE CONTATOS SET "
                + "NOME = ? ,"
                + "EMAIL= ?  ,"
                + "ENDERECO= ? ,"
                + "DATANASCIMENTO = ? "
                + "WHERE ID = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, contato.getNome());
            stmt.setString(2, contato.getEmail());
            stmt.setString(3, contato.getEndereco());
            stmt.setDate(4, new Date(contato.getDataNascimento().getTimeInMillis()));
            stmt.setLong(5, contato.getId());

            stmt.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Contato buscarPorEmail(String email) throws SQLException {
        String sql = "select * from contatos "
                + " where email = ? ";

        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, email);
            Contato contato = null;

            try (ResultSet resultSet = pstm.executeQuery()) {
                if (resultSet.next()) {
                    contato = new Contato();
                    contato.setId(resultSet.getLong("id"));
                    contato.setNome(resultSet.getString("nome"));
                    contato.setEmail(resultSet.getString("email"));
                    contato.setEndereco(resultSet.getString("endereco"));
                    if (resultSet.getDate("dataNascimento") != null) {
                        Calendar dataNascimento = Calendar.getInstance();
                        dataNascimento.setTime(resultSet.getDate("dataNascimento"));

                        contato.setDataNascimento(dataNascimento);
                    }
                }
            }
            return contato;
        }
    }

    public List<Contato> buscarPorNome(String nome) throws SQLException {
        List<Contato> contatos = new ArrayList<>();
        String sql = "select * from contatos "
                + " where nome = ? ";

        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, nome);

            try (ResultSet rs = pstm.executeQuery()) {

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
            }

            return contatos;
        }
    }

}
