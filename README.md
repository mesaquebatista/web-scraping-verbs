# web-scraping-verbs
O projeto desenvolvido visa buscar informações em listas de verbos em inglês e, em seguida, realiza a inserção no banco de dados.

### Ambiente
openjdk version "1.8.0_362"
mysql version 8.0.32-0ubuntu0.22.04.2
Chrome version 111

### Configuração
Antes de executar o código, é necessário atualizar as informações de conexão com o banco de dado presente no arquivo: `src/main/java/br/com/mesaque/db/ConnectionMySql.java`

### Execução
Para executar o web scrapping basta usar a classe `src/main/java/br/com/mesaque/App.java`