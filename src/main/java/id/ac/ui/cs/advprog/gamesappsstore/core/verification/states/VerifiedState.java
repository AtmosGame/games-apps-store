package id.ac.ui.cs.advprog.gamesappsstore.core.verification.states;

import id.ac.ui.cs.advprog.gamesappsstore.models.auth.User;
import id.ac.ui.cs.advprog.gamesappsstore.core.verification.AppDataVerification;
import id.ac.ui.cs.advprog.gamesappsstore.exceptions.ForbiddenMethodCall;
import id.ac.ui.cs.advprog.gamesappsstore.exceptions.UnauthorizedException;

import java.util.Date;

public class VerifiedState implements AppDataVerificationState {
    private AppDataVerification context;

    @Override
    public void setContext(AppDataVerification context) {
        this.context = context;
    }

    @Override
    public void verify(User admin) {
        throw new ForbiddenMethodCall("Cannot verify unverified apps");
    }

    @Override
    public void reject(User admin) {
        if (Boolean.FALSE.equals(admin.isAdmin())) throw new UnauthorizedException("User is not admin");
        context.changeState(new RejectedState(), admin, new Date());
    }

    @Override
    public void requestReverification() {
        throw new ForbiddenMethodCall("Cannot request verification on verified apps");
    }
}
