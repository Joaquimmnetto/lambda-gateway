package ut.com.joaquimmnetto.lambdagateway.infra.handler;

import com.joaquimmnetto.lambdagateway.infra.handler.LambdaMessageHandler;
import com.joaquimmnetto.lambdagateway.infra.http.HTTPInboundMessage;
import com.joaquimmnetto.lambdagateway.infra.http.exception.InvalidParameterException;
import com.joaquimmnetto.lambdagateway.infra.inbound.InboundMessage;
import com.joaquimmnetto.lambdagateway.service.LambdaService;
import org.junit.Test;

import java.util.HashMap;
import java.util.Optional;

import static com.joaquimmnetto.lambdagateway.fixture.LambdaFixture.identifierFor;
import static com.joaquimmnetto.lambdagateway.fixture.LambdaFixture.payloadFor;
import static java.util.Collections.singletonMap;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

public class LambdaMessageHandlerTest {

    private final LambdaService lambdaService =  mock(LambdaService.class);
    private final LambdaMessageHandler handler = new LambdaMessageHandler(lambdaService);

    @Test
    public void callsNamedParamLambdaWithInboundMessageBodyReturningLambdaResponse() {
        Object lambdaMessage = mock(Object.class);
        String lambdaName = "lambda-name";
        Optional<Object> lambdaResponse = Optional.of(mock(Object.class));
        InboundMessage message = inboundMessageForLambda(lambdaName, lambdaMessage);
        when(lambdaService.callLambda(identifierFor(lambdaName), payloadFor(lambdaMessage)))
                .thenReturn(lambdaResponse);

        Optional<Object> response = handler.handle(message);

        assertThat(lambdaResponse, is(response));
    }

    @Test
    public void callsNamedParamLambdaWithoutInboundMessageReturningLambdaResponse() {
        Object lambdaMessage = null;
        String lambdaName = "lambda-name";
        Optional<Object> lambdaResponse = Optional.of(mock(Object.class));
        InboundMessage message = inboundMessageForLambda(lambdaName, lambdaMessage);
        when(lambdaService.callLambda(identifierFor(lambdaName), payloadFor(lambdaMessage)))
                .thenReturn(lambdaResponse);

        Optional<Object> response = handler.handle(message);

        assertThat(lambdaResponse, is(response));
    }

    @Test(expected= InvalidParameterException.class)
    public void throwsExceptionWhenNoLambdaNameIsProvided() {
        Object lambdaMessage = mock(Object.class);
        InboundMessage message = inboundMessageForLambda(null, lambdaMessage);

        handler.handle(message);

        fail("Should had thrown InvalidParameter exception");
    }



    private HTTPInboundMessage inboundMessageForLambda(String lambdaName, Object lambdaMessage) {
        return new HTTPInboundMessage(lambdaMessage,
                new HashMap<>(), singletonMap(":lambda_name", lambdaName), new HashMap<>());
    }

}