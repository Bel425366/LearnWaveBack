-- Correções para compatibilidade com o código Java

-- 1. Adicionar campos faltantes na tabela usuarios
ALTER TABLE usuarios ADD cpf NVARCHAR(14);
ALTER TABLE usuarios ADD telefone NVARCHAR(20);
ALTER TABLE usuarios ADD disciplina NVARCHAR(255);
ALTER TABLE usuarios ADD escola NVARCHAR(255);
ALTER TABLE usuarios ADD documento_url NVARCHAR(500);
ALTER TABLE usuarios ADD status_verificacao NVARCHAR(50) DEFAULT 'PENDENTE';

-- 2. Atualizar CHECK constraints para usar valores do enum Java
ALTER TABLE usuarios DROP CONSTRAINT CK__usuarios__tipo_u__[constraint_name];
ALTER TABLE usuarios ADD CONSTRAINT CK_usuarios_tipo_usuario 
    CHECK (tipo_usuario IN ('ESTUDANTE', 'PROFESSOR', 'ADMIN'));

ALTER TABLE usuarios DROP CONSTRAINT CK__usuarios__status__[constraint_name];
ALTER TABLE usuarios ADD CONSTRAINT CK_usuarios_status 
    CHECK (status IN ('ativo', 'inativo'));

-- 3. Atualizar CHECK constraints para atividades
ALTER TABLE atividades DROP CONSTRAINT CK__atividade__statu__[constraint_name];
ALTER TABLE atividades ADD CONSTRAINT CK_atividades_status 
    CHECK (status IN ('RASCUNHO', 'PUBLICADO', 'ARQUIVADO'));

-- 4. Atualizar CHECK constraints para videoaulas
ALTER TABLE videoaulas DROP CONSTRAINT CK__videoaula__statu__[constraint_name];
ALTER TABLE videoaulas ADD CONSTRAINT CK_videoaulas_status 
    CHECK (status IN ('RASCUNHO', 'PUBLICADO', 'ARQUIVADO'));

-- 5. Atualizar CHECK constraints para materiais
ALTER TABLE materiais DROP CONSTRAINT CK__materiais__statu__[constraint_name];
ALTER TABLE materiais ADD CONSTRAINT CK_materiais_status 
    CHECK (status IN ('RASCUNHO', 'PUBLICADO', 'ARQUIVADO'));

-- 6. Atualizar dados existentes para usar os novos valores
UPDATE usuarios SET tipo_usuario = 'ADMIN' WHERE tipo_usuario = 'admin';
UPDATE usuarios SET tipo_usuario = 'PROFESSOR' WHERE tipo_usuario = 'professor';
UPDATE usuarios SET tipo_usuario = 'ESTUDANTE' WHERE tipo_usuario = 'aluno';

UPDATE atividades SET status = 'PUBLICADO' WHERE status = 'publicada';
UPDATE atividades SET status = 'RASCUNHO' WHERE status = 'rascunho';
UPDATE atividades SET status = 'ARQUIVADO' WHERE status = 'arquivada';

UPDATE videoaulas SET status = 'PUBLICADO' WHERE status = 'publicada';
UPDATE videoaulas SET status = 'RASCUNHO' WHERE status = 'rascunho';
UPDATE videoaulas SET status = 'ARQUIVADO' WHERE status = 'arquivada';

UPDATE materiais SET status = 'PUBLICADO' WHERE status = 'publicado';
UPDATE materiais SET status = 'RASCUNHO' WHERE status = 'rascunho';
UPDATE materiais SET status = 'ARQUIVADO' WHERE status = 'arquivado';