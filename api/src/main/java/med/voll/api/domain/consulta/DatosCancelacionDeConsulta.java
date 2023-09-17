package med.voll.api.domain.consulta;

import jakarta.validation.constraints.NotNull;

public record DatosCancelacionDeConsulta(Long idConsulta, MotivoCancelacion motivo) {
}
