CREATE DATABASE LearnWave;
GO

USE LearnWave;
GO

CREATE TABLE usuarios (
    id INT IDENTITY(1,1) PRIMARY KEY,
    nome NVARCHAR(255) NOT NULL,
    email NVARCHAR(255) UNIQUE NOT NULL,
    senha NVARCHAR(255) NOT NULL,
    tipo_usuario NVARCHAR(50) NOT NULL,
    cpf NVARCHAR(14),
    telefone NVARCHAR(20),
    disciplina NVARCHAR(255),
    escola NVARCHAR(255),
    documento_url NVARCHAR(500),
    status_verificacao NVARCHAR(50),
    area_ensino NVARCHAR(255),
    formacao NVARCHAR(500),
    experiencia NVARCHAR(1000),
    status NVARCHAR(50),
    data_criacao DATETIME2,
    data_atualizacao DATETIME2
);

CREATE TABLE atividades (
    id INT IDENTITY(1,1) PRIMARY KEY,
    titulo NVARCHAR(255) NOT NULL,
    descricao NTEXT,
    area NVARCHAR(100) NOT NULL,
    professor_id INT NOT NULL,
    conteudo NTEXT,
    status NVARCHAR(50),
    data_criacao DATETIME2,
    data_atualizacao DATETIME2,
    FOREIGN KEY (professor_id) REFERENCES usuarios(id)
);

CREATE TABLE videoaulas (
    id INT IDENTITY(1,1) PRIMARY KEY,
    titulo NVARCHAR(255) NOT NULL,
    descricao NTEXT,
    area NVARCHAR(100) NOT NULL,
    professor_id INT NOT NULL,
    url_video NVARCHAR(500),
    duracao NVARCHAR(20),
    status NVARCHAR(50),
    data_criacao DATETIME2,
    data_atualizacao DATETIME2,
    FOREIGN KEY (professor_id) REFERENCES usuarios(id)
);

CREATE TABLE materiais (
    id INT IDENTITY(1,1) PRIMARY KEY,
    titulo NVARCHAR(255) NOT NULL,
    descricao NTEXT,
    area NVARCHAR(100) NOT NULL,
    professor_id INT NOT NULL,
    arquivo_url NVARCHAR(500),
    tipo_arquivo NVARCHAR(10),
    tamanho_arquivo INT,
    status NVARCHAR(50),
    data_criacao DATETIME2,
    data_atualizacao DATETIME2,
    FOREIGN KEY (professor_id) REFERENCES usuarios(id)
);

CREATE TABLE progresso_atividades (
    id INT IDENTITY(1,1) PRIMARY KEY,
    aluno_id INT NOT NULL,
    atividade_id INT NOT NULL,
    status NVARCHAR(50),
    nota DECIMAL(4,2),
    tentativas INT,
    data_inicio DATETIME2,
    data_conclusao DATETIME2,
    data_atualizacao DATETIME2,
    FOREIGN KEY (aluno_id) REFERENCES usuarios(id),
    FOREIGN KEY (atividade_id) REFERENCES atividades(id)
);

CREATE TABLE progresso_videoaulas (
    id INT IDENTITY(1,1) PRIMARY KEY,
    aluno_id INT NOT NULL,
    videoaula_id INT NOT NULL,
    status NVARCHAR(50),
    tempo_assistido INT,
    data_inicio DATETIME2,
    data_conclusao DATETIME2,
    data_atualizacao DATETIME2,
    FOREIGN KEY (aluno_id) REFERENCES usuarios(id),
    FOREIGN KEY (videoaula_id) REFERENCES videoaulas(id)
);

CREATE TABLE downloads_materiais (
    id INT IDENTITY(1,1) PRIMARY KEY,
    aluno_id INT NOT NULL,
    material_id INT NOT NULL,
    data_download DATETIME2,
    FOREIGN KEY (aluno_id) REFERENCES usuarios(id),
    FOREIGN KEY (material_id) REFERENCES materiais(id)
);

CREATE TABLE configuracoes (
    id INT IDENTITY(1,1) PRIMARY KEY,
    chave NVARCHAR(100) UNIQUE NOT NULL,
    valor NTEXT,
    descricao NTEXT,
    data_atualizacao DATETIME2
);

INSERT INTO usuarios (nome, email, senha, tipo_usuario, status) VALUES 
('Admin', 'admin@learnwave.com', '123456', 'ADMIN', 'ativo');

INSERT INTO usuarios (nome, email, senha, tipo_usuario, status) VALUES 
('João Professor', 'joao@learnwave.com', '123456', 'PROFESSOR', 'ativo');

INSERT INTO usuarios (nome, email, senha, tipo_usuario, status) VALUES 
('Maria Aluna', 'maria@learnwave.com', '123456', 'ESTUDANTE', 'ativo');