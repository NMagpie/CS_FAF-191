package core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import customitem.CustomItem;
import controllers.Controller;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import static main.Main.getController;

public class FileProcessing {

    private static final Controller controller = getController();

    private static ArrayList<CustomItem> customItems = new ArrayList<>();

    public static ArrayList<CustomItem> getCustomItems() {
        return customItems;
    }

    public static void parseJSONFile(File file) {

        CustomItem.resetSelItems();

        ObjectMapper mapper = new ObjectMapper();

        try {
            CustomItem[] items = mapper.readValue(file, CustomItem[].class);
            customItems = new ArrayList<>(Arrays.asList(items));
        } catch (MismatchedInputException e) {
            controller.setStatusText("Error while parsing JSON file!");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static ArrayList<CustomItem> selectedArrayList() {

        ArrayList<CustomItem> selectedCustomItems = new ArrayList<>();

        for (CustomItem customItem : customItems)
            if (customItem.isSelected()) selectedCustomItems.add(customItem);

        if (selectedCustomItems.isEmpty())
            JOptionPane.showMessageDialog(null, "No policies are selected!");

        return selectedCustomItems;

    }

    public static void parseFileObject(File file) throws FileNotFoundException {

        CustomItem.resetSelItems();

        customItems = new ArrayList<>();

        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            if (line.matches("\\s*<custom_item>\\s*")) {
                line = scanner.nextLine();

                CustomItem customItem = new CustomItem();

                while (!line.matches("\\s*</custom_item>\\s*")) {
                    line = line.trim();
                    String[] twoArguments = line.split(" *: *", 2);

                    if (twoArguments[1].matches("^\".*[^\"]$"))
                        while (!line.matches(".*\"$")) {
                            line = scanner.nextLine();
                            twoArguments[1] += " " + line;
                        }

                    if (twoArguments[1].startsWith("\"")) twoArguments[1] = twoArguments[1].substring(1);

                    if (twoArguments[1].endsWith("\""))
                        twoArguments[1] = twoArguments[1].substring(0, twoArguments[1].length() - 1);

                    switch (twoArguments[0]) {
                        case ("type"):
                            customItem.setType(twoArguments[1]);
                            break;
                        case ("reg_key"):
/*                            if (twoArguments[1].startsWith("HKLM"))
                            twoArguments[1] = twoArguments[1].replace("HKLM","HKEY_LOCAL_MACHINE");
                            else if (twoArguments[1].startsWith("HKU"))
                            twoArguments[1] = twoArguments[1].replace("HKU","HKEY_USERS");
                            else if (twoArguments[1].startsWith("HKCU"))
                            twoArguments[1] = twoArguments[1].replace("HKCU","HKEY_CURRENT_USER");*/
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

                if (customItem.hashCode() != -1) customItems.add(customItem);
            }
        }

        scanner.close();

    }
}