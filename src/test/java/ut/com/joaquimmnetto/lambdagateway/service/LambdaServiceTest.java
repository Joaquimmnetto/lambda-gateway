package ut.com.joaquimmnetto.lambdagateway.service;

import com.joaquimmnetto.lambdagateway.model.Lambda;
import com.joaquimmnetto.lambdagateway.service.LambdaRegistry;
import com.joaquimmnetto.lambdagateway.service.LambdaService;
import org.junit.Test;

import java.util.Optional;

import static com.joaquimmnetto.lambdagateway.fixture.LambdaFixture.aLambdaIdentifier;
import static com.joaquimmnetto.lambdagateway.fixture.LambdaFixture.aLambdaPayload;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.hamcrest.CoreMatchers.is;


public class LambdaServiceTest {

    private final LambdaRegistry lambdaRegistry = mock(LambdaRegistry.class);
    private final LambdaService service = new LambdaService(lambdaRegistry);

    @Test
    public void callsIdentifierLambdaReturningItsResponse() {
        var anyIdentifier = aLambdaIdentifier();
        var anyPayload = aLambdaPayload();
        var lambdaFromRegistry = mock(Lambda.class);
        var lambdaResponse = Optional.of(mock(Object.class));
        when(lambdaRegistry.get(anyIdentifier)).thenReturn(lambdaFromRegistry);
        when(lambdaFromRegistry.call(anyPayload)).thenReturn(lambdaResponse);

        Object response = service.callLambda(anyIdentifier, anyPayload);

        assertThat(response, is(lambdaResponse));
    }

}