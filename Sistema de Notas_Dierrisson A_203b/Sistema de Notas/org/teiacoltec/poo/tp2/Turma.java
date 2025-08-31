package org.teiacoltec.poo.tp2;

import java.io.Serializable;
import java.util.Date;
import java.util.ArrayList;

public class Turma implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String nome;
    private String descricao;
    private Date inicio;
    private Date fim;
    private Pessoa[] participantes;
    private Turma turmaPai;
    private Turma[] turmasFilhas;
    private Atividade[] atividades;

    public Turma(int id, String nome, String descricao, Date inicio, Date fim) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.inicio = inicio;
        this.fim = fim;
        this.participantes = new Pessoa[0];
        this.turmasFilhas = new Turma[0];
        this.atividades = new Atividade[0];
    }

    public Turma(Turma turmaPai) {
        this(0, "Subturma de " + turmaPai.getNome(), "Subturma", new Date(), new Date());
        this.turmaPai = turmaPai;
    }


    public Pessoa[] obtemListaParticipantes() {
        return participantes;
    }

    
    public void adicionarParticipante(Pessoa p) throws PessoaJaParticipanteException {
        if (participa(p)) {
            throw new PessoaJaParticipanteException("Pessoa ja participa da turma.");
        }
        Pessoa[] novoArray = new Pessoa[this.participantes.length + 1];
        System.arraycopy(this.participantes, 0, novoArray, 0, this.participantes.length);
        novoArray[this.participantes.length] = p;
        this.participantes = novoArray;
    }

    public void removerParticipante(Pessoa p) throws PessoaNaoEncontradaException {
        if (!participa(p)) {
            throw new PessoaNaoEncontradaException("Pessoa nao encontrada na turma.");
        }
        Pessoa[] novoArray = new Pessoa[this.participantes.length - 1];
        int index = 0;
        for (Pessoa participante : this.participantes) {
            if (!participante.getCpf().equals(p.getCpf())) {
                novoArray[index++] = participante;
            }
        }
        this.participantes = novoArray;
    }

    public boolean participa(Pessoa p) {
        for (Pessoa participante : this.participantes) {
            if (participante.getCpf().equals(p.getCpf())) {
                return true;
            }
        }
        return false;
    }


    public void associaSubturma(Turma t) {
        Turma[] novoArray = new Turma[this.turmasFilhas.length + 1];
        System.arraycopy(this.turmasFilhas, 0, novoArray, 0, this.turmasFilhas.length);
        novoArray[this.turmasFilhas.length] = t;
        this.turmasFilhas = novoArray;
    }
    
    private <T> T[] concatArrays(T[] arr1, T[] arr2) {
        if (arr1 == null || arr1.length == 0) return arr2;
        if (arr2 == null || arr2.length == 0) return arr1;
        T[] result = (T[]) java.lang.reflect.Array.newInstance(arr1[0].getClass(), arr1.length + arr2.length);
        System.arraycopy(arr1, 0, result, 0, arr1.length);
        System.arraycopy(arr2, 0, result, arr1.length, arr2.length);
        return result;
    }

    public Professor[] obtemListaProfessores(boolean completa) {
        ArrayList<Professor> lista = new ArrayList<>();
        for (Pessoa p : this.participantes) {
            if (p instanceof Professor) {
                lista.add((Professor) p);
            }
        }
        Professor[] resultado = lista.toArray(new Professor[0]);
        if (completa) {
            for (Turma filha : this.turmasFilhas) {
                resultado = concatArrays(resultado, filha.obtemListaProfessores(true));
            }
        }
        return resultado;
    }

    public Aluno[] obtemListaAlunos(boolean completa) {
        ArrayList<Aluno> lista = new ArrayList<>();
        for (Pessoa p : this.participantes) {
            if (p instanceof Aluno) {
                lista.add((Aluno) p);
            }
        }
        Aluno[] resultado = lista.toArray(new Aluno[0]);
        if (completa) {
            for (Turma filha : this.turmasFilhas) {
                resultado = concatArrays(resultado, filha.obtemListaAlunos(true));
            }
        }
        return resultado;
    }
    
    public Monitor[] obtemListaMonitores(boolean completa) {
        ArrayList<Monitor> lista = new ArrayList<>();
        for (Pessoa p : this.participantes) {
            if (p instanceof Monitor) {
                lista.add((Monitor) p);
            }
        }
        Monitor[] resultado = lista.toArray(new Monitor[0]);
        if (completa) {
            for (Turma filha : this.turmasFilhas) {
                resultado = concatArrays(resultado, filha.obtemListaMonitores(true));
            }
        }
        return resultado;
    }

    public void associaAtividade(Atividade a) throws AtividadeJaAssociadaATurmaException {
        for(Atividade atv : this.atividades) {
            if(atv.getId() == a.getId()) {
                throw new AtividadeJaAssociadaATurmaException("Atividade ja associada.");
            }
        }
        Atividade[] novoArray = new Atividade[this.atividades.length + 1];
        System.arraycopy(this.atividades, 0, novoArray, 0, this.atividades.length);
        novoArray[this.atividades.length] = a;
        this.atividades = novoArray;
    }

    public void desassociaAtividade(Atividade a) throws AtividadeNaoAssociadaATurmaException {
        boolean encontrada = false;
        for(Atividade atv : this.atividades) {
            if(atv.getId() == a.getId()) {
                encontrada = true;
                break;
            }
        }
        if(!encontrada) {
            throw new AtividadeNaoAssociadaATurmaException("Atividade nao associada.");
        }
        Atividade[] novoArray = new Atividade[this.atividades.length - 1];
        int index = 0;
        for (Atividade atv : this.atividades) {
            if (atv.getId() != a.getId()) {
                novoArray[index++] = atv;
            }
        }
        this.atividades = novoArray;
    }
  
    public Atividade[] obtemAtividadesDaTurma(boolean completa) {
         Atividade[] resultado = this.atividades;
         if(completa) {
             for(Turma filha : this.turmasFilhas) {
                 resultado = concatArrays(resultado, filha.obtemAtividadesDaTurma(true));
             }
         }
         return resultado;
    }

    public Atividade[] obtemAtividadesDaTurma(boolean completa, Date inicio, Date fim) {
        Atividade[] todas = obtemAtividadesDaTurma(completa);
        ArrayList<Atividade> filtradas = new ArrayList<>();
        for(Atividade atv : todas) {
            if(!atv.getInicio().after(fim) && !atv.getFim().before(inicio)) {
                filtradas.add(atv);
            }
        }
        return filtradas.toArray(new Atividade[0]);
    }

    public Turma obtemTurmaPorID(int id) {
        for (Turma t : Main.todasAsTurmas) {
            if (t.getId() == id) {
                return t;
            }
        }
        return null;
    }

    public Turma[] obtemTurmasDaPessoa(Pessoa p) {
        ArrayList<Turma> resultados = new ArrayList<>();
        for (Turma t : Main.todasAsTurmas) {
            if (t.participa(p)) {
                resultados.add(t);
            }
        }
        return resultados.toArray(new Turma[0]);
    }
    
 
    public int getId() { 
        return id; }
    public String getNome() { 
        return nome; }
}