# SP_SpringBoot_AWS_Mensageria
### Atividade: Mensageria

Objetivo: Desenvolver microservices em Java com comunicação assíncrona via mensageria entre dois serviços.

### Microserviços desenvolvidos:
+ msdeposito: Responsável por realizar depósitos e enviar os dados via RabbitMQ para o microserviço msextratobancario.
+ msextratobancario: Responsável por receber os dados enviados pelo msdeposito e salvar as movimentações no extrato bancário da conta de um cliente.


O cliente realiza um depósito através do microserviço msdeposito informando dados do depósito (idContaCliente e valorDeposito) são enviados para queue transacao-banco.
O microserviço msextratobancario consome a mensagem enviada para queue transacao-banco e registra a movimentação no extrato bancário do cliente.

### Pré-requisitos para rodar a aplicação
Docker e Docker Compose instalados (para subir o RabbitMQ facilmente) \
RabbitMQ configurado e em execução. 


1 Inicializar o Extrato Bancário \
Antes de realizar qualquer depósito, é necessário criar o extrato bancário de uma conta cliente no microserviço msextratobancario. Utilize o seguinte endpoint: 

Endpoint: \
POST http://localhost:8081/extrato-bancario \
Exemplo de Body (JSON): 

json \
Copiar código \
{ \
  "idContaCliente": "1" \
} \
Esse endpoint inicializa o extrato bancário para a conta do cliente com idContaCliente = 1.

2 Realizar um Depósito \
Com o extrato bancário criado, você pode realizar um depósito no microserviço msdeposito.

Endpoint: \
POST http://localhost:8080/deposito \
Exemplo de Body (JSON): \
{ \
  "idContaCliente": "1", \
  "valorDeposito": 100.00 \
} \
Quando o depósito é efetuado:

O msdeposito envia uma mensagem para o RabbitMQ com os dados do depósito. \
O msextratobancario consome a mensagem e processa a movimentação se for uma movimentação válida ele salva, se não ele manda a mensagem para dead letter queue. \
Endpoints Resumidos:

Microserviço msextratobancario \
Criar Extrato Bancário: \
POST http://localhost:8081/extrato-bancario \
Body: \
json \
Copiar código \
{ \
  "idContaCliente": "1" \
} \
Microserviço msextratobancario \
Criar Extrato Bancário: \
POST http://localhost:8081/extrato-bancario \
Body: \
{ \
  "idExtrato": 1, \
  "idContaCliente": 3, \
  "dataExpedicao": "2024-11-25T15:19:31.979257", \
  "saldo": null, \ 
  "movimentacoes": [] \
} \
Microserviço msdeposito \
Realizar Depósito: \
POST http://localhost:8080/deposito \
Body: \
json \
Copiar código \
{ \
  "idContaCliente": "1", \
  "valorDeposito": 100.00 \
} \


### Execute a aplicação na sua máquina
Clone o repositório
> git clone https://github.com/LazaroKy/SP_SpringBoot_AWS_Mensageria/

Abra com sua IDE e execute o programa localmente, ou se preferir também pode rodar com docker compose \
abra o bash no repositório clonado e rode o comando: \
>  docker-compose -p desafio-mensageria up -d

### Siga os passos descritos na Configuração Inicial:
Crie o extrato bancário da conta do cliente. \
Realize depósitos e acompanhe as movimentações no msextratobancario 

Você pode executar os EndPoints pelo Postman \
[<img src="https://run.pstmn.io/button.svg" alt="Run In Postman" style="width: 128px; height: 32px;">](https://grupo-04-desafio-02.postman.co/collection/39006660-2540cb27-2b98-46e1-ae9d-159b0f468931?source=rip_markdown)

Acompanhe o status das mensagens no painel do RabbitMQ: http://localhost:15672.
