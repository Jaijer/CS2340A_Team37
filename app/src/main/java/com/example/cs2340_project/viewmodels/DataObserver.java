package com.example.cs2340_project.viewmodels;

public interface DataObserver<T> {
    void onDataUpdated(T data);
}
