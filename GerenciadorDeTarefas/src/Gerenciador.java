import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Gerenciador {
    
    public void adicionar(String descricao) {
        String sql = "INSERT INTO tarefas (descricao) VALUES (?)";

        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, descricao);
            stmt.executeUpdate();
            System.out.println("Tarefa adicionada!");
        } catch (SQLException e) {
            System.out.println("Erro ao adicionar: " + e.getMessage());
        }
    }

    public List<Tarefa> listar() {
        List<Tarefa> tarefas = new ArrayList<>();
        String sql = "SELECT * FROM tarefas";

        try (Connection con = Conexao.conectar();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                tarefas.add(new Tarefa(
                    rs.getInt("id"),
                    rs.getString("descricao"),
                    rs.getBoolean("concluida")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar: " + e.getMessage());
        }

        return tarefas;
    }

    public void concluir(int id) {
        String sql = "UPDATE tarefas SET concluida = TRUE WHERE id = ?";
        try ( Connection con = Conexao.conectar();
                PreparedStatement stmt = con.prepareStatement(sql))  {
            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            System.out.println(rows > 0 ? "Tarefa concluida!" : "ID nao econtrado.");
        } catch (SQLException e) {
            System.out.println("Erro ao concluir: " + e.getMessage());
        }    
    }

    public void remover(int id) {
        String sql = "DELETE FROM tarefas WHERE id = ?";
        try ( Connection con = Conexao.conectar();
            PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setInt(1, id);
                int rows = stmt.executeUpdate();
                System.out.println(rows > 0 ? "Tarefa removida!" : "ID nao encontrado.");
                
            } catch (SQLException e) {
                System.out.println("Erro ao remover: " + e.getMessage());
            }
    }
}
