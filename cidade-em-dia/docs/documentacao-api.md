# Documentação da API - Cidade em Dia

## Introducción

Esta documentação descreve todos os endpoints REST da API Cidade em Dia, incluindo exemplos de requisições e respostas.

**Base URL:** `http://localhost:8080/api/v1`

## Autenticação

Todos os endpoints requerem autenticação HTTP Basic com as seguintes credenciais:

```
Username: cidadao | Senha: senha123   (Perfil: CIDADÃO)
Username: atendente | Senha: senha456 (Perfil: ATENDENTE)
Username: admin | Senha: admin123     (Perfil: ADMIN)
```

## Códigos de Status HTTP

| Código | Descrição |
|--------|-----------|
| 200 | OK - Requisição bem-sucedida |
| 201 | Created - Recurso criado com sucesso |
| 204 | No Content - Recurso excluído com sucesso |
| 400 | Bad Request - Dados inválidos |
| 401 | Unauthorized - Autenticação necessária |
| 403 | Forbidden - Acesso não permitido |
| 404 | Not Found - Recurso não encontrado |
| 409 | Conflict - Violação de regra de negócio |
| 500 | Internal Server Error - Erro do servidor |

---

## Endpoints

### 1. USUÁRIOS

#### 1.1 Criar Usuário
**POST** `/usuarios`

**Permissão:** ADMIN

**Request Body:**
```json
{
  "nome": "João Silva",
  "email": "joao@example.com",
  "senha": "senha123",
  "perfil": "CIDADAO",
  "ativo": true
}
```

**Response (201 Created):**
```json
{
  "id": 1,
  "nome": "João Silva",
  "email": "joao@example.com",
  "perfil": "CIDADAO",
  "ativo": true,
  "dataCriacao": "2024-11-11T10:30:00",
  "dataAtualizacao": "2024-11-11T10:30:00"
}
```

#### 1.2 Listar Todos os Usuários
**GET** `/usuarios`

**Permissão:** ADMIN

**Response (200 OK):**
```json
[
  {
    "id": 1,
    "nome": "João Silva",
    "email": "joao@example.com",
    "perfil": "CIDADAO",
    "ativo": true,
    "dataCriacao": "2024-11-11T10:30:00",
    "dataAtualizacao": "2024-11-11T10:30:00"
  }
]
```

#### 1.3 Obter Usuário por ID
**GET** `/usuarios/{id}`

**Permissão:** ADMIN

**Response (200 OK):**
```json
{
  "id": 1,
  "nome": "João Silva",
  "email": "joao@example.com",
  "perfil": "CIDADAO",
  "ativo": true,
  "dataCriacao": "2024-11-11T10:30:00",
  "dataAtualizacao": "2024-11-11T10:30:00"
}
```

#### 1.4 Atualizar Usuário
**PUT** `/usuarios/{id}`

**Permissão:** ADMIN

**Request Body:**
```json
{
  "nome": "João Silva",
  "email": "joao.silva@example.com",
  "senha": "novaSenha123",
  "perfil": "CIDADAO",
  "ativo": true
}
```

#### 1.5 Excluir Usuário
**DELETE** `/usuarios/{id}`

**Permissão:** ADMIN

---

### 2. CATEGORIAS

#### 2.1 Criar Categoria
**POST** `/categorias`

**Permissão:** ADMIN, ATENDENTE

**Request Body:**
```json
{
  "nome": "Infraestrutura",
  "descricao": "Problemas de infraestrutura urbana",
  "ativa": true
}
```

**Response (201 Created):**
```json
{
  "id": 1,
  "nome": "Infraestrutura",
  "descricao": "Problemas de infraestrutura urbana",
  "ativa": true,
  "dataCriacao": "2024-11-11T10:30:00",
  "dataAtualizacao": "2024-11-11T10:30:00"
}
```

#### 2.2 Listar Categorias
**GET** `/categorias`

**Permissão:** CIDADAO, ATENDENTE, ADMIN

#### 2.3 Obter Categoria por ID
**GET** `/categorias/{id}`

**Permissão:** CIDADAO, ATENDENTE, ADMIN

#### 2.4 Listar Categorias Ativas
**GET** `/categorias/ativas`

**Permissão:** CIDADAO, ATENDENTE, ADMIN

#### 2.5 Atualizar Categoria
**PUT** `/categorias/{id}`

**Permissão:** ADMIN, ATENDENTE

#### 2.6 Excluir Categoria
**DELETE** `/categorias/{id}`

**Permissão:** ADMIN

**Response (409 Conflict) - Se houver ocorrências associadas:**
```json
{
  "timestamp": "2024-11-11T10:30:00",
  "status": 409,
  "error": "Conflict",
  "mensagem": "Não é possível excluir a categoria 'Infraestrutura' pois ela possui 5 ocorrência(s) associada(s)"
}
```

---

### 3. OCORRÊNCIAS

#### 3.1 Criar Ocorrência
**POST** `/ocorrencias`

**Permissão:** CIDADAO, ATENDENTE, ADMIN

**Request Body:**
```json
{
  "titulo": "Buraco na rua",
  "descricao": "Há um buraco grande na Rua Principal que oferece perigo aos pedestres e veículos",
  "rua": "Rua Principal",
  "numero": "123",
  "bairro": "Centro",
  "cidade": "São Paulo",
  "referencia": "Próximo ao semáforo",
  "foto": "url_foto",
  "urgencia": "ALTA",
  "prioridade": "ALTA",
  "categoriaId": 1,
  "autorId": 1
}
```

**Response (201 Created):**
```json
{
  "id": 1,
  "titulo": "Buraco na rua",
  "descricao": "Há um buraco grande na Rua Principal que oferece perigo aos pedestres e veículos",
  "rua": "Rua Principal",
  "numero": "123",
  "bairro": "Centro",
  "cidade": "São Paulo",
  "referencia": "Próximo ao semáforo",
  "foto": "url_foto",
  "urgencia": "ALTA",
  "prioridade": "ALTA",
  "status": "NOVA",
  "categoriaId": 1,
  "autorId": 1,
  "totalConfirmacoes": 0,
  "dataCriacao": "2024-11-11T10:30:00",
  "dataAtualizacao": "2024-11-11T10:30:00"
}
```

#### 3.2 Listar Ocorrências
**GET** `/ocorrencias`

**Permissão:** CIDADAO, ATENDENTE, ADMIN

#### 3.3 Obter Ocorrência por ID
**GET** `/ocorrencias/{id}`

**Permissão:** CIDADAO, ATENDENTE, ADMIN

#### 3.4 Listar Ocorrências por Categoria
**GET** `/ocorrencias/categoria/{categoriaId}`

**Permissão:** CIDADAO, ATENDENTE, ADMIN

#### 3.5 Listar Ocorrências por Status
**GET** `/ocorrencias/status/{status}`

**Permissão:** CIDADAO, ATENDENTE, ADMIN

**Valores válidos:** NOVA, EM_ANALISE, EM_ANDAMENTO, RESOLVIDA

#### 3.6 Listar Ocorrências por Autor
**GET** `/ocorrencias/autor/{autorId}`

**Permissão:** CIDADAO, ATENDENTE, ADMIN

#### 3.7 Listar Ocorrências por Urgência
**GET** `/ocorrencias/urgencia/{urgencia}`

**Permissão:** CIDADAO, ATENDENTE, ADMIN

**Valores válidos:** BAIXA, MEDIA, ALTA

#### 3.8 Atualizar Ocorrência
**PUT** `/ocorrencias/{id}`

**Permissão:** ADMIN, ATENDENTE

#### 3.9 Alterar Status da Ocorrência (Funcionalidade Específica)
**POST** `/ocorrencias/{id}/status`

**Permissão:** ADMIN, ATENDENTE

**Request Body:**
```json
{
  "statusNovo": "EM_ANDAMENTO",
  "motivo": "Equipe iniciou os reparos"
}
```

**Response (200 OK):**
```json
{
  "id": 1,
  "status": "EM_ANDAMENTO",
  "dataCriacao": "2024-11-11T10:30:00",
  "dataAtualizacao": "2024-11-11T11:00:00"
}
```

**Histórico de Status Criado Automaticamente:**
```json
{
  "id": 1,
  "ocorrenciaId": 1,
  "usuarioId": 2,
  "statusAnterior": "NOVA",
  "statusNovo": "EM_ANDAMENTO",
  "motivo": "Equipe iniciou os reparos",
  "perfilQueAlterou": "ATENDENTE",
  "data": "2024-11-11T11:00:00"
}
```

#### 3.10 Excluir Ocorrência
**DELETE** `/ocorrencias/{id}`

**Permissão:** ADMIN

---

### 4. CONFIRMAÇÕES

#### 4.1 Criar Confirmação
**POST** `/confirmacoes`

**Permissão:** CIDADAO, ATENDENTE, ADMIN

**Request Body:**
```json
{
  "ocorrenciaId": 1,
  "usuarioId": 1,
  "comentario": "Confirmo que o buraco ainda existe e oferece perigo"
}
```

**Response (201 Created):**
```json
{
  "id": 1,
  "ocorrenciaId": 1,
  "usuarioId": 1,
  "comentario": "Confirmo que o buraco ainda existe e oferece perigo",
  "dataCriacao": "2024-11-11T10:30:00"
}
```

#### 4.2 Listar Confirmações
**GET** `/confirmacoes`

**Permissão:** CIDADAO, ATENDENTE, ADMIN

#### 4.3 Obter Confirmação por ID
**GET** `/confirmacoes/{id}`

**Permissão:** CIDADAO, ATENDENTE, ADMIN

#### 4.4 Listar Confirmações por Ocorrência
**GET** `/confirmacoes/ocorrencia/{ocorrenciaId}`

**Permissão:** CIDADAO, ATENDENTE, ADMIN

#### 4.5 Listar Confirmações por Usuário
**GET** `/confirmacoes/usuario/{usuarioId}`

**Permissão:** CIDADAO, ATENDENTE, ADMIN

#### 4.6 Excluir Confirmação
**DELETE** `/confirmacoes/{id}`

**Permissão:** ADMIN

---

## Exemplos de Erros

### 400 Bad Request - Validação
```json
{
  "timestamp": "2024-11-11T10:30:00",
  "status": 400,
  "error": "Bad Request",
  "erros": {
    "titulo": "Título deve ter entre 5 e 100 caracteres",
    "urgencia": "Urgência é obrigatória"
  }
}
```

### 401 Unauthorized
```json
{
  "timestamp": "2024-11-11T10:30:00",
  "status": 401,
  "error": "Unauthorized",
  "mensagem": "Credenciais inválidas ou ausentes"
}
```

### 403 Forbidden
```json
{
  "timestamp": "2024-11-11T10:30:00",
  "status": 403,
  "error": "Forbidden",
  "mensagem": "Você não possui permissão para executar esta operação"
}
```

### 404 Not Found
```json
{
  "timestamp": "2024-11-11T10:30:00",
  "status": 404,
  "error": "Not Found",
  "mensagem": "Ocorrência com ID 999 não encontrada"
}
```

### 409 Conflict
```json
{
  "timestamp": "2024-11-11T10:30:00",
  "status": 409,
  "error": "Conflict",
  "mensagem": "Não é possível registrar ocorrência na categoria 'Inativa' pois ela está inativa"
}
```

---

## Flow de Uso Típico

1. **Autenticar** como CIDADAO
2. **Criar Categoria** (necessário ADMIN/ATENDENTE)
3. **Registrar Ocorrência** referenciando a categoria e usuário
4. **Confirmar Ocorrência** para aumentar visibilidade
5. **Autenticar** como ATENDENTE
6. **Alterar Status** da ocorrência conforme trabalha
7. **Consultar Histórico** de alterações

---

## Notas Importantes

- Todas as datas são retornadas em formato ISO 8601
- As validações são realizadas automaticamente pelo Spring Validation
- A paginação não está implementada nesta versão
- As imagens são armazenadas como URLs de texto
- O banco de dados é criado automaticamente na primeira execução
