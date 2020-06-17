package it.com.joaquimmnetto.lambdagateway.infra.inbound.sparkjava;

import com.joaquimmnetto.lambdagateway.infra.http.HTTPBody;
import com.joaquimmnetto.lambdagateway.infra.http.HTTPRequestHandler;
import com.joaquimmnetto.lambdagateway.infra.http.HTTPResponse;
import com.joaquimmnetto.lambdagateway.infra.http.sparkjava.SparkJavaRESTFacade;
import com.joaquimmnetto.lambdagateway.infra.tools.Serializer;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SparkJavaRESTFacadeTest {

    private final Serializer serializer = mock(Serializer.class);
    private final SparkJavaRESTFacade facade = new SparkJavaRESTFacade(serializer);


    @Test
    public void passClientRequestDataToHandle() throws IOException {
        HTTPRequestHandler httpHandler = mock(HTTPRequestHandler.class);
        var aBody = new HTTPBody("body");
        var httpHeaders = new String[] {"header", "value"};
        var namedParameters = new String[] {":path", "test-path"};
        var getParameters = new String[] {"getParam", "value"};


        var responseBody = "body";
        var responseStatus = 200;
        var responseContentType = ContentType.APPLICATION_JSON.getMimeType();
        HTTPResponse response = new HTTPResponse(responseBody, responseStatus, responseContentType);
        when(httpHandler.handle(eq(aBody),
                                    argThat(map -> map.get(httpHeaders[0]).equals(httpHeaders[1])),
                                    argThat(map -> map.get(namedParameters[0]).equals(namedParameters[1])),
                                    argThat(map -> map.get(getParameters[0]).equals(getParameters[1]))))
                .thenReturn(response);
        facade.listenPOST(String.format("/%s", namedParameters[0]), httpHandler);
        var postResponse = Request.Post("http://localhost:4567/test-path?getParam=value")
                .addHeader(httpHeaders[0], httpHeaders[1])
                .bodyStream(new ByteArrayInputStream(responseBody.getBytes()))
                .execute();

        assertThat(postResponse.returnResponse().getStatusLine().getStatusCode(), is(responseStatus));
    }

}