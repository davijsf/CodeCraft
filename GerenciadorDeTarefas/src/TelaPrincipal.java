import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TelaPrincipal extends JFrame {
    private Gerenciador gerenciador;
    private JTable tabela;
    private DefaultTableModel modelo;

    public TelaPrincipal() {
        super("Gerenciador de Tarefas");
        gerenciador = new Gerenciador();

        setLayout(new BorderLayout());
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Tabela
        modelo = new DefaultTableModel(new String[]{"ID", "Descrição", "Concluída"}, 0);
        tabela = new JTable(modelo);

        tabela.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            if (evt.getClickCount() == 2) {
                int linha = tabela.getSelectedRow();
                if (linha != -1) {
                    String descricao = modelo.getValueAt(linha, 1).toString();
                    JOptionPane.showMessageDialog(
                        TelaPrincipal.this, descricao, "Descrição", JOptionPane.INFORMATION_MESSAGE
                    );
                }
            }
        }
    });
        JScrollPane scroll = new JScrollPane(tabela);
        add(scroll, BorderLayout.CENTER);


        // Painel de botons
        JPanel botoes = new JPanel();
        JButton btnAdicionar = new JButton("Adicionar");
        JButton btnConcluir = new JButton("Concluir");
        JButton btnRemover = new JButton("Remover");
        JButton btnAtualizar = new JButton("Atualizar");

        botoes.add(btnAdicionar);
        botoes.add(btnConcluir);
        botoes.add(btnRemover);
        botoes.add(btnAtualizar);

        add(botoes, BorderLayout.SOUTH);


        // Listeners
        btnAdicionar.addActionListener(e -> adicionarTarefa());
        btnConcluir.addActionListener(e -> concluirTarefa());
        btnRemover.addActionListener(e -> removerTarefa());
        btnAtualizar.addActionListener(e -> carregarTarefas());

        carregarTarefas();
    }

    private void carregarTarefas() {
        modelo.setRowCount(0); // limpa tabela
        List<Tarefa> tarefas = gerenciador.listar();
        for (Tarefa t : tarefas) {
            modelo.addRow(new Object[] {
                t.getId(),
                t.getDescricao(),
                t.isConcluida() ? "✔" : "✗"
            });
        }
    }

    private void adicionarTarefa() {
        String descricao = JOptionPane.showInputDialog(this, "Descrição da tarefa:");
        if (descricao != null && !descricao.trim().isEmpty()) {
            gerenciador.adicionar(descricao);
            carregarTarefas();
        }
    }

    private void concluirTarefa() {
        int linha = tabela.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma tarefa!");
            return;
        }
        int id = (int) modelo.getValueAt(linha, 0);
        gerenciador.concluir(id);
        carregarTarefas();
    }

    private void removerTarefa() {
        int linha = tabela.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma tarefa1!");
            return;
        }
        int id = (int) modelo.getValueAt(linha, 0);
        gerenciador.remover(id);
        carregarTarefas();
    }
}


