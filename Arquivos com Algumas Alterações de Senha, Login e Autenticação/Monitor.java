package org.teiacoltec.poo.tp2;

import java.time.LocalDate;

public class Monitor extends Pessoa {
    
    private static final long serialVersionUID = 1L;

    private String Matricula;
    private String Curso;
    
    public Monitor(String CPF, String nome, LocalDate nascimento, String email, String endereco, String login, String senhaHash, String Matricula, String Curso){
        super(CPF, nome, nascimento, email, endereco, login, senhaHash);
        this.Matricula = Matricula;
        this.Curso = Curso;

    }

    public String getMatricula() {
        return Matricula;
    }

    public void setMatricula(String Matricula) {
        this.Matricula = Matricula;
    }

    public String getCurso() {
        return Curso;
    }

    public void setCurso(String Curso) {
        this.Curso = Curso;
    }

}
