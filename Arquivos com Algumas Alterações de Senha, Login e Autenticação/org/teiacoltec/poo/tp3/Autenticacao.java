package org.teiacoltec.poo.tp3;
import java.util.List;

public class Autenticacao {
    
    public static Pessoa autenticar (String login, String senha, List<Pessoa> usuarios) throws CredenciaisInvalidasException{


return usuarios.stream()
                   .filter(usuario -> usuario.getLogin().equals(login) && usuario.verificarSenha(senha))
                   .findFirst()
                   .orElseThrow(() -> new CredenciaisInvalidasException());

    }
  
/// eu acho que o logout teria que ser feito na main, já que não existe a classe usuário.

      public void logout(Pessoa usuario) {
        if (usuario != null) {
            System.out.println("\n" + usuario.getNome() + " deslogado com sucesso.");
        }
    }

}