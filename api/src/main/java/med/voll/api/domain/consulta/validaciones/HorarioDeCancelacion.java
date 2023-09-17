package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DatosCancelacionDeConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class HorarioDeCancelacion implements ValidadorCancelacionDeConsulta {

    @Autowired
    private ConsultaRepository consultaRepository;
    @Override
    public void validar(DatosCancelacionDeConsulta datos) {

        var consulta = consultaRepository.getReferenceById(datos.idConsulta());
        var ahora = LocalDateTime.now();
        var diferenciaDe24Hs = Duration.between(ahora, consulta.getFecha()).toHours()>24;

        if(!diferenciaDe24Hs){
            throw new ValidationException("Las consultas deben cancelarse con una antelacion superior a 24 hs");
        }

    }
}
