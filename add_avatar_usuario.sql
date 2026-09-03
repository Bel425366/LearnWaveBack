USE LearnWave;
GO

ALTER TABLE usuarios
ADD cor_avatar NVARCHAR(100) NULL,
    emoji_avatar NVARCHAR(20) NULL;
GO
