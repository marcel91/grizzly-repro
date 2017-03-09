package repro;

import java.net.URI;

import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

public class Main {

    public static void main(String[] args) {
        ResourceConfig resourceConfig = new ResourceConfig();
        resourceConfig.register(new ExampleResourceHandler());
        URI uri = UriBuilder.fromUri("").host("localhost").port(3003).build();
        GrizzlyHttpServerFactory.createHttpServer(uri, resourceConfig, true);
    }
    
}
