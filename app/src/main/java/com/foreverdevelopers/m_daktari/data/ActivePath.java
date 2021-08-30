package com.foreverdevelopers.m_daktari.data;

public class ActivePath {
    public Object baseItem;
    public String remoteAction;
    public ActivePath(){}
    public ActivePath(Object baseItem, String remoteAction){
        this.baseItem = baseItem;
        this.remoteAction = remoteAction;
    }
}
