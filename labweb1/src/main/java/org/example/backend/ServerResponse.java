package org.example.backend;

import com.fastcgi.FCGIRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.backend.exceptions.UnsupportedHttpException;
import java.io.IOException;

public class ServerResponse {

    HitChecker service = new HitChecker();

    public ServerResponseCompose<?> mapRequestMethod(FCGIRequest request) throws UnsupportedHttpException {
        String httpMethod = request.params.getProperty("REQUEST_METHOD");
        return switch (httpMethod) {
            case "POST" -> postMapping(request);
            default -> throw new UnsupportedHttpException(httpMethod + " не поддерживается");
        };
    }
    private ServerResponseCompose<?> postMapping(FCGIRequest request) {
        try {
            StringBuilder body = new StringBuilder();
            int in_char = request.inStream.read();
            while (in_char != -1) {
                body.append((char) in_char);
                in_char = request.inStream.read();
            }
            String requestBody = body.toString();
            ObjectMapper jsonMapper = new ObjectMapper();
            HitRequest hitRequest = jsonMapper.readValue(requestBody, HitRequest.class);

            HitResponse hitResponse = new HitResponse();
            hitResponse.setX(hitRequest.getX());
            hitResponse.setY(hitRequest.getY());
            hitResponse.setR(hitRequest.getR());
            hitResponse.setHit(service.isHit(hitRequest));

            return new ServerResponseCompose<>(hitResponse, 200);
        } catch (IOException e) {
            e.printStackTrace();
            return new ServerResponseCompose<>(null, 400);
        }
    }
}