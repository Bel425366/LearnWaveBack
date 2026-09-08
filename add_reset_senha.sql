USE LearnWave;
GO

ALTER TABLE usuarios
ADD reset_token NVARCHAR(100) NULL,
    reset_token_expiracao DATETIME2 NULL;
GO
