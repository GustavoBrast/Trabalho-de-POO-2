// a maioria é bem chat gpt msm, pq seria foda mudar tudo isso pra array list manualmente.
// vou dar uma conferida depois se consigo dar uma mudada no final.

package org.teiacoltec.poo.tp2;

import java.time.LocalDate;
import java.util.ArrayList;

public class Turma {

    private int ID;
    private String nome;
    private String descricao;
    private LocalDate inicio;
    private LocalDate fim;
    private ArrayList<Pessoa> participantes;
    private Turma turma_pai;
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
        for (Pessoa p : participantes) {
            if (p.getCPF().equals(pessoa.getCPF())) {
                throw new PessoaJaParticipanteException();
            }
        }
        participantes.add(pessoa);
    }

    public void removerParticipante(Pessoa pessoa) throws PessoaNaoEncontradaException {
    Pessoa paraRemover = null;

    for (Pessoa pess : participantes) {
        if (pess.getCPF().equals(pessoa.getCPF())) {
            paraRemover = pess;
            break;
        }
    }

    if (paraRemover != null) {
        participantes.remove(paraRemover);
    } else {
        throw new PessoaNaoEncontradaException();
    }
    }


    public boolean participa(Pessoa pessoa) {
        for (Pessoa p : participantes) {
            if (p.getCPF().equals(pessoa.getCPF())) {
                return true;
            }
        }
        return false;
    }

    public void associaSubturma(Turma subturma) {
        turmas_filhas.add(subturma);
        subturma.setTurmaPai(this);
    }

    public ArrayList<Professor> obtemListaProfessores(boolean completa) {
        ArrayList<Professor> lista = new ArrayList<>();
        for (Pessoa pessoa : participantes) {
            if (pessoa instanceof Professor && !contido(lista, (Professor) pessoa)) {
                lista.add((Professor)pessoa);
            }
        }
        if (completa) {
            for (Turma filha : turmas_filhas) {
                for (Professor professor : filha.obtemListaProfessores(true)) {
                    if (!contido(lista, professor)) {
                        lista.add(professor);
                    }
                }
            }
        }
        return lista;
    }

    public ArrayList<Aluno> obtemListaAlunos(boolean completa) {
        ArrayList<Aluno> lista = new ArrayList<>();
        for (Pessoa pessoa : participantes) {
            if (pessoa instanceof Aluno && !contido(lista, (Aluno) pessoa)) {
                lista.add((Aluno) pessoa);
            }
        }
        if (completa) {
            for (Turma filha : turmas_filhas) {
                for (Aluno aluno : filha.obtemListaAlunos(true)) {
                    if (!contido(lista, aluno)) {
                        lista.add(aluno);
                    }
                }
            }
        }
        return lista;
    }

    public ArrayList<Monitor> obtemListaMonitores(boolean completa) {
        ArrayList<Monitor> lista = new ArrayList<>();
        for (Pessoa pessoa : participantes) {
            if (pessoa instanceof Monitor && !contido(lista, (Monitor) pessoa)) {
                lista.add((Monitor) pessoa);
            }
        }
        if (completa) {
            for (Turma filha : turmas_filhas) {
                for (Monitor monitor : filha.obtemListaMonitores(true)) {
                    if (!contido(lista, monitor)) {
                        lista.add(monitor);
                    }
                }
            }
        }
        return lista;
    }

    public void associaAtividade(Atividade atividade) {
        atividades.add(atividade);
    }

    public void desassociaAtividade(Atividade atividade) {
        atividades.remove(atividade);
    }

    public ArrayList<Atividade> obtemAtividadesDaTurma(boolean completa) {
    ArrayList<Atividade> lista = new ArrayList<>();
    
    for (Atividade atividade : atividades) {
        lista.add(atividade);
    }

    if (completa) {
        for (Turma filha : turmas_filhas) {
            ArrayList<Atividade> listaFilha = filha.obtemAtividadesDaTurma(true);
            for (Atividade a : listaFilha) {
                lista.add(a);
            }
        }
    }

    return lista;
    }


    public ArrayList<Atividade> obtemAtividadesDaTurma(boolean completa, LocalDate inicio, LocalDate fim) {
    ArrayList<Atividade> lista = new ArrayList<>();

    for (Atividade atividade : atividades) {
        LocalDate dataInicio = atividade.getInicio();

        if ((dataInicio.isEqual(inicio) || dataInicio.isAfter(inicio)) && (dataInicio.isEqual(fim) || dataInicio.isBefore(fim))) {
            lista.add(atividade);
        }
    }

    if (completa) {
        for (Turma filha : turmas_filhas) {
            ArrayList<Atividade> atividadesFilhas = filha.obtemAtividadesDaTurma(true, inicio, fim);
            for (Atividade a : atividadesFilhas) {
                lista.add(a);
            }
        }
    }

    return lista;
    }

    public ArrayList<Atividade> obtemAtividadesDaTurmaCompleta(boolean completa) {
        return obtemAtividadesDaTurma(completa);
    }

    public ArrayList<Atividade> obtemAtividadesDaTurmaCompleta(boolean completa, LocalDate inicio, LocalDate fim) {
        return obtemAtividadesDaTurma(completa, inicio, fim);
    }

    public Turma obtemTurmaPorID(int id) {
        if(this.ID == id){
        return this;
        }
        for (Turma filha : turmas_filhas) {
            Turma encontrada = filha.obtemTurmaPorID(id);
            if (encontrada != null) return encontrada;
        }
        return null;
    }

    public ArrayList<Turma> obtemTurmasDaPessoa(Pessoa pessoa) {
    ArrayList<Turma> turmas = new ArrayList<>();

    if (participa(pessoa)) {
        turmas.add(this);
    }

    for (Turma filha : turmas_filhas) {
        ArrayList<Turma> turmasDaFilha = filha.obtemTurmasDaPessoa(pessoa);
        for (Turma turma : turmasDaFilha) {
            turmas.add(turma);
        }
    }

    return turmas;
    }

// ? extends gostaria de mudar
    private boolean contido(ArrayList<? extends Pessoa> lista, Pessoa p) {
        for (Pessoa existente : lista) {
            if (existente.getCPF().equals(p.getCPF())) {
                return true;
            }
        }
        return false;
    }
}
