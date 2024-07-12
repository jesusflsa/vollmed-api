package med.voll.api.domain.consultas.validaciones;

import med.voll.api.domain.consultas.DatosAgendarConsulta;

public interface ValidadorDeConsultas {
    void validar(DatosAgendarConsulta datos);
}
