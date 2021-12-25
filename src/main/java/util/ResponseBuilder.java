package util;

import lombok.SneakyThrows;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import javax.servlet.http.HttpServletResponse;
import java.io.StringWriter;
import java.io.Writer;

public class ResponseBuilder {

    private HttpServletResponse response;

    private ResponseBuilder() {}

    public static ResponseBuilder create(HttpServletResponse httpServletResponse) {
        final ResponseBuilder responseBuilder = new ResponseBuilder();
        responseBuilder.response = httpServletResponse;

        return responseBuilder;
    }

    public ResponseBuilder setStatus(int status) {
        response.setStatus(status);

        return this;
    }

    @SneakyThrows
    public ResponseBuilder setBody(Object body) {
        response.setContentType("text/xml");
        response.setCharacterEncoding("UTF-8");
        Writer writer = new StringWriter();
        Serializer serializer = new Persister();
        serializer.write(body, writer);
        String xml = writer.toString();
        response.getWriter().print(xml);

        return this;
    }
}
