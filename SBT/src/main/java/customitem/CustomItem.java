package customitem;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.scene.control.CheckBox;
import lombok.Setter;
import main.Main;

import java.util.Objects;
import java.util.TreeMap;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomItem {

    @JsonIgnore
    private static int selectedItems = 0;

    @JsonProperty("type")
    @Setter
    private String type;

    @JsonProperty("regKey")
    @Setter
    private String regKey;

    @JsonProperty("regItem")
    @Setter
    private String regItem;

    @JsonProperty("regOption")
    @Setter
    private String regOption;

    @JsonProperty("valueType")
    @Setter
    private String valueType;

    @JsonProperty("valueData")
    @Setter
    private String valueData;

    @JsonProperty("reference")
    @Setter
    private String reference;

    @JsonProperty("description")
    @Setter
    private String description;

    @JsonProperty("info")
    @Setter
    private String info;

    @JsonProperty("solution")
    @Setter
    private String solution;

    @JsonProperty("seeAlso")
    @Setter
    private String seeAlso;

    @JsonProperty("otherAttributes")
    @Setter
    private TreeMap<String, String> otherAttributes = new TreeMap<>();

    @JsonIgnore
    @Setter
    private boolean selected;

    @JsonIgnore
    private CheckBox selectedCB;

    @JsonIgnore
    public CustomItem() {
        this.selected = false;

        createCheckBox();
    }

    @JsonCreator
    public CustomItem(@JsonProperty("type") String type,
                      @JsonProperty("regKey") String regKey,
                      @JsonProperty("regItem") String regItem,
                      @JsonProperty("regOption") String regOption,
                      @JsonProperty("valueType") String valueType,
                      @JsonProperty("valueData") String valueData,
                      @JsonProperty("reference") String reference,
                      @JsonProperty("description") String description,
                      @JsonProperty("info") String info,
                      @JsonProperty("solution") String solution,
                      @JsonProperty("seeAlso") String seeAlso,
                      @JsonProperty("otherAttributes") TreeMap<String, String> otherAttributes) {
        this.type = type;
        this.regKey = regKey;
        this.regItem = regItem;
        this.regOption = regOption;
        this.valueType = valueType;
        this.valueData = valueData;
        this.reference = reference;
        this.description = description;
        this.info = info;
        this.solution = solution;
        this.seeAlso = seeAlso;
        this.otherAttributes = otherAttributes;
        this.selected = false;

        createCheckBox();
    }

    public String getType() {
        return type;
    }

    public String getRegKey() {
        return regKey;
    }

    public String getRegItem() {
        return regItem;
    }

    public String getRegOption() {
        return regOption;
    }

    public String getValueType() {
        return valueType;
    }

    public String getValueData() {
        return valueData;
    }

    public String getReference() {
        return reference;
    }

    public String getDescription() {
        return description;
    }

    public String getInfo() {
        return info;
    }

    public CheckBox getSelectedCB() {
        return selectedCB;
    }

    public boolean isSelected() {
        return selected;
    }

    public void addOtherAttributes(String[] entry) {
        this.otherAttributes.put(entry[0], entry[1]);
    }

    public void setSelectedCB() {
        selectedCB.setSelected(selected);
    }

    private void createCheckBox() {
        selectedCB = new CheckBox();
        setSelectedCB();
        selectedCB.setOnAction(event -> {
            selected = selectedCB.isSelected();

            if (selected) selectedItems++; else selectedItems--;

            if (selectedItems > 0 && !Main.getController().getCheckItemsActive()) Main.getController().setCheckItemsActive(true);
            if (selectedItems < 1 && Main.getController().getCheckItemsActive()) Main.getController().setCheckItemsActive(false);
        });
    }

    public void fire() {
        selectedCB.fire();
    }

    public static void resetSelItems() {
        selectedItems = 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CustomItem that = (CustomItem) o;

        if (!Objects.equals(type, that.type)) return false;
        if (!regKey.equals(that.regKey)) return false;
        if (!regItem.equals(that.regItem)) return false;
        if (!Objects.equals(regOption, that.regOption)) return false;
        if (!Objects.equals(valueType, that.valueType)) return false;
        if (!Objects.equals(valueData, that.valueData)) return false;
        if (!Objects.equals(reference, that.reference)) return false;
        if (!Objects.equals(description, that.description)) return false;
        if (!Objects.equals(info, that.info)) return false;
        if (!Objects.equals(solution, that.solution)) return false;
        if (!Objects.equals(seeAlso, that.seeAlso)) return false;
        return Objects.equals(otherAttributes, that.otherAttributes);
    }

    @Override
    public int hashCode() {
        if ((regKey == null) || (regItem == null))
            return -1;
        int result = regKey.hashCode();
        result = 31 * result + regItem.hashCode();
        return result;
    }
}