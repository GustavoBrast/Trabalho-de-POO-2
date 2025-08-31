package org.teiacoltec.poo.tp2;

import java.io.Serializable;
import java.util.Date;

public abstract class Pessoa implements Serializable {
    private static final long serialVersionUID = 1L;

    private String cpf;
    private String nome;
    private Date nascimento;
    private String email;
    private String endereco;

    public Pessoa(String cpf, String nome, Date nascimento, String email, String endereco) {
        this.cpf = cpf;
        this.nome = nome;
        this.nascimento = nascimento;
        this.email = email;
        this.endereco = endereco;
    }

    public String obterInformacoes() {
        return "Nome: " + nome + "\nCPF: " + cpf + "\nNascimento: " + nascimento +
               "\nEmail: " + email + "\nEndereço: " + endereco;
    }

    
    public String getCpf() {
         return cpf; }
    public void setCpf(String cpf) { 
        this.cpf = cpf; }
    public String getNome() {
        return nome; }
    public void setNome(String nome) {
         this.nome = nome; }
    public Date getNascimento() { 
        return nascimento; }
    public void setNascimento(Date nascimento) {
         this.nascimento = nascimento; }
    public String getEmail() {
         return email; }
    public void setEmail(String email) {
         this.email = email; }
    public String getEndereco() {
         return endereco; }
    public void setEndereco(String endereco) { 
        this.endereco = endereco; }
}