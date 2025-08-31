package org.teiacoltec.poo.tp2;

import java.util.Date;


public class Aluno extends Pessoa {
    private static final long serialVersionUID = 1L;
    private String matricula;
    private String curso;

    public Aluno(String cpf, String nome, Date nascimento, String email, String endereco, String matricula, String curso) {
        super(cpf, nome, nascimento, email, endereco);
        this.matricula = matricula;
        this.curso = curso;
    }

    @Override
    public String obterInformacoes() {
        return super.obterInformacoes() + "\nMatricula: " + matricula + "\nCurso: " + curso + "\nTipo: Aluno";
    }
    
   
    public String getMatricula() {
      return matricula; }
    public void setMatricula(String matricula) { 
     this.matricula = matricula; }
    public String getCurso() { 
     return curso; }
    public void setCurso(String curso) {
      this.curso = curso; }
}