package org.example.Utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtils {


    public static Boolean writeJsonFile(String filepath, Object object){
        try {
            // Create a Gson object
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            FileWriter writer = new FileWriter(filepath);
            gson.toJson(object, writer);
            writer.close();
            return true;
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        return false;
    }



    public static String readJsonFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            Gson gson = new Gson();
            Object obj = gson.fromJson(br, Object.class);
            return gson.toJson(obj);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static void main(String[] args) throws IOException {
        System.out.println(FileUtils.readJsonFile("src/Data/user.json"));
    }
}
