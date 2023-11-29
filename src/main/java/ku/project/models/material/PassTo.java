package ku.project.models.material;

import ku.project.models.account.Account;

public class PassTo {
    private Account account;
    private Material material;

    public PassTo(Account account, Material material){
        this.account = account;
        this.material = material;
    }
    public PassTo(Account account){
        this.account=account;
    }

    public Account getAccount() {
        return account;
    }

    public Material getMaterial() {
        return material;
    }
}
