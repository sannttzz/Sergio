# Guia de Configuração e Execução - Cidade em Dia

## 📋 Pré-requisitos

- **Java 17 ou superior**
  - Download: https://www.oracle.com/java/technologies/downloads/
  - Verificar: `java -version`

- **Maven 3.6 ou superior**
  - Download: https://maven.apache.org/download.cgi
  - Verificar: `mvn -version`

- **MySQL 8.0 ou superior**
  - Download: https://dev.mysql.com/downloads/mysql/
  - Verificar: `mysql --version`

- **Git** (para clonar o repositório)
  - Download: https://git-scm.com/

- **Postman** (para testar a API)
  - Download: https://www.postman.com/downloads/

## 🔧 Passo a Passo

### 1. Clonar o Repositório

```bash
git clone <url-do-repositorio>
cd cidade-em-dia
```

### 2. Criar Banco de Dados MySQL

Abra o MySQL Command Line ou MySQL Workbench e execute:

```sql
CREATE DATABASE cidade_em_dia CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

**Opcional:** Criar usuário específico para a aplicação:

```sql
CREATE USER 'cidade_user'@'localhost' IDENTIFIED BY 'cidade_password';
GRANT ALL PRIVILEGES ON cidade_em_dia.* TO 'cidade_user'@'localhost';
FLUSH PRIVILEGES;
```

### 3. Configurar Credenciais do Banco

Edite o arquivo `src/main/resources/application.properties`:

```properties
# Se usando usuário root padrão:
spring.datasource.url=jdbc:mysql://localhost:3306/cidade_em_dia?useTimezone=true&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=

# Se usando usuário específico:
spring.datasource.url=jdbc:mysql://localhost:3306/cidade_em_dia?useTimezone=true&serverTimezone=UTC
spring.datasource.username=cidade_user
spring.datasource.password=cidade_password
```

### 4. Compilar o Projeto

Abra o terminal/prompt de comando na pasta do projeto e execute:

```bash
mvn clean install
```

Isso irá:
- Fazer download de todas as dependências
- Compilar o código
- Executar testes (se houver)
- Criar o JAR da aplicação

### 5. Executar a Aplicação

**Opção 1: Usando Maven**
```bash
mvn spring-boot:run
```

**Opção 2: Executar JAR diretamente**
```bash
mvn clean package
java -jar target/cidade-em-dia-1.0.0.jar
```

**Opção 3: No IDE (Eclipse/IntelliJ)**
- Clique com botão direito no projeto
- Run As → Spring Boot App

### 6. Verificar se está Rodando

Acesse no navegador:
```
http://localhost:8080
```

Você deve receber uma resposta com erro 401 (Unauthorized), indicando que o servidor está rodando.

## ✅ Testando com Postman

### Importar Coleção

1. Abra o Postman
2. Clique em "Import"
3. Selecione o arquivo `postman/projeto-integrador.postman_collection.json`
4. Configure a variável `base_url` para `http://localhost:8080/api/v1`

### Testar Autenticação

1. Na coleção, selecione "Autenticação" → "Login como CIDADAO"
2. Clique em "Send"
3. Você deve receber uma resposta 200 OK com lista de categorias

### Testar Endpoints

Siga a ordem recomendada:

1. **Criar Categoria** (use credenciais de ATENDENTE)
2. **Criar Usuário** (use credenciais de ADMIN)
3. **Criar Ocorrência** (use credenciais de CIDADAO)
4. **Criar Confirmação** (qualquer usuário autenticado)
5. **Alterar Status** (use credenciais de ATENDENTE)
6. **Tentar Excluir Categoria** (com ocorrências) → deve retornar erro 409

## 🐛 Solução de Problemas

### Erro: "Connection refused" ao iniciar

**Solução:**
- Verifique se MySQL está rodando
- Execute: `mysql -u root` para testar conexão
- No Windows, abra Services e procure por "MySQL80"

### Erro: "Database 'cidade_em_dia' doesn't exist"

**Solução:**
- Execute o comando SQL para criar o banco (passo 2)
- Verifique o nome do banco em `application.properties`

### Erro: "java.lang.ClassNotFoundException" ou dependências faltando

**Solução:**
```bash
mvn clean install -DskipTests
```

### Porta 8080 já está em uso

**Solução - Opção 1:**
Edite `application.properties`:
```properties
server.port=8081
```

**Solução - Opção 2 (Windows):**
```bash
netstat -ano | findstr :8080
taskkill /PID <PID> /F
```

### Erro ao fazer login no Postman

**Verifique:**
- Credenciais corretas (cidadao/senha123, atendente/senha456, admin/admin123)
- Usuários estão cadastrados em memória no `SecurityConfig.java`
- Autenticação Basic está ativada

## 📝 Scripts Úteis

### Script para Criar Dados de Teste

Crie um arquivo `setup.sql` e execute:

```sql
USE cidade_em_dia;

-- Categorias
INSERT INTO categorias (nome, descricao, ativa, data_criacao, data_atualizacao) VALUES
('Infraestrutura', 'Problemas de infraestrutura urbana', true, NOW(), NOW()),
('Sinalização', 'Problemas de sinalização e trânsito', true, NOW(), NOW()),
('Iluminação', 'Problemas de iluminação pública', true, NOW(), NOW()),
('Limpeza', 'Limpeza de vias e espaços públicos', true, NOW(), NOW()),
('Segurança', 'Problemas relacionados à segurança', true, NOW(), NOW());

-- Executar no MySQL:
-- mysql -u root < setup.sql
```

## 🔗 Recursos Úteis

- **Documentação Spring Boot:** https://spring.io/projects/spring-boot/docs
- **Spring Data JPA:** https://docs.spring.io/spring-data/jpa/docs/
- **Spring Security:** https://docs.spring.io/spring-security/docs/
- **MySQL Documentation:** https://dev.mysql.com/doc/
- **Postman Learning Center:** https://learning.postman.com/
- **RFC HTTP Status Codes:** https://httpwg.org/specs/rfc7231.html

## 📞 Suporte

Se encontrar problemas:

1. Consulte a documentação oficial dos projetos
2. Verifique os logs da aplicação (nível DEBUG em `application.properties`)
3. Valide a configuração do `application.properties`
4. Teste a conexão MySQL separadamente
5. Reinicie todos os serviços

---

**Último update:** Novembro 2024
