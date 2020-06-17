package ut.com.joaquimmnetto.lambdagateway.infra.inbound;

import com.joaquimmnetto.lambdagateway.infra.handler.MessageHandler;
import com.joaquimmnetto.lambdagateway.infra.http.HTTPEndpoint;
import com.joaquimmnetto.lambdagateway.infra.http.HTTPRequestHandler;
import com.joaquimmnetto.lambdagateway.infra.http.RESTOperationsFacade;
import com.joaquimmnetto.lambdagateway.infra.inbound.*;
import com.joaquimmnetto.lambdagateway.infra.inbound.exception.UnsuportedHTTPMethodException;
import org.junit.Test;
import org.mockito.internal.matchers.Any;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

public class RESTHandlerBinderTest {

    private final RequestHandlerWrapper wrapper = mock(RequestHandlerWrapper.class);
    private final RESTOperationsFacade restFacade = mock(RESTOperationsFacade.class);
    private final RESTHandlerBinder restBinder = new RESTHandlerBinder(wrapper, restFacade);

    @Test
    public void bindsRESTEndpointToMessageHandler() {
        HTTPEndpoint HTTPEndpoint = new HTTPEndpoint("POST", "a/path");
        MessageHandler aMessageHandler = mock(MessageHandler.class);
        Class<?> handlerInboundObjClass = Any.class;
        HTTPRequestHandler httpWrapperForBindingRequestHandler = mock(HTTPRequestHandler.class);

        when(wrapper.wrapForHTTP(aMessageHandler, handlerInboundObjClass)).thenReturn(httpWrapperForBindingRequestHandler);

        restBinder.bind(HTTPEndpoint, aMessageHandler, handlerInboundObjClass);

        verify(restFacade).listenPOST(HTTPEndpoint.path(),  httpWrapperForBindingRequestHandler);
    }

    @Test(expected = UnsuportedHTTPMethodException.class)
    public void throwsUnsuportedHttpMethodInCaseOfUnsupportedMethod() {
        HTTPEndpoint HTTPEndpoint = new HTTPEndpoint("UNSUPORTED", "a/path");

        restBinder.bind(HTTPEndpoint, mock(MessageHandler.class), Any.class);

        fail("Should had thrown UnsuportedHTTPMethodException");
    }
}