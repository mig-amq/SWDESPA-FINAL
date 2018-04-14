package udc.objects.account;

import udc.objects.enums.AccountType;
import udc.objects.enums.ClientType;

public class Client  extends Account {
    private ClientType clientType;

    public Client(int id, String firstName, String lastName) {
        super(id, firstName, lastName);
        this.setType(AccountType.CLIENT);
        this.setClientType(ClientType.REGULAR);
    }

    public void setClientType(ClientType clientType) {
        this.clientType = clientType;
    }

    public ClientType getClientType() {
        return clientType;
    }
}
