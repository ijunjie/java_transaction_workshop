package davenkin.step4_transaction_template;

import davenkin.BankService;
import davenkin.step3_connection_holder.ConnectionHolderBankDao;
import davenkin.step3_connection_holder.ConnectionHolderInsuranceDao;

public class TransactionTemplateBankService implements BankService {
    private ConnectionHolderBankDao connectionHolderBankDao;
    private ConnectionHolderInsuranceDao connectionHolderInsuranceDao;

    public TransactionTemplateBankService() {
        connectionHolderBankDao = new ConnectionHolderBankDao();
        connectionHolderInsuranceDao = new ConnectionHolderInsuranceDao();
    }

    public void transfer(final int fromId, final int toId, final int amount) {
        new TransactionTemplate() {
            @Override
            protected void doJob() throws Exception {
                connectionHolderBankDao.withdraw(fromId, amount);
                connectionHolderInsuranceDao.deposit(toId, amount);
            }
        }.doJobInTransaction();
    }
}
