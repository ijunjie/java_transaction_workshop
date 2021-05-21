package davenkin.step5_transaction_proxy;

import davenkin.BankService;
import davenkin.step3_connection_holder.ConnectionHolderBankDao;
import davenkin.step3_connection_holder.ConnectionHolderInsuranceDao;

public class BareBankService implements BankService {
    private ConnectionHolderBankDao connectionHolderBankDao;
    private ConnectionHolderInsuranceDao connectionHolderInsuranceDao;

    public BareBankService() {
        connectionHolderBankDao = new ConnectionHolderBankDao();
        connectionHolderInsuranceDao = new ConnectionHolderInsuranceDao();
    }

    public void transfer(final int fromId, final int toId, final int amount) {
        try {
            connectionHolderBankDao.withdraw(fromId, amount);
            connectionHolderInsuranceDao.deposit(toId, amount);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
