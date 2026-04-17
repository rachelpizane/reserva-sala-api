# 📅 Reserva de Salas API

API backend desenvolvida em Java com Spring Boot para gerenciamento de reservas de salas de reunião.

O sistema permite o cadastro de salas, criação de reservas e validação de conflitos de horário, garantindo consistência nas regras de negócio e organização do domínio.

<br> 

🔗 API disponível em produção: [link](https://reserva-sala-api-n9z1.onrender.com/)

*⚠️ A aplicação pode levar alguns segundos para responder na primeira requisição devido ao cold start da plataforma.*

<br>

## 🖥️ Frontend da aplicação

Este projeto possui uma interface web desenvolvida separadamente para consumo da API:

🔗 Frontend: [link](https://reserva-sala-app.onrender.com/)

🔗 Repositório: [link](https://github.com/rachelpizane/reserva-sala-app)


<br>

## ✨ Principais funcionalidade
- **Cadastro de salas de reunião**: Permite registrar salas com informações como nome, capacidade, localização e descrição.
- **Criação de reservas**: Permite agendar uma sala em um intervalo de tempo específico.
- **Validações de regras de reserva**: Garante consistência dos horários, incluindo período válido, restrição a horário comercial e bloqueio de datas passadas.
- **Consulta de agenda semanal**: Permite visualizar reservas agrupadas por dia em uma semana específica, com suporte à navegação entre semanas.
- **Persistência de dados**: Utiliza banco relacional com versionamento de schema.
- **Documentação interativa da API**: Endpoints documentados via Swagger/OpenAPI.
- **Testes automatizados**: Cobertura com testes unitários.

<br>

## 🛠️ Tecnologias utilizadas

- Java 21
- Spring Boot
- Maven
- Docker
- PostgreSQL
- Flyway
- JUnit
- Mockito
- JaCoCo
- Swagger (OpenAPI)
- MapStruct
- Lombok

<br>

## 🚀 Como rodar o projeto

### Pré-requisitos
Antes de começar, você precisará ter instalado em sua máquina:

- **Docker**
- **Git**
- **Java** (versão 21 - LTS recomendado)
- **Maven** (ou utilizar o wrapper `mvnw` incluído no projeto)

### 1. Clone o repositório
   ```bash
   # Clone o repositório
   git clone https://github.com/rachelpizane/reserva-sala-api.git
   
   # Acesse a pasta do projeto
   cd reserva-sala-api
   ```

### 2.1. Opção 1: Rodar tudo com Docker
#### 2.1.1 Suba backend + banco de dados
   ```bash
   docker-compose up -d --build
   ```

### 2.2. Opção 2: Rodar backend local + banco no Docker
#### 2.2.1 Suba apenas o banco de dados:
   ```bash
   docker-compose up -d postgres
   ```

#### 2.2.2. Rode a aplicação localmente
   ```bash
   ./mvnw spring-boot:run
   ```

<br>

## 🧪 Testes
O projeto possui:
- Testes unitários

Para executar os testes automatizados:
   ```bash
   ./mvnw test
   ```

<br>

## 📊 Cobertura de código
O projeto utiliza JaCoCo para análise de cobertura.

Para gerar o relatório:
   ```bash
   ./mvnw clean verify
   ```
*OBS: O relatório também é gerado após a execução dos testes automatizados.*

O relatório estará disponível em:
   ```bash
   target/site/jacoco/index.html
   ```
Abra o arquivo index.html no navegador para visualizar os detalhes da cobertura.

<br>

## 📄 Documentação da API
A documentação da API está disponível via Swagger:

   ```bash
   http://localhost:8080/swagger-ui/index.html
   ```
Após iniciar a aplicação, acesse o link acima para:

- Visualizar os endpoints disponíveis
- Testar requisições diretamente
- Consultar contratos da API

⚠️ *Por questões de segurança, a documentação Swagger é habilitada apenas em ambientes de desenvolvimento.*

<br>

## 🙋🏻‍♀️ Autora

Desenvolvido por [Rachel Pizane](https://br.linkedin.com/in/rachel-pizane). 💜