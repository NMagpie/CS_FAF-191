package core.registryutils.registryitem;

import customitem.CustomItem;

public class Item {

    private final CustomItem customItem;

    private final String currentValue;

    private final int hkey;

    private final String path;

    private Status status;

    public Item(CustomItem customItem, String currentValue, int hkey, String path) {
        this.customItem = customItem;
        this.currentValue = currentValue;
        this.hkey = hkey;
        this.path = path;

        if (currentValue == null) status = Status.NotFound;
        else if (currentValue.matches(customItem.getValueData())) status = Status.Ok;
        else status = Status.WrongValue;

    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public CustomItem getCustomItem() {
        return customItem;
    }

    public String getCurrentValue() {
        return currentValue;
    }

    public Status getStatus() {
        return status;
    }

    public int getHkey() {
        return hkey;
    }

    public String getPath() {
        return path;
    }
}
