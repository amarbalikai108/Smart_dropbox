package com.example.smartdropbox.home.model;

public class DrawerModel
{
    private boolean isSelected=false;
    private String menuName="";

    public DrawerModel(String menuName) {
        this.menuName = menuName;
    }

    public boolean getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }
}
