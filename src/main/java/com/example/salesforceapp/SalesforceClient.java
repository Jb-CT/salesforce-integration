package com.example.salesforceapp;

import java.net.http.*;
import java.net.URI;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SalesforceClient {
    private static final String INSTANCE_URL = "https://clevertap2.my.salesforce.com"; // Replace with your instance URL

    public static void fetchLeads(String accessToken) throws Exception {
        HttpClient client = HttpClient.newHttpClient();

        String soqlQuery = "SELECT Id, Name, Company, Email FROM Lead LIMIT 10";
        String encodedQuery = java.net.URLEncoder.encode(soqlQuery, "UTF-8");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(INSTANCE_URL + "/services/data/v58.0/query?q=" + encodedQuery))
                .header("Authorization", "Bearer " + accessToken)
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(response.body());
            JsonNode records = rootNode.get("records");

            System.out.println("Fetched Leads:");
            for (JsonNode record : records) {
                String id = record.get("Id").asText();
                String name = record.get("Name").asText();
                String company = record.has("Company") ? record.get("Company").asText() : "N/A";
                String email = record.has("Email") ? record.get("Email").asText() : "N/A";

                System.out.printf("Id: %s, Name: %s, Company: %s, Email: %s%n", id, name, company, email);
            }
        } else {
            System.err.println("Error fetching leads: " + response.body());
        }
    }
}
