package models;

public class PhoneNumber {
    private Account account;
    private String phoneNumber;

    public PhoneNumber(Account account, String phoneNumber) {
        this.account = account;
        this.phoneNumber = phoneNumber;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
