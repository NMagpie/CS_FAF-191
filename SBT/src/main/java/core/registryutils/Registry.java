package core.registryutils;

import core.registryutils.registryitem.Item;
import core.registryutils.registryitem.Status;
import customitem.CustomItem;
import javafx.util.Pair;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import static core.FileProcessing.selectedArrayList;

public class Registry {

    private static ArrayList<Item> checkedItems;

    public static ArrayList<Item> checkItems() {

        checkedItems = new ArrayList<>();

        ArrayList<CustomItem> customItems = selectedArrayList();

        for (CustomItem customItem : customItems) {

            Pair<Integer, String> pair = parsePath(customItem.getRegKey());

            try {
                String value = WinRegistry.readString(pair.getKey(), pair.getValue(), customItem.getRegItem());

                checkedItems.add( new Item(customItem, value, pair.getKey(), pair.getValue()) );

            } catch (InvocationTargetException | IllegalAccessException e) { e.printStackTrace(); }

        }

        return checkedItems;

    }

    public static void processItems(){
        if (checkedItems == null || checkedItems.isEmpty()) return;

        try {

            for (Item item : checkedItems) {
                if (item.getStatus() == Status.NotFound) createItem(item);
                if (item.getStatus() == Status.WrongValue) rewriteItem(item);
            }

        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private static void createItem(Item item) throws InvocationTargetException, IllegalAccessException {
        WinRegistry.createKey(item.getHkey(), item.getPath());
        WinRegistry.writeStringValue(item.getHkey(), item.getPath(), item.getCustomItem().getRegItem(), item.getCustomItem().getValueData());
    }

    private static void rewriteItem(Item item) throws InvocationTargetException, IllegalAccessException {
        WinRegistry.writeStringValue(item.getHkey(), item.getPath(), item.getCustomItem().getRegItem(), item.getCustomItem().getValueData());
    }

    private static Pair<Integer, String> parsePath(String path) {

        String[] list = path.split("\\\\",2);

        Integer ret = null;

        switch (list[0]) {
            case "HKCR":
                ret = WinRegistry.HKEY_CLASSES_ROOT;
                break;
            case "HKCU":
                ret = WinRegistry.HKEY_CURRENT_USER;
                break;
            case "HKLM":
                ret = WinRegistry.HKEY_LOCAL_MACHINE;
                break;
            case "HKU":
                ret = WinRegistry.HKEY_USERS;
                break;
            case "HKCC":
                ret = WinRegistry.HKEY_CURRENT_CONFIG;
                break;
        }

        return new Pair<>(ret, list[1]);

    }

}