package davenkin.step6_annotation;

import davenkin.BankFixture;
import davenkin.BankService;
import davenkin.step3_connection_holder.TransactionManager;
import org.junit.Test;

import java.sql.SQLException;

import static junit.framework.Assert.assertEquals;

public class AnnotationBankServiceTest extends BankFixture {
    @Test
    public void transferSuccess() throws SQLException {
        TransactionManager transactionManager = new TransactionManager();
        TransactionEnabledAnnotationProxyManager transactionEnabledProxyManager = new TransactionEnabledAnnotationProxyManager(transactionManager);
        BankService bankService = new AnnotationBankService();
        BankService proxyBankService = (BankService) transactionEnabledProxyManager.proxyFor(bankService);
        proxyBankService.transfer(1111, 2222, 200);

        assertEquals(800, getBankAmount(1111));
        assertEquals(1200, getInsuranceAmount(2222));
    }

    @Test
    public void transferFailure() throws SQLException {
        TransactionEnabledAnnotationProxyManager transactionEnabledAnnotationProxyManager = new TransactionEnabledAnnotationProxyManager(new TransactionManager());
        BankService bankService = new AnnotationBankService();
        BankService proxyBankService = (BankService) transactionEnabledAnnotationProxyManager.proxyFor(bankService);

        int toNonExistId = 3333;
        proxyBankService.transfer(1111, toNonExistId, 200);

        assertEquals(1000, getBankAmount(1111));
        assertEquals(1000, getInsuranceAmount(2222));
    }
}
