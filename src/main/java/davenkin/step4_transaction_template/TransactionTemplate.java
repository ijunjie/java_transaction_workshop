package davenkin.step4_transaction_template;

import davenkin.step3_connection_holder.TransactionManager;

public abstract class TransactionTemplate {
    private TransactionManager transactionManager;

    protected TransactionTemplate() {
        transactionManager = new TransactionManager();
    }

    public void doJobInTransaction() {
        try {
            transactionManager.start();
            doJob();
            transactionManager.commit();
        } catch (Exception e) {
            transactionManager.rollback();
        } finally {
            transactionManager.close();
        }
    }

    protected abstract void doJob() throws Exception;
}
