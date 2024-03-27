# Engenharia de Software - Desafio de Marcos Paulo Silva

**PROCESSO SELETIVO – DESAFIO DE PROGRAMAÇÃO – NÍVEL 1**

## Descrição

Este projeto faz parte da primeira fase do processo seletivo de Estágio do Dev Training Program da Tunts.Rocks 2024,
na qual foi proposto que o candidato criasse uma aplicação na linguagem de programação de preferência do candidato,
no meu caso a linguagem escolhida foi Java. A aplicação deve ser capaz de ler uma planilha do Google Sheets,
buscar as informações necessárias, calcular e escrever o resultado na planilha.

## Critérios Avaliativos

- Bom entendimento do problema a ser resolvido;
- Êxito na implementação da funcionalidade;
- Estrutura do código fonte;
- Documentação e utilização de boas práticas;
- Utilização de ferramentas de desenvolvimento básicas.

## Desenvolvimento

Para a construção dessa aplicação, eu utilizei a [Documentação da Google Sheets](https://developers.google.com/sheets/api/guides/concepts?hl=pt-br),
na sessão de [Guia de início rápido do Java](https://developers.google.com/sheets/api/quickstart/java?hl=pt-br).

Segui o passo a passo disponibilizado pela Google, que consiste na ativação da API Google Sheets, configuração da permissão
OAuth, autorização de credenciais para um aplicativo de computador, até a preparação do espaço de trabalho utilizando o
Gradle.

A versão do Gradle utilizada foi [7.6.4](https://gradle.org/releases/).

O projeto foi construído dentro da pasta "Calculadora De Media De Alunos" utilizando o comando _gradle init --type basic_
no terminal.

Após estudar o código de conexão disponibilizado pela Google e buscar algumas informações em fóruns, como o StackOverflow
e na própria documentação, consegui compreender o funcionamento da API Google Sheets e extrair da planilha as informações
necessárias para o desenvolvimento da lógica utilizada.

Optei por reaproveitar a estrutura disponibilizada no Guia de início rápido do Java e implementar condicionais que analisam
os valores recebidos em cada linha da planilha. Dessa forma, é possível verificar o número de faltas do aluno e a sua média.

Para ser aprovado, o aluno deve possuir no mínimo 25% de presença nas aulas, ou a aplicação escreverá "Reprovado por Falta" no campo _Situação_
e o valor _0_ no campo _Nota para Aprovação Final_ referente ao aluno. Se a média entre as notas das 3 provas for superior ou igual a 7,
o aluno é aprovado, e a aplicação escreverá "Aprovado" no campo _Situação_ e o valor _0_ no campo _Nota para Aprovação Final_. Se a média for superior ou igual a 5,
mas menor que 7, a aplicação escreverá "Exame Final" no campo "Situação" e calculará a nota mínima que o aluno precisa
atingir no Exame Final para que obtenha a Nota para Aprovação Final necessária para sua aprovação. Caso a nota do aluno
seja inferior a 5, o aluno é reprovado, e a aplicação escreverá "Reprovado por Nota" no campo _Situação_ e o valor _0_
no campo _Nota para Aprovação Final_.

## Resultados

Após construir o código-fonte desta aplicação em Java, foi possível ler, processar e escrever dados em uma planilha
do Google Sheets utilizando o comando _gradle run_. Foram desenvolvidas habilidades de configuração da API do Google,
conhecimentos em construção de aplicações com Gradle e aprimoramento nos conhecimentos de desenvolvimento em Java.

## Autor

**Marcos Paulo Silva**
- [Github](https://www.github.com/souomarcos)
- [Linkedin](https://www.github.com/souomarcos)

Fevereiro de 2024.
