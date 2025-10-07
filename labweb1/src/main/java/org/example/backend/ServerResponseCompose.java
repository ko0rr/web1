package org.example.backend;

public class ServerResponseCompose<T> {
    private T body;
    private int statusCode;
    private long executionTime;
    public ServerResponseCompose(T body, int statusCode) {
        this.body = body;
        this.statusCode = statusCode;
    }
    public T getBody() {
        return body;
    }
    public void setBody(T body) {
        this.body = body;
    }
    public int getStatusCode() {
        return statusCode;
    }
    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
    public long getExecutionTime() {
        return executionTime;
    }
    public void setExecutionTime(long executionTime) {
        this.executionTime = executionTime;
    }
}
