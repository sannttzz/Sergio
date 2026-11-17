# Quick Start - Cidade em Dia

## ⚡ Iniciar Rápido (5 minutos)

### 1. Criar Banco de Dados
```sql
CREATE DATABASE cidade_em_dia;
```

### 2. Configurar Banco (opcional)
Se usando usuário diferente de root, edite `src/main/resources/application.properties`:
```properties
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
```

### 3. Compilar
```bash
cd c:\Users\kauan\Desktop\cidade-em-dia
mvn clean install
```

### 4. Executar
```bash
mvn spring-boot:run
```

### 5. Testar
**URL:** http://localhost:8080/api/v1/categorias

**Com Postman:**
1. Importar: `postman/projeto-integrador.postman_collection.json`
2. Usar credenciais: cidadao / senha123

---

## 🎯 Testes Rápidos

### Listar Categorias (CIDADAO)
```bash
curl -X GET "http://localhost:8080/api/v1/categorias" \
  -H "Authorization: Basic Y2lkYWRhbzpzZW5oYTEyMw=="
```

### Criar Categoria (ATENDENTE)
```bash
curl -X POST "http://localhost:8080/api/v1/categorias" \
  -H "Content-Type: application/json" \
  -H "Authorization: Basic YXRlbmRlbnRlOnNlbmhhNDU2" \
  -d '{"nome":"Infraestrutura","descricao":"Problemas urbanos","ativa":true}'
```

### Sem Autenticação (deve retornar 401)
```bash
curl -X GET "http://localhost:8080/api/v1/categorias"
```

---

## 📁 Arquivos Importantes

| Arquivo | Descrição |
|---------|-----------|
| `README.md` | Documentação principal |
| `pom.xml` | Dependências Maven |
| `src/main/resources/application.properties` | Configurações do banco |
| `docs/SETUP.md` | Guia de setup completo |
| `docs/documentacao-api.md` | Documentação dos endpoints |
| `postman/projeto-integrador.postman_collection.json` | Testes |

---

## 🔑 Credenciais

```
cidadao   / senha123   → CIDADAO (criar ocorrências)
atendente / senha456   → ATENDENTE (gerenciar)
admin     / admin123   → ADMIN (total)
```

---

## ⚠️ Problemas Comuns

### Porta 8080 já está em uso
Edite `application.properties`:
```properties
server.port=8081
```

### Erro ao conectar MySQL
Verifique:
- MySQL está rodando? `mysql -u root`
- Banco existe? `SHOW DATABASES;`
- Credenciais corretas em `application.properties`

### Maven não encontrado
Adicione Maven ao PATH ou use Java IDE (Eclipse/IntelliJ)

---

## 📖 Documentação Completa

Para informações detalhadas, veja:
- `docs/SETUP.md` - Configuração completa
- `docs/documentacao-api.md` - Todos os endpoints
- `docs/RESUMO_RECURSOS.md` - Lista de recursos
- `docs/ENTREGA.md` - Guia de entrega

---

**BOA SORTE!** 🚀
