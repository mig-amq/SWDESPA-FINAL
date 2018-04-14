package udc.objects.account;

import udc.objects.enums.AccountType;

public class Secretary extends Account{

    public Secretary(String firstName, String lastName, int id) {
        super(id, firstName, lastName);

        this.setType(AccountType.SECRETARY);
    }
}
