package davenkin.step6_annotation;

import davenkin.BankService;
import davenkin.step3_connection_holder.ConnectionHolderBankDao;
import davenkin.step3_connection_holder.ConnectionHolderInsuranceDao;

public class AnnotationBankService implements BankService {
    private ConnectionHolderBankDao connectionHolderBankDao;
    private ConnectionHolderInsuranceDao connectionHolderInsuranceDao;

    public AnnotationBankService() {
        connectionHolderBankDao = new ConnectionHolderBankDao();
        connectionHolderInsuranceDao = new ConnectionHolderInsuranceDao();
    }

    @Transactional
    public void transfer(final int fromId, final int toId, final int amount) {
        try {
            connectionHolderBankDao.withdraw(fromId, amount);
            connectionHolderInsuranceDao.deposit(toId, amount);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
