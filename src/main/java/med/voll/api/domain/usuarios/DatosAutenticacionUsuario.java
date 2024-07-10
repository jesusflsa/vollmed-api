package med.voll.api.domain.usuarios;

import jakarta.validation.constraints.NotBlank;

public record DatosAutenticacionUsuario(
        @NotBlank
        String login,
        @NotBlank
        String clave
) {}
