## Repositório do Front: 
https://github.com/gabrielfurtadon/Oficina2UI 

# Projeto ELLP - Ensino Lúdico de Lógica e Programação

Este projeto é uma solução de **Registro de Presença** para o projeto de extensão **ELLP - Ensino Lúdico de Lógica e Programação**. A proposta é desenvolver um sistema para gerenciar a presença dos alunos que participam das oficinas, permitindo que os professores acompanhem a frequência e o engajamento dos alunos ao longo do tempo.

## Tecnologias Utilizadas

- **Linguagens de Programação**: Java, JavaScript
- **Frameworks**: React (para o front-end), Spring Boot (para a API e back-end)
- **Banco de Dados**: MySQL
- **Testes**: Jest, JUnit (para testes de back-end em Java), Playwright com JavaScript (para testes E2E)

## Requisitos Funcionais

- **Cadastro de Instrutor**: O sistema permitirá o cadastro de instrutores. Apenas instrutores cadastrados terão acesso ao sistema.
  
- **Login de Instrutor**: O sistema permitirá que os instrutores se autentiquem de forma segura, garantindo que apenas usuários cadastrados possam acessar o sistema para gerenciar workshops e participantes.
  
- **Cadastro de Workshops**: O instrutor poderá cadastrar workshops, informando dados como título, descrição, data, duração, número máximo de participantes e tipo de evento (presencial ou online). O instrutor poderá também editar ou cancelar workshops, conforme necessário.
  
- **Cadastro de Participantes**: O sistema permitirá que o instrutor cadastre os participantes dos workshops.
  
- **Geração de Certificados**: Após a conclusão de um workshop, o sistema gerará automaticamente certificados personalizados para os participantes que participarem.

## Arquitetura do Sistema

A arquitetura do sistema segue uma abordagem modular, com as seguintes camadas:

1. **Camada de Apresentação** (Frontend - React): Interface do aplicativo que permite aos professores fazerem o registro de presença e visualizarem os relatórios e gráficos de frequência.
  
2. **Camada de Aplicação** (Backend - Spring Boot): Responsável pela lógica de negócios, incluindo autenticação de professores, registro de presença e geração de relatórios.
  
3. **Camada de Persistência** (Banco de Dados - MySQL): Armazena os dados de presença e usuários, garantindo a integridade e acessibilidade dos registros.

![Imagem do WhatsApp de 2024-11-07 à(s) 20 38 20_41d93110](https://github.com/user-attachments/assets/0fee4ab4-4f1f-4b86-bcbe-06d3c913c438)


## Estratégia de Automação de Testes

1. **Testes de Unidade**:
   - Utilizando **JUnit** para cobrir as funcionalidades do back-end, garantindo que cada unidade de código funcione conforme esperado.Com cobertura de decisão e declaração.
  
2. **Testes de Integração e Funcionais**:
   - Interação entre o front-end e o back-end, assegurando que o sistema como um todo funcione de maneira integrada e confiável.

3. **Testes E2E**:
   - **Playwright** em JavaScript para testes end-to-end (E2E), validando o fluxo completo desde o login do professor até o registro e visualização de presenças.

![image](https://github.com/user-attachments/assets/901ec728-1cb3-4f9f-8923-abaead1314c8)

- **Sprint 1**: Foco principal em testes de unidade (JUnit, Jest) e testes de integração (Spring Boot Test, Jest) para garantir que as funcionalidades básicas e a comunicação entre as camadas (frontend/backend) estejam corretas.

- **Sprint 2**: Concentração nos testes end-to-end (Playwright) para validar o sistema completo em fluxo real de usuário, com ajustes e correções baseadas nos testes da Sprint 1.
