package id.ac.ui.cs.advprog.gamesappsstore.core.notification;


public interface Subject {
    void addSubscriber(Subscriber subscriber);
    void removeSubscriber(Subscriber subscriber);
}
