package udc.notifier;

import javafx.util.Duration;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;
import udc.objects.account.Account;
import udc.objects.account.Client;
import udc.objects.enums.AccountType;
import udc.objects.enums.ClientType;
import udc.objects.time.concrete.Agenda;
import udc.objects.time.concrete.Appointment;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class AppointmentNotifier extends Thread {

    private Account account;
    private boolean started;

    public AppointmentNotifier(Account account) {
        this.setStarted(true);
        this.setAccount(account);
    }

    @Override
    public void run() {
        while (this.isStarted()) {
            if (this.account.getType() == AccountType.CLIENT && ((Client) this.account).getClientType() == ClientType.WALKIN)
                this.off();
            else {
                this.checkAppointments(this.account.getAppointments());
            }
        }
    }

    private synchronized void checkAppointments (ArrayList<Agenda> appointments) {
        int timeDelta = 0;
        LocalDateTime ldt;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm a");

        for (Agenda a : appointments) {
            ldt = LocalDateTime.now();

            if (ldt.getDayOfMonth() == a.getStartTime().getDayOfMonth() && ldt.getMonthValue() == a.getStartTime().getMonthValue()
                    && ldt.getHour() == a.getStartTime().getHour()) {
                timeDelta = a.getStartTime().getMinute() - ldt.getMinute();

                if (timeDelta == 20 || timeDelta == 10 || timeDelta == 5) { // display notification
                    TrayNotification trayNotif = new TrayNotification();
                    trayNotif.setTitle("Appointment Incoming!");
                    trayNotif.setMessage("Appointment @ " + a.getStartTime().format(dtf));
                    trayNotif.setNotificationType(NotificationType.NOTICE);
                    trayNotif.showAndDismiss(Duration.seconds(5));
                }
            }
        }
    }

    public void off () {
        this.setStarted(false);
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public boolean isStarted() {
        return started;
    }

    public Account getAccount() {
        return account;
    }

}
