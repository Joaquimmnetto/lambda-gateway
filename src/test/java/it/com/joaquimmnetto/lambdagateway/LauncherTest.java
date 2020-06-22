package it.com.joaquimmnetto.lambdagateway;

import com.joaquimmnetto.lambdagateway.Launcher;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.junit.After;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class LauncherTest {

    @Test
    public void launchesUsingIoCContainer() throws IOException {
        executor.submit(Launcher::launchWithIoCInjector);

        var postResponse = sendNonExistingLambdaRequestToApp();

        assertThat(postResponse.getStatusLine().getStatusCode(), is(400));
    }

    @Test
    public void launchesUsingBinderObject() throws IOException {
        executor.submit(Launcher::launchWithBinder);

        var postResponse = sendNonExistingLambdaRequestToApp();

        assertThat(postResponse.getStatusLine().getStatusCode(), is(400));
    }

    private HttpResponse sendNonExistingLambdaRequestToApp() throws IOException {
        sleep(500L);
        String testLambdaName = "non-existing-lambda";

        return Request.Post("http://localhost:4567/invoke/" + testLambdaName)
                .execute().returnResponse();

    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    @After
    public void stopApp() {
        executor.shutdownNow();
    }

}