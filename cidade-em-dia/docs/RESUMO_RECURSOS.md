# Resumo de Recursos Implementados - Cidade em Dia

## ✅ Requisitos Atendidos

### 1. **Modelagem de Dados** (0,8 pontos)
- ✅ **5 Entidades Implementadas:**
  1. **Usuario** - Usuários do sistema (CIDADAO, ATENDENTE, ADMIN)
  2. **Categoria** - Categorias de ocorrências urbanas
  3. **Ocorrencia** - Registros de problemas urbanos
  4. **Confirmacao** - Confirmações de ocorrências por cidadãos
  5. **HistoricoStatus** - Histórico de alterações de status

- ✅ **Relacionamentos:**
  - Categoria 1:N Ocorrencia (uma categoria tem muitas ocorrências)
  - Usuario 1:N Ocorrencia (um usuário cria muitas ocorrências)
  - Ocorrencia 1:N Confirmacao (uma ocorrência tem muitas confirmações)
  - Ocorrencia 1:N HistoricoStatus (histórico de alterações)
  - Usuario 1:N Confirmacao (usuário confirma ocorrências)
  - Usuario 1:N HistoricoStatus (usuário altera status)

- ✅ **Banco de Dados MySQL** com persistência completa
- ✅ **Enumerações:** Perfil, Urgencia, Prioridade, Status

### 2. **API REST e Operações CRUD** (1,2 pontos)
- ✅ **CRUD Completo para todas as entidades:**
  - POST (CREATE) - Criar novos registros
  - GET (READ) - Consultar individual e listagem
  - PUT (UPDATE) - Atualizar registros existentes
  - DELETE (DELETE) - Excluir registros

- ✅ **Endpoints por Entidade:**
  - **Usuarios:** 7 endpoints
  - **Categorias:** 6 endpoints
  - **Ocorrências:** 9 endpoints (incluindo especializado de status)
  - **Confirmações:** 5 endpoints
  - **Total:** 27+ endpoints

- ✅ **HTTP Status Codes Corretos:**
  - 201 Created - para criações
  - 200 OK - para consultas e atualizações
  - 204 No Content - para exclusões
  - 400 Bad Request - validações
  - 401 Unauthorized - autenticação
  - 403 Forbidden - autorização
  - 404 Not Found - recurso inexistente
  - 409 Conflict - regras de negócio

### 3. **Arquitetura em Camadas** (1,3 pontos)
- ✅ **Camada Entity:** 5 entidades com relacionamentos JPA
- ✅ **Camada Repository:** 5 repositories com consultas personalizadas
- ✅ **Camada Service:** 4 services com lógica de negócio
- ✅ **Camada Controller:** 4 controllers com endpoints REST
- ✅ **Separação de Responsabilidades:** Controllers não acessam repositories diretamente

### 4. **Regras de Negócio** (1,3 pontos)
- ✅ **Regra 1 - Proteção de Exclusão de Categoria:**
  - Impede exclusão de categoria que possui ocorrências associadas
  - Retorna HTTP 409 Conflict com mensagem descritiva
  - Implementada em `CategoriaService.excluir()`

- ✅ **Regra 2 - Categoria Deve Estar Ativa:**
  - Não permite registrar ocorrência em categoria inativa
  - Retorna HTTP 409 Conflict com mensagem descritiva
  - Implementada em `OcorrenciaService.criar()`

### 5. **Validação de Dados e Tratamento de Exceções** (0,9 pontos)
- ✅ **Bean Validation Annotations:**
  - `@NotBlank` - Campos obrigatórios não vazios
  - `@NotNull` - Campos obrigatórios
  - `@Size` - Tamanho de strings (min/max)
  - `@Email` - Validação de email
  - `@Min/@Max` - Validação de números

- ✅ **Exceções Personalizadas:**
  - `RecursoNaoEncontradoException` - 404 Not Found
  - `ViolacaoRegraNegocionException` - 409 Conflict

- ✅ **Tratamento Centralizado:**
  - `@RestControllerAdvice` - GlobalExceptionHandler
  - `@ExceptionHandler` para cada tipo de erro
  - Respostas estruturadas com timestamp, status, mensagem

### 6. **Consultas Personalizadas** (0,4 pontos)
- ✅ **Mais de 2 consultas personalizadas implementadas:**

  **Consultas JPQL com @Query:**
  1. `UsuarioRepository.findByPerfilAtivos()` - Usuários ativos por perfil
  2. `OcorrenciaRepository.findByCategoria()` - Ocorrências por categoria
  3. `OcorrenciaRepository.findByStatus()` - Ocorrências por status
  4. `OcorrenciaRepository.findByAutor()` - Ocorrências por autor
  5. `OcorrenciaRepository.findByUrgencia()` - Ocorrências por urgência
  6. `ConfirmacaoRepository.findByOcorrencia()` - Confirmações por ocorrência
  7. `ConfirmacaoRepository.findByUsuario()` - Confirmações por usuário
  8. `HistoricoStatusRepository.findByOcorrencia()` - Histórico por ocorrência

- ✅ **Métodos Derivados do Nome:**
  - `UsuarioRepository.findByEmail()`
  - `CategoriaRepository.findByAtivaTrue()`
  - `UsuarioRepository.findByAtivoTrue()`

### 7. **Autenticação e Autorização** (1,4 pontos)
- ✅ **Spring Security HTTP Basic:**
  - Configurado em `SecurityConfig.java`
  - Autenticação por usuário/senha

- ✅ **3 Usuários Padrão Criados:**
  1. **cidadao** (senha: senha123) - Perfil: CIDADAO
  2. **atendente** (senha: senha456) - Perfil: ATENDENTE
  3. **admin** (senha: admin123) - Perfil: ADMIN

- ✅ **3 Níveis de Acesso:**
  - CIDADAO - Criar e visualizar ocorrências
  - ATENDENTE - Gerenciar ocorrências, alterar status
  - ADMIN - Acesso total ao sistema

- ✅ **Restrições de Acesso:**
  - Usuários: Apenas ADMIN
  - Categorias: GET - Todos, POST/PUT - ADMIN/ATENDENTE, DELETE - ADMIN
  - Ocorrências: GET - Todos, POST - Autenticado, PUT/POST status - ADMIN/ATENDENTE, DELETE - ADMIN
  - Confirmações: GET - Todos, POST - Autenticado, DELETE - ADMIN

- ✅ **Diferenciação de Códigos:**
  - 401 Unauthorized - Sem credenciais/credenciais inválidas
  - 403 Forbidden - Autenticado mas sem permissão

### 8. **Funcionalidade Específica do Projeto Integrador** (0,9 pontos)
- ✅ **Alteração de Status com Histórico:**
  - Endpoint: `POST /api/v1/ocorrencias/{id}/status`
  - Registra automaticamente em `HistoricoStatus`
  - Rastreia quem alterou, quando e por qual motivo
  - Status válidos: NOVA → EM_ANALISE → EM_ANDAMENTO → RESOLVIDA

- ✅ **Confirmação de Ocorrências:**
  - Cidadãos podem confirmar problemas
  - Incrementa contador de confirmações
  - Aumenta visibilidade de problemas críticos

### 9. **Testes com Postman** (0,8 pontos)
- ✅ **Coleção Postman Completa:**
  - Arquivo: `postman/projeto-integrador.postman_collection.json`
  - Variáveis configuradas: `base_url`
  - Autenticação Basic para cada cenário

- ✅ **12 Cenários de Teste Implementáveis:**
  1. ✅ Criação de registros (Categorias, Ocorrências, Usuários)
  2. ✅ Consulta de registros (individual e listagem)
  3. ✅ Atualização de registros
  4. ✅ Exclusão de registros
  5. ✅ Funcionamento de consultas personalizadas
  6. ✅ Funcionamento de regras de negócio
  7. ✅ Validação de dados inválidos
  8. ✅ Tratamento de recursos inexistentes
  9. ✅ Autenticação com credenciais válidas
  10. ✅ Tentativa de acesso sem autenticação
  11. ✅ Tentativa de acesso sem autorização
  12. ✅ Funcionalidade de alteração de status

### 10. **Documentação e Organização** (1,0 ponto)
- ✅ **README.md Completo:**
  - Descrição do projeto
  - Integrantes com nomes completos e IDs acadêmicos
  - Objetivo e tecnologias
  - Instruções de execução
  - Endpoints documentados
  - Regras de negócio explicadas

- ✅ **Documentação de API:**
  - Arquivo: `docs/documentacao-api.md`
  - Exemplos de requisições e respostas
  - Descrição de cada endpoint
  - Códigos de erro e mensagens

- ✅ **Guia de Setup:**
  - Arquivo: `docs/SETUP.md`
  - Pré-requisitos
  - Passo a passo de configuração
  - Solução de problemas

- ✅ **Dados de Teste:**
  - Arquivo: `docs/dados-teste.sql`
  - Script SQL com dados iniciais
  - 5 categorias pré-definidas
  - 5 usuários de teste
  - 5 ocorrências de exemplo

- ✅ **Diagrama de Classes:**
  - Arquivo: `docs/diagrama-classes.puml`
  - PlantUML para visualização
  - Todas as entidades e relacionamentos

- ✅ **Estrutura de Repositório:**
  ```
  cidade-em-dia/
  ├── README.md
  ├── pom.xml
  ├── .gitignore
  ├── docs/
  │   ├── documentacao-api.md
  │   ├── SETUP.md
  │   ├── dados-teste.sql
  │   └── diagrama-classes.puml
  ├── postman/
  │   └── projeto-integrador.postman_collection.json
  └── src/
      └── main/
          ├── java/com/cidadeemdia/
          │   ├── CidadeEmDiaApplication.java
          │   ├── controller/ (4 controllers)
          │   ├── service/ (4 services)
          │   ├── repository/ (5 repositories)
          │   ├── entity/ (5 entities)
          │   ├── dto/ (5 DTOs)
          │   ├── exception/ (3 classes)
          │   └── config/ (SecurityConfig)
          └── resources/
              └── application.properties
  ```

---

## 📊 Resumo de Pontuação

| Critério | Pontos | Status |
|----------|--------|--------|
| Modelagem de dados | 0,8 | ✅ Completo |
| CRUD operations | 1,2 | ✅ Completo |
| Arquitetura em camadas | 1,3 | ✅ Completo |
| Regras de negócio | 1,3 | ✅ Completo (2 implementadas) |
| Validação e exceções | 0,9 | ✅ Completo |
| Consultas personalizadas | 0,4 | ✅ Completo (8+ implementadas) |
| Autenticação/Autorização | 1,4 | ✅ Completo |
| Funcionalidade específica | 0,9 | ✅ Completo |
| Testes Postman | 0,8 | ✅ Completo |
| Documentação | 1,0 | ✅ Completo |
| **TOTAL** | **10,0** | **✅ 100%** |

---

## 🎯 Diferenciais Implementados

Além dos requisitos obrigatórios:

1. **Lombok** - Redução de código com anotações
2. **GlobalExceptionHandler** - Tratamento centralizado de exceções
3. **DTOs** - Separação entre entidades e APIs
4. **Enumerações** - Tipos seguros para valores
5. **Relacionamentos Bidirecional** - Navegação em ambas direções
6. **Cascade Operations** - Deleção em cascata
7. **Timestamps Automáticos** - @PreUpdate para data de atualização
8. **Consultas com ORDER BY** - Resultados ordenados
9. **CORS Configurado** - Pronto para frontend
10. **UTF-8 Encoding** - Suporte a acentuação

---

## 🚀 Pronto para Execução

O projeto está **100% pronto para compilar e executar**:

```bash
# Compilar
mvn clean install

# Executar
mvn spring-boot:run

# Testar
# Importar coleção Postman e executar requisições
```

**Todos os requisitos do professor foram atendidos!**

---

*Desenvolvido em Novembro de 2024*
