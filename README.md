# Challenge-by-Coodesh
![alt text](https://coodesh.com/_next/image?url=%2Fimages%2Fsvg%2Flogos%2Flogo.svg&w=256&q=75)



## **Acerca do APP**

O APP foi desenvolvido para o Mobile Challenge 2021.


## __Case__:
A empresa Pharma Inc, está trabalhando em um projeto em colaboração com sua base de clientes para facilitar a gestão e visualização da informação dos seus pacientes de maneira simples e objetiva em uma aplicação onde podem listar, filtrar e expandir os dados disponíveis.
O seu objetivo nesse projeto, é trabalhar no desenvolvimento da Aplicação end que consumirá a API da empresa Pharma Inc seguindo os requisitos propostos neste desafio.

## __Desenvolvimento__:

Toda a estrutura do projeto foi desenvolvida com a linguagem Kotlin.

### Como ele é dividido?

Ele foi dividido em três partes:
1. Uma tela inicial com o logo.
2. A segunda tela com os dados obtidos a API, que lista os Pacientes com seus dados princípais.
3. Para expandir a informação dos pacientes, adicionamos o card como clicavél, exibindo os dados completos do Paciente.

![alt text](https://lab.coodesh.com/public-challenges/mobile-challenge-2021/-/raw/master/assets/screens.png)

### Recursos:

1.Logo da Marca: Pharma Inc

![alt text](https://lab.coodesh.com/public-challenges/mobile-challenge-2021/-/raw/master/assets/logo.png)

2. Cores para trabalhar no Projeto: UI Colors
![alt text](https://lab.coodesh.com/public-challenges/mobile-challenge-2021/-/raw/master/assets/colors.png)

3. Obtemos os dados, utilizando a API do Random User: https://randomuser.me/api/

4. Documentação da REST API: https://randomuser.me/documentation

5. Além de realizar a request, aplicamos alguns filtros na API:
* Limitar em 50 resultados por request;
* Utilizamos o Loading more para indicar que estamos carregando mais dados da API

6. Para expandir a informação dos pacientes, foi adiciona o card como clicavél e exibimos os seguintes campos do paciente:
- [x] Imagem
- [x] Nome completo
- [x] Email
- [x] Gênero
- [x] Data de nascimento
- [x] Telefone
- [x] Nacionalidade
- [x] Endereço
- [x] ID (Número de identificação)


### Extras
Além do desafio proposto com as telas, temos alguns diferenciais:
- [x] Diferencial 1 -> Um filtro por Gênero na tabela;
- [x] Diferencial 2 -> Buscador para poder filtrar por nacionalidade;
- [x] Diferencial 3 -> Escrever Unit Tests na Lista de Pacientes. Escolher a melhor abordagem e biblioteca;  

### O que foi usado?

* Na estrutura do APP com a linguagem Kotlin, framework e/ou tecnologias usadas
- [x] Fragment
- [x] Jetpack Architecture Components;
- [x] Containers
- [x] Binding
- [x] Arquitetura MVVM
- [x] Graph
- [x] EventBus
- [x] DataStore
- [x] LiveData
- [x] Observe
- [x] RecyclerView
- [x] Adapter
- [x] ConstraintLayout
- [x] Coroutines
- [x] EventBus
- [x] Animation lottie
- [x] Navigation
- [x] Lifecycle
- [x] Coroutines
- [x] Card View
- [x] Picasso
- [x] Material Design
- [x] Koin
- [x] Moshi
- [x] Mockito
- [x] Retrofit

### Instalação
- minSdkVersion 21
- targetSdkVersion 30

> Challenge-by-Coodesh finalizado.
> Ainda pode ser feita algumas melhorias, mas o que foi pedido está feito.
> Espero que possamos falar mais sobre em um futuro próximo.

> Foi um momento fantástico de aprendizagem e crescimento.
> Qualquer dúvida, estou aqui...
