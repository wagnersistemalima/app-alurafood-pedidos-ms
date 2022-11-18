package br.com.alurafood.pedidos.mapper;

import br.com.alurafood.pedidos.constant.ApiConstantRequestId;
import br.com.alurafood.pedidos.constant.ApiConstantVersion;
import br.com.alurafood.pedidos.exceptions.BadRequestExceptions;
import br.com.alurafood.pedidos.model.Observabilidade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class ObservabilidadeMapperTest {

    @InjectMocks
    private ObservabilidadeMapper observabilidadeMapper;

    private final static String version = ApiConstantVersion.versionApiOne;
    private final static String requestId = ApiConstantRequestId.requestIdAluraFood;
    private final static String correlationId = UUID.randomUUID().toString();
    private final static String resourceName = "resourceName";

    @Test
    @DisplayName("deve mapear a version, requestId, resourceName, correlationId para observabilidade")
    public void deveMapearComSucessoObservabilidade() {

        Observabilidade observabilidade = observabilidadeMapper.map(version, requestId, resourceName, correlationId);

        assertNotNull(observabilidade);
        assertEquals(version, observabilidade.getVersion());
        assertEquals(requestId, observabilidade.getRequestId());
        assertEquals(resourceName, observabilidade.getResourceName());
        assertEquals(correlationId, observabilidade.getCorrelationId());

    }

    @Test
    @DisplayName("deveria falhar BadRequestexception quando não for validado a version")
    public void deveFalharBadRequestExceptionVersion() {

        assertThrows(BadRequestExceptions.class, () -> {
            observabilidadeMapper.map("version", requestId, resourceName, correlationId);
        });
    }

    @Test
    @DisplayName("deveria falhar BadRequestexception quando não for validado a version")
    public void deveFalharBadRequestExceptionRequestId() {

        assertThrows(BadRequestExceptions.class, () -> {
            observabilidadeMapper.map(version, "requestId", resourceName, correlationId);
        });
    }

}