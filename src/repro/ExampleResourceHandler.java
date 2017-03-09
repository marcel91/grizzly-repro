package repro;

import java.io.IOException;

import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.glassfish.grizzly.http.server.Request;
import org.glassfish.jersey.server.ChunkedOutput;


@Path("example")
public class ExampleResourceHandler {

    @Valid
    @GET
    @Path("test")
    @Produces({
        "application/octet-stream",
        "application/json"
    })
    public void getTest(@javax.ws.rs.core.Context Request req, @javax.ws.rs.core.Context javax.ws.rs.core.HttpHeaders headers, @javax.ws.rs.core.Context javax.ws.rs.core.UriInfo uri, 
        @QueryParam("q")
        String q,
        @QueryParam("timeout")
        Integer timeout,
        @javax.ws.rs.container.Suspended
        javax.ws.rs.container.AsyncResponse async)
        throws Exception {
        
        ChunkedOutput<byte[]> chunkedOutput = new ChunkedOutput<>(byte[].class, new byte[0]);        
        Response response = Response.status(200)
                .header("Content-Type", "application/octet-stream")
                .entity(chunkedOutput)
                .build();
        async.resume(response);
        new Thread(new Runnable() {
            @Override
            public void run() {
                veryExpensiveOperation();
            }
 
            private void veryExpensiveOperation() {
                try {
                    for (int i = 0; i < 3; ++i) {
                        chunkedOutput.write("test\n".getBytes());
                        Thread.sleep(1000);
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } finally {
                    try {
                        chunkedOutput.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
    
}
