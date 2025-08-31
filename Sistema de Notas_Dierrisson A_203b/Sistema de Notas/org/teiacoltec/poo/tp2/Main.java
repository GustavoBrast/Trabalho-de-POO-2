package org.teiacoltec.poo.tp2;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Main {

    
    public static ArrayList<Pessoa> todasAsPessoas = new ArrayList<>();
    public static ArrayList<Turma> todasAsTurmas = new ArrayList<>();
    public static ArrayList<Atividade> todasAsAtividades = new ArrayList<>();
    public static ArrayList<Tarefa> todasAsTarefas = new ArrayList<>();

  
    private static int proximoIdTurma = 1;
    private static int proximoIdAtividade = 1;
    private static int proximoIdTarefa = 1;

    
    private static Scanner scanner = new Scanner(System.in);
    private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private static final String ARQUIVO_DADOS = "escola.dat";

    public static void main(String[] args) {
        carregarDados();
        System.out.println("=========================================");
        System.out.println("   SISTEMA DE GESTÃO ESCOLAR - COLTEC");
        System.out.println("=========================================");

        int opcao = -1;
        while (opcao != 0) {
            exibirMenuPrincipal();
            try {
                opcao = Integer.parseInt(scanner.nextLine());
                roteadorPrincipal(opcao);
            } catch (NumberFormatException e) {
                System.out.println("\nERRO: Opção inválida. Por favor, digite um número.");
            } catch (Exception e) {
                System.out.println("\nERRO: " + e.getMessage());
            }
        }
        
        System.out.println("\nSalvando dados antes de sair...");
        salvarDados();
        System.out.println("Sistema finalizado. Até logo!");
    }

    private static void exibirMenuPrincipal() {
        System.out.println("\n--- MENU PRINCIPAL ---");
        System.out.println("1. Gerenciar Pessoas");
        System.out.println("2. Gerenciar Turmas");
        System.out.println("3. Gerenciar Atividades e Tarefas");
        System.out.println("0. Salvar e Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static void roteadorPrincipal(int opcao) throws Exception {
        switch (opcao) {
            case 1: menuPessoas(); break;
            case 2: menuTurmas(); break;
            case 3: menuAtividadesTarefas(); break;
            case 0: break;
            default: System.out.println("Opção inválida.");
        }
    }

  
    private static void menuPessoas() {
        int opcao = -1;
        while (opcao != 0) {
            try {
                System.out.println("\n--- Gerenciar Pessoas ---");
                System.out.println("1. Cadastrar nova Pessoa (Aluno/Professor/Monitor)");
                System.out.println("2. Listar todas as Pessoas");
                System.out.println("3. Atualizar dados de uma Pessoa");
                System.out.println("4. Remover Pessoa");
                System.out.println("0. Voltar ao Menu Principal");
                System.out.print("Escolha uma opção: ");
                opcao = Integer.parseInt(scanner.nextLine());

                switch(opcao) {
                    case 1:
                        System.out.print("Qual tipo de pessoa? (1-Aluno, 2-Professor, 3-Monitor): ");
                        int tipo = Integer.parseInt(scanner.nextLine());
                        System.out.print("Nome: "); String nome = scanner.nextLine();
                        System.out.print("CPF: "); String cpf = scanner.nextLine();
                        System.out.print("Email: "); String email = scanner.nextLine();
                        System.out.print("Endereço: "); String endereco = scanner.nextLine();
                        
                        if (tipo == 1) {
                            System.out.print("Matrícula do Aluno: "); String mat = scanner.nextLine();
                            System.out.print("Curso: "); String curso = scanner.nextLine();
                            todasAsPessoas.add(new Aluno(cpf, nome, new Date(), email, endereco, mat, curso));
                        } else if (tipo == 2) {
                            System.out.print("Matrícula do Professor: "); String mat = scanner.nextLine();
                            System.out.print("Formação: "); String formacao = scanner.nextLine();
                            todasAsPessoas.add(new Professor(cpf, nome, new Date(), email, endereco, mat, formacao));
                        } else if (tipo == 3) {
                             System.out.print("Matrícula do Monitor: "); String mat = scanner.nextLine();
                            System.out.print("Curso: "); String curso = scanner.nextLine();
                            todasAsPessoas.add(new Monitor(cpf, nome, new Date(), email, endereco, mat, curso));
                        } else { throw new Exception("Tipo de pessoa inválido."); }
                        System.out.println("SUCESSO: Pessoa cadastrada!");
                        break;
                    case 2:
                        System.out.println("\n--- Lista de Pessoas Cadastradas ---");
                        if (todasAsPessoas.isEmpty()) System.out.println("Nenhuma pessoa cadastrada.");
                        for (Pessoa p : todasAsPessoas) {
                            imprimirInformacoes(p);
                            System.out.println("--------------------");
                        }
                        break;
                    case 3:
                         System.out.print("Digite o CPF da pessoa para atualizar: ");
                         Pessoa pAtualizar = encontrarPessoaPorCpf(scanner.nextLine());
                         System.out.print("Digite o novo endereço (deixe em branco para não alterar): ");
                         String novoEndereco = scanner.nextLine();
                         if (!novoEndereco.isEmpty()) pAtualizar.setEndereco(novoEndereco);
                         
                         System.out.print("Digite o novo email (deixe em branco para não alterar): ");
                         String novoEmail = scanner.nextLine();
                         if (!novoEmail.isEmpty()) pAtualizar.setEmail(novoEmail);
                         
                         System.out.println("SUCESSO: Dados atualizados!");
                         break;
                    case 4:
                         System.out.print("Digite o CPF da pessoa para remover: ");
                         Pessoa pRemover = encontrarPessoaPorCpf(scanner.nextLine());
                         
                        
                         for (Turma t : todasAsTurmas) {
                             if (t.participa(pRemover)) {
                                 try {
                                     t.removerParticipante(pRemover);
                                     System.out.println(" -> Removido(a) da turma: " + t.getNome());
                                 } catch (PessoaNaoEncontradaException e) { /* Ignorar, pois já verificamos */ }
                             }
                         }
                         
                         todasAsPessoas.remove(pRemover);
                         System.out.println("SUCESSO: " + pRemover.getNome() + " foi removido(a) do sistema.");
                         break;
                    case 0: break;
                    default: System.out.println("Opção inválida.");
                }
            } catch (Exception e) {
                System.out.println("\nERRO: " + e.getMessage() + ". Tente novamente.");
            }
        }
    }


    private static void menuTurmas() {
        int opcao = -1;
        while (opcao != 0) {
            try {
                System.out.println("\n--- Gerenciar Turmas ---");
                System.out.println("1. Criar nova Turma");
                System.out.println("2. Listar todas as Turmas");
                System.out.println("3. Ver detalhes de uma Turma");
                System.out.println("4. Adicionar Participante a uma Turma");
                System.out.println("5. Remover Participante de uma Turma");
                System.out.println("0. Voltar ao Menu Principal");
                System.out.print("Escolha uma opção: ");
                opcao = Integer.parseInt(scanner.nextLine());

         switch(opcao) {
                case 1:
                     System.out.print("Nome da nova Turma: "); String nome = scanner.nextLine();
                    Turma novaTurma = new Turma(proximoIdTurma++, nome, "Descrição Padrão", new Date(), new Date());
                     todasAsTurmas.add(novaTurma);
                    System.out.println("SUCESSO: Turma '" + nome + "' criada!");
                break;
                case 2:
                        System.out.println("\n--- Lista de Turmas Cadastradas ---");
                        if (todasAsTurmas.isEmpty()) System.out.println("Nenhuma turma cadastrada.");
                        for(Turma t : todasAsTurmas) imprimirInformacoes(t);
                        break;
                      case 3:
                        System.out.print("Digite o ID da Turma para ver detalhes: ");
                        Turma turmaDetalhes = encontrarTurmaPorId(Integer.parseInt(scanner.nextLine()));
                        System.out.println("\n--- Detalhes da Turma: " + turmaDetalhes.getNome() + " ---");
                        Pessoa[] participantes = turmaDetalhes.obtemListaParticipantes();
                        if (participantes.length == 0) System.out.println(" -> Nenhum participante nesta turma.");
                        else {
                            System.out.println("Participantes:");
                            for(Pessoa p : participantes) System.out.println(" -> " + p.getNome() + " (" + p.getClass().getSimpleName() + ")");
                        }
                        break;
                    case 4:
                        System.out.print("Digite o CPF da Pessoa a ser adicionada: ");
                        Pessoa pAdd = encontrarPessoaPorCpf(scanner.nextLine());
                        System.out.print("Digite o ID da Turma: ");
                        Turma tAdd = encontrarTurmaPorId(Integer.parseInt(scanner.nextLine()));
                        tAdd.adicionarParticipante(pAdd);
                        System.out.println("SUCESSO: " + pAdd.getNome() + " adicionado(a) à turma " + tAdd.getNome());
                        break;
                    case 5:
                        System.out.print("Digite o CPF da Pessoa a ser removida: ");
                         Pessoa pRem = encontrarPessoaPorCpf(scanner.nextLine());
                        System.out.print("Digite o ID da Turma: ");
                        Turma tRem = encontrarTurmaPorId(Integer.parseInt(scanner.nextLine()));
                        tRem.removerParticipante(pRem);
                        System.out.println("SUCESSO: " + pRem.getNome() + " removido(a) da turma " + tRem.getNome());
                        break;
                     case 0: break;
                    default: System.out.println("Opção inválida.");
                }
            } catch (Exception e) {
                System.out.println("\nERRO: " + e.getMessage() + ". Tente novamente.");
            }
        }
    }
    

    private static void menuAtividadesTarefas() {
         int opcao = -1;
        while (opcao != 0) {
            try {
                System.out.println("\n--- Gerenciar Atividades e Tarefas ---");
                System.out.println("1. Criar Atividade e Tarefas para uma Turma");
                 System.out.println("2. Lançar/Atualizar nota para uma Tarefa");
                System.out.println("3. Listar todas as Tarefas");
                System.out.println("4. Listar todas as Atividades");
                System.out.println("0. Voltar ao Menu Principal");
                System.out.print("Escolha uma opção: ");
                opcao = Integer.parseInt(scanner.nextLine());
                
                switch(opcao) {
                    case 1: executarExemploCriarTarefa(); break;
                    case 2:
                        System.out.print("Digite o ID da Tarefa: ");
                        Tarefa tarefa = encontrarTarefaPorId(Integer.parseInt(scanner.nextLine()));
                        System.out.print("Digite a nota (de 0 a " + tarefa.getAtividade().getValor() + "): ");
                        float nota = Float.parseFloat(scanner.nextLine());
                        tarefa.setNota(nota);
                        System.out.println("SUCESSO: Nota lançada!");
                        break;
                    case 3:
                         System.out.println("\n--- Lista de Tarefas ---");
                         if(todasAsTarefas.isEmpty()) System.out.println("Nenhuma tarefa cadastrada.");
                         for(Tarefa t : todasAsTarefas) imprimirInformacoes(t);
                         break;
                    case 4:
                         System.out.println("\n--- Lista de Atividades ---");
                         if(todasAsAtividades.isEmpty()) System.out.println("Nenhuma atividade cadastrada.");
                         for(Atividade a : todasAsAtividades) imprimirInformacoes(a);
                         break;
                    case 0: break;
                    default: System.out.println("Opção inválida.");
                }
            } catch (Exception e) {
                 System.out.println("\nERRO: " + e.getMessage() + ". Tente novamente.");
            }
        }
    }

    private static void executarExemploCriarTarefa() throws Exception {
        System.out.println("\n--- Ação: Criar Tarefa para Turma ---");
        System.out.print("Dados: Digite o ID da turma: ");
        Turma turma = encontrarTurmaPorId(Integer.parseInt(scanner.nextLine()));
        
        System.out.print("Dados: Nome da nova atividade: "); String nomeAtividade = scanner.nextLine();
        System.out.print("Dados: Descrição da atividade: "); String descAtividade = scanner.nextLine();
        System.out.print("Dados: Data de início (dd/MM/yyyy): "); Date inicio = sdf.parse(scanner.nextLine());
        System.out.print("Dados: Data de fim (dd/MM/yyyy): "); Date fim = sdf.parse(scanner.nextLine());
        System.out.print("Dados: Valor da atividade: "); float valorAtividade = Float.parseFloat(scanner.nextLine());
        
        Atividade atividade = new Atividade(proximoIdAtividade++, nomeAtividade, descAtividade, inicio, fim, valorAtividade);
        todasAsAtividades.add(atividade);
        
        turma.associaAtividade(atividade);
        System.out.println("Ação: Atividade '" + nomeAtividade + "' associada à turma '" + turma.getNome() + "'.");
        
        Aluno[] alunosDaTurma = turma.obtemListaAlunos(false);
        System.out.println("Ação: Encontrados " + alunosDaTurma.length + " alunos na turma.");
        
        if(alunosDaTurma.length == 0) {
            System.out.println("AVISO: Nenhum aluno na turma para criar tarefas.");
            return;
        }

        for (Aluno aluno : alunosDaTurma) {
            Tarefa tarefa = new Tarefa(proximoIdTarefa++, aluno, turma, atividade);
            todasAsTarefas.add(tarefa);
            System.out.println("Ação: Tarefa criada para o aluno: " + aluno.getNome());
        }
        System.out.println("--- Exemplo concluído com sucesso! ---");
    }


    private static Pessoa encontrarPessoaPorCpf(String cpf) throws Exception {
        for (Pessoa p : todasAsPessoas) {
            if (p.getCpf().equals(cpf)) return p;
        }
        throw new PessoaNaoEncontradaException("Nenhuma pessoa encontrada com o CPF: " + cpf);
    }

    private static Turma encontrarTurmaPorId(int id) throws Exception {
        for (Turma t : todasAsTurmas) {
            if (t.getId() == id) return t;
        }
        throw new Exception("Nenhuma turma encontrada com o ID: " + id);
    }

    private static Tarefa encontrarTarefaPorId(int id) throws Exception {
        for (Tarefa t : todasAsTarefas) {
            if (t.getId() == id) return t;
        }
        throw new Exception("Nenhuma tarefa encontrada com o ID: " + id);
    }
    
   
    public static void imprimirInformacoes(Pessoa pessoa) {
        System.out.println(pessoa.obterInformacoes());
    }

    public static void imprimirInformacoes(Turma turma) {
        System.out.println("ID: " + turma.getId() + " | Nome: " + turma.getNome() + " | Participantes: " + turma.obtemListaParticipantes().length);
    }

    public static void imprimirInformacoes(Atividade atividade) {
        System.out.println("ID: " + atividade.getId() + " | Nome: " + atividade.getNome() + " | Valor: " + atividade.getValor());
    }
    
    public static void imprimirInformacoes(Tarefa tarefa) {
        System.out.println("ID Tarefa: " + tarefa.getId() + " | Aluno: " + tarefa.getAluno().getNome() + " | Atividade: " + tarefa.getAtividade().getNome() + " | Nota: " + tarefa.getNota());
    }

    private static void salvarDados() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO_DADOS))) {
            oos.writeObject(todasAsPessoas);
            oos.writeObject(todasAsTurmas);
            oos.writeObject(todasAsAtividades);
            oos.writeObject(todasAsTarefas);
            oos.writeObject(proximoIdTurma);
            oos.writeObject(proximoIdAtividade);
            oos.writeObject(proximoIdTarefa);
        } catch (IOException e) {
            System.out.println("ERRO CRÍTICO: Não foi possível salvar os dados. " + e.getMessage());
        }
    }

    private static void carregarDados() {
        File f = new File(ARQUIVO_DADOS);
        if (!f.exists()) {
            System.out.println("INFO: Nenhum arquivo de dados encontrado. Iniciando um novo sistema.");
            return;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
            todasAsPessoas = (ArrayList<Pessoa>) ois.readObject();
            todasAsTurmas = (ArrayList<Turma>) ois.readObject();
            todasAsAtividades = (ArrayList<Atividade>) ois.readObject();
            todasAsTarefas = (ArrayList<Tarefa>) ois.readObject();
            proximoIdTurma = (int) ois.readObject();
            proximoIdAtividade = (int) ois.readObject();
            proximoIdTarefa = (int) ois.readObject();
            System.out.println("INFO: Dados anteriores carregados com sucesso.");
        } catch (Exception e) {
            System.out.println("AVISO: O arquivo de dados 'escola.dat' foi encontrado, mas está corrompido ou é incompatível. Ele será ignorado.");
        }
    }
}