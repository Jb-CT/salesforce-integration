package com.example.salesforceapp;


public class Main {
    public static void main(String[] args) {
        try {
            // Authenticate and get the access token
            String accessToken = SalesforceAuth.authenticate();
            System.out.println("Access Token: " + accessToken);

            // Fetch leads using the access token
            SalesforceClient.fetchLeads(accessToken);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
