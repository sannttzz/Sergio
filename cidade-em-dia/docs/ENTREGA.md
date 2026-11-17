# Guia de Entrega - Cidade em Dia

## 📋 Checklist Pré-Entrega

Antes de submeter o projeto, verifique todos esses itens:

### ✅ Código-Fonte
- [x] Todas as 5 entidades implementadas
- [x] CRUD completo para todas as entidades
- [x] Arquitetura em camadas (Entity, Repository, Service, Controller)
- [x] 2+ regras de negócio implementadas
- [x] Validações com Bean Validation
- [x] Tratamento centralizado de exceções
- [x] 2+ consultas personalizadas
- [x] Spring Security com HTTP Basic
- [x] Funcionalidade específica (alteração de status com histórico)
- [x] Código compila sem erros

### ✅ Documentação
- [x] README.md com todos os dados solicitados
- [x] Identificação de TODOS os integrantes (nome completo e RA)
- [x] Descrição do projeto e objetivo
- [x] Tecnologias utilizadas
- [x] Instruções de execução
- [x] Documentação dos endpoints (docs/documentacao-api.md)
- [x] Diagrama de classes/ER (docs/diagrama-classes.puml)
- [x] Guia de setup (docs/SETUP.md)

### ✅ Banco de Dados
- [x] MySQL configurado
- [x] Banco de dados criado automaticamente pelo Hibernate
- [x] Tabelas geradas corretamente
- [x] Relacionamentos implementados

### ✅ Testes Postman
- [x] Coleção exportada em JSON
- [x] Arquivo salvo em: postman/projeto-integrador.postman_collection.json
- [x] Variáveis incluídas na exportação
- [x] Exemplos de requisições para todos os endpoints
- [x] Respostas salvas (Save Response)
- [x] Cenários de erro testados (400, 401, 403, 404, 409)

### ✅ Organização do Repositório
- [x] Código-fonte em src/main/java
- [x] Resources em src/main/resources
- [x] Documentação em docs/
- [x] Coleção Postman em postman/
- [x] pom.xml configurado corretamente
- [x] .gitignore presente
- [x] README.md na raiz

---

## 📝 Integrantes Identificados

> **IMPORTANTE:** Verifique se todos os nomes e RAs estão corretos no README.md

1. **Kauan Ribeiro** (RA: 273006)
2. **Luiz Gustavo Legori Ramos** (RA: 273066)
3. **Marcos Vinicius Gonçalves Miotto** (RA: 272975)
4. **Pedro Henrique Cavalheiro Martins** (RA: 272747)
5. **Gustavo Rodrigues Giamarco** (RA: 272805)
6. **Otavio Siqueira Freire dos Santos** (RA: 273524)

---

## 🔐 Verificação de Segurança

Antes de submeter, verifique:

- [x] Sem senhas reais no código
- [x] Sem dados sensíveis no repositório
- [x] Credenciais de teste apenas (senha123, senha456, admin123)
- [x] application.properties com dados genéricos
- [x] .gitignore configurado para evitar envio de arquivos sensíveis

---

## 📦 Como Submeter

### 1. Inicializar Repositório Git (se ainda não fez)

```bash
cd cidade-em-dia
git init
git add .
git commit -m "Projeto Integrador - Cidade em Dia - Backend"
git branch -M main
git remote add origin https://github.com/seu-usuario/cidade-em-dia.git
git push -u origin main
```

### 2. Copiar Link do Repositório

Exemplo: `https://github.com/seu-usuario/cidade-em-dia`

### 3. Acessar Ambiente Conhecer

- Acesse o Ambiente Conhecer
- Localize a atividade: **Projeto Integrador**
- Clique em "Enviar"
- Cole o link do repositório GitHub
- Clique em "Enviar Atividade"

### 4. Verificação Final

Após submeter, verifique:
- [x] Link é válido e acessível
- [x] Repositório é público
- [x] Todos os arquivos foram enviados
- [x] README.md está visível
- [x] Estrutura do projeto está correta

---

## 🧪 Teste Local Antes de Entregar

Execute localmente para garantir que funciona:

```bash
# 1. Criar banco de dados
# No MySQL: CREATE DATABASE cidade_em_dia;

# 2. Compilar
mvn clean install

# 3. Executar
mvn spring-boot:run

# 4. Testar endpoints
# Usar Postman com a coleção exportada

# 5. Verificar regras de negócio
# Tentar excluir categoria com ocorrências (deve falhar)
# Tentar criar ocorrência em categoria inativa (deve falhar)

# 6. Testar autenticação
# Credenciais válidas: funcionam
# Credenciais inválidas: erro 401
# Sem credenciais: erro 401
# Sem permissão: erro 403
```

---

## 📞 Último Contato Antes da Entrega

### Pontos-Chave para Apresentação

Certifique-se de que alguém do grupo conhece bem:

1. **Arquitetura**: Como funciona a separação de responsabilidades
2. **Regras de Negócio**: Por que existem (Regra 1 e 2)
3. **Autenticação**: Como funciona HTTP Basic e Spring Security
4. **Funcionalidade Específica**: Como o histórico de status é registrado
5. **Tratamento de Erros**: Como as exceções são tratadas

### Possíveis Perguntas do Professor

- "Por que vocês usaram essa arquitetura?"
- "Como a validação funciona?"
- "O que acontece ao tentar excluir uma categoria com ocorrências?"
- "Como o histórico de status é registrado?"
- "Qual a diferença entre 401 e 403?"
- "Como implementarem a autenticação?"

---

## ⚠️ Erros Comuns a Evitar

- ❌ Enviar código com senhas reais
- ❌ Não incluir nomes completos e RAs dos integrantes
- ❌ Repositório privado (deve ser público)
- ❌ Falta de README.md
- ❌ Código que não compila
- ❌ Banco de dados não funciona
- ❌ Endpoints não funcionam
- ❌ Falta de autenticação
- ❌ Regras de negócio não implementadas
- ❌ Projeto sem documentação

---

## ✨ Diferenciais para Avalição Positiva

O projeto implementa:

1. ✅ **Exceções Personalizadas** - Melhor error handling
2. ✅ **GlobalExceptionHandler** - Tratamento centralizado
3. ✅ **DTOs** - Melhor separação de conceitos
4. ✅ **Lombok** - Código mais limpo
5. ✅ **Consultas Personalizadas** - Mais de 2 implementadas
6. ✅ **Enumerações** - Tipos seguros
7. ✅ **Cascade Operations** - Integridade referencial
8. ✅ **Documentação Completa** - API documentada
9. ✅ **Scripts de Teste** - Dados iniciais
10. ✅ **Diagrama UML** - Visual das classes

---

## 📅 Cronograma Recomendado

| Data | Atividade |
|------|-----------|
| 2 semanas antes | Terminar implementação |
| 1 semana antes | Testes e documentação |
| 3 dias antes | Testes Postman e correções |
| 1 dia antes | Verificação final de tudo |
| No prazo | Fazer submissão |

---

## 🎓 Recursos de Referência

- **Spring Boot Documentation:** https://spring.io/projects/spring-boot
- **Spring Data JPA:** https://spring.io/projects/spring-data-jpa
- **Spring Security:** https://spring.io/projects/spring-security
- **Jakarta Bean Validation:** https://jakarta.ee/specifications/bean-validation/
- **MySQL Documentation:** https://dev.mysql.com/doc/

---

## 📧 Suporte

Se tiver dúvidas:

1. Consulte a documentação oficial
2. Verifique os logs da aplicação
3. Teste com Postman
4. Revise o README.md do projeto

---

**BOA SORTE NA ENTREGA!** 🚀

*Projeto desenvolvido com sucesso - Novembro 2024*
