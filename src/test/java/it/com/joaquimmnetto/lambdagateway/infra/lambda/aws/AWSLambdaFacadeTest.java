package it.com.joaquimmnetto.lambdagateway.infra.lambda.aws;

import com.joaquimmnetto.lambdagateway.infra.http.exception.InvalidParameterException;
import com.joaquimmnetto.lambdagateway.infra.lambda.aws.AWSLambdaFacade;
import org.junit.Test;

import static com.joaquimmnetto.lambdagateway.fixture.LambdaFixture.aRawPayload;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.fail;

public class AWSLambdaFacadeTest {

    private final AWSLambdaFacade facade = new AWSLambdaFacade();

    @Test
    public void invokesLambdaPassingGivenPayload() {
        String lambdaName = "it-lambda";
        String payload = aRawPayload();

        String response = facade.invoke(lambdaName, payload);

        assertThat(response, is(payload));
    }

    @Test(expected = InvalidParameterException.class)
    public void throwsInvalidParameterExceptionWhenLambdaIsNotFound() {
        String lambdaName = "non-existent-lambda";
        String payload = aRawPayload();

        String response = facade.invoke(lambdaName, payload);

        fail("Should had thrown InvalidParameterException");
    }

}