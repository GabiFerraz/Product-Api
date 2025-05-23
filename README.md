# Product API
Essa api é parte do projeto da **Fase 4** da Especialização em Arquitetura e Desenvolvimento Java da FIAP.
Um sistema de gerenciamento de pedidos integrado com Spring e Mocrosserviços. A aplicação foi desenvolvida em **Java 21**,
utilizando **Spring Boot**, **Maven**, um banco de dados **H2** para testes, **Mockito** e **JUnit 5** para testes
unitários, **Lombok** para facilitar o desenvolvimento e documentação gerada pelo **Swagger**.

## Descrição do Projeto
O objetivo desse sistema é abranger desde a gestão de clientes e produtos até o processamento e entrega de pedidos,
enfatizando a autonomia dos serviços, comunicação eficaz e persistência de dados isolada. Esta API é responsável pela
gestão de produtos.

## Funcionalidades
A API permite:
- **Cadastrar, buscar, atualizar e deletar** produtos e seus respectivos dados.

## Tecnologias Utilizadas
- **Java 21**
- **Spring Boot**
- **Spring Data JPA**
- **Maven**
- **Banco de Dados H2**
- **Banco de Dados Mysql**
- **Mockito** e **JUnit 5**
- **Lombok**
- **Swagger**
- **Docker Compose**
- **Spotless**
- **Jacoco**
- **Docker**
- **RabbitMQ**

## Estrutura do Projeto
O projeto segue uma arquitetura modularizada, organizada nas seguintes camadas:
- `core`: Contém as regras de negócio do sistema.
- `domain`: Define as entidades principais do domínio.
- `domain.exception`: Exceções personalizadas para regras de negócio.
- `dto`: Representa as entradas e saídas de dados para a API.
- `gateway`: Interfaces para interação com o banco de dados.
- `usecase`: Contém os casos de uso do sistema.
- `usecase.exception`: Exceções personalizadas para regras de negócio.
- `entrypoint.configuration`: Configurações do sistema, incluindo tratamento de exceções.
- `entrypoint.controller`: Controladores responsáveis por expor os endpoints da API.
- `infrastructure.gateway`: Implementações das interfaces de gateway.
- `infrastructure.persistence.entity`: Representação das entidades persistidas no banco de dados.
- `infrastructure.persistence.repository`: Interfaces dos repositórios Spring Data JPA.
- `presenter`: Representação dos dados de saída para a API.

## Pré-requisitos
- Java 21
- Maven 3.6+
- IDE como IntelliJ IDEA ou Eclipse

## Configuração e Execução
1. **Clone o repositório**:
   ```bash
   git clone https://github.com/GabiFerraz/Product-Api.git
   ```
2. **Instale as dependências:**
   ```bash
   mvn clean install
   ```
3. **Execute o projeto:**
   ```bash
   mvn spring-boot:run
   ```

## Uso da API
Para visualização dos dados da api no banco de dados H2, rodar o comando: **mvn "-Dspring-boot.run.profiles=h2" spring-boot:run**
e acessar localmente o banco através do endpoint:
- **Banco H2**: http://localhost:8080/h2-console
- **Driver Class**: org.h2.Driver
- **JDBC URL**: jdbc:h2:mem:product
- **User Name**: gm
- **Password**:
  Para visualização dos dados da api no banco de dados Mysql, subir o docker-compose: **docker-compose up --build**

Os endpoints estão documentados via **Swagger**:
- **Swagger UI**: http://localhost:8080/swagger-ui/index.html
- **Swagger JSON**: http://localhost:8080/v3/api-docs

### Possibilidades de Chamadas da API
1. **Cadastro de Produto:**
```json
curl --location 'localhost:8080/api/products' \
--header 'Content-Type: application/json' \
--data '{
"name": "Bola de Futebol",
"sku": "BOLA-123-ABC",
"price": 10.0
}'
```

2. **Busca de Produto:**
```json
curl --location 'localhost:8080/api/products/BOLA-123-ABC'
```

3. **Atualização de Produto:**
```json
curl --location --request PUT 'localhost:8080/api/products/BOLA-123-ABC' \
--header 'Content-Type: application/json' \
--data '{
"name": "Bola de Futebol 2",
"sku": "BOLA-123-ABC",
"price": 20.0
}'
```

4. **Delete de Produto:**
```json
curl --location --request DELETE 'localhost:8080/api/products/BOLA-123-ABC'
```


## Testes
Para rodar os testes unitários:
```bash
mvn test
```
Lembrando que o docker precisa estar rodando para os testes funcionarem: docker-compose up -d

**Rodar o coverage:**
   ```bash
   mvn clean package
   ```
Depois acessar pasta target/site/jacoco/index.html

O projeto inclui testes unitários, testes de integração e testes de arquitetura para garantir a qualidade e
confiabilidade da API.

## Desenvolvedora:
- **Gabriela de Mesquita Ferraz** - RM: 358745