# SP_SpringBoot_AWS_Mensageria
### Desafio: Processamento assíncrono com Mensageria

Objetivo: Desenvolver microservices em Java com comunicação assíncrona via mensageria entre dois serviços.

### Microserviços desenvolvidos:
+ msdeposito: Responsável por realizar depósitos e enviar os dados via RabbitMQ para o microserviço msextratobancario.
+ msextratobancario: Responsável por receber os dados enviados pelo msdeposito e salvar as movimentações no extrato bancário da conta de um cliente.


O cliente realiza um depósito através do microserviço msdeposito informando dados do depósito (idContaCliente e valorDeposito) são enviados para queue transacao-banco.  
O microserviço msextratobancario consome a mensagem enviada para queue transacao-banco e registra a movimentação no extrato bancário do cliente.

### Pré-requisitos para rodar a aplicação
- Docker e Docker Compose instalados     
- RabbitMQ configurado e em execução.
- Maven
- Java 21
> Maven e Java é necessário caso deseje a aplicação diretamente na sua máquina.  
> Caso vá executar os containers não se faz necessário instala-los. 

### Para Execução
1. Antes de realizar qualquer depósito, é necessário criar o extrato bancário de uma conta cliente no microserviço msextratobancario. Utilize o seguinte endpoint: 

> Endpoint:  
> POST http://localhost:8081/extrato-bancario 

Exemplo de Body (JSON):  
```json
{ 
  "idContaCliente": "1" 
}
```
> Esse endpoint inicializa o extrato bancário para a conta do cliente com idContaCliente = 1.

2. Realizar um Depósito  
Com o extrato bancário criado, você pode realizar um depósito no microserviço msdeposito.

Endpoint:  
POST http://localhost:8080/deposito  
Exemplo de Body (JSON): 
```json
{ 
  "idContaCliente": 1, 
  "valorDeposito": 100.00 
}
```

- O que acontece quando depósito é efetuado?
O msdeposito envia uma mensagem para o RabbitMQ com os dados do depósito.   
O msextratobancario consome a mensagem e processa a movimentação se for uma movimentação válida ele salva, do contrário ele manda a mensagem para dead letter queue.

### Endpoints Resumidos:

- Microserviço msextratobancario  
  Cria Extrato Bancário:  
  POST http://localhost:8081/extrato-bancario  
  Body: 
  ```json
  { 
    "idContaCliente": "1"
  }
  ```
  Buscar Extratos Bancário:  
  GET http://localhost:8081/extrato-bancario   
  
- Microserviço msdeposito   
  Realizar Depósito:  
  POST http://localhost:8080/deposito  
  Body: 
  ```json 
  { 
    "idContaCliente": "1",
    "valorDeposito": 100.00 
  }
  ```


### Execute a aplicação na sua máquina  
1. Clone o repositório  
> git clone https://github.com/LazaroKy/SP_SpringBoot_AWS_Mensageria/  

2. Rode o serviço do RabbitMQ     
> docker run -it --rm --name rabbitmqms -p 5672:5672 -p 15672:15672 rabbitmq:4.0-management  
  
Quando o container for parado ele vai ser apagado, caso desejar manter o container execute o comando sem "--rm"    
  
3. Acesse o projeto msextratobancario e msdeposito no bash ou cmd e execute o programa localmente com o maven    

   ```Maven
   ~/SP_SpringBoot_AWS_Mensageria/Mensageria_RabbitMQ/msdeposito/# mvn spring-boot:run
   ~/SP_SpringBoot_AWS_Mensageria/Mensageria_RabbitMQ/msextratobancario/# mvn spring-boot:run
   ```

> ⚠️Ou abra o bash na pasta Mensageria_RabbitMQ do repositório clonado e rode o comando:  
>  docker-compose -p desafio-mensageria up -d
> Executará três serviços, sendo eles: RabbitMQ, msextrato-bancario e msdeposito

### Siga os passos descritos para execução da aplicação:  
Crie o extrato bancário da conta do cliente.   
Realize depósitos e acompanhe as movimentações no msextratobancario   

Você pode executar os EndPoints pelo Postman \
[<img src="https://run.pstmn.io/button.svg" alt="Run In Postman" style="width: 128px; height: 32px;">](https://grupo-04-desafio-02.postman.co/collection/39006660-2540cb27-2b98-46e1-ae9d-159b0f468931?source=rip_markdown)

Acompanhe o status das mensagens no painel do RabbitMQ: http://localhost:15672.
