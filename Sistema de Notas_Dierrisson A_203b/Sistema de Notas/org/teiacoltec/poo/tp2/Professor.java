package org.teiacoltec.poo.tp2;

import java.util.Date;

public class Professor extends Pessoa {
    private static final long serialVersionUID = 1L;
    private String matricula;
    private String formacao;

    public Professor(String cpf, String nome, Date nascimento, String email, String endereco, String matricula, String formacao) {
        super(cpf, nome, nascimento, email, endereco);
        this.matricula = matricula;
        this.formacao = formacao;
    }

    @Override
    public String obterInformacoes() {
        return super.obterInformacoes() + "\nMatricula: " + matricula + "\nFormação: " + formacao + "\nTipo: Professor";
    }
    

    public String getMatricula() { 
        return matricula; }
    public void setMatricula(String matricula) { 
        this.matricula = matricula; }
    public String getFormacao() { 
        return formacao; }
    public void setFormacao(String formacao) { 
        this.formacao = formacao; }
}