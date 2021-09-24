package com;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class CoreApp {

//    private static HashMap<String,HashMap<String,String>> policies = new HashMap<>();

    private static ArrayList<CustomItem> customItems = new ArrayList<>();

    public static ArrayList<CustomItem> getCustomItems() {
        return customItems;
    }

    /*    public static ArrayList<HashMap> getPoliciesArrayList() {
        if (policies!=null) return new ArrayList<>(policies.values());
        else return null;
    }

    public static HashMap<String, HashMap<String, String>> getPolicies() {
        if (policies!=null) return policies;
        else return null;
    }*/

/*    public static String buildJSON() {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        String json = gson.toJson(policies);
        return json;
    }*/

/*    public static void parseFile(File file) throws FileNotFoundException {

        ArrayList<String> attributes = new ArrayList<>();

        Scanner scanner = new Scanner(file);

        HashMap<String,String> policy;

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            if (line.matches("\\s*<custom_item>\\s*")){
                line = scanner.nextLine();
                policy = new HashMap<>();

                while (!line.matches("\\s*</custom_item>\\s*"))
                {
                    line=line.trim();
                    String[] twoArguments = line.split(" *: *",2);

                    if (!attributes.contains(twoArguments[0]))
                        attributes.add(twoArguments[0]);

                    if (twoArguments[1].matches("^\".*[^\"]$"))
                        while (!line.matches(".*\"$"))
                        {
                            line = scanner.nextLine();
                            twoArguments[1]+=" "+line;
                        }

                    policy.put(twoArguments[0],twoArguments[1]);
                    line = scanner.nextLine();
                }

                if ((policy.containsKey("reg_item"))&&(policy.containsKey("reg_key")))
                    policies.put(policy.get("reg_item").substring(1,policy.get("reg_item").length()-1)+"_"+policy.get("reg_key").substring(1,policy.get("reg_key").length()-1),policy);
            }
        }

        *//*for (String key : policies.keySet())
            System.out.println(key+" "+policies.get(key)+"\n");

        System.out.println("\n"+attributes);*//*

        scanner.close();

    }*/

    public static String buildJSONbyObject() {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        return gson.toJson(customItems);
    }

    public static void parseJSONFile(File file) throws FileNotFoundException {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        JsonReader reader = new JsonReader(new FileReader(file));
        customItems = gson.fromJson(reader,ArrayList.class);
        //System.out.println(customItems);
    }

    public static void parseFileObject(File file) throws FileNotFoundException {

        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            if (line.matches("\\s*<custom_item>\\s*")){
                line = scanner.nextLine();

                CustomItem customItem = new CustomItem();

                while (!line.matches("\\s*</custom_item>\\s*"))
                {
                    line=line.trim();
                    String[] twoArguments = line.split(" *: *",2);

                    if (twoArguments[1].matches("^\".*[^\"]$"))
                        while (!line.matches(".*\"$"))
                        {
                            line = scanner.nextLine();
                            twoArguments[1]+=" "+line;
                        }

                    switch (twoArguments[0]) {
                        case ("type"):
                            customItem.setType(twoArguments[1]);
                            break;
                        case ("reg_key"):
                            customItem.setRegKey(twoArguments[1]);
                            break;
                        case ("reg_item"):
                            customItem.setRegItem(twoArguments[1]);
                            break;
                        case ("reg_option"):
                            customItem.setRegOption(twoArguments[1]);
                            break;
                        case ("value_type"):
                            customItem.setValueType(twoArguments[1]);
                            break;
                        case ("value_data"):
                            customItem.setValueData(twoArguments[1]);
                            break;
                        case ("reference"):
                            customItem.setReference(twoArguments[1]);
                            break;
                        case ("description"):
                            customItem.setDescription(twoArguments[1]);
                            break;
                        case ("info"):
                            customItem.setInfo(twoArguments[1]);
                            break;
                        case ("solution"):
                            customItem.setSolution(twoArguments[1]);
                            break;
                        case ("see_also"):
                            customItem.setSeeAlso(twoArguments[1]);
                            break;
                        default:
                            customItem.addOtherAttributes(twoArguments);
                            break;
                    }

                    line = scanner.nextLine();
                }

                if (customItem.hashCode()!=-1) customItems.add(customItem);
            }
        }

        scanner.close();

    }
}