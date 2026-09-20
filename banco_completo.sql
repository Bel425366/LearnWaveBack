USE LearnWave;
GO

CREATE TABLE usuarios (
    id                    INT             IDENTITY(1,1)  PRIMARY KEY,
    nome                  NVARCHAR(255)   NOT NULL,
    email                 NVARCHAR(255)   NOT NULL        UNIQUE,
    senha                 NVARCHAR(255)   NOT NULL,
    tipo_usuario          NVARCHAR(50)    NOT NULL,
    cpf                   NVARCHAR(14)    NULL,
    telefone              NVARCHAR(20)    NULL,
    escola                NVARCHAR(255)   NULL,
    documento_url         NVARCHAR(MAX)   NULL,
    status_verificacao    NVARCHAR(50)    NULL,
    area_ensino           NVARCHAR(255)   NULL,
    formacao              NVARCHAR(500)   NULL,
    experiencia           NVARCHAR(1000)  NULL,
    bio                   NVARCHAR(500)   NULL,
    fotoperfil            NVARCHAR(MAX)   NULL,
    cor_avatar            NVARCHAR(100)   NULL,
    emoji_avatar          NVARCHAR(20)    NULL,
    status                NVARCHAR(50)    NULL,
    reset_token           NVARCHAR(100)   NULL,
    reset_token_expiracao DATETIME2       NULL,
    data_criacao          DATETIME2       NULL,
    data_atualizacao      DATETIME2       NULL
);
GO

CREATE TABLE atividades (
    id               INT           IDENTITY(1,1)  PRIMARY KEY,
    titulo           NVARCHAR(255) NOT NULL,
    descricao        NTEXT         NULL,
    area             NVARCHAR(100) NOT NULL,
    professor_id     INT           NOT NULL,
    conteudo         NTEXT         NULL,
    status           NVARCHAR(50)  NULL,
    situacao         NVARCHAR(50)  NOT NULL DEFAULT 'ativo',
    data_criacao     DATETIME2     NULL,
    data_atualizacao DATETIME2     NULL,
    CONSTRAINT FK_atividades_professor FOREIGN KEY (professor_id) REFERENCES usuarios(id)
);
GO

CREATE TABLE videoaulas (
    id               INT           IDENTITY(1,1)  PRIMARY KEY,
    titulo           NVARCHAR(255) NOT NULL,
    descricao        NTEXT         NULL,
    area             NVARCHAR(100) NOT NULL,
    professor_id     INT           NOT NULL,
    url_video        NVARCHAR(500) NULL,
    duracao          NVARCHAR(20)  NULL,
    status           NVARCHAR(50)  NULL,
    thumbnail_url    NVARCHAR(500) NULL,
    data_criacao     DATETIME2     NULL,
    data_atualizacao DATETIME2     NULL,
    CONSTRAINT FK_videoaulas_professor FOREIGN KEY (professor_id) REFERENCES usuarios(id)
);
GO

CREATE TABLE materiais (
    id               INT           IDENTITY(1,1)  PRIMARY KEY,
    titulo           NVARCHAR(255) NOT NULL,
    descricao        NTEXT         NULL,
    area             NVARCHAR(100) NOT NULL,
    professor_id     INT           NOT NULL,
    arquivo_url      NVARCHAR(MAX) NULL,
    tipo_arquivo     NVARCHAR(10)  NULL,
    tamanho_arquivo  INT           NULL,
    status           NVARCHAR(50)  NULL,
    situacao         NVARCHAR(50)  NOT NULL DEFAULT 'ativo',
    data_criacao     DATETIME2     NULL,
    data_atualizacao DATETIME2     NULL,
    CONSTRAINT FK_materiais_professor FOREIGN KEY (professor_id) REFERENCES usuarios(id)
);
GO

CREATE TABLE progresso_atividades (
    id               INT           IDENTITY(1,1)  PRIMARY KEY,
    aluno_id         INT           NOT NULL,
    atividade_id     INT           NOT NULL,
    status           NVARCHAR(50)  NULL,
    nota             DECIMAL(4,2)  NULL,
    resposta_aluno   NVARCHAR(MAX) NULL,
    tentativas       INT           NULL DEFAULT 0,
    data_inicio      DATETIME2     NULL,
    data_conclusao   DATETIME2     NULL,
    data_atualizacao DATETIME2     NULL,
    CONSTRAINT FK_progresso_ativ_aluno     FOREIGN KEY (aluno_id)     REFERENCES usuarios(id),
    CONSTRAINT FK_progresso_ativ_atividade FOREIGN KEY (atividade_id) REFERENCES atividades(id)
);
GO

CREATE TABLE progresso_videoaulas (
    id               INT          IDENTITY(1,1)  PRIMARY KEY,
    aluno_id         INT          NOT NULL,
    videoaula_id     INT          NOT NULL,
    status           NVARCHAR(50) NULL,
    tempo_assistido  INT          NULL DEFAULT 0,
    data_inicio      DATETIME2    NULL,
    data_conclusao   DATETIME2    NULL,
    data_atualizacao DATETIME2    NULL,
    CONSTRAINT FK_progresso_vid_aluno     FOREIGN KEY (aluno_id)     REFERENCES usuarios(id),
    CONSTRAINT FK_progresso_vid_videoaula FOREIGN KEY (videoaula_id) REFERENCES videoaulas(id)
);
GO

CREATE TABLE downloads_materiais (
    id            INT       IDENTITY(1,1) PRIMARY KEY,
    aluno_id      INT       NOT NULL,
    material_id   INT       NOT NULL,
    data_download DATETIME2 NULL DEFAULT GETDATE(),
    CONSTRAINT FK_downloads_aluno    FOREIGN KEY (aluno_id)    REFERENCES usuarios(id),
    CONSTRAINT FK_downloads_material FOREIGN KEY (material_id) REFERENCES materiais(id)
);
GO

CREATE TABLE professores_favoritos (
    id              INT       IDENTITY(1,1) PRIMARY KEY,
    aluno_id        INT       NOT NULL,
    professor_id    INT       NOT NULL,
    data_favoritado DATETIME2 NULL DEFAULT GETDATE(),
    CONSTRAINT UQ_aluno_professor UNIQUE (aluno_id, professor_id)
);
GO

CREATE TABLE configuracoes (
    id               INT           IDENTITY(1,1) PRIMARY KEY,
    chave            NVARCHAR(100) NOT NULL UNIQUE,
    valor            NTEXT         NULL,
    descricao        NTEXT         NULL,
    data_atualizacao DATETIME2     NULL
);
GO

INSERT INTO usuarios (nome, email, senha, tipo_usuario, status_verificacao, status, data_criacao, data_atualizacao)
VALUES ('Isabelly Pereira', 'Pereiraisabelly585@gmail.com', 'ADM123', 'ADMINISTRADOR', 'APROVADO', 'ativo', GETDATE(), GETDATE());
GO
