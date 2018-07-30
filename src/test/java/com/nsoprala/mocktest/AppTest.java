package com.nsoprala.mocktest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;

import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.google.gson.Gson;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
        System.out.println("rwerew");
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp() throws Exception
    {
        System.out.println("Starting test ------------");

        BufferedReader br = null;
        CloseableHttpClient client =  null;
        CloseableHttpResponse response = null;
        try {
            
            client = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost("http://localhost:9999/api/vaishali");
            String jsonString = getJsonInput();
            StringEntity entity = new StringEntity(jsonString);
            httpPost.setEntity(entity);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            response = client.execute(httpPost);

            assertEquals(response.getStatusLine().getStatusCode(), 200);

            br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
            String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }
        }
        catch (Exception ex) {
            System.out.println("ERRRRR : " + ex.getMessage());
            if (br != null) br.close();
            if (client != null) client.close();
            if (response != null) response.close();
        }
    }

    protected String getJsonInput() throws Exception {
        String jsonString = null;
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            jsonString = IOUtils.toString(classLoader.getResourceAsStream("input.json"));
            System.out.println("input json is : " + jsonString);
        } catch(Exception ex) {
            System.out.println("ERRRRR READ File: " + ex.getMessage());
        }
        return jsonString;
    }
}
