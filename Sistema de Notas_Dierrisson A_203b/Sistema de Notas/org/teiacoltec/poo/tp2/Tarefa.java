package org.teiacoltec.poo.tp2;

import java.io.Serializable;
import java.util.Date;
import java.util.ArrayList;

public class Tarefa implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private Aluno aluno;
    private Turma turma;
    private Atividade atividade;
    private float nota;

    public Tarefa(int id, Aluno aluno, Turma turma, Atividade atividade) {
        this.id = id;
        this.aluno = aluno;
        this.turma = turma;
        this.atividade = atividade;
        this.nota = 0.0f;
    }

    public Tarefa obtemTarefaPorID(int id) {
        for (Tarefa tarefa : Main.todasAsTarefas) {
            if (tarefa.getId() == id) {
                return tarefa;
            }
        }
        return null;
    }

    public Tarefa[] obtemTarefasDaPessoa(Pessoa p) {
        ArrayList<Tarefa> resultados = new ArrayList<>();
        if (p instanceof Aluno) {
            for (Tarefa tarefa : Main.todasAsTarefas) {
                if (tarefa.getAluno().getCpf().equals(p.getCpf())) {
                    resultados.add(tarefa);
                }
            }
        }
        return resultados.toArray(new Tarefa[0]);
    }

    public Tarefa[] obtemTarefasDaPessoa(Pessoa p, Date inicio, Date fim) {
        ArrayList<Tarefa> resultados = new ArrayList<>();
        if (p instanceof Aluno) {
            Tarefa[] tarefasDaPessoa = obtemTarefasDaPessoa(p);
            for (Tarefa tarefa : tarefasDaPessoa) {
                Atividade atv = tarefa.getAtividade();
                if (!atv.getInicio().after(fim) && !atv.getFim().before(inicio)) {
                    resultados.add(tarefa);
                }
            }
        }
        return resultados.toArray(new Tarefa[0]);
    }
    
   
    public int getId() { 
        return id; }
    public void setId(int id) { 
        this.id = id; }
    public Aluno getAluno() { 
        return aluno; }
    public void setAluno(Aluno aluno) { 
        this.aluno = aluno; }
    public Turma getTurma() { 
        return turma; }
    public void setTurma(Turma turma) { 
        this.turma = turma; }
    public Atividade getAtividade() { 
        return atividade; }
    public void setAtividade(Atividade atividade) { 
        this.atividade = atividade; }
    public float getNota() { 
        return nota; }
    public void setNota(float nota) { 
        this.nota = nota; }
}