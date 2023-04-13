package id.ac.ui.cs.advprog.gamesappsstore.core.verification.states;

import id.ac.ui.cs.advprog.gamesappsstore.core.user.User;
import id.ac.ui.cs.advprog.gamesappsstore.core.verification.AppDataVerification;
import id.ac.ui.cs.advprog.gamesappsstore.exceptions.ForbiddenMethodCall;
import id.ac.ui.cs.advprog.gamesappsstore.exceptions.UnauthorizedException;

public class VerifiedState implements AppDataVerificationState {
    private AppDataVerification context;

    @Override
    public void setContext(AppDataVerification context) {
        this.context = context;
    }

    @Override
    public void verify(User admin) {
        if (!admin.isAdmin()) throw new UnauthorizedException("User is not admin");
        throw new ForbiddenMethodCall("Cannot verify unverified apps");
    }

    @Override
    public void reject(User admin) {
        if (!admin.isAdmin()) throw new UnauthorizedException("User is not admin");
        throw new ForbiddenMethodCall("Cannot reject verified apps");
    }

    @Override
    public void requestVerification() {
        throw new ForbiddenMethodCall("Cannot request verification on verified apps");
    }
}
