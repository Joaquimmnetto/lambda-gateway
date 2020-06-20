package it.com.joaquimmnetto.lambdagateway.ioc;

import com.joaquimmnetto.lambdagateway.infra.handler.LambdaMessageHandler;
import com.joaquimmnetto.lambdagateway.infra.inbound.RESTHandlerBinder;
import com.joaquimmnetto.lambdagateway.infra.tools.GSONSerializer;
import com.joaquimmnetto.lambdagateway.infra.tools.Serializer;
import com.joaquimmnetto.lambdagateway.ioc.LambdaHandlerDependencyModule;
import com.joaquimmnetto.lambdagateway.ioc.RESTDependencyModule;
import com.joaquimmnetto.lambdagateway.ioc.ToolsDependencyModule;
import com.joaquimmnetto.lambdagateway.ioc.internals.IoCInjector;
import com.joaquimmnetto.lambdagateway.ioc.internals.spring.SpringIoCInjector;
import org.junit.Test;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class SpringDependencyLoadTest {

    @Test
    public void loadsToolsDependenciesWithoutError() {
        IoCInjector injector = new SpringIoCInjector(singletonList(new ToolsDependencyModule()));

        var serializerObj = injector.instance(Serializer.class);

        assertThat(serializerObj, is(instanceOf(GSONSerializer.class)));
    }

    @Test
    public void loadsRESTDependenciesWithoutError() {
        IoCInjector injector = new SpringIoCInjector(
                asList(new ToolsDependencyModule(), new RESTDependencyModule()));

        var moduleAPIObj = injector.instance(RESTHandlerBinder.class);

        assertThat(moduleAPIObj, is(instanceOf(RESTHandlerBinder.class)));
    }


    @Test
    public void loadsLambdaHandlerDependenciesWithoutError() {
        IoCInjector injector = new SpringIoCInjector(
                asList(new ToolsDependencyModule(), new LambdaHandlerDependencyModule()));

        var moduleAPIObj = injector.instance(LambdaMessageHandler.class);

        assertThat(moduleAPIObj, is(instanceOf(LambdaMessageHandler.class)));
    }

}
