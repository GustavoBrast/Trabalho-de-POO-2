//copia de atividade
package org.teiacoltec.poo.tp3;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Tarefa implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private int ID;
    private Aluno aluno;
    private Turma turma;
    private Atividade atividade;
    private float nota;

    public Tarefa(int ID, Aluno aluno, Turma turma, Atividade atividade, float nota) {
    this.ID = ID;
    this.aluno = aluno;
    this.turma = turma;
    this.atividade = atividade;
    this.nota = nota;    
    }
        
    public int getID(){
        return ID;
    }
        
    public void setID(int ID) {
        this.ID = ID;
    }
    
    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Turma getTurma() {
        return turma;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }

    public Atividade getAtividade() {
        return atividade;
    }

    public void setAtividade(Atividade atividade) {
        this.atividade = atividade;
    }

    public float getNota() {
        return nota;
    }

    public void setNota(float nota) {
        this.nota = nota;
    }
    

    public static Tarefa obtemTarefaPorID(int id, ArrayList<Tarefa> tarefas) {
        return tarefas.stream()                  
                .filter(tarefa -> tarefa.getID() == id) 
                .findFirst()                        
                .orElse(null);                      
    }

    
   
    
    // Este método agora retorna um ArrayList, compatível com o código em Main.
    public static ArrayList<Tarefa> obtemTarefasDaPessoa(Pessoa pessoa, List<Tarefa> todasAsTarefas) {
    ArrayList<Tarefa> tarefasDaPessoa = todasAsTarefas.stream()
            .filter(tarefa -> tarefa.getAluno().getCPF().equals(pessoa.getCPF()))
            .collect(Collectors.toCollection(ArrayList::new));
    
    return tarefasDaPessoa;
}
    
    public static ArrayList<Tarefa> obtemTarefasDaPessoa(Pessoa pessoa, LocalDate inicio, LocalDate fim, ArrayList<Tarefa> todasAsTarefas) {

        return todasAsTarefas.stream()
            .filter(tarefa -> tarefa.getAluno().getCPF().equals(pessoa.getCPF()))
            .filter(tarefa -> {
                LocalDate dataAtividade = tarefa.getAtividade().getInicio();
                return (dataAtividade.isEqual(inicio) || dataAtividade.isAfter(inicio)) &&
                       (dataAtividade.isEqual(fim) || dataAtividade.isBefore(fim));
            })
            .collect(Collectors.toCollection(ArrayList::new)); 
    }
}
