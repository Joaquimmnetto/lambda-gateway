package ut.com.joaquimmnetto.lambdagateway.infra.inbound;

import com.joaquimmnetto.lambdagateway.infra.handler.MessageHandler;
import com.joaquimmnetto.lambdagateway.infra.http.HTTPRequestHandler;
import com.joaquimmnetto.lambdagateway.infra.inbound.MessageHandlerWrapper;
import com.joaquimmnetto.lambdagateway.infra.tools.Serializer;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.mockito.Mockito.mock;


public class MessageHandlerWrapperTest {
    private final Serializer serializer = mock(Serializer.class);
    private final MessageHandlerWrapper wrapper = new MessageHandlerWrapper(serializer);

    @Test
    public void wrapsRequestHandlerToProcessHTTPRequests() {
        MessageHandler messageHandler = mock(MessageHandler.class);
        Class<?> handlerInboundObjClass = Class.class;

        HTTPRequestHandler wrappedHandler = wrapper.wrapForHTTP(messageHandler, handlerInboundObjClass);

        assertThat(wrappedHandler, is(not(nullValue())));
    }
}