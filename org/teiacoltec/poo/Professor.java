package org.teiacoltec.poo.tp2;


public class Professor extends Pessoa {
    
    private String matricula;
    private String formacao;

    public Professor(String CPF, String nome, String nascimento, String email, String endereco, String matricula, String formacao){
        super(CPF, nome, nascimento, email, endereco);
        this.matricula = matricula;
        this.formacao = formacao;

       
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getFormacao() {
        return formacao;
    }

    public void setFormacao(String formacao) {
        this.formacao = formacao;
    }

}
