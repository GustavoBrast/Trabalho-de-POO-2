package org.teiacoltec.poo.tp3;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Turma implements Serializable {

    private static final long serialVersionUID = 1L;

    private int ID;
    private String nome;
    private String descricao;
    private LocalDate inicio;
    private LocalDate fim;
    private ArrayList<Pessoa> participantes;
    private transient Turma turma_pai;
    private ArrayList<Turma> turmas_filhas;
    private ArrayList<Atividade> atividades;

    public Turma(int ID, String nome, String descricao, LocalDate inicio, LocalDate fim, ArrayList<Pessoa> participantes, Turma turma_pai, ArrayList<Turma> turmas_filhas, ArrayList<Atividade> atividades) {

        this.ID = ID;
        this.nome = nome;
        this.descricao = descricao;
        this.inicio = inicio;
        this.fim = fim;
        if (participantes != null) {
        this.participantes = participantes;
        } else {
            this.participantes = new ArrayList<>();
          }
        if (turmas_filhas != null) {
        this.turmas_filhas = turmas_filhas;
        } else {
            this.turmas_filhas = new ArrayList<>();
          }
        if (atividades != null) {
        this.atividades = atividades;
        } else {
            this.atividades = new ArrayList<>();
          }
        this.turma_pai = turma_pai;
    }

    public Turma(Turma turmaPai) {
        this.turma_pai = turmaPai;
        this.participantes = new ArrayList<>();
        this.turmas_filhas = new ArrayList<>();
        this.atividades = new ArrayList<>();

        if (turmaPai != null) {
            turmaPai.associaSubturma(this);
        }
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getInicio() {
        return inicio;
    }

    public void setInicio(LocalDate inicio) {
        this.inicio = inicio;
    }

    public LocalDate getFim() {
        return fim;
    }

    public void setFim(LocalDate fim) {
        this.fim = fim;
    }

    public Turma getTurmaPai() {
        return turma_pai;
    }

    public void setTurmaPai(Turma turma_pai) {
        this.turma_pai = turma_pai;
    }

    public ArrayList<Turma> getTurmasFilhas() {
        return turmas_filhas;
    }

    public void setTurmasFilhas(ArrayList<Turma> turmas_filhas) {
        this.turmas_filhas = turmas_filhas;
    }

    public ArrayList<Atividade> getAtividades() {
        return atividades;
    }

    public void setAtividades(ArrayList<Atividade> atividades) {
        this.atividades = atividades;
    }
    
    public ArrayList<Pessoa> obtemListaParticipantes() {
        return participantes;
    }

    public void adicionarParticipante(Pessoa pessoa) throws PessoaJaParticipanteException {
        if (participa(pessoa)) {
            throw new PessoaJaParticipanteException();
        }
        participantes.add(pessoa);
    }

    public void removerParticipante(Pessoa pessoa) throws PessoaNaoEncontradaException {
           
        boolean removido = participantes.removeIf(p -> p.getCPF().equals(pessoa.getCPF()));
        if (!removido) {
            throw new PessoaNaoEncontradaException();
        }
    }


    public boolean participa(Pessoa pessoa) {
        
        return participantes.stream()
                .anyMatch(p -> p.getCPF().equals(pessoa.getCPF()));
    }

    public void associaSubturma(Turma subturma) {
        turmas_filhas.add(subturma);
        subturma.setTurmaPai(this);
    }
    

    public ArrayList<Professor> obtemListaProfessores(boolean completa) {
        Stream<Professor> professoresAtuais = participantes.stream()
                .filter(p -> p instanceof Professor)
                .map(p -> (Professor) p);

        if (completa) {
            Stream<Professor> professoresFilhas = turmas_filhas.stream()
                    .flatMap(filha -> filha.obtemListaProfessores(true).stream());
            
            return Stream.concat(professoresAtuais, professoresFilhas)
                    .distinct()
                    .collect(Collectors.toCollection(ArrayList::new));
        }

        return professoresAtuais.collect(Collectors.toCollection(ArrayList::new));
    }
    

     public ArrayList<Aluno> obtemListaAlunos(boolean completa) {
        Stream<Aluno> alunosAtuais = participantes.stream()
                .filter(p -> p instanceof Aluno)
                .map(p -> (Aluno) p);

        if (completa) {
            Stream<Aluno> alunosFilhas = turmas_filhas.stream()
                    .flatMap(filha -> filha.obtemListaAlunos(true).stream());
            
            return Stream.concat(alunosAtuais, alunosFilhas)
                    .distinct()
                    .collect(Collectors.toCollection(ArrayList::new));
        }

        return alunosAtuais.collect(Collectors.toCollection(ArrayList::new));
    }
    

     public ArrayList<Monitor> obtemListaMonitores(boolean completa) {
        Stream<Monitor> monitoresAtuais = participantes.stream()
                .filter(p -> p instanceof Monitor)
                .map(p -> (Monitor) p);
        
        if (completa) {
            Stream<Monitor> monitoresFilhas = turmas_filhas.stream()
                .flatMap(filha -> filha.obtemListaMonitores(true).stream());

            return Stream.concat(monitoresAtuais, monitoresFilhas)
                .distinct()
                .collect(Collectors.toCollection(ArrayList::new));
        }
        
        return monitoresAtuais.collect(Collectors.toCollection(ArrayList::new));
    }
    

     public void associaAtividade(Atividade atividade) {
        atividades.add(atividade);
    }

    public void desassociaAtividade(Atividade atividade) {
        atividades.remove(atividade);
    }
    

    public ArrayList<Atividade> obtemAtividadesDaTurma(boolean completa, LocalDate inicio, LocalDate fim) {
        Stream<Atividade> atividadesAtuaisFiltradas = atividades.stream()
            .filter(atividade -> {
                LocalDate dataInicio = atividade.getInicio();
                return (dataInicio.isEqual(inicio) || dataInicio.isAfter(inicio)) &&
                       (dataInicio.isEqual(fim) || dataInicio.isBefore(fim));
            });
    
        if (completa) {
            Stream<Atividade> atividadesFilhas = turmas_filhas.stream()
                .flatMap(filha -> filha.obtemAtividadesDaTurma(true, inicio, fim).stream());
            
            return Stream.concat(atividadesAtuaisFiltradas, atividadesFilhas)
                .collect(Collectors.toCollection(ArrayList::new));
        }
    
        return atividadesAtuaisFiltradas.collect(Collectors.toCollection(ArrayList::new));
    }


    public ArrayList<Atividade> obtemAtividadesDaTurma(boolean completa) {
        Stream<Atividade> atividadesAtuais = atividades.stream();
    
        if (completa) {
            Stream<Atividade> atividadesFilhas = turmas_filhas.stream()
                    .flatMap(filha -> filha.obtemAtividadesDaTurma(true).stream());
            
            return Stream.concat(atividadesAtuais, atividadesFilhas)
                    .collect(Collectors.toCollection(ArrayList::new));
        }
        return atividadesAtuais.collect(Collectors.toCollection(ArrayList::new));
    }


    public ArrayList<Atividade> obtemAtividadesDaTurmaCompleta(boolean completa) {
        return obtemAtividadesDaTurma(completa);
    }

    public ArrayList<Atividade> obtemAtividadesDaTurmaCompleta(boolean completa, LocalDate inicio, LocalDate fim) {
        return obtemAtividadesDaTurma(completa, inicio, fim);
    }
    

   public Turma obtemTurmaPorID(int id) {
        if (this.ID == id) {
            return this;
        }
        return turmas_filhas.stream()
            .map(filha -> filha.obtemTurmaPorID(id))
            .filter(encontrada -> encontrada != null)
            .findFirst()
            .orElse(null);
    }
    
    
    public ArrayList<Turma> obtemTurmasDaPessoa(Pessoa pessoa) {
            Stream<Turma> turmasAtuais = participa(pessoa) ? Stream.of(this) : Stream.empty();
            
            Stream<Turma> turmasFilhas = turmas_filhas.stream()
                    .flatMap(filha -> filha.obtemTurmasDaPessoa(pessoa).stream());
    
            return Stream.concat(turmasAtuais, turmasFilhas)
                    .collect(Collectors.toCollection(ArrayList::new));
        }
    }


    private <generica extends Pessoa> boolean contido(ArrayList<generica> lista, Pessoa pessoa) {
    for (Pessoa existente : lista) {
        if (existente.getCPF().equals(pessoa.getCPF())) {
            return true;
        }
    }
    return false;
    }
}
