package udc.objects.account;

import udc.objects.enums.AccountType;

public class Secretary extends Account{

    public Secretary(int id, String firstName, String lastName) {
        super(id, firstName, lastName);

        this.setType(AccountType.SECRETARY);
    }
}
