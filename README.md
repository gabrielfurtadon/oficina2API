
# Projeto ELLP - Ensino Lúdico de Lógica e Programação

Este projeto é uma solução de **Registro de Presença** para o projeto de extensão **ELLP - Ensino Lúdico de Lógica e Programação**. A proposta é desenvolver um sistema para gerenciar a presença dos alunos que participam das oficinas, permitindo que os professores acompanhem a frequência e o engajamento dos alunos ao longo do tempo.

## Tecnologias Utilizadas

- **Linguagens de Programação**: Java, JavaScript
- **Frameworks**: React (para o front-end), Spring Boot (para a API e back-end)
- **Banco de Dados**: MySQL
- **Automação e Integração Contínua**: GitHub Actions (ou outra ferramenta gratuita similar)
- **Testes**: JUnit (para testes de back-end em Java), Playwright com JavaScript (para testes E2E)

## Requisitos Funcionais

1. **Registro de Presença**: Professores podem registrar a presença dos alunos durante as aulas usando um dispositivo com internet.
2. **Autenticação de Usuários**: Apenas professores têm acesso ao sistema, com login seguro para garantir a privacidade dos dados.
3. **Visualização de Presenças**: Professores podem visualizar a presença de cada aluno em cada aula.
4. **Relatórios de Frequência**: Professores podem gerar relatórios semanais ou mensais sobre a frequência dos alunos, o que facilita o acompanhamento.
5. **Painel de Dados para Professores**: Um painel intuitivo onde o professor pode visualizar dados agregados sobre a presença e o engajamento dos alunos, incluindo:
   - **Alunos com maior e menor frequência**: Identificação dos alunos mais e menos assíduos.
   - **Gráficos de tendências de presença ao longo do tempo**: Análise visual das tendências de presença, facilitando o acompanhamento e intervenções necessárias.
6. **CRUD de Alunos Voluntários**: Gestão completa dos dados dos alunos voluntários, com possibilidade de importar informações via arquivo CSV.
7. **CRUD de Turmas**: Gestão das turmas, facilitando a organização e atribuição dos alunos.

## Arquitetura do Sistema

A arquitetura do sistema segue uma abordagem modular, com as seguintes camadas:

1. **Camada de Apresentação** (Frontend - React): Interface do aplicativo que permite aos professores fazerem o registro de presença e visualizarem os relatórios e gráficos de frequência.
2. **Camada de Aplicação** (Backend - Spring Boot): Responsável pela lógica de negócios, incluindo autenticação de professores, registro de presença e geração de relatórios.
3. **Camada de Persistência** (Banco de Dados - MySQL): Armazena os dados de presença e usuários, garantindo a integridade e acessibilidade dos registros.
4. **Serviços de Integração e Automação**: GitHub Actions para integração contínua e automação de testes, garantindo a qualidade do código e a implementação de melhorias de forma ágil.

   ![image](https://github.com/user-attachments/assets/f034d5a3-8c28-44b6-8f81-ed6a74f90583)

## Estratégia de Automação de Testes

1. **Testes de Unidade**:
   - Utilizando **JUnit** para cobrir as funcionalidades do back-end, garantindo que cada unidade de código funcione conforme esperado.
2. **Testes E2E**:
   - **Playwright** em JavaScript para testes end-to-end (E2E), validando o fluxo completo desde o login do professor até o registro e visualização de presenças.
3. **Testes de Integração e Funcionais**:
   - Cobrem a interação entre o front-end e o back-end, assegurando que o sistema como um todo funcione de maneira integrada e confiável.
4. **Integração Contínua com GitHub Actions**:
   - Configurado para executar os testes automaticamente a cada commit, identificando problemas de forma rápida e garantindo uma entrega contínua de melhorias.

## Diferenciais do Sistema

- **Painel de Dados Agregados**: Visão completa para o professor sobre a presença e o engajamento dos alunos, com dados visuais e insights que facilitam intervenções.
- **Acompanhamento de Tendências**: Gráficos que mostram a frequência dos alunos ao longo do tempo, permitindo que o professor identifique padrões e tome decisões para incentivar a participação.
- **Relatórios Personalizados**: Opção de gerar relatórios completos para acompanhamento da presença, promovendo transparência e facilitando o planejamento das aulas.



