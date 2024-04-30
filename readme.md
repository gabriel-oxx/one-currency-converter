# Currency Converter
## Descrição
O Currency Converter é uma aplicação Java que permite aos usuários converter valores entre diferentes moedas com base em taxas de câmbio fornecidas pela API CurrencyApi. A aplicação foi desenvolvida para simplificar tarefas de conversão de moedas, tornando-a uma ferramenta útil para usuários que lidam frequentemente com múltiplas moedas.
## Funcionalidades
- Conversão de Moedas: A aplicação permite converter valores entre diferentes moedas com base nas taxas de câmbio fornecidas.
- Salvamento: A aplicação oferece a possibilidade de salvamento das conversões realizadas em um arquivo JSON para que o usuário possa acessá-las mais tarde.
- Interface de Linha de Comando: A aplicação é executada através de uma interface de linha de comando, facilitando o uso para usuários familiarizados com essa forma de interação.
## Como Usar
1. Executar a Aplicação: Para executar a aplicação, navegue até o diretório do projeto no terminal e execute o comando java Main.
2. Selecionar Moedas: Após a execução, a aplicação solicitará que o usuário insira a moeda de origem, o valor desejado  e em seguida a moeda de destino. (caso já existam conversões salvas, o programa perguntará se o usuário deseja consultá-las antes de exibir as possibilidades de conversão).
3. Resultado: A aplicação exibirá o valor de uma unidade da moeda de origem convertida na moeda de destino com base na taxa de câmbio atual.
4. Após a realização de cada conversão a aplicação perguntará ao usuário se ele deseja continuar fazendo outras conversões ou simplesmente sair da aplicação.
5. A aplicação exibirá uma lista das conversões realizadas (caso exista mais de uma conversão realizada, ou simplesmente a única conversão realizada).
6. Antes de ser encerrada, a aplicação perguntará ao usuário se ele deseja salvar as conversões realizadas.
## Requisitos
- A aplicação foi desenvolvida e testada com a versão 22 do Java, mas deve funcionar com Java a partir da versão 11.
- Gson: A aplicação utiliza o Gson para realizar a conversão de objetos Java em JSON e vice-versa. Para utilizar esta biblioteca, é necessário adicioná-la ao projeto. No desenvolvimento do projeto foi utilizada a versão 2.10.1 do Gson.
- uma chave de API: Para utilizar a API do CurrencyApi é necessário obter uma chave de API. Para obter uma chave, é necessário se cadastrar em uma conta no CurrencyApi e gerá-la.
## Estrutura do Projeto
- src/: Contém os arquivos de código-fonte da aplicação.
- Main.java: Ponto de entrada da aplicação, onde a lógica de conversão de moedas é implementada.
- Currency.java: Define uma classe para representar os atributos e métodos para a implementação da conversão.
## Licença
Este projeto está licenciado sob a licença MIT. Veja o arquivo LICENSE para mais detalhes.