-- Script para popular banco de dados com dados de teste
-- Execute após criar as tabelas (Hibernate irá criar automaticamente)

USE cidade_em_dia;

-- Inserir Categorias
INSERT INTO categorias (nome, descricao, ativa, data_criacao, data_atualizacao) 
VALUES 
  ('Infraestrutura', 'Problemas de infraestrutura urbana (buracos, rachaduras, etc)', 1, NOW(), NOW()),
  ('Sinalização', 'Problemas de sinalização e semáforos', 1, NOW(), NOW()),
  ('Iluminação', 'Problemas de iluminação pública', 1, NOW(), NOW()),
  ('Limpeza', 'Problemas de limpeza de vias e espaços públicos', 1, NOW(), NOW()),
  ('Segurança', 'Problemas relacionados à segurança pública', 1, NOW(), NOW());

-- Inserir Usuários
-- Senhas já estão criptografadas no SecurityConfig em memória
-- Para uso em produção, seria necessário criar um serviço de usuários

INSERT INTO usuarios (nome, email, senha, perfil, ativo, data_criacao, data_atualizacao)
VALUES
  ('João Silva', 'joao.silva@email.com', 'senha123_criptografada', 'CIDADAO', 1, NOW(), NOW()),
  ('Maria Santos', 'maria.santos@email.com', 'senha456_criptografada', 'ATENDENTE', 1, NOW(), NOW()),
  ('Carlos Admin', 'carlos.admin@email.com', 'admin123_criptografada', 'ADMIN', 1, NOW(), NOW()),
  ('Pedro Oliveira', 'pedro.oliveira@email.com', 'senha123_criptografada', 'CIDADAO', 1, NOW(), NOW()),
  ('Ana Costa', 'ana.costa@email.com', 'senha456_criptografada', 'ATENDENTE', 1, NOW(), NOW());

-- Inserir Ocorrências de Exemplo
INSERT INTO ocorrencias 
(titulo, descricao, rua, numero, bairro, cidade, referencia, foto, urgencia, prioridade, status, categoria_id, autor_id, data_criacao, data_atualizacao)
VALUES
  (
    'Buraco na Rua Principal',
    'Há um buraco grande na Rua Principal que oferece perigo aos pedestres e veículos. O buraco tem aproximadamente 2 metros de diâmetro.',
    'Rua Principal',
    '123',
    'Centro',
    'São Paulo',
    'Próximo ao semáforo, em frente à farmácia',
    'https://example.com/foto1.jpg',
    'ALTA',
    'ALTA',
    'NOVA',
    1,
    1,
    DATE_SUB(NOW(), INTERVAL 5 DAY),
    DATE_SUB(NOW(), INTERVAL 5 DAY)
  ),
  (
    'Semáforo Quebrado',
    'O semáforo na interseção da Rua A com Avenida B está com funcionamento irregular. A luz vermelha não está funcionando corretamente.',
    'Avenida B',
    '456',
    'Vila Mariana',
    'São Paulo',
    'Próximo à estação de metrô',
    'https://example.com/foto2.jpg',
    'ALTA',
    'ALTA',
    'EM_ANALISE',
    2,
    1,
    DATE_SUB(NOW(), INTERVAL 3 DAY),
    DATE_SUB(NOW(), INTERVAL 1 DAY)
  ),
  (
    'Iluminação Pública Desligada',
    'Vários postes de iluminação na Rua Secundária estão desligados, deixando o local completamente escuro à noite.',
    'Rua Secundária',
    '789',
    'Zona Leste',
    'São Paulo',
    'Entre o número 700 e 850',
    'https://example.com/foto3.jpg',
    'MEDIA',
    'MEDIA',
    'EM_ANDAMENTO',
    3,
    4,
    DATE_SUB(NOW(), INTERVAL 7 DAY),
    DATE_SUB(NOW(), INTERVAL 2 DAY)
  ),
  (
    'Lixo Acumulado',
    'Há acumulação de lixo na praça pública. Os contêineres estão cheios e há sujeira espalhada pela área.',
    'Praça da Liberdade',
    's/n',
    'Centro',
    'São Paulo',
    'Lado sul da praça',
    'https://example.com/foto4.jpg',
    'MEDIA',
    'MEDIA',
    'NOVA',
    4,
    4,
    NOW(),
    NOW()
  ),
  (
    'Árvore Caída na Via',
    'Uma árvore grande caiu na via bloqueando o trânsito. Oferece risco à segurança dos motoristas e pedestres.',
    'Avenida Paulista',
    '1000',
    'Bela Vista',
    'São Paulo',
    'Próximo ao número 1020',
    'https://example.com/foto5.jpg',
    'ALTA',
    'ALTA',
    'EM_ANDAMENTO',
    5,
    1,
    DATE_SUB(NOW(), INTERVAL 1 DAY),
    NOW()
  );

-- Inserir Confirmações de Exemplo
INSERT INTO confirmacoes (ocorrencia_id, usuario_id, comentario, data_criacao)
VALUES
  (1, 4, 'Confirmo que o buraco continua presente e oferece risco', DATE_SUB(NOW(), INTERVAL 4 DAY)),
  (1, 1, 'Também vi o buraco hoje, é bem perigoso mesmo', DATE_SUB(NOW(), INTERVAL 3 DAY)),
  (2, 1, 'O semáforo realmente não está funcionando', DATE_SUB(NOW(), INTERVAL 2 DAY)),
  (3, 1, 'Já vi equipe trabalhando no local', DATE_SUB(NOW(), INTERVAL 1 DAY)),
  (5, 4, 'A árvore está bloqueando toda a via', NOW());

-- Inserir Histórico de Status de Exemplo
INSERT INTO historicos_status 
(ocorrencia_id, usuario_id, status_anterior, status_novo, motivo, perfil_que_alterou, data)
VALUES
  (2, 2, 'NOVA', 'EM_ANALISE', 'Recebido para análise pela prefeitura', 'ATENDENTE', DATE_SUB(NOW(), INTERVAL 2 DAY)),
  (3, 2, 'NOVA', 'EM_ANDAMENTO', 'Equipe iniciou os reparos de iluminação', 'ATENDENTE', DATE_SUB(NOW(), INTERVAL 5 DAY)),
  (5, 2, 'NOVA', 'EM_ANDAMENTO', 'Equipe de poda removendo a árvore', 'ATENDENTE', DATE_SUB(NOW(), INTERVAL 1 DAY));

-- Verificar dados inseridos
SELECT 'Categorias' AS tabela, COUNT(*) AS quantidade FROM categorias
UNION ALL
SELECT 'Usuários', COUNT(*) FROM usuarios
UNION ALL
SELECT 'Ocorrências', COUNT(*) FROM ocorrencias
UNION ALL
SELECT 'Confirmações', COUNT(*) FROM confirmacoes
UNION ALL
SELECT 'Históricos', COUNT(*) FROM historicos_status;
