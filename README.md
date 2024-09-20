# Projeto Livraria

**Objetivo:** criar um projeto para cadastro de usuários, livros e empréstimos e devoluções usando **Java Spring Boot**.

## Arquitetura do Sistema
O projeto está organizado em camadas, organizadas em quatro pacotes:
- Controladores (*controllers*): responsável por definir os *endpoints* com a interface externa.
- Domínio (*domain*): responsável pelas classes do modelo de negócio: *User*, *Book* e *Loan*.
- Repositórios (*repository*): responsável pelas interfaces que fazem a intermediação com o banco de dados.
- Serviços (*services*): responsável pelos casos de uso da aplicação envolvendo as classes de domínio.

Um arquivo de configuração foi definido para o tratamento com *LocalDate* no pacote *config*.

## API REST

As APIs criadas no projeto são:

- **books**:
  - GET (/books/): retorna todos os livros cadastrados.
  - GET (/books/api/?title=<title>): retorna os livros pesquisados na API do Google Books.
  - GET (/books/<book_id>): retorna os atributos do livro pelo id.
  - POST (/books/): cria um novo livro, com os parametros pelo *body*.
  - DELETE (/books/<book_id>): remove um livro pelo id.
  - UPDATE (/books/<book_id>): atualiza os atributos de um livro pelo id, passando os atributos pelo *body*.

- **users**:
  - GET (/users/): retorna todos os usuários cadastrados.
  - GET (/users/recommend/<user_id>): retorna os recomendados pelo id do usuário.
  - GET (/users/<user_id>): retorna os atributos do usuário pelo id.
  - POST (/users/): cria um novo usuário, com os parametros pelo *body*.
  - DELETE (/users/<user_id>): remove um usuário pelo id.
  - UPDATE (/users/<user_id>): atualiza os atributos de um usuário pelo id, passando os atributos pelo *body*.

- **loans**:
  - GET (/loans/): retorna todos os empréstimos cadastrados.
  - POST (/loans/: cria um novo empréstimo, com os parametros pelo *body*.
  - PATCH (/<loan_id>/): finaliza um empréstimo pelo id.
  - DELETE (/<loan_id>/): cancela um empréstimo pelo id.

## Testes

Os testes foram organizados no diretório *test*. Foram criados testes para os endpoints da aplicação.

