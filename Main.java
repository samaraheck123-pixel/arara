
import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

class Tarefa {
    int id;
    String nome;
    LocalDate dataEntrega;
    String prioridade;

    public Tarefa(int id, String nome, LocalDate dataEntrega, String prioridade) {
        this.id = id;
        this.nome = nome;
        this.dataEntrega = dataEntrega;
        this.prioridade = prioridade;
    }

    public void exibir() {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        System.out.println("--------------------------------");
        System.out.println("ID: " + id);
        System.out.println("Nome: " + nome);
        System.out.println("Data de entrega: " + dataEntrega.format(formato));
        System.out.println("Prioridade: " + prioridade);
        System.out.println("--------------------------------");
    }
}

public class Main {

    static ArrayList<Tarefa> tarefas = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);
    static int proximoId = 1;

    // Cadastrar uma nova tarefa
    public static void cadastrarTarefa() {

        System.out.println("\n=== CADASTRAR TAREFA ===");

        System.out.print("Nome da tarefa: ");
        String nome = scanner.nextLine();

        LocalDate data = null;

        while (data == null) {
            try {
                System.out.print("Data de entrega (dd/MM/yyyy): ");
                String dataTexto = scanner.nextLine();

                DateTimeFormatter formato =
                    DateTimeFormatter.ofPattern("dd/MM/yyyy");

                data = LocalDate.parse(dataTexto, formato);

            } catch (DateTimeParseException e) {
                System.out.println("Data inválida! Tente novamente.");
            }
        }

        String prioridade;

        while (true) {
            System.out.print("Prioridade (alta, media ou baixa): ");
            prioridade = scanner.nextLine().trim().toLowerCase();

            if (prioridade.equals("alta") ||
                prioridade.equals("media") ||
                prioridade.equals("baixa")) {
                break;
            }

            System.out.println("Prioridade inválida!");
        }

        Tarefa nova = new Tarefa(proximoId, nome, data, prioridade);

        tarefas.add(nova);

        System.out.println("\nTarefa cadastrada com sucesso!");
        System.out.println("ID da tarefa: " + proximoId);

        proximoId++;
    }

    // Listar todas as tarefas
    public static void listarTarefas() {

        System.out.println("\n=== TODAS AS TAREFAS ===");

        if (tarefas.isEmpty()) {
            System.out.println("Nenhuma tarefa cadastrada.");
            return;
        }

        for (Tarefa tarefa : tarefas) {
            tarefa.exibir();
        }
    }

    // Remover uma tarefa concluída
    public static void removerTarefa() {

        System.out.println("\n=== REMOVER TAREFA ===");

        if (tarefas.isEmpty()) {
            System.out.println("Não existem tarefas cadastradas.");
            return;
        }

        listarTarefas();

        System.out.print("Digite o ID da tarefa concluída: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < tarefas.size(); i++) {

            if (tarefas.get(i).id == id) {

                tarefas.remove(i);

                System.out.println("Tarefa removida com sucesso!");
                return;
            }
        }

        System.out.println("Tarefa não encontrada.");
    }

    // Ordenar tarefas usando Bubble Sort
    public static void ordenarTarefas() {

        System.out.println("\n=== TAREFAS ORDENADAS POR DATA ===");

        if (tarefas.isEmpty()) {
            System.out.println("Nenhuma tarefa cadastrada.");
            return;
        }

        int n = tarefas.size();

        for (int i = 0; i < n - 1; i++) {

            for (int j = 0; j < n - i - 1; j++) {

                if (tarefas.get(j).dataEntrega.isAfter(
                    tarefas.get(j + 1).dataEntrega)) {

                    Tarefa auxiliar = tarefas.get(j);

                    tarefas.set(j, tarefas.get(j + 1));

                    tarefas.set(j + 1, auxiliar);
                }
            }
        }

        System.out.println("Tarefas organizadas da data mais próxima para a mais distante!");

        for (Tarefa tarefa : tarefas) {
            tarefa.exibir();
        }
    }

    // Menu principal
    public static void main(String[] args) {

        int opcao;

        do {

            System.out.println("\n================================");
            System.out.println("   GERENCIADOR DE TAREFAS");
            System.out.println("================================");
            System.out.println("1 - Cadastrar nova tarefa");
            System.out.println("2 - Remover tarefa concluída");
            System.out.println("3 - Listar todas as tarefas");
            System.out.println("4 - Ordenar tarefas por data");
            System.out.println("0 - Sair");
            System.out.println("================================");
            System.out.print("Escolha uma opção: ");

            while (!scanner.hasNextInt()) {
                System.out.println("Digite uma opção válida!");
                scanner.nextLine();
                System.out.print("Escolha uma opção: ");
            }

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {

                case 1:
                    cadastrarTarefa();
                    break;

                case 2:
                    removerTarefa();
                    break;

                case 3:
                    listarTarefas();
                    break;

                case 4:
                    ordenarTarefas();
                    break;

                case 0:
                    System.out.println("Sistema encerrado!");
                    break;

                default:
                    System.out.println("Opção inválida!");
            }

        } while (opcao != 0);

        scanner.close();
    }
}
