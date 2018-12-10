package infrastructure.services;

import api.PalindromeMessageCheckerApi;
import api.dto.in.MessageDto;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mokassem.exceptions.EntityNotFoundException;
import com.mokassem.spark.SparkConfig;
import domain.message.MessageQuery;

import java.util.Collections;
import java.util.Optional;

import static spark.Spark.delete;
import static spark.Spark.exception;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;

public class RootService implements SparkConfig {
    private final Gson gson;
    private final PalindromeMessageCheckerApi api;

    public RootService(GsonBuilder gsonBuilder, PalindromeMessageCheckerApi api) {
        this.gson = gsonBuilder.create();
        this.api = api;
    }

    @Override
    public void configure() {

        get("/messages", (request, response) -> {
            response.type("application/json; charset=utf-8");
            MessageQuery query;
            int queryMessageNumber;
            boolean queryIsPalindrome;
            if (request.queryMap().toMap().keySet().isEmpty()) {
                query = MessageQuery.builder().build();
            } else {
                if (request.queryMap("number").hasValue() && request.queryMap("ispalindrome").hasValue()) {
                    queryMessageNumber = Integer.parseInt(request.queryMap("number").value());
                    queryIsPalindrome = Boolean.getBoolean(request.queryMap("ispalindrome").value());
                    query = MessageQuery.builder()
                                        .number(Optional.of(queryMessageNumber))
                                        .isPalindeome(Optional.of(queryIsPalindrome))
                                        .build();
                } else if (request.queryMap("number").hasValue()) {
                    queryMessageNumber = Integer.parseInt(request.queryMap("number").value());
                    query = MessageQuery.builder()
                                        .number(Optional.of(queryMessageNumber))
                                        .build();
                } else {
                    response.status(204);
                    return Collections.EMPTY_SET;
                }
            }
            return api.findMessages(query);
        }, gson::toJson);

        get("/message/:id", (request, response) -> {
            response.type("application/json; charset=utf-8");
            return api.findMessageById(request.params(":id"));
        }, gson::toJson);

        post("/create-message", (request, response) -> {
            MessageDto messageDto = gson.fromJson(request.body(), MessageDto.class);
            api.createMessage(messageDto, messageDto.isIgnoreCaseAndPunct());
            response.status(204);
            return "";
        });

        put("/update-message/:id", (request, response) -> {
            api.updateMessage(request.params(":id"), gson.fromJson(request.body(), MessageDto.class));
            response.status(204);
            return "";
        }, gson::toJson);

        delete("/remove-message/:id", (request, response) -> {
            api.deleteMessage(request.params(":id"));
            response.status(204);
            return "";
        }, gson::toJson);

        exception(EntityNotFoundException.class, (exception, request, response) -> {
            response.status(404);
            response.body(gson.toJson(exception.getMessage()));
        });
    }
}