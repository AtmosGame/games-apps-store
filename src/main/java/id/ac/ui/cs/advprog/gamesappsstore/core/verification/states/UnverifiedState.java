package id.ac.ui.cs.advprog.gamesappsstore.core.verification.states;

import id.ac.ui.cs.advprog.gamesappsstore.models.user.User;
import id.ac.ui.cs.advprog.gamesappsstore.core.verification.AppDataVerification;
import id.ac.ui.cs.advprog.gamesappsstore.exceptions.ForbiddenMethodCall;
import id.ac.ui.cs.advprog.gamesappsstore.exceptions.UnauthorizedException;

import java.util.Date;

public class UnverifiedState implements AppDataVerificationState {
    private AppDataVerification context;

    @Override
    public void setContext(AppDataVerification context) {
        this.context = context;
    }

    @Override
    public void verify(User admin) {
        if (Boolean.FALSE.equals(admin.isAdmin())) throw new UnauthorizedException("User is not admin");
        context.changeState(new VerifiedState(), admin, new Date());
    }

    @Override
    public void reject(User admin) {
        if (Boolean.FALSE.equals(admin.isAdmin())) throw new UnauthorizedException("User is not admin");
        context.changeState(new RejectedState(), admin, new Date());
    }

    @Override
    public void requestReverification() {
        throw new ForbiddenMethodCall("Cannot request verification on unverified apps");
    }
}
