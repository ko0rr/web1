package org.example.backend;

import org.example.backend.exceptions.UnsupportedHttpException;
import org.example.backend.exceptions.UnsupportedRequestException;
import com.fastcgi.FCGIRequest;

import java.util.logging.Logger;


public class Route{

    private static Logger logger = Logger.getLogger(Route.class.getName());

    public ServerResponseCompose<?> mapRequestEndpoint(FCGIRequest request) throws UnsupportedHttpException, UnsupportedRequestException {

        String requestUri = request.params.getProperty("REQUEST_URI");
        String cleanedUri = requestUri.replace("/fcgi", "");

        logger.info(() -> String.format("Маршрутизируем запрос: %s -> %s", requestUri, cleanedUri));

        return switch (request.params.getProperty("REQUEST_URI").replace("/fcgi", "")) {
            case "/" -> {
                logger.fine("Направляем в корень");
                yield new ServerResponse().mapRequestMethod(request);
            }
            default -> {
                String errorMsg = "Неподдерживаемый маршрут: " + requestUri;
                logger.warning(errorMsg);
                throw new UnsupportedRequestException("Программа не предоставляет услуги на конечной точке маршрута " + request.params.getProperty("REQUEST_URI"));
            }
        };
    }
}