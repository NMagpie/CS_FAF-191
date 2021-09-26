package com;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class CoreApp {

    private static ArrayList<CustomItem> customItems = new ArrayList<>();

    public static ArrayList<CustomItem> getCustomItems() {
        return customItems;
    }

    public static void parseJSONFile(File file) throws FileNotFoundException {
        GsonBuilder builder = new GsonBuilder();
        builder.excludeFieldsWithoutExposeAnnotation();
        Gson gson = builder.create();
        JsonReader reader = new JsonReader(new FileReader(file));
        customItems = gson.fromJson(reader, new TypeToken<ArrayList<CustomItem>>() {}.getType());
        for (CustomItem customItem : customItems)
            customItem.setSelectedCB();
    }

    public static String buildJSONbyObject() {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        builder.excludeFieldsWithoutExposeAnnotation();
        Gson gson = builder.create();
        ArrayList<CustomItem> selectedCustomItems = new ArrayList<>();

        for (CustomItem customItem: customItems)
            if (customItem.getSelected()) selectedCustomItems.add(customItem);

        return gson.toJson(selectedCustomItems);
    }

    public static void parseFileObject(File file) throws FileNotFoundException {

        customItems = new ArrayList<>();

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

                    if (twoArguments[1].startsWith("\"")) twoArguments[1]=twoArguments[1].substring(1);

                    if (twoArguments[1].endsWith("\"")) twoArguments[1]=twoArguments[1].substring(0,twoArguments[1].length()-1);

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