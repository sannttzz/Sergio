# Cidade em Dia - Backend

Sistema de registro de ocorrências de problemas urbanos desenvolvido com Spring Boot, Spring Data JPA e MySQL.

## 📋 Descrição

O **Cidade em Dia** é uma plataforma que permite que cidadãos registrem problemas urbanos (buracos, semáforos quebrados, iluminação inadequada, etc.) e que atendentes da prefeitura acompanhem e resolvam esses problemas. O sistema mantém histórico de alterações de status e permite confirmações de ocorrências.

## 👥 Integrantes do Grupo

- **Kauan Ribeiro** (RA: 273006)
- **Luiz Gustavo Legori Ramos** (RA: 273066)
- **Marcos Vinicius Gonçalves Miotto** (RA: 272975)
- **Pedro Henrique Cavalheiro Martins** (RA: 272747)
- **Gustavo Rodrigues Giamarco** (RA: 272805)
- **Otavio Siqueira Freire dos Santos** (RA: 273524)

## 🎯 Objetivo

Desenvolver um backend funcional que implemente:
- Arquitetura em camadas (Entity, Repository, Service, Controller)
- APIs REST com operações CRUD completas
- Persistência de dados com MySQL e JPA
- Validações de dados e tratamento de exceções
- Autenticação e autorização com Spring Security
- Regras de negócio específicas do domínio
- Consultas personalizadas com Spring Data JPA

## 🛠️ Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.1.5**
- **Spring Data JPA**
- **Spring Security**
- **MySQL 8.0**
- **Maven**
- **Lombok**
- **Jakarta Validation**

## 📊 Modelagem de Dados

### Entidades Principais

1. **Usuario** - Usuários do sistema (Cidadão, Atendente, Admin)
2. **Categoria** - Categorias de ocorrências (Infraestrutura, Sinalização, Iluminação, etc.)
3. **Ocorrencia** - Registros de problemas urbanos
4. **Confirmacao** - Confirmações de ocorrências por cidadãos
5. **HistoricoStatus** - Histórico de alterações de status

### Diagrama Entidade-Relacionamento

```
USUARIO
├── id (PK)
├── nome
├── email (UNIQUE)
├── senha
├── perfil (ENUM: CIDADAO, ATENDENTE, ADMIN)
├── ativo
└── timestamps

CATEGORIA
├── id (PK)
├── nome (UNIQUE)
├── descricao
├── ativa
└── timestamps

OCORRENCIA
├── id (PK)
├── titulo
├── descricao
├── rua
├── numero
├── bairro
├── cidade
├── referencia
├── foto
├── urgencia (ENUM: BAIXA, MEDIA, ALTA)
├── prioridade (ENUM: BAIXA, MEDIA, ALTA)
├── status (ENUM: NOVA, EM_ANALISE, EM_ANDAMENTO, RESOLVIDA)
├── categoria_id (FK)
├── autor_id (FK)
└── timestamps

CONFIRMACAO
├── id (PK)
├── ocorrencia_id (FK)
├── usuario_id (FK)
├── comentario
└── data_criacao

HISTORICO_STATUS
├── id (PK)
├── ocorrencia_id (FK)
├── usuario_id (FK)
├── status_anterior (ENUM)
├── status_novo (ENUM)
├── motivo
├── perfil_que_alterou (ENUM)
└── data
```

## 🚀 Como Executar

### Pré-requisitos

- Java 17 ou superior
- Maven 3.6+
- MySQL 8.0+
- Postman (para testar a API)

### 1. Configurar Banco de Dados

```sql
CREATE DATABASE cidade_em_dia CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### 2. Configurar Credenciais

Editar `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/cidade_em_dia?useTimezone=true&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=sua_senha
```

### 3. Compilar o Projeto

```bash
mvn clean install
```

### 4. Executar a Aplicação

```bash
mvn spring-boot:run
```

A aplicação iniciará em `http://localhost:8080`

## 🔐 Autenticação e Autorização

### Usuários Padrão

| Username | Senha | Perfil | Acesso |
|----------|-------|--------|--------|
| cidadao | senha123 | CIDADAO | Criar e visualizar ocorrências |
| atendente | senha456 | ATENDENTE | Criar, visualizar, atualizar ocorrências |
| admin | admin123 | ADMIN | Acesso total |

### Tipos de Acesso

- **CIDADAO**: Pode registrar ocorrências e confirmar ocorrências
- **ATENDENTE**: Pode gerenciar ocorrências, alterar status e categorias
- **ADMIN**: Acesso total ao sistema, gerenciar usuários

## 📚 Endpoints da API

### Usuários (Apenas ADMIN)

```
POST   /api/v1/usuarios              - Criar usuário
GET    /api/v1/usuarios              - Listar todos
GET    /api/v1/usuarios/{id}         - Obter por ID
GET    /api/v1/usuarios/email/{email} - Obter por email
GET    /api/v1/usuarios/perfil/{perfil} - Listar por perfil
PUT    /api/v1/usuarios/{id}         - Atualizar
DELETE /api/v1/usuarios/{id}         - Excluir
```

### Categorias

```
POST   /api/v1/categorias            - Criar (ADMIN, ATENDENTE)
GET    /api/v1/categorias            - Listar todas
GET    /api/v1/categorias/{id}       - Obter por ID
GET    /api/v1/categorias/ativas     - Listar ativas
PUT    /api/v1/categorias/{id}       - Atualizar (ADMIN, ATENDENTE)
DELETE /api/v1/categorias/{id}       - Excluir (ADMIN)
```

### Ocorrências

```
POST   /api/v1/ocorrencias           - Criar (Autenticado)
GET    /api/v1/ocorrencias           - Listar todas
GET    /api/v1/ocorrencias/{id}      - Obter por ID
GET    /api/v1/ocorrencias/categoria/{categoriaId} - Por categoria
GET    /api/v1/ocorrencias/status/{status} - Por status
GET    /api/v1/ocorrencias/autor/{autorId} - Por autor
GET    /api/v1/ocorrencias/urgencia/{urgencia} - Por urgência
PUT    /api/v1/ocorrencias/{id}      - Atualizar (ADMIN, ATENDENTE)
POST   /api/v1/ocorrencias/{id}/status - Alterar status (ADMIN, ATENDENTE)
DELETE /api/v1/ocorrencias/{id}      - Excluir (ADMIN)
```

### Confirmações

```
POST   /api/v1/confirmacoes          - Criar (Autenticado)
GET    /api/v1/confirmacoes          - Listar todas
GET    /api/v1/confirmacoes/{id}     - Obter por ID
GET    /api/v1/confirmacoes/ocorrencia/{ocorrenciaId} - Por ocorrência
GET    /api/v1/confirmacoes/usuario/{usuarioId} - Por usuário
DELETE /api/v1/confirmacoes/{id}     - Excluir (ADMIN)
```

## 🎯 Regras de Negócio Implementadas

### Regra 1: Não permitir exclusão de categoria com ocorrências

Ao tentar excluir uma categoria que possui ocorrências associadas, o sistema retorna erro 409 (Conflict):

```json
{
  "timestamp": "2024-11-11T20:00:00",
  "status": 409,
  "error": "Conflict",
  "mensagem": "Não é possível excluir a categoria 'Infraestrutura' pois ela possui 5 ocorrência(s) associada(s)"
}
```

### Regra 2: Categoria deve estar ativa para registrar ocorrências

Ao tentar registrar uma ocorrência em uma categoria inativa, o sistema retorna erro 409:

```json
{
  "timestamp": "2024-11-11T20:00:00",
  "status": 409,
  "error": "Conflict",
  "mensagem": "Não é possível registrar ocorrência na categoria 'Categoria Antiga' pois ela está inativa"
}
```

## 🔍 Consultas Personalizadas

### 1. Ocorrências por Status

```
GET /api/v1/ocorrencias/status/EM_ANDAMENTO
```

Retorna todas as ocorrências com status EM_ANDAMENTO, ordenadas por data de criação.

### 2. Ocorrências por Urgência

```
GET /api/v1/ocorrencias/urgencia/ALTA
```

Retorna todas as ocorrências com urgência ALTA.

### 3. Usuários Ativos por Perfil

Implementado em backend:
```java
@Query("SELECT u FROM Usuario u WHERE u.perfil = :perfil AND u.ativo = true")
List<Usuario> findByPerfilAtivos(@Param("perfil") Usuario.Perfil perfil);
```

### 4. Confirmações por Ocorrência

```
GET /api/v1/confirmacoes/ocorrencia/1
```

Retorna todas as confirmações de uma ocorrência específica, ordenadas por data.

## ✅ Validações Implementadas

- `@NotBlank`: Campos obrigatórios não vazios
- `@NotNull`: Campos obrigatórios
- `@Size`: Tamanho de strings (min/max)
- `@Email`: Validação de email
- `@Min/@Max`: Validação de números

## 🧪 Testes com Postman

A coleção Postman está disponível em `postman/projeto-integrador.postman_collection.json`

### Cenários de Teste Inclusos

1. ✅ Criar categorias, usuários e ocorrências
2. ✅ Consultar registros (individual e listagem)
3. ✅ Atualizar registros
4. ✅ Excluir registros
5. ✅ Testar consultas personalizadas
6. ✅ Testar regras de negócio
7. ✅ Testar validações (dados inválidos)
8. ✅ Testar autenticação (credenciais válidas/inválidas)
9. ✅ Testar autorização (acesso negado)
10. ✅ Testar funcionalidade de alteração de status

## 📝 Tratamento de Erros

A aplicação implementa tratamento centralizado de exceções com os seguintes status HTTP:

- **400 Bad Request**: Dados inválidos ou requisição mal formatada
- **401 Unauthorized**: Usuário não autenticado
- **403 Forbidden**: Usuário autenticado mas sem permissão
- **404 Not Found**: Recurso não encontrado
- **409 Conflict**: Violação de regra de negócio
- **500 Internal Server Error**: Erro interno do servidor

## 📂 Estrutura do Projeto

```
cidade-em-dia/
├── pom.xml
├── README.md
├── docs/
│   └── modelo-dados.png
├── postman/
│   └── projeto-integrador.postman_collection.json
└── src/
    └── main/
        ├── java/com/cidadeemdia/
        │   ├── CidadeEmDiaApplication.java
        │   ├── controller/
        │   ├── service/
        │   ├── repository/
        │   ├── entity/
        │   ├── dto/
        │   ├── exception/
        │   └── config/
        └── resources/
            └── application.properties
```

## 🔧 Configurações Importantes

### Criação Automática de Tabelas

A aplicação cria automaticamente as tabelas do banco de dados na primeira execução graças à configuração:

```properties
spring.jpa.hibernate.ddl-auto=update
```

### Logging

Para ativar logs de SQL, descomente em `application.properties`:

```properties
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

## 📞 Suporte e Dúvidas

Para dúvidas sobre a implementação, consulte:
- Documentação do Spring Boot: https://spring.io/projects/spring-boot
- Spring Data JPA: https://spring.io/projects/spring-data-jpa
- Spring Security: https://spring.io/projects/spring-security

## 📄 Licença

Projeto desenvolvido para fins acadêmicos.

---

**Desenvolvido por:** Grupo de Projeto Integrador
**Data:** Novembro de 2024
**Disciplina:** Desenvolvimento de Aplicações Web
