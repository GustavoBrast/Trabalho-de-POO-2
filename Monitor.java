package org.teiacoltec.poo.tp2;

public class Monitor extends Pessoa {
    
    private String Matricula;
    private String Curso;
    
    public Monitor(String CPF, String nome, String nascimento, String email, String endereco, String matricula, String curso){
        super(CPF, nome, nascimento, email, endereco);
        this.matricula = matricula;
        this.curso = curso;

        
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
