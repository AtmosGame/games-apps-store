package id.ac.ui.cs.advprog.gamesappsstore.core.verification;

import id.ac.ui.cs.advprog.gamesappsstore.core.app.AppData;
import id.ac.ui.cs.advprog.gamesappsstore.core.user.User;
import id.ac.ui.cs.advprog.gamesappsstore.core.user.UserRole;
import id.ac.ui.cs.advprog.gamesappsstore.core.verification.states.AppDataVerificationState;
import id.ac.ui.cs.advprog.gamesappsstore.core.verification.states.RejectedState;
import id.ac.ui.cs.advprog.gamesappsstore.core.verification.states.UnverifiedState;
import id.ac.ui.cs.advprog.gamesappsstore.core.verification.states.VerifiedState;
import id.ac.ui.cs.advprog.gamesappsstore.models.verification.VerificationStatus;
import id.ac.ui.cs.advprog.gamesappsstore.repository.AppRegistration.AppDataRepository;

import java.util.Date;

public class AppDataVerification {
    private AppData appData;
    private AppDataVerificationState state;
    private User admin;
    private Date date;
    private AppDataRepository repository;

    public AppDataVerification(AppData appData, AppDataRepository repository) {
        this.appData = appData;
        this.repository = repository;
        this.admin = null;
        this.date = null;

        VerificationStatus appStatus = appData.getVerificationStatus();
        Integer appAdminId = appData.getVerificationAdminId();
        Date appDate = appData.getVerificationDate();
        if (appStatus.equals(VerificationStatus.VERIFIED)) {
            this.state = new VerifiedState();
            this.admin = new User(appAdminId, UserRole.ADMINISTRATOR); // TODO: Fetch
            this.date = appDate;
        } else if (appStatus.equals(VerificationStatus.REJECTED)) {
            this.state = new RejectedState();
            this.admin = new User(appAdminId, UserRole.ADMINISTRATOR); // TODO: Fetch
            this.date = appDate;
        } else {
            this.state = new UnverifiedState();
        }

        this.state.setContext(this);
    }

    public void changeState(AppDataVerificationState state) {
        if (!(state instanceof UnverifiedState)) {
            throw new IllegalArgumentException();
        }

        appData.setVerificationStatus(VerificationStatus.UNVERIFIED);
        appData.setVerificationAdminId(null);
        appData.setVerificationDate(null);
        repository.save(appData);

        this.state = state;
        this.admin = null;
        this.date = null;
    }

    public void changeState(AppDataVerificationState state, User admin, Date date) {
        if (state instanceof UnverifiedState) {
            throw new IllegalArgumentException();
        }

        appData.setVerificationStatus(VerificationStatus.UNVERIFIED);
        appData.setVerificationAdminId(admin.getId());
        appData.setVerificationDate(date);
        repository.save(appData);

        this.state = state;
        this.admin = admin;
        this.date = date;
    }

    public void verify(User admin) {
        this.state.verify(admin);
    }

    public void reject(User admin) {
        this.state.reject(admin);
    }

    public void requestReverification() {
        this.state.requestVerification();
    }

    private VerificationStatus getVerificationStatus(AppDataVerificationState state) {
        if (state == null) {
            return VerificationStatus.UNVERIFIED;
        }

        if (state instanceof VerifiedState) {
            return VerificationStatus.VERIFIED;
        } else if (state instanceof RejectedState) {
            return VerificationStatus.REJECTED;
        } else {
            return VerificationStatus.UNVERIFIED;
        }
    }
}
