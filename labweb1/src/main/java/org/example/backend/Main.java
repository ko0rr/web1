package org.example.backend;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.logging.*;
import java.io.IOException;

import com.fastcgi.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;



public class Main {

    //логирование
    private static Logger logger = Logger.getLogger(Main.class.getName());
    static {
        try {
            // получаем путь к директории, где находится jar
            String jarPath = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            String jarDir = new File(jarPath).getParent();
            String logPath = jarDir + File.separator + "logs" + File.separator + "application.%g.log";

            new File(jarDir + File.separator + "logs").mkdirs();

            FileHandler fileHandler = new FileHandler(logPath, 1024*1024, 10, true);
            fileHandler.setFormatter(new SimpleFormatter());
            fileHandler.setLevel(Level.ALL);

            logger.addHandler(fileHandler);
            logger.setLevel(Level.ALL);
            logger.setUseParentHandlers(false);

            logger.info("логируем: " + logPath);
            System.out.println("файлы с логами лежат там: " + logPath);

        } catch (IOException e) {
            System.err.println("не получилось логировать: " + e.getMessage());
        }
    }


    public static final String BASE_RESPONSE = """
            Access-Control-Allow-Origin: *
            Connection: keep-alive
            Content-Type: application/json
            Content-Length: %d
                        
            %s
            """;

    public static void main(String[] args) {
        FCGIInterface fcgi = new FCGIInterface();
        Route route = new Route();
        while (fcgi.FCGIaccept() >= 0) {
            long startTime = System.currentTimeMillis();
            FCGIRequest request = FCGIInterface.request;

            String requestUri = request.params.getProperty("REQUEST_URI");
            String requestMethod = request.params.getProperty("REQUEST_METHOD");

            logger.info(() -> String.format("Полученный запрос: %s %s", requestMethod, requestUri));

            try {
                ServerResponseCompose<?> httpResponse = route.mapRequestEndpoint(request);
                httpResponse.setExecutionTime(System.currentTimeMillis() - startTime);
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.registerModule(new JavaTimeModule());
                objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
                String json = objectMapper.writeValueAsString(httpResponse);

                logger.info(() -> String.format("Запрос обработан за %d мс со статусом %d", httpResponse.getExecutionTime(), httpResponse.getStatusCode()));

                System.out.println(formatResponse(json));
            } catch (Exception ex) {

                logger.severe(() -> String.format("Ошибка: %s - %s", requestUri, ex.getMessage()));

                System.out.println(formatResponse("\"exception\":\""+ ex.getMessage() +"\""));
            }

        }
    }

    private static String formatResponse(String body) {
        return String.format(BASE_RESPONSE, body.getBytes(StandardCharsets.UTF_8).length, body);
    }

}
