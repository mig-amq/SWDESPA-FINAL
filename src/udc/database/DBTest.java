package udc.database;

import udc.Model;

public class DBTest {
    public static void main(String[] args) {
        Model model = new Model();

        model.getDbController().getClientID("Khaldon Minaga");
    }
}

