# PixGuardian

## Requisitos
- Java 17+
- Maven 3.8+
- MySQL

## Como rodar localmente
1. Criar banco: CREATE DATABASE pixguardian;
2. Ajustar src/main/resources/application.properties com usuário/senha.
3. mvn spring-boot:run
4. API disponível em http://localhost:8080/api

## Endpoints principais
- /api/users
- /api/accounts
- /api/pix-keys
- /api/transactions
- /api/fraud-reports
- /api/alerts

## Observações
- Algumas transações podem ser marcadas como `simulated` via DTO para fins educacionais.
- Regras de detecção estão em `FraudDetectionService`.
