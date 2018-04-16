package udc;

import network.threads.ClientThread;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import udc.customfx.paneledview.PaneledView;
import udc.database.DataBaseController;
import udc.objects.account.Account;
import udc.objects.account.Doctor;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

public class Model {
    private int serverPort, dbPort;
    private String dbUser, dbPass;
    private String serverAddress, dbAddress;

    private Account account;
    private ClientThread thread;
    private DataBaseController dbController;
    private PaneledView viewController;

    public Model () {
        this.load_settings();
        this.setDbController(new DataBaseController(this));
    }

    public void getState () {
        if (this.account != null) {
            try {
                switch (this.account.getType()) {
                    case DOCTOR:
                        this.getAccount().setAppointments(this.getDbController().getAppointments(this.account.getId(), "DOCTOR"));
                        ((Doctor) this.getAccount()).setExceptions(this.getDbController().getExceptions(this.account.getId()));
                        break;
                    case SECRETARY:
                        this.getAccount().setAppointments(this.getDbController().getAppointments(this.account.getId(), "SECRETARY"));
                        break;
                    case CLIENT:
                        this.getAccount().setAppointments(this.getDbController().getAppointments(this.account.getId(), "CLIENT"));
                        break;
                }

                if (viewController != null)
                    viewController.update();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void setState () {
        if (thread != null) {
            this.getThread().update();
        }
    }

    public void load_settings () {
        File f = new File(getClass().getResource("res/settings.xml").getPath());

        if (f.exists()) {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            try {
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document dom = db.parse(f);

                Element server = (Element) dom.getDocumentElement().getElementsByTagName("server").item(0);
                Element database = (Element) dom.getDocumentElement().getElementsByTagName("db").item(0);

                this.setServerAddress(server.getAttribute("address"));
                this.setServerPort(Integer.parseInt(server.getAttribute("port")));

                this.setDbAddress(database.getAttribute("address"));
                this.setDbPort(Integer.parseInt(database.getAttribute("port")));

                this.setDbUser(database.getElementsByTagName("username").item(0).getTextContent());
                this.setDbPass(database.getElementsByTagName("password").item(0).getTextContent());
            } catch (IOException | ParserConfigurationException | SAXException e) {
                e.printStackTrace();
            }
        }
    }

    public void save_settings () {
        File f = new File(getClass().getResource("res/settings.xml").getPath());

        if (f.exists()) {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            try {
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document dom = db.parse(f);

                Element server = (Element) dom.getDocumentElement().getElementsByTagName("server").item(0);
                Element database = (Element) dom.getDocumentElement().getElementsByTagName("db").item(0);

                server.setAttribute("address", this.getServerAddress());
                server.setAttribute("port", String.valueOf(this.getServerPort()));

                database.setAttribute("address", this.getDbAddress());
                database.setAttribute("port", String.valueOf(this.getDbPort()));

                database.getElementsByTagName("username").item(0).setTextContent(this.getDbUser());
                database.getElementsByTagName("password").item(0).setTextContent(this.getDbPass());

                Transformer t = TransformerFactory.newDefaultInstance().newTransformer();
                Result output = new StreamResult(f);
                Source src = new DOMSource(dom);

                t.transform(src,output);
            } catch (IOException | ParserConfigurationException | SAXException | TransformerException e) {
                e.printStackTrace();
            }
        }
    }

    public void setThread(ClientThread thread) {
        this.thread = thread;
    }

    public void setAccount(Account account) {
        this.account = account;

        this.getState();
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public void setDbAddress(String dbAddress) {
        this.dbAddress = dbAddress;
    }

    public void setDbPass(String dbPass) {
        this.dbPass = dbPass;
    }

    public void setDbPort(int dbPort) {
        this.dbPort = dbPort;
    }

    public void setDbUser(String dbUser) {
        this.dbUser = dbUser;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public void setDbController(DataBaseController dbController) {
        this.dbController = dbController;
    }

    public void setViewController(PaneledView viewController) {
        this.viewController = viewController;
    }

    public ClientThread getThread() {
        return thread;
    }

    public Account getAccount() {
        return account;
    }

    public int getServerPort() {
        return serverPort;
    }

    public int getDbPort() {
        return dbPort;
    }

    public String getDbAddress() {
        return dbAddress;
    }

    public String getDbPass() {
        return dbPass;
    }

    public String getDbUser() {
        return dbUser;
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public DataBaseController getDbController() {
        return dbController;
    }

    public PaneledView getViewController() {
        return viewController;
    }
}
