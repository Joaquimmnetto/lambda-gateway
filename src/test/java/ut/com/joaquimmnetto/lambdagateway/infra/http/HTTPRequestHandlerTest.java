package ut.com.joaquimmnetto.lambdagateway.infra.http;

import com.joaquimmnetto.lambdagateway.infra.handler.MessageHandler;
import com.joaquimmnetto.lambdagateway.infra.http.exception.InvalidParameter;
import com.joaquimmnetto.lambdagateway.infra.http.HTTPResponse;
import com.joaquimmnetto.lambdagateway.infra.http.HTTPBody;
import com.joaquimmnetto.lambdagateway.infra.inbound.InboundMessage;
import com.joaquimmnetto.lambdagateway.infra.http.HTTPRequestHandler;
import com.joaquimmnetto.lambdagateway.infra.tools.Serializer;
import org.junit.Test;
import org.mockito.ArgumentMatcher;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static java.util.Collections.singletonMap;
import static java.util.Collections.unmodifiableMap;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

public class HTTPRequestHandlerTest {

    private final Serializer serializer = mock(Serializer.class);

    @Test
    public void returns200ForValidHTTPInboundRequestWithResponseWithContent() {
        HTTPBody httpBody = mock(HTTPBody.class);
        Class<?> handlerInboundObjectClass = Object.class;
        Object handlerInboundObject = mock(handlerInboundObjectClass);
        when(serializer.deserialize(eq(httpBody.body()), any())).thenReturn(handlerInboundObject);

        Map<String, String> httpHeaders = singletonMap("Header", "Value");
        Map<String, String> namedParameters = singletonMap("param", "value");
        Map<String, String> urlParameters = singletonMap("urlparam", "value");

        MessageHandler innerMessageHandler = mock(MessageHandler.class);
        Object outboundObject = mock(Object.class);
        String serializedOutboundObject = "serialized";

        when(serializer.serialize(outboundObject)).thenReturn(serializedOutboundObject);

        when(innerMessageHandler.handle(argThat(inboundMessageFromHTTP(handlerInboundObject,
                        httpHeaders, namedParameters, urlParameters)))).thenReturn(Optional.of(outboundObject));

        HTTPRequestHandler wrapping = new HTTPRequestHandler(serializer, innerMessageHandler,
                                                                             handlerInboundObjectClass);

        HTTPResponse response = wrapping.handle(httpBody, httpHeaders, namedParameters, urlParameters);

        assertThat(response.body().orElse(null), is(serializedOutboundObject));
        assertThat(response.status(), is(200));
        assertThat(response.contentType().orElse(null), is("application/json"));
    }

    @Test
    public void returns204ForValidHTTPInboundRequestWithResponseWithoutContent() {
        HTTPBody httpBody = mock(HTTPBody.class);
        Class<?> handlerInboundObjectClass = Object.class;
        Object handlerInboundObject = mock(handlerInboundObjectClass);
        when(serializer.deserialize(eq(httpBody.body()), any())).thenReturn(handlerInboundObject);

        Map<String, String> httpHeaders = singletonMap("Header", "Value");
        Map<String, String> namedParameters = singletonMap("param", "value");
        Map<String, String> urlParameters = singletonMap("urlparam", "value");

        MessageHandler innerMessageHandler = mock(MessageHandler.class);

        when(innerMessageHandler.handle(argThat(inboundMessageFromHTTP(handlerInboundObject,
                httpHeaders, namedParameters, urlParameters)))).thenReturn(Optional.empty());

        HTTPRequestHandler requestHandler = new HTTPRequestHandler(serializer, innerMessageHandler,
                handlerInboundObjectClass);

        HTTPResponse response = requestHandler.handle(httpBody, httpHeaders, namedParameters, urlParameters);

        assertThat(response.body(), is(Optional.empty()));
        assertThat(response.status(), is(204));
        assertThat(response.contentType(), is(Optional.empty()));
    }

    @Test
    public void returns400ForInvalidHTTPInboundRequest() {
        HTTPBody httpBody = mock(HTTPBody.class);
        Class<?> handlerInboundObjectClass = Object.class;
        Object handlerInboundObject = mock(handlerInboundObjectClass);
        when(serializer.deserialize(eq(httpBody.body()), any())).thenReturn(handlerInboundObject);

        Map<String, String> httpHeaders = singletonMap("Header", "Value");
        Map<String, String> namedParameters = singletonMap("param", "value");
        Map<String, String> urlParameters = singletonMap("urlparam", "value");

        MessageHandler innerMessageHandler = mock(MessageHandler.class);

        String exceptionMessage = "message";
        when(innerMessageHandler.handle(argThat(inboundMessageFromHTTP(handlerInboundObject,
                httpHeaders, namedParameters, urlParameters)))).thenThrow(new InvalidParameter(exceptionMessage));

        HTTPRequestHandler requestHandler = new HTTPRequestHandler(serializer, innerMessageHandler,
                handlerInboundObjectClass);

        HTTPResponse response = requestHandler.handle(httpBody, httpHeaders, namedParameters, urlParameters);

        assertThat(response.body().orElse(null), is(exceptionMessage));
        assertThat(response.status(), is(400));
        assertThat(response.contentType().orElse(null), is("text/plain"));
    }

    @Test
    public void returns500ForValidHTTPInboundRequestGeneratingUnknownError() {
        HTTPBody httpBody = mock(HTTPBody.class);
        Class<?> handlerInboundObjectClass = Object.class;
        Object handlerInboundObject = mock(handlerInboundObjectClass);
        when(serializer.deserialize(eq(httpBody.body()), any())).thenReturn(handlerInboundObject);

        Map<String, String> httpHeaders = singletonMap("Header", "Value");
        Map<String, String> namedParameters = singletonMap("param", "value");
        Map<String, String> urlParameters = singletonMap("urlparam", "value");

        MessageHandler innerMessageHandler = mock(MessageHandler.class);

        String exceptionMessage = "message";
        when(innerMessageHandler.handle(argThat(inboundMessageFromHTTP(handlerInboundObject,
                httpHeaders, namedParameters, urlParameters)))).thenThrow(new RuntimeException(exceptionMessage));

        HTTPRequestHandler requestHandler = new HTTPRequestHandler(serializer, innerMessageHandler,
                handlerInboundObjectClass);

        HTTPResponse response = requestHandler.handle(httpBody, httpHeaders, namedParameters, urlParameters);

        assertThat(response.body().orElse(null), is(exceptionMessage));
        assertThat(response.status(), is(500));
        assertThat(response.contentType().orElse(null), is("text/plain"));
    }


    private ArgumentMatcher<InboundMessage> inboundMessageFromHTTP(Object inboundObject,
                                                                   Map<String, String> httpHeaders,
                                                                   Map<String, String> namedParameters,
                                                                   Map<String, String> urlParameters) {
        var mergedMap = new HashMap<>(namedParameters);
        mergedMap.putAll(urlParameters);
        return inboundMessage ->
                inboundMessage.content().equals(inboundObject) &&
                inboundMessage.headers().equals(unmodifiableMap(httpHeaders)) &&
                inboundMessage.namedParams().equals(unmodifiableMap(mergedMap));
    }

}