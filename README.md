# XY Inc | Pontos de Interesse (POIs) #


Plataforma para disponibilização de um dispositivo inovador que auxilia pessoas para encontrar pontos de interesse (POIs).
A mesma foi desenvolvida em uma arquitetura de serviços utilizando os principios básicos do [REST](https://pt.wikipedia.org/wiki/REST).
O frontend é baseado em uma aplicação [Angular](https://angularjs.org/) e o backend é composto de web services baseados na implementação do [Spring RESTful](https://spring.io/guides/gs/rest-service/) utilizando o JSON como trafego de informações nos serviços.


### Dependências ###

As seguintes dependências são necessárias:

- [Java Development Kit](http://www.oracle.com/technetwork/java/javase/downloads/index.html) (versão 8)
- [Maven](https://maven.apache.org/) (versão 3.3 ou maior)
- [Node.js](https://nodejs.org/) (versão 4.2.2 ou maior)
- Instalação Global do [Grunt](http://gruntjs.com/) (versão 1.0.0 ou maior) - execute: `npm install -g grunt-cli`
- [MySQL]() - servidor de banco de dados;


### Configurando o banco de dados ###
Acesse a interface do seu banco de dados MySQL e execute a seguinte ação:
- Crie 2 banco de dados, sendo 1 para executar a aplicação e outro para executar os testes automatizados.

As configurações de banco de dados se encontram nos arquivos (URL, usuário, senha e nome do banco):
src/main/resources/application.properties (Configurações de produção)
src/main/resources/application-test.properties (Configurações para os testes)

### Compilando e iniciando o servidor ###

Caso não esteja com a linha de comando posicionada na pasta de seu projeto, entre na mesma.

Nesse passo da compilação do projeto já é executado os testes automatizados implementados no servidor.

Crie o projeto com o Maven:
mvn clean install

Execute a aplicação apartir do diretório `target`:
java -jar xy-inc-0.0.1-SNAPSHOT.jar

Após a inicialização do servidor, a aplicação está acessível na URL:

    http://localhost:8080/


### Frontend Overview ###

O projeto é uma aplicação web com uma interface baseada em AngularJS, HTML5 e CSS3.
A aplicação é responsiva, uma vez que se adapta a diferentes tamanhos de tela.
Com o intuito de fornecer a melhor interface para o usuário, foi utilizado o conceito de Material Design desenvolvido pelo Google com o objetivo de alcançar as melhores práticas de usabilidade, acessibilidade e mobilidade.

### Backend Overview ###

A aplicação do servidor é baseado em Java 8, Spring, JPA e banco de dados MySQL.
As configurações do Spring são baseadas em anotações em Java e arquivo de .properties para configuração do banco de dados.
É utilizado os módulos do Spring: Spring MVC, Spring RESTful, Spring Data JPA e Spring Test.

As informações consumidas e produzidas nos web services são todas seguindo o modelo de dados JSON.
Os serviços web REST são baseados no Spring MVC e JSON com a implementação do Spring RESTful.

Os testes de unidades são feitos com o spring test e as funcionalidades da API REST são testados utilizando o [Spring test MVC](http://docs.spring.io/spring/docs/current/spring-framework-reference/html/testing.html#spring-mvc-test-framework).


#### REST API ####

A API REST do servidor é composto pelo seguinte serviço:

##### Ponto de Interesse #####

Url           |Verb          | Description
--------------|------------- | -------------
/ws/poi       |GET          | lista todos os pontos de interesse cadastrado na aplicação.
/ws/poi|POST| cria um novo ponto de interesse.
/ws/poi| PUT| atualiza um ponto de interesse caso exista.
/ws/poi/{id}|GET| retorna um ponto de interesse apartir do identificador único (id).
/ws/poi/{id}|DELETE| apaga um ponto de interesse apartir do identificador único (id).
/ws/poi/search|GET| busca pontos de interesse baseado em uma localização x e y e uma distância máxima.


### Testando a aplicação ###

Necessário essa seção de documentação?