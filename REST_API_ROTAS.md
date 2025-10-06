# REST API - Rotas de Consumo

## Usuários (/api/usuarios)

### CRUD Básico
- `POST /api/usuarios` - Cadastrar usuário
- `GET /api/usuarios` - Listar todos os usuários
- `GET /api/usuarios/{id}` - Buscar usuário por ID
- `PUT /api/usuarios/{id}` - Atualizar usuário
- `DELETE /api/usuarios/{id}` - Excluir usuário

### Consultas Específicas
- `GET /api/usuarios/email/{email}` - Buscar por email
- `GET /api/usuarios/tipo/{tipo}` - Buscar por tipo (ALUNO, PROFESSOR, ADMINISTRADOR)
- `GET /api/usuarios/status/{status}` - Buscar por status (ativo, inativo)

### Autenticação
- `POST /api/usuarios/login?email={email}&senha={senha}` - Login

### Operações de Status
- `PATCH /api/usuarios/{id}/status?status={status}` - Alterar status (ativo/inativo)

### Professores
- `GET /api/usuarios/professores/pendentes` - Listar professores pendentes
- `PATCH /api/usuarios/{id}/aprovar` - Aprovar professor
- `PATCH /api/usuarios/{id}/rejeitar` - Rejeitar professor

## Atividades (/api/atividades)

### CRUD Básico
- `POST /api/atividades` - Criar atividade
- `GET /api/atividades` - Listar todas as atividades
- `GET /api/atividades/{id}` - Buscar atividade por ID
- `PUT /api/atividades/{id}` - Atualizar atividade
- `DELETE /api/atividades/{id}` - Excluir atividade

### Consultas Específicas
- `GET /api/atividades/professor/{professorId}` - Listar por professor
- `GET /api/atividades/area/{area}` - Listar por área

### Operações de Status
- `PATCH /api/atividades/{id}/publicar` - Publicar atividade
- `PATCH /api/atividades/{id}/arquivar` - Arquivar atividade

## Videoaulas (/api/videoaulas)

### CRUD Básico
- `POST /api/videoaulas` - Criar videoaula
- `GET /api/videoaulas` - Listar todas as videoaulas
- `GET /api/videoaulas/{id}` - Buscar videoaula por ID
- `PUT /api/videoaulas/{id}` - Atualizar videoaula
- `DELETE /api/videoaulas/{id}` - Excluir videoaula

### Consultas Específicas
- `GET /api/videoaulas/professor/{professorId}` - Listar por professor
- `GET /api/videoaulas/area/{area}` - Listar por área

### Operações de Status
- `PATCH /api/videoaulas/{id}/publicar` - Publicar videoaula
- `PATCH /api/videoaulas/{id}/arquivar` - Arquivar videoaula

## Materiais (/api/materiais)

### CRUD Básico
- `POST /api/materiais` - Criar material
- `GET /api/materiais` - Listar todos os materiais
- `GET /api/materiais/{id}` - Buscar material por ID
- `PUT /api/materiais/{id}` - Atualizar material
- `DELETE /api/materiais/{id}` - Excluir material

### Consultas Específicas
- `GET /api/materiais/professor/{professorId}` - Listar por professor
- `GET /api/materiais/area/{area}` - Listar por área

### Operações de Status
- `PATCH /api/materiais/{id}/publicar` - Publicar material
- `PATCH /api/materiais/{id}/arquivar` - Arquivar material

## Configurações Gerais

### Headers
- `Content-Type: application/json` (para requisições POST/PUT)
- `Accept: application/json`

### CORS
- Configurado para aceitar requisições de qualquer origem (`*`)

### Códigos de Resposta
- `200 OK` - Sucesso
- `404 Not Found` - Recurso não encontrado
- `400 Bad Request` - Dados inválidos

### Exemplo de Uso (JavaScript)

```javascript
// Cadastrar usuário
fetch('/api/usuarios', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({
        nome: 'João Silva',
        email: 'joao@email.com',
        senha: '123456',
        tipo: 'ALUNO'
    })
});

// Login
fetch('/api/usuarios/login?email=joao@email.com&senha=123456', {
    method: 'POST'
});

// Listar atividades
fetch('/api/atividades')
    .then(response => response.json())
    .then(data => console.log(data));
```