package org.teiacoltec.poo.tp2;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;

abstract public class Pessoa implements Serializable {

    private static final long serialVersionUID = 1L;

    private String CPF;
    private String nome;
    private LocalDate nascimento;
    private String email;
    private String endereco;
    private String login;
    private String senhaHash; // criptografado


    Pessoa(String CPF, String nome, LocalDate nascimento, String email, String endereco, String login, String senha){
    this.CPF = CPF;
    this.nome = nome;
    this.nascimento = nascimento;
    this.email = email;
    this.endereco = endereco;
    this.login = login;
    this.senhaHash = hashSenha(senha);
    }
    
 
    //// FUNÇÃO PARA CRIPTOGRAFIA, PEGUEI PRONTA.
 
    private String hashSenha(String senhaPlana) {
        try {
            // Obtém uma instância do algoritmo de hash SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            
            // Gera o hash dos bytes da senha (usando UTF-8 para consistência)
            byte[] hashBytes = digest.digest(senhaPlana.getBytes(StandardCharsets.UTF_8));
            
            // Converte o array de bytes para uma representação em String hexadecimal
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
            
        } catch (NoSuchAlgorithmException e) {
            // Este erro não deve acontecer em um ambiente Java padrão
            System.err.println("Erro: Algoritmo de hash SHA-256 não encontrado.");
            throw new RuntimeException("Erro ao hashear senha", e);
        }
    }


    public boolean verificarSenha(String tentativaSenha) {
        String hashTentativa = hashSenha(tentativaSenha);
        return this.senhaHash.equals(hashTentativa);
    }


    String obterInformacoes(){
  
     return "CPF: " + CPF + "\n" +
            "Nome: " + nome + "\n" +
            "Nascimento: " + nascimento + "\n" +
            "Email: " + email + "\n" +
            "Endereço: " + endereco;

    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getNascimento() {
        return nascimento;
    }

    public void setNascimento(LocalDate nascimento) {
        this.nascimento = nascimento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenhaHash() {
        return senhaHash;
    }

    public void setSenhaHash(String senhaHash) {
        this.senhaHash = senhaHash;
    }

}