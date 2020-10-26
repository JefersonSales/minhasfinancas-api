package io.github.jefersonsales.minhasfinancas.DTO;

import io.github.jefersonsales.minhasfinancas.model.entity.Usuario;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {

    private String nome;
    private String email;

    public UserDTO(Usuario user) {
        setNome(user.getNome());
        setEmail(user.getEmail());
    }

    public void setEmail(String email) {
        if(email.contains("@")) {
            this.email = email;
        }else{
            this.email = null;
        }
    }
}
