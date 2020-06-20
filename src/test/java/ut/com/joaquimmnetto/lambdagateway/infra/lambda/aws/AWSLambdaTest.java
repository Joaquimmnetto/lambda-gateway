package ut.com.joaquimmnetto.lambdagateway.infra.lambda.aws;

import com.joaquimmnetto.lambdagateway.infra.lambda.aws.AWSLambda;
import com.joaquimmnetto.lambdagateway.infra.lambda.aws.AWSLambdaFacade;
import com.joaquimmnetto.lambdagateway.infra.tools.Serializer;
import com.joaquimmnetto.lambdagateway.model.LambdaIdentifier;
import com.joaquimmnetto.lambdagateway.model.LambdaPayload;
import org.junit.Test;

import java.util.Map;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AWSLambdaTest {

    private final Serializer serializer = mock(Serializer.class);
    private final AWSLambdaFacade lambdaFacade = mock(AWSLambdaFacade.class);
    LambdaIdentifier lambdaIdentifier = new LambdaIdentifier("identifier");

    @Test
    public void callsLambdaWithPayload() {
        Map payloadContent = mock(Map.class);
        LambdaPayload payload = new LambdaPayload(payloadContent);
        String serializedPayload = "{\"key\":\"value\"}";
        when(serializer.serialize(payloadContent)).thenReturn(serializedPayload);

        String rawResponse = "response";
        when(lambdaFacade.invoke(lambdaIdentifier.lambdaName(), serializedPayload)).thenReturn(rawResponse);

        Map<?,?> deserializedResponse = mock(Map.class);
        when(serializer.deserialize(rawResponse, Map.class)).thenReturn(deserializedResponse);

        AWSLambda lambda = new AWSLambda(serializer, lambdaIdentifier, lambdaFacade);
        Optional<Object> response = lambda.call(payload);

        assertThat(response.orElse(null), is(deserializedResponse));
    }

    @Test
    public void callsLambdaWithoutPayload() {
        LambdaPayload emptyPayload = new LambdaPayload(null);

        String rawResponse = "response";
        when(lambdaFacade.invoke(lambdaIdentifier.lambdaName(), "")).thenReturn(rawResponse);

        Map<?,?> deserializedResponse = mock(Map.class);
        when(serializer.deserialize(rawResponse, Map.class)).thenReturn(deserializedResponse);

        AWSLambda lambda = new AWSLambda(serializer, lambdaIdentifier, lambdaFacade);
        Optional<Object> response = lambda.call(emptyPayload);

        assertThat(response.orElse(null), is(deserializedResponse));
    }

    @Test
    public void processLambdaWithoutResponse() {
        Map payloadContent = mock(Map.class);
        LambdaPayload payload = new LambdaPayload(payloadContent);
        String serializedPayload = "{\"key\":\"value\"}";
        when(serializer.serialize(payloadContent)).thenReturn(serializedPayload);

        String rawResponse = "";
        when(lambdaFacade.invoke(lambdaIdentifier.lambdaName(), serializedPayload)).thenReturn(rawResponse);

        AWSLambda lambda = new AWSLambda(serializer, lambdaIdentifier, lambdaFacade);
        Optional<Object> response = lambda.call(payload);

        assertThat(response.isEmpty(), is(true));
    }

}