package id.ac.ui.cs.advprog.gamesappsstore.core.verification;

import id.ac.ui.cs.advprog.gamesappsstore.models.app.AppData;
import id.ac.ui.cs.advprog.gamesappsstore.models.user.User;
import id.ac.ui.cs.advprog.gamesappsstore.core.verification.states.AppDataVerificationState;
import id.ac.ui.cs.advprog.gamesappsstore.core.verification.states.RejectedState;
import id.ac.ui.cs.advprog.gamesappsstore.core.verification.states.UnverifiedState;
import id.ac.ui.cs.advprog.gamesappsstore.core.verification.states.VerifiedState;
import id.ac.ui.cs.advprog.gamesappsstore.models.app.enums.VerificationStatus;

import java.util.Date;

public class AppDataVerification {
    private AppData appData;
    private AppDataVerificationState state;

    public AppDataVerification(AppData appData) {
        this.appData = appData;

        VerificationStatus appStatus = appData.getVerificationStatus();
        if (appStatus.equals(VerificationStatus.VERIFIED)) {
            this.state = new VerifiedState();
        } else if (appStatus.equals(VerificationStatus.REJECTED)) {
            this.state = new RejectedState();
        } else {
            this.state = new UnverifiedState();
        }

        this.state.setContext(this);
    }

    public void changeState(AppDataVerificationState state) {
        if (!(state instanceof UnverifiedState)) {
            throw new IllegalArgumentException();
        }

        appData.setVerificationStatus(getVerificationStatus(state));
        appData.setVerificationAdminId(null);
        appData.setVerificationDate(null);

        this.state = state;
    }

    public void changeState(AppDataVerificationState state, User admin, Date date) {
        if (state instanceof UnverifiedState) {
            throw new IllegalArgumentException();
        }

        appData.setVerificationStatus(getVerificationStatus(state));
        appData.setVerificationAdminId(admin.getId());
        appData.setVerificationDate(date);

        this.state = state;
    }

    public void verify(User admin) {
        this.state.verify(admin);
    }

    public void reject(User admin) {
        this.state.reject(admin);
    }

    public void requestReverification() {
        this.state.requestReverification();
    }

    private VerificationStatus getVerificationStatus(AppDataVerificationState state) {
        if (state instanceof VerifiedState) {
            return VerificationStatus.VERIFIED;
        } else if (state instanceof RejectedState) {
            return VerificationStatus.REJECTED;
        } else {
            return VerificationStatus.UNVERIFIED;
        }
    }
}
