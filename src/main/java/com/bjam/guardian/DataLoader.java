package com.bjam.guardian;

import java.math.BigDecimal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.bjam.guardian.model.Account;
import com.bjam.guardian.model.Alert;
import com.bjam.guardian.model.FraudReport;
import com.bjam.guardian.model.PixKey;
import com.bjam.guardian.model.Transaction;
import com.bjam.guardian.model.User;
import com.bjam.guardian.repository.AccountRepository;
import com.bjam.guardian.repository.AlertRepository;
import com.bjam.guardian.repository.FraudReportRepository;
import com.bjam.guardian.repository.PixKeyRepository;
import com.bjam.guardian.repository.TransactionRepository;
import com.bjam.guardian.repository.UserRepository;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepo;
    private final AccountRepository accountRepo;
    private final PixKeyRepository pixKeyRepo;
    private final TransactionRepository transactionRepo;
    private final FraudReportRepository fraudReportRepo;
    private final AlertRepository alertRepo;

    public DataLoader(UserRepository userRepo,
                      AccountRepository accountRepo,
                      PixKeyRepository pixKeyRepo,
                      TransactionRepository transactionRepo,
                      FraudReportRepository fraudReportRepo,
                      AlertRepository alertRepo) {
        this.userRepo = userRepo;
        this.accountRepo = accountRepo;
        this.pixKeyRepo = pixKeyRepo;
        this.transactionRepo = transactionRepo;
        this.fraudReportRepo = fraudReportRepo;
        this.alertRepo = alertRepo;
    }

    @Override
    public void run(String... args) throws Exception {
        // Criar user
        User u = new User("Joao Silva", "joao@example.com", "senhaHashExemplo", User.Role.USER);
        userRepo.save(u);

        // Criar account
        Account a = new Account(u, "12345-6", "Banco Exemplo", new BigDecimal("10000.00"));
        accountRepo.save(a);

        // Criar pix key
        PixKey k = new PixKey(PixKey.KeyType.EMAIL, "joao@example.com", a);
        pixKeyRepo.save(k);

        // Criar transaction simulada (simulated=true)
        Transaction t = new Transaction(a, k, new BigDecimal("50.00"), true, "Teste simulada");
        transactionRepo.save(t);

        // Criar fraud report
        FraudReport fr = new FraudReport(u, t, "Suspeita de fraude");
        fraudReportRepo.save(fr);

        // Criar alerta
        Alert al = new Alert(t, Alert.Level.INFO, "Alerta teste");
        alertRepo.save(al);

        System.out.println("=== DataLoader executado: registros iniciais criados ===");
        System.out.println("Users: " + userRepo.count());
        System.out.println("Accounts: " + accountRepo.count());
        System.out.println("PixKeys: " + pixKeyRepo.count());
        System.out.println("Transactions: " + transactionRepo.count());
        System.out.println("FraudReports: " + fraudReportRepo.count());
        System.out.println("Alerts: " + alertRepo.count());
    }
}
