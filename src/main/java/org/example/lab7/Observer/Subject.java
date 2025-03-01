package org.example.lab7.Observer;

import java.util.ArrayList;
import java.util.List;

public interface Subject {
    List<Observer> observerList = new ArrayList<>();
    void registerObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers();
}
