public class Tarefa {
      private int id;
      private String descricao;
      private boolean concluida;
      
      public Tarefa(int id, String descricao, boolean concluida) {
        this.id = id;
        this.descricao = descricao;
        this.concluida = concluida;
      }

      public int getId() { return id;}
      public String getDescricao() { return descricao; }
      public boolean isConcluida() { return concluida; }

      public String toString() {
            return (concluida ? "[X] " : "[ ] ") + id + " - " + descricao;
      }
}
