package com;

import com.google.gson.annotations.Expose;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;

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

    @Expose
    private boolean selected;

    private CheckBox selectedCB;

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
        this.selectedCB = new CheckBox();
        selectedCB.setSelected(selected);
    }

    public CheckBox getSelectedCB() {
        return selectedCB;
    }

    public CustomItem() {

        this.selectedCB = new CheckBox();

        selectedCB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (selectedCB.isSelected()) selected=true; else selected=false;
            }
        });

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
