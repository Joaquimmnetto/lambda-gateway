package it.com.joaquimmnetto.lambdagateway.endtoend;

import com.joaquimmnetto.lambdagateway.Launcher;
import org.apache.http.client.fluent.Request;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.joaquimmnetto.lambdagateway.fixture.LambdaFixture.aRawPayload;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class LambdaMessageEndToEndTest {

    @Test
    public void receives200WithApropriatedPayloadForValidRequestWithResponse() throws IOException {
        String testLambdaName = "it-lambda";
        String requestBody = aRawPayload();

        var postResponse = Request.Post("http://localhost:4567/invoke/" + testLambdaName)
                .bodyStream(new ByteArrayInputStream(requestBody.getBytes()))
                .execute().returnResponse();


        assertThat(postResponse.getStatusLine().getStatusCode(), is(200));
        assertThat(new String(postResponse.getEntity().getContent().readAllBytes()), is(requestBody));
    }

    @Test
    public void receives400WhenLambdaDoesNotExists() throws IOException {
        String testLambdaName = "non-existing-lambda";

        var postResponse = Request.Post("http://localhost:4567/invoke/" + testLambdaName)
                .execute().returnResponse();

        assertThat(postResponse.getStatusLine().getStatusCode(), is(400));
    }

    @Test
    public void receives404WhenInvokingNonDescribedEndpoint() throws IOException {
        var postResponse = Request.Post("http://localhost:4567/non-existent-endpoint")
                .execute().returnResponse();

        assertThat(postResponse.getStatusLine().getStatusCode(), is(404));
    }

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    @Before
    public void startApp() {
        executor.submit(() -> Launcher.main(new String[0]));
    }

    @After
    public void stopApp() {
        executor.shutdownNow();
    }

}
