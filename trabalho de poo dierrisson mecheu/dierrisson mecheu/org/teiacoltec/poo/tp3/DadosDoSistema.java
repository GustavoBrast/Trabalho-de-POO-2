package org.teiacoltec.poo.tp3;

import java.io.Serializable;
import java.util.List;

public class DadosDoSistema implements Serializable {
    private List<Turma> listaDeTurmas;
    private List<Pessoa> listaDePessoas;
    private List<Atividade> listaDeAtividades;
    private List<Tarefa> listaDeTarefas;
    private int proximoIdTarefa;

    public DadosDoSistema(List<Turma> turmas, List<Pessoa> pessoas, List<Atividade> atividades, List<Tarefa> tarefas, int proximoId) {
        this.listaDeTurmas = turmas;
        this.listaDePessoas = pessoas;
        this.listaDeAtividades = atividades;
        this.listaDeTarefas = tarefas;
        this.proximoIdTarefa = proximoId;
    }

    public List<Turma> getListaDeTurmas() {
        return listaDeTurmas;
    }

    public List<Pessoa> getListaDePessoas() {
        return listaDePessoas;
    }

    public List<Atividade> getListaDeAtividades() {
        return listaDeAtividades;
    }

    public List<Tarefa> getListaDeTarefas() {
        return listaDeTarefas;
    }

    public int getProximoIdTarefa() {
        return proximoIdTarefa;
    }
}
