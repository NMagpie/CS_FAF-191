package com;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class CoreApp {

    private static HashMap<String,HashMap<String,String>> policies = new HashMap<>();

    public static ArrayList<HashMap> getPoliciesArrayList() {
        if (policies!=null) return new ArrayList<>(policies.values());
        else return null;
    }

    public static HashMap<String, HashMap<String, String>> getPolicies() {
        if (policies!=null) return policies;
        else return null;
    }

    public static String buildFile() {
        String output = "";
        for (HashMap<String,String> policy: policies.values()) {
            output+="\n";
            output+="Item\t\t: "+policy.get("reg_item")+"\n\n";
            output+="Path\t\t: "+policy.get("reg_key")+"\n\n";
            output+="Type\t\t: "+policy.get("type")+"\n\n";
            for (String attribute: policy.keySet()) {
                if (!((attribute.equals("reg_item"))||(attribute.equals("reg_key"))||(attribute.equals("type"))))
                    output+=attribute+"\t: "+policy.get(attribute)+"\n\n";
            }
            output+="////////////////////////////////////////////////////////////////////////////////////////////////////////\n\n";
        }
        return output;
    }

    public static String buildJSON() {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        String json = gson.toJson(policies);
        return json;
    }

    public static void parseFile(File file) throws FileNotFoundException {

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

        /*for (String key : policies.keySet())
            System.out.println(key+" "+policies.get(key)+"\n");

        System.out.println("\n"+attributes);*/

        scanner.close();

    }
}