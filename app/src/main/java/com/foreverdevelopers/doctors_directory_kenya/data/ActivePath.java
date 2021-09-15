package com.foreverdevelopers.doctors_directory_kenya.data;

public class ActivePath {
    public PathData currentPath, nextPath, previousPath;
    public ActivePath(){}
    public ActivePath(PathData previousPath, PathData currentPath, PathData nextPath){
        this.currentPath = currentPath;
        this.nextPath = nextPath;
        this.previousPath = previousPath;
    }
}


