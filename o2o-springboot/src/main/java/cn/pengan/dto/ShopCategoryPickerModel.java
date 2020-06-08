package cn.pengan.dto;

import java.util.List;

public class ShopCategoryPickerModel {
    private String Label;
    private Long Value;
    private List<ShopCategoryPickerModel> Children;

    public ShopCategoryPickerModel() { }

    public ShopCategoryPickerModel(String label, Long value) {
        Label = label;
        Value = value;
    }

    public ShopCategoryPickerModel(String label, Long value, List<ShopCategoryPickerModel> children) {
        Label = label;
        Value = value;
        this.Children = children;
    }

    public String getLabel() {
        return Label;
    }

    public void setLabel(String label) {
        Label = label;
    }

    public Long getValue() {
        return Value;
    }

    public void setValue(Long value) {
        Value = value;
    }

    public List<ShopCategoryPickerModel> getChildren() {
        return Children;
    }

    public void setChildren(List<ShopCategoryPickerModel> children) {
        this.Children = children;
    }

    @Override
    public String toString() {
        return "ShopCategoryPickerModel{" +
                "Label='" + Label + '\'' +
                ", Value='" + Value + '\'' +
                ", children=" + Children +
                '}';
    }
}
