package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService() {
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }


    // Main Methods / CRUD
    public Account addAccount(Account account) {
        if (!isValidAccount(account)) {
            return null;
        }

        return accountDAO.insertAccount(account);
    }

    public Account tryLogin(Account account) {
        String uname = account.getUsername();

        Account loggingInAccount = accountDAO.geAccountByUsername(uname);

        if (loggingInAccount == null) {
            return null;
        }

        // verify password
        String correctPassword = loggingInAccount.getPassword();
        String attempedPassword = account.getPassword();
        
        if (correctPassword.equals(attempedPassword)) {
            return loggingInAccount;
        }

        return null;
    }

    public Account getAccount(int id) {
        return accountDAO.getAccountById(id);
    }


    // Helpers

    /**
     * @param author an account object.
     * @return Whether account is valid / meets business requirements
     */
    public boolean isValidAccount(Account account) {
        String uname = account.getUsername();
        
        // username is not blank
        if (uname == null || uname.length() == 0) {
            return false;
        }

        // account with that username does not already exist
        if (accountDAO.geAccountByUsername(uname) != null) {
            return false;
        }

        String password = account.getPassword();
        
        // lastly: password is at least 4 characters long
        return (password != null && password.length() > 3);
    }
}