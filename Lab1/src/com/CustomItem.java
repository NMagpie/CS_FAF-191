package com;

import com.google.gson.annotations.Expose;
import javafx.scene.control.CheckBox;

import java.util.Objects;
import java.util.TreeMap;

public class CustomItem {

    @Expose
    private String type;

    @Expose
    private String regKey;

    @Expose
    private String regItem;

    @Expose
    private String regOption;

    @Expose
    private String valueType;

    @Expose
    private String valueData;

    @Expose
    private String reference;

    @Expose
    private String description;

    @Expose
    private String info;

    @Expose
    private String solution;

    @Expose
    private String seeAlso;

    @Expose
    private TreeMap<String, String> otherAttributes = new TreeMap<>();

    private boolean selected;

    private final CheckBox selectedCB;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type; }

    public String getRegKey() {
        return regKey;
    }

    public void setRegKey(String regKey) { this.regKey = regKey; }

    public String getRegItem() {
        return regItem;
    }

    public void setRegItem(String regItem) {
        this.regItem = regItem; }

    public String getRegOption() {
        return regOption;
    }

    public void setRegOption(String regOption) { this.regOption = regOption; }

    public String getValueType() {
        return valueType;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType; }

    public String getValueData() {
        return valueData;
    }

    public void setValueData(String valueData) {
        this.valueData = valueData; }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference; }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description; }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info; }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution; }

    public String getSeeAlso() {
        return seeAlso;
    }

    public void setSeeAlso(String seeAlso) {
        this.seeAlso = seeAlso; }

    public TreeMap<String, String> getOtherAttributes() {
        return otherAttributes;
    }

    public void setOtherAttributes(TreeMap<String, String> otherAttributes) {
        this.otherAttributes = otherAttributes;
    }

    public void addOtherAttributes(String[] entry) {
        this.otherAttributes.put(entry[0], entry[1]);
    }

    public boolean getSelected() { return selected; }

    public void setSelected(boolean selected) { this.selected = selected; }

    public void setSelectedCB() {
        selectedCB.setSelected(selected);
    }

    public CheckBox getSelectedCB() {
        return selectedCB;
    }

    public CustomItem() {

        this.selected = false;
        this.selectedCB = new CheckBox();

        selectedCB.setOnAction(event -> selected= selectedCB.isSelected());

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
        if ((regKey==null)||(regItem==null))
                return -1;
        int result = regKey.hashCode();
        result = 31 * result + regItem.hashCode();
        return result;
    }
}
