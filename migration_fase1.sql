-- ============================================================
-- MIGRAÇÃO FASE 1 - LearnWave
-- Executar no SQL Server (banco LearnWave no Somee)
-- ============================================================

USE LearnWave;
GO

-- 1. Adicionar coluna 'thumbnail_url' na tabela videoaulas
IF NOT EXISTS (
    SELECT * FROM INFORMATION_SCHEMA.COLUMNS 
    WHERE TABLE_NAME = 'videoaulas' AND COLUMN_NAME = 'thumbnail_url'
)
BEGIN
    ALTER TABLE videoaulas ADD thumbnail_url NVARCHAR(500) NULL;
END
GO

-- 2. Garantir que videoaulas sem status recebam RASCUNHO
UPDATE videoaulas SET status = 'RASCUNHO' WHERE status IS NULL;
GO

-- 3. Garantir que atividades sem status recebam RASCUNHO
UPDATE atividades SET status = 'RASCUNHO' WHERE status IS NULL;
GO

-- 4. Garantir que materiais sem status recebam RASCUNHO
UPDATE materiais SET status = 'RASCUNHO' WHERE status IS NULL;
GO

-- 5. Migrar atividades que estavam com situacao='lixeira' para status='LIXEIRA'
-- (apenas se ainda não foram migradas)
UPDATE atividades SET status = 'LIXEIRA' WHERE situacao = 'lixeira' AND status != 'LIXEIRA';
GO

-- 6. Migrar materiais que estavam com situacao='lixeira' para status='LIXEIRA'
UPDATE materiais SET status = 'LIXEIRA' WHERE situacao = 'lixeira' AND status != 'LIXEIRA';
GO
