package org.teiacoltec.poo.tp3;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class Atividade implements Serializable {

    private static final long serialVersionUID = 1L;

    private int ID;
    private String nome;
    private String descricao;
    private LocalDate inicio;
    private LocalDate fim;
    private float valor;

    Atividade(int ID, String nome, String descricao, LocalDate inicio, LocalDate fim, float valor){

        this.ID = ID;
        this.nome = nome;
        this.descricao = descricao;
        this.inicio = inicio;
        this.fim = fim;
        this.valor = valor;
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

    public float getValor(){
        return valor;
    }    

    public void setValor(float valor){
        this.valor = valor;
    }    

    public static Atividade obtemAtividadePorID(int id, ArrayList<Atividade> atividades) {
    for (Atividade atividade : atividades) {
        if (atividade.getID() == id) {
            return atividade;
        }
    }
    return null;
    }

}