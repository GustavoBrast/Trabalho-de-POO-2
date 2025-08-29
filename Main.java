package org.teiacoltec.poo.tp2;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList; 
import java.util.List;
import java.util.Scanner; 

public class Main {

    private static List<Turma> listaDeTurmas = new ArrayList<>();
    private static List<Pessoa> listaDePessoas = new ArrayList<>();
    private static List<Atividade> listaDeAtividades = new ArrayList<>();
    private static List<Tarefa> listaDeTarefas = new ArrayList<>();
    private static int proximoIdTarefa = 1;
    private static DateTimeFormatter formatadorDeData = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void main(String[] args) {
        popularDadosIniciais();

        Scanner scanner = new Scanner(System.in);
        int opcaoEscolhida = -1; 

        while (opcaoEscolhida != 0) {
            exibirMenuPrincipal(); 
            try {
                opcaoEscolhida = Integer.parseInt(scanner.nextLine());

                switch (opcaoEscolhida) {
                    case 1:
                        criarTarefaParaTurma(scanner);
                        break;
                    case 2:
                        adicionarPessoaNaTurma(scanner);
                        break;
                    case 3:
                        listarParticipantesDaTurma(scanner);
                        break;
                    case 0:
                        System.out.println("Obrigado! Volte sempre!");
                        break;
                    default:
                        System.out.println("Opção inválida. Por favor, escolha um número do menu.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Erro: Por favor, digite apenas números.");
            } catch (Exception e) {
              
                System.out.println("Ops! Algo deu errado: " + e.getMessage());
            }
        }

        scanner.close(); 
    }


    private static void exibirMenuPrincipal() {
        System.out.println("\n===== BEM-VINDO AO SISTEMA DA ESCOLA =====");
        System.out.println("1. Criar e associar uma nova tarefa para uma turma");
        System.out.println("2. Adicionar uma pessoa a uma turma");
        System.out.println("3. Ver quem está em uma turma");
        System.out.println("0. Sair do Sistema");
        System.out.print("Digite sua opção: ");
    }

    private static void criarTarefaParaTurma(Scanner scanner) {
        System.out.println("\n--- Receita: Criar Nova Tarefa ---");
        System.out.print("Primeiro, qual o ID da turma? ");
        int idTurma = Integer.parseInt(scanner.nextLine());

        
        Turma turma = buscarTurmaPorId(idTurma);
        if (turma == null) {
            System.out.println("Erro: Não achei nenhuma turma com o ID " + idTurma);
            return;
        }

        
        System.out.print("Nome da Atividade: ");
        String nomeAtividade = scanner.nextLine();
        System.out.print("Descrição: ");
        String descAtividade = scanner.nextLine();
        System.out.print("Data de Início (dd/MM/yyyy): ");
        String inicioAtividade = scanner.nextLine();
        System.out.print("Data de Fim (dd/MM/yyyy): ");
        String fimAtividade = scanner.nextLine();
        System.out.print("Valor (pontos): ");
        float valorAtividade = Float.parseFloat(scanner.nextLine());

    
        Atividade novaAtividade = new Atividade(listaDeAtividades.size() + 1, nomeAtividade, descAtividade, inicioAtividade, fimAtividade, valorAtividade);
        listaDeAtividades.add(novaAtividade); 

        
        turma.associaAtividade(novaAtividade);
        System.out.println("Ok! Atividade '" + nomeAtividade + "' associada à turma '" + turma.getNome() + "'.");

        
        ArrayList<Aluno> alunosDaTurma = turma.obtemListaAlunos(true);
        if (alunosDaTurma.isEmpty()) {
            System.out.println("Aviso: Esta turma não tem alunos. Nenhuma tarefa foi criada.");
            return;
        }

       
        for (Aluno aluno : alunosDaTurma) {
            Tarefa novaTarefa = new Tarefa(proximoIdTarefa++, aluno, turma, novaAtividade);
            listaDeTarefas.add(novaTarefa);
        }

        System.out.println("Sucesso! " + alunosDaTurma.size() + " tarefas foram criadas para os alunos.");
    }


    private static void adicionarPessoaNaTurma(Scanner scanner) {
         System.out.println("\n--- Receita: Matricular Pessoa na Turma ---");
         System.out.print("Qual o CPF da pessoa? ");
         String cpf = scanner.nextLine();
         System.out.print("Qual o ID da turma? ");
         int idTurma = Integer.parseInt(scanner.nextLine());

         try {
             Pessoa pessoa = buscarPessoaPorCpf(cpf);
             Turma turma = buscarTurmaPorId(idTurma);

             if (pessoa == null || turma == null) {
                 System.out.println("Erro: Pessoa ou Turma não encontrada. Verifique o CPF e o ID.");
                 return;
             }
            
             turma.adicionarParticipante(pessoa);
             System.out.println("Sucesso! " + pessoa.getnome() + " foi adicionado(a) à turma " + turma.getNome() + ".");

         } catch (PessoaJaParticipanteException e) {
              System.out.println("Erro: Essa pessoa já está nessa turma!");
         } catch (Exception e) {
             System.out.println("Erro ao adicionar: " + e.getMessage());
         }
    }


    private static void listarParticipantesDaTurma(Scanner scanner) {
        System.out.println("\n--- Receita: Ver a Chamada da Turma ---");
        System.out.print("Qual o ID da Turma? ");
        int idTurma = Integer.parseInt(scanner.nextLine());
        
        Turma turma = buscarTurmaPorId(idTurma);
        if (turma == null) {
            System.out.println("Erro: Turma não encontrada.");
            return;
        }

        ArrayList<Pessoa> participantes = turma.obtemListaParticipantes();
        if (participantes.isEmpty()) {
            System.out.println("A turma '" + turma.getNome() + "' está vazia.");
            return;
        }
        
        System.out.println("----- Participantes da Turma: " + turma.getNome() + " -----");
        for(Pessoa p : participantes){
            String tipo = p instanceof Aluno ? "Aluno" : "Professor";
            System.out.println("- " + p.getnome() + " (Cargo: " + tipo + ")");
        }
    }
    

    private static Turma buscarTurmaPorId(int id) {
        for (Turma t : listaDeTurmas) {
            if (t.getID() == id) {
                return t; 
            }
        }
        return null; 
    }

    private static Pessoa buscarPessoaPorCpf(String cpf) {
        for (Pessoa p : listaDePessoas) {
            if (p.getCPF().equals(cpf)) {
                return p; 
            }
        }
        return null; 
    }
    
    /**
     * Apenas cria alguns dados falsos para que o programa não comece vazio
     * e possamos testar as funcionalidades sem ter que cadastrar tudo toda vez.
     */
    private static void popularDadosIniciais() {

        Aluno aluno1 = new Aluno("111", "João Silva", "10/05/2007", "joao@email.com", "Rua A", "A01", "DS");
        Aluno aluno2 = new Aluno("222", "Maria Santos", "15/03/2008", "maria@email.com", "Rua B", "A02", "DS");
        Professor prof1 = new Professor("333", "Carlos Souza", "20/08/1985", "carlos@email.com", "Rua C", "P01", "POO");
        listaDePessoas.addAll(List.of(aluno1, aluno2, prof1));

 
        Turma turma1 = new Turma(101, "203-A", "Manhã", LocalDate.now(), LocalDate.now().plusMonths(6), null, null, null, null);
        listaDeTurmas.add(turma1);
        
        System.out.println("Dados iniciais carregados: 2 alunos, 1 professor e 1 turma (ID 101).");
    }
}
