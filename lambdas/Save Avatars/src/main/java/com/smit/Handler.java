package com.smit;

import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.URL;
import java.time.Instant;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Handler implements RequestHandler<Map<String, Object>, Map<String, Object>> {
    private static final String BUCKET_NAME = "tweet-app-avatars";
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private AmazonS3 s3Client;

    public Handler() {
        this.s3Client = AmazonS3ClientBuilder.standard().withCredentials(new EnvironmentVariableCredentialsProvider())
                .withRegion(Regions.US_EAST_1)
                .build();
    }

    public Map<String, Object> handleRequest(Map<String, Object> event, Context context) {
        LambdaLogger logger = context.getLogger();

        logger.log(gson.toJson(event.get("request")));

        ObjectMetadata objectMetadata = new ObjectMetadata();

        try {
            if (event.get("request") != null) {
                Type type = new TypeToken<HashMap<String, Object>>(){}.getType();
                HashMap<String, Object> request = gson.fromJson(gson.toJson(event.get("request")), type);

                Type type2 = new TypeToken<HashMap<String, String>>(){}.getType();
                HashMap<String, String> userAttributes = gson.fromJson(gson.toJson(request.get("userAttributes")), type2);

                String gender = userAttributes.get("gender").toLowerCase();
                String email = userAttributes.get("sub");

                logger.log("Gender: " + gender);
                logger.log("sub: " + email);

                String url = "https://avatars.dicebear.com/api/" + gender + "/" + email + ".svg";
                String fileName = UUID.randomUUID().toString() + ".svg";
                logger.log("saving avatar for " + email + ", File: " + fileName);

                InputStream in = new URL(url).openStream();
                byte[] avatar = IOUtils.toByteArray(in);

                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(avatar);

                objectMetadata.setContentLength(avatar.length);
                objectMetadata.setContentType("image/svg+xml");

                s3Client.putObject(new PutObjectRequest(BUCKET_NAME, fileName, byteArrayInputStream, objectMetadata));

            } else {
                logger.log("ERROR: Input Data not Found");
            }
        } catch (Exception e) {
            logger.log("ERROR: " + e.getMessage());
        }
        return event;
    }
}
