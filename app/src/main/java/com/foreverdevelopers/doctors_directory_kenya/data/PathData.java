package com.foreverdevelopers.doctors_directory_kenya.data;

public class PathData{
    public String remoteAction;
    public Object data;
    public Integer path;
    public PathData(){}
    public PathData(String remoteAction, Object data, Integer path ){
        this.remoteAction = remoteAction;
        this.data = data;
        this.path = path;
    }
}
