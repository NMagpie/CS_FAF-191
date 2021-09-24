package com;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.CheckBox;

import java.util.TreeMap;

public class CustomItem {

    private SimpleStringProperty type;

    private SimpleStringProperty regKey;

    private SimpleStringProperty regItem;

    private SimpleStringProperty regOption;

    private SimpleStringProperty valueType;

    private SimpleStringProperty valueData;

    private SimpleStringProperty reference;

    private SimpleStringProperty description;

    private SimpleStringProperty info;

    private SimpleStringProperty solution;

    private SimpleStringProperty seeAlso;

    private TreeMap<String, String> otherAttributes;

    private CheckBox selected;

    public String getType() {
        return type.get();
    }

    public void setType(String type) {
        this.type = new SimpleStringProperty(type); }

    public String getRegKey() {
        return regKey.get();
    }

    public void setRegKey(String regKey) {
        this.regKey = new SimpleStringProperty(regKey);
    }

    public String getRegItem() {
        return regItem.get();
    }

    public void setRegItem(String regItem) {
        this.regItem = new SimpleStringProperty(regItem); }

    public String getRegOption() {
        return regOption.get();
    }

    public void setRegOption(String regOption) {
        this.regOption = new SimpleStringProperty(regOption);
    }

    public String getValueType() {
        return valueType.get();
    }

    public void setValueType(String valueType) {
        this.valueType = new SimpleStringProperty(valueType); }

    public String getValueData() {
        return valueData.get();
    }

    public void setValueData(String valueData) {
        this.valueData = new SimpleStringProperty(valueData); }

    public String getReference() {
        return reference.get();
    }

    public void setReference(String reference) {
        this.reference = new SimpleStringProperty(reference); }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description = new SimpleStringProperty(description); }

    public String getInfo() {
        return info.get();
    }

    public void setInfo(String info) {
        this.info = new SimpleStringProperty(info); }

    public String getSolution() {
        return solution.get();
    }

    public void setSolution(String solution) {
        this.solution = new SimpleStringProperty(solution); }

    public String getSeeAlso() {
        return seeAlso.get();
    }

    public void setSeeAlso(String seeAlso) {
        this.seeAlso = new SimpleStringProperty(seeAlso); }

    public TreeMap<String, String> getOtherAttributes() {
        return otherAttributes;
    }

    public void setOtherAttributes(TreeMap<String, String> otherAttributes) {
        this.otherAttributes = otherAttributes;
    }

    public void addOtherAttributes(String[] entry) {
        this.otherAttributes.put(entry[0], entry[1]);
    }

    public CheckBox getSelected() { return selected; }

    public void setSelected(CheckBox selected) { this.selected = selected; }

    public CustomItem() {
         otherAttributes = new TreeMap<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CustomItem that = (CustomItem) o;

        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (!regKey.equals(that.regKey)) return false;
        if (!regItem.equals(that.regItem)) return false;
        if (regOption != null ? !regOption.equals(that.regOption) : that.regOption != null) return false;
        if (valueType != null ? !valueType.equals(that.valueType) : that.valueType != null) return false;
        if (valueData != null ? !valueData.equals(that.valueData) : that.valueData != null) return false;
        if (reference != null ? !reference.equals(that.reference) : that.reference != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (info != null ? !info.equals(that.info) : that.info != null) return false;
        if (solution != null ? !solution.equals(that.solution) : that.solution != null) return false;
        if (seeAlso != null ? !seeAlso.equals(that.seeAlso) : that.seeAlso != null) return false;
        return otherAttributes != null ? otherAttributes.equals(that.otherAttributes) : that.otherAttributes == null;
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
