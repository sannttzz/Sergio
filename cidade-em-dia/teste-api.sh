#!/bin/bash
# Script com exemplos de testes da API Cidade em Dia
# Execute com: bash teste-api.sh

BASE_URL="http://localhost:8080/api/v1"

# Credenciais (base64 encoded)
# cidadao:senha123
CIDADAO_AUTH="Authorization: Basic Y2lkYWRhbzpzZW5oYTEyMw=="
# atendente:senha456
ATENDENTE_AUTH="Authorization: Basic YXRlbmRlbnRlOnNlbmhhNDU2"
# admin:admin123
ADMIN_AUTH="Authorization: Basic YWRtaW46YWRtaW4xMjM="

echo "=========================================="
echo "Testes da API Cidade em Dia"
echo "=========================================="
echo ""

# Teste 1: Listar Categorias (sem autenticação - deve falhar)
echo "1. Teste: Listar categorias SEM autenticação (deve retornar 401)"
curl -X GET "$BASE_URL/categorias" \
  -H "Content-Type: application/json"
echo -e "\n"

# Teste 2: Listar Categorias (com autenticação CIDADAO)
echo "2. Teste: Listar categorias como CIDADAO (deve retornar 200)"
curl -X GET "$BASE_URL/categorias" \
  -H "Content-Type: application/json" \
  -H "$CIDADAO_AUTH"
echo -e "\n"

# Teste 3: Criar Categoria (sem autorização - deve falhar com 403)
echo "3. Teste: Criar categoria como CIDADAO (deve retornar 403 - sem permissão)"
curl -X POST "$BASE_URL/categorias" \
  -H "Content-Type: application/json" \
  -H "$CIDADAO_AUTH" \
  -d '{
    "nome": "Teste Sem Permissão",
    "descricao": "Esta requisição deve falhar",
    "ativa": true
  }'
echo -e "\n"

# Teste 4: Criar Categoria (com ATENDENTE)
echo "4. Teste: Criar categoria como ATENDENTE (deve retornar 201)"
CATEGORIA_RESPONSE=$(curl -s -X POST "$BASE_URL/categorias" \
  -H "Content-Type: application/json" \
  -H "$ATENDENTE_AUTH" \
  -d '{
    "nome": "Infraestrutura",
    "descricao": "Problemas de infraestrutura urbana",
    "ativa": true
  }')
echo $CATEGORIA_RESPONSE
CATEGORIA_ID=$(echo $CATEGORIA_RESPONSE | grep -o '"id":[0-9]*' | head -1 | cut -d':' -f2)
echo "Categoria criada com ID: $CATEGORIA_ID"
echo -e "\n"

# Teste 5: Criar Usuário (apenas ADMIN)
echo "5. Teste: Criar usuário como ADMIN"
USUARIO_RESPONSE=$(curl -s -X POST "$BASE_URL/usuarios" \
  -H "Content-Type: application/json" \
  -H "$ADMIN_AUTH" \
  -d '{
    "nome": "João Silva",
    "email": "joao@example.com",
    "senha": "senha123",
    "perfil": "CIDADAO",
    "ativo": true
  }')
echo $USUARIO_RESPONSE
USUARIO_ID=$(echo $USUARIO_RESPONSE | grep -o '"id":[0-9]*' | head -1 | cut -d':' -f2)
echo "Usuário criado com ID: $USUARIO_ID"
echo -e "\n"

# Teste 6: Criar Ocorrência
echo "6. Teste: Criar ocorrência como CIDADAO"
OCORRENCIA_RESPONSE=$(curl -s -X POST "$BASE_URL/ocorrencias" \
  -H "Content-Type: application/json" \
  -H "$CIDADAO_AUTH" \
  -d "{
    \"titulo\": \"Buraco na rua\",
    \"descricao\": \"Há um buraco grande na Rua Principal que oferece perigo aos pedestres\",
    \"rua\": \"Rua Principal\",
    \"numero\": \"123\",
    \"bairro\": \"Centro\",
    \"cidade\": \"São Paulo\",
    \"referencia\": \"Próximo ao semáforo\",
    \"urgencia\": \"ALTA\",
    \"prioridade\": \"ALTA\",
    \"categoriaId\": 1,
    \"autorId\": 1
  }")
echo $OCORRENCIA_RESPONSE
OCORRENCIA_ID=$(echo $OCORRENCIA_RESPONSE | grep -o '"id":[0-9]*' | head -1 | cut -d':' -f2)
echo "Ocorrência criada com ID: $OCORRENCIA_ID"
echo -e "\n"

# Teste 7: Validação - Criar com dados inválidos
echo "7. Teste: Criar ocorrência com dados inválidos (deve retornar 400)"
curl -s -X POST "$BASE_URL/ocorrencias" \
  -H "Content-Type: application/json" \
  -H "$CIDADAO_AUTH" \
  -d '{
    "titulo": "ABC",
    "descricao": "Desc",
    "rua": "",
    "numero": "",
    "bairro": "",
    "cidade": "",
    "urgencia": "INVÁLIDO",
    "prioridade": "INVÁLIDO",
    "categoriaId": 1,
    "autorId": 1
  }' | python -m json.tool
echo -e "\n"

# Teste 8: Buscar ocorrência por ID
echo "8. Teste: Buscar ocorrência por ID"
curl -s -X GET "$BASE_URL/ocorrencias/1" \
  -H "Content-Type: application/json" \
  -H "$CIDADAO_AUTH" | python -m json.tool
echo -e "\n"

# Teste 9: Buscar ocorrências por status
echo "9. Teste: Buscar ocorrências com status NOVA"
curl -s -X GET "$BASE_URL/ocorrencias/status/NOVA" \
  -H "Content-Type: application/json" \
  -H "$CIDADAO_AUTH" | python -m json.tool
echo -e "\n"

# Teste 10: Alterar status de ocorrência (ATENDENTE)
echo "10. Teste: Alterar status de ocorrência como ATENDENTE"
curl -s -X POST "$BASE_URL/ocorrencias/1/status" \
  -H "Content-Type: application/json" \
  -H "$ATENDENTE_AUTH" \
  -d '{
    "statusNovo": "EM_ANALISE",
    "motivo": "Recebido para análise"
  }' | python -m json.tool
echo -e "\n"

# Teste 11: Confirmar ocorrência
echo "11. Teste: Confirmar ocorrência"
curl -s -X POST "$BASE_URL/confirmacoes" \
  -H "Content-Type: application/json" \
  -H "$CIDADAO_AUTH" \
  -d '{
    "ocorrenciaId": 1,
    "usuarioId": 1,
    "comentario": "Confirmo que o problema existe"
  }' | python -m json.tool
echo -e "\n"

# Teste 12: Tentar excluir categoria com ocorrências (deve retornar 409)
echo "12. Teste: Tentar excluir categoria com ocorrências (deve retornar 409)"
curl -s -X DELETE "$BASE_URL/categorias/1" \
  -H "Content-Type: application/json" \
  -H "$ADMIN_AUTH" | python -m json.tool
echo -e "\n"

# Teste 13: Buscar recurso inexistente (deve retornar 404)
echo "13. Teste: Buscar categoria inexistente (deve retornar 404)"
curl -s -X GET "$BASE_URL/categorias/9999" \
  -H "Content-Type: application/json" \
  -H "$CIDADAO_AUTH" | python -m json.tool
echo -e "\n"

# Teste 14: Atualizar categoria
echo "14. Teste: Atualizar categoria"
curl -s -X PUT "$BASE_URL/categorias/1" \
  -H "Content-Type: application/json" \
  -H "$ATENDENTE_AUTH" \
  -d '{
    "nome": "Infraestrutura Urbana",
    "descricao": "Problemas de infraestrutura (atualizado)",
    "ativa": true
  }' | python -m json.tool
echo -e "\n"

# Teste 15: Listar usuários (apenas ADMIN)
echo "15. Teste: Listar usuários como ADMIN"
curl -s -X GET "$BASE_URL/usuarios" \
  -H "Content-Type: application/json" \
  -H "$ADMIN_AUTH" | python -m json.tool
echo -e "\n"

echo "=========================================="
echo "Testes finalizados!"
echo "=========================================="
