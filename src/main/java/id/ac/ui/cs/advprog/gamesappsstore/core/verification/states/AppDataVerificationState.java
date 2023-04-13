package id.ac.ui.cs.advprog.gamesappsstore.core.verification.states;

import id.ac.ui.cs.advprog.gamesappsstore.core.user.User;
import id.ac.ui.cs.advprog.gamesappsstore.core.verification.AppDataVerification;

public interface AppDataVerificationState {
    void setContext(AppDataVerification context);
    void verify(User admin);
    void reject(User admin);
    void requestReverification();
}
