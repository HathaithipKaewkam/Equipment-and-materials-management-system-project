package ku.project.models.products;

import ku.project.models.account.Account;

public class Lender {
    private Account borrower;
    private Product product;

    public Lender(Account borrower, Product product) {
        this.borrower = borrower;
        this.product = product;
    }
    public Lender(Account borrower) {
        this.borrower = borrower;
    }

    public Account getBorrower() {
        return borrower;
    }

    public Product getProduct() {
        return product;
    }
}
