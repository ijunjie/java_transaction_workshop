package davenkin.step3_connection_holder;

import davenkin.BankService;

public class ConnectionHolderBankService implements BankService {
    private TransactionManager transactionManager;
    private ConnectionHolderBankDao connectionHolderBankDao;
    private ConnectionHolderInsuranceDao connectionHolderInsuranceDao;

    public ConnectionHolderBankService() {
        transactionManager = new TransactionManager();
        connectionHolderBankDao = new ConnectionHolderBankDao();
        connectionHolderInsuranceDao = new ConnectionHolderInsuranceDao();

    }

    public void transfer(int fromId, int toId, int amount) {
        try {
            transactionManager.start();
            connectionHolderBankDao.withdraw(fromId, amount);
            connectionHolderInsuranceDao.deposit(toId, amount);
            transactionManager.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transactionManager.rollback();
        } finally {
            transactionManager.close();
        }
    }
}
