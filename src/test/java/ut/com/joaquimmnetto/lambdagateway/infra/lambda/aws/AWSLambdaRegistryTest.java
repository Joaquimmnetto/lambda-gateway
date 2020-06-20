package ut.com.joaquimmnetto.lambdagateway.infra.lambda.aws;

import com.joaquimmnetto.lambdagateway.fixture.LambdaFixture;
import com.joaquimmnetto.lambdagateway.infra.lambda.aws.AWSLambdaFacade;
import com.joaquimmnetto.lambdagateway.infra.lambda.aws.AWSLambdaRegistry;
import com.joaquimmnetto.lambdagateway.infra.tools.Serializer;
import com.joaquimmnetto.lambdagateway.model.Lambda;
import com.joaquimmnetto.lambdagateway.model.LambdaIdentifier;
import org.junit.Test;

import static com.joaquimmnetto.lambdagateway.fixture.LambdaFixture.aLambdaIdentifier;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.hamcrest.CoreMatchers.is;

public class AWSLambdaRegistryTest {

    private final Serializer serializer = mock(Serializer.class);
    private final AWSLambdaFacade facade = mock(AWSLambdaFacade.class);
    private AWSLambdaRegistry registry = new AWSLambdaRegistry(serializer, facade);

    @Test
    public void getsNewLambdaObjectBasedOnRemoteAWSLambda() {
        LambdaIdentifier identifier = aLambdaIdentifier();

        Lambda lambda = registry.get(identifier);

        assertThat(lambda, is(notNullValue()));
    }

}