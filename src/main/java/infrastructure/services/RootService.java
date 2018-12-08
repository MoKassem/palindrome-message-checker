package infrastructure.services;


import api.PalindromeMessageCheckerApi;
import api.dto.MyMessage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mokassem.spark.SparkConfig;

import static spark.Spark.get;
import static spark.Spark.internalServerError;
import static spark.Spark.notFound;
import static spark.Spark.post;

public class RootService implements SparkConfig {
    private final Gson gson;
    private final PalindromeMessageCheckerApi api;

    public RootService(GsonBuilder gsonBuilder, PalindromeMessageCheckerApi api) {
        this.gson = gsonBuilder.create();
        this.api = api;
    }

    @Override
    public void configure() {

        get("/hello", (request, response) -> {
            response.status(404);
            return "fdfdsfsd";
        }, gson::toJson);

        post("/users", (request, response) -> {
            response.type("\"application/json; charset=utf-8\"");
            MyMessage myMessage = gson.fromJson(request.body(), MyMessage.class);
            api.createMessage(myMessage);
            return "kassemoo";
        });

        // Using Route
        internalServerError((req, res) -> {
            res.type("application/json");
            return "{\"message\":\"Cusssssstom 500 handling\"}";
        });

        notFound((req, res) -> {
            res.type("application/json");
            return "{\"message\":\"Custom 404\"}";
        });
    }


}