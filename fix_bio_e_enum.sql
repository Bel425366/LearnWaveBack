-- Adicionar coluna bio se não existir
IF NOT EXISTS (
    SELECT * FROM INFORMATION_SCHEMA.COLUMNS 
    WHERE TABLE_NAME = 'usuarios' AND COLUMN_NAME = 'bio'
)
BEGIN
    ALTER TABLE usuarios ADD bio NVARCHAR(1000) NULL;
END
GO

-- Padronizar status_verificacao para maiúsculo (JPA usa EnumType.STRING = nome do enum)
UPDATE usuarios SET status_verificacao = 'PENDENTE'  WHERE status_verificacao = 'pendente';
UPDATE usuarios SET status_verificacao = 'APROVADO'  WHERE status_verificacao = 'aprovado';
UPDATE usuarios SET status_verificacao = 'REJEITADO' WHERE status_verificacao = 'rejeitado';
GO
