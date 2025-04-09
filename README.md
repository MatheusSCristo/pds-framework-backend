# 🧠 English Teaching Assistant - Backend

Este é o backend da aplicação **English Teaching Assistant**, uma plataforma SaaS que utiliza inteligência artificial para auxiliar professores de inglês na geração de conteúdo personalizado, organização de aulas e acompanhamento de alunos.

---

## ⚙️ Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.x**
- **Spring Web** – Criação de APIs RESTful
- **Spring Data JPA** – Persistência de dados
- **PostgreSQL** – Banco de dados relacional
- **WebClient** – Consumo de API externa (Gemini IA)
- **Lombok** – Redução de boilerplate

---

## 📦 Endpoints Principais

| Método | Rota                     | Descrição |
|--------|--------------------------|-----------|
| POST   | `/auth/register`         | Cadastro de professor |
| POST   | `/auth/login`            | Login e geração de token JWT |
| GET    | `/alunos`                | Listar alunos do professor logado |
| POST   | `/aulas/plano`           | Criar plano de aula |
| POST   | `/ia/gerar`              | Gerar material com IA |
| GET    | `/flashcards`            | Listar flashcards personalizados |
| GET    | `/relatorios`            | Relatórios de desempenho do aluno |

---

## Como rodar

No terminal, rode:
```bash
docker compose up -d
```

Rode o projeto

