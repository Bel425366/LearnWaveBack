-- Script para adicionar/ajustar campos de perfil (bio e fotoPerfil) na tabela usuarios
-- Executar no SQL Server (Somee)

-- Adicionar coluna bio se não existir
IF NOT EXISTS (SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'usuarios' AND COLUMN_NAME = 'bio')
BEGIN
    ALTER TABLE usuarios ADD bio NVARCHAR(500) NULL;
    PRINT 'Coluna bio adicionada com sucesso';
END
ELSE
BEGIN
    -- Garantir que o tamanho é suficiente
    ALTER TABLE usuarios ALTER COLUMN bio NVARCHAR(500) NULL;
    PRINT 'Coluna bio já existe, tamanho ajustado para 500';
END

-- Adicionar coluna fotoperfil se não existir (TEXT para base64)
IF NOT EXISTS (SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'usuarios' AND COLUMN_NAME = 'fotoperfil')
BEGIN
    ALTER TABLE usuarios ADD fotoperfil NVARCHAR(MAX) NULL;
    PRINT 'Coluna fotoperfil adicionada com sucesso';
END
ELSE
BEGIN
    -- Garantir que o tipo suporta base64 grande
    ALTER TABLE usuarios ALTER COLUMN fotoperfil NVARCHAR(MAX) NULL;
    PRINT 'Coluna fotoperfil já existe, tipo ajustado para NVARCHAR(MAX)';
END
