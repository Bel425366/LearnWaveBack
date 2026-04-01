-- Remover constraint que está impedindo a alteração da coluna
ALTER TABLE usuarios DROP CONSTRAINT CK__usuarios__tipo_u__37A5467C;

-- Alterar a coluna para aceitar valores maiores
ALTER TABLE usuarios ALTER COLUMN tipo_usuario VARCHAR(255);

-- Recriar constraint se necessário (opcional)
-- ALTER TABLE usuarios ADD CONSTRAINT CK_usuarios_tipo_usuario CHECK (tipo_usuario IN ('ALUNO', 'PROFESSOR', 'ADMINISTRADOR', 'ADMIN', 'ESTUDANTE'));