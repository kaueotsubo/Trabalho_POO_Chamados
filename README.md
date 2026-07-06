# Sistema de Chamados Automatizado de HelpDesk



### Integrantes: 
- Ana Luiza Costa da Frota
- Kauê Otsubo de Araujo
- Matheus Guilherme Nascimento Soares
- Rodrigo Freitas Medeiros

### Tecnologias Usadas
- Linguagem Java
- JDK (Java Development Kit)
- IntelliJ IDEA

---

## 1 - INTRODUÇÃO DO PROBLEMA

O gerenciamento de serviços de TI em muitas organizações frequentemente sofre com a falta de centralização e rastreabilidade. Em cenários que dependem de planilhas ou e-mails, as solicitações de suporte (chamados) se perdem facilmente, tornando difícil o acompanhamento do status do problema. Além disso, a falta de padronização gera dados inconsistentes, como a duplicação de identificadores, e falhas de comunicação, já que solicitantes e técnicos não são notificados de forma eficiente sobre o progresso do atendimento.

Outro desafio comum em sistemas legados é a rigidez das estruturas de cadastro. Regras de negócio inflexíveis limitam a colaboração da equipe técnica e, na ausência de um tratamento de erros robusto, erros simples de digitação por parte do usuário podem causar instabilidade ou o fechamento inesperado da aplicação, prejudicando a operação diária da infraestrutura de TI.

## 2 - SOLUÇÃO PROPOSTA

Para sanar essas limitações, foi desenvolvido um Sistema Automatizado de Help Desk estruturado em camadas e construído em Java. A solução centraliza o controle de chamados utilizando estruturas de dados em memória (`HashMap`), o que impede cadastros duplicados ao indexar usuários unicamente por seus e-mails. Cada chamado recebe um identificador sequencial automático e tem seu ciclo de vida monitorado de forma segura através de estados predefinidos (`ABERTO`, `EM_ANDAMENTO`, `RESOLVIDO`).

A comunicação e a flexibilidade do sistema foram aprimoradas através da Programação Orientada a Objetos (POO). Utilizando o polimorfismo por meio de uma interface de notificação, o sistema dispara alertas automáticos tanto para clientes quanto para técnicos sempre que há atualizações. Adicionalmente, o software garante a estabilidade da operação ao isolar as responsabilidades do código (Visão, Controle e Modelo) e implementar um tratamento rigoroso de exceções, impedindo que falhas em tempo de execução derrubem a aplicação e assegurando uma experiência fluida para o usuário.

---

## 3 - REQUISITOS FUNCIONAIS

|Requisitos Funcionais do sistema|Informações dos requisitos funcionais do sistema|
|--------------------------------|----------------------------------------------------------------------------------------------------------------------|
|Requisito Funcional 01:| O sistema deve permitir o cadastro de novos usuários, diferenciando o perfil entre Cliente e Técnico.|
|Requisito Funcional 02:| O sistema deve permitir que um Cliente registre um novo chamado informando a descrição do problema.|
|Requisito Funcional 03:| O sistema deve permitir que um Técnico assuma a responsabilidade pelo atendimento de um chamado.|
|Requisito Funcional 04:| O sistema deve gerenciar e atualizar os status dos chamados (Aberto, Em Andamento, Resolvido, Fechado).|
|Requisito Funcional 05:| O sistema deve acionar uma interface de notificação para alertar os envolvidos quando houver alterações no andamento.|
|Requisito Funcional 06:| O sistema deve possuir uma funcionalidade de busca para consultar usuários e chamados específicos registrados na base.|

---

## 4 - DIAGRAMA DE CASOS DE USO

<p align="center">
    <img src="imgs/usodiagrama.png">
</p>

Como ilustrado no diagrama, as interações principais do sistema são divididas entre dois atores:
- **Técnico:** Gerencia o ciclo de vida do chamado (assumir, alterar status, buscar) e consulta usuários.
- **Cliente:** Inicia o processo (abrir chamado) e pode acompanhar (buscar chamado e buscar usuário).
Ambos recebem notificações automáticas do sistema.

---

## 5 - DIAGRAMA DE ESTADOS

```mermaid
stateDiagram-v2
    [*] --> ABERTO : Cliente cria o Chamado
    
    ABERTO --> EM_ANDAMENTO : Tecnico assume o Chamado
    
    EM_ANDAMENTO --> RESOLVIDO : Tecnico soluciona o problema
    
    RESOLVIDO --> FECHADO : Cliente aprova a solução
    RESOLVIDO --> EM_ANDAMENTO : Cliente recusa a solução
    
    FECHADO --> [*]
```

O diagrama representa o fluxo de estados que um chamado percorre no sistema, desde a sua abertura até o encerramento definitivo:

- **ABERTO:** O fluxo se inicia quando o **Cliente cria o Chamado**.
- **EM_ANDAMENTO:** O estado é alterado assim que um **Técnico assume o Chamado**.
- **RESOLVIDO:** Quando o **Técnico soluciona o problema**, o chamado avança para este estado. Aqui ocorre uma tomada de decisão por parte do usuário:
    - **Cliente recusa a solução:** O chamado retorna para o estado **EM_ANDAMENTO** para que o técnico trabalhe nele novamente.
    - **Cliente aprova a solução:** O fluxo segue para a etapa final.
- **FECHADO:** O estado final do ciclo de vida, atingido após a validação positiva do cliente.

---

## 6 - DIAGRAMA DE CLASSES

```mermaid
classDiagram
    class Usuario {
        <<abstract>>
        #String id
        #String nome
        #String email
        +getId() String
        +getNome() String
    }

    class Cliente {
        -String departamento
        +abrirChamado() Chamado
    }

    class Tecnico {
        -String especialidade
        +atenderChamado(Chamado chamado) void
    }

    class Chamado {
        -String idChamado
        -String descricao
        -StatusChamado status
        -Cliente solicitante
        -Tecnico responsavel
        +atualizarStatus(StatusChamado status) void
        +getStatus() StatusChamado
    }

    class StatusChamado {
        <<enumeration>>
        ABERTO
        EM_ANDAMENTO
        RESOLVIDO
        FECHADO
    }

    class Notificavel {
        <<interface>>
        +enviarNotificacao(String mensagem) void
    }

    class SistemaNotificacao {
        +notificar(Notificavel destino, String mensagem) void
    }

    Usuario <|-- Cliente : Herança
    Usuario <|-- Tecnico : Herança
    
    Cliente ..|> Notificavel : Implementa
    Tecnico ..|> Notificavel : Implementa
    
    Cliente "1" -- "*" Chamado : Solicita
    Tecnico "1" -- "*" Chamado : Resolve
    Chamado "1" *-- "1" StatusChamado : Possui
    
    SistemaNotificacao ..> Notificavel : Depende
```

O diagrama de classes ilustra a estrutura do sistema, destacando as relações, tipos de dados e a aplicação prática dos pilares de POO:

- **Herança (Abstração de Usuários):** A classe abstrata `Usuario` serve como base, contendo os atributos comuns (`id`, `nome`, `email`). Dela derivam as classes filhas `Cliente` (que possui o atributo específico `departamento` e o método `abrirChamado`) e `Tecnico` (que possui `especialidade` e o método `atenderChamado`).
- **A Classe Chamado e Associações:** A classe `Chamado` centraliza o negócio. Ela possui uma relação de associação com os envolvidos: armazena um `Cliente` (solicitante) e um `Tecnico` (responsavel). O ciclo de vida do chamado é tipado rigidamente por uma composição com a enumeração `StatusChamado` (`ABERTO`, `EM_ANDAMENTO`, `RESOLVIDO`, `FECHADO`).
- **Polimorfismo e Notificação (Interfaces):** A interface `Notificavel` define o método `enviarNotificacao(String mensagem)`. Tanto `Cliente` quanto `Tecnico` implementam essa interface (indicado pelas setas tracejadas), permitindo que a classe `SistemaNotificacao` envie alertas para qualquer um dos atores de forma genérica e polimórfica através do método `notificar`.

---

## 7 - MANUAL DE USUÁRIO

Ao executar a classe `Main.java`, o sistema exibirá o menu principal no console, aguardando a inserção dos comandos numéricos.

### Passo 1: Cadastro de Usuários
Antes de abrir um chamado, é necessário registrar os atores no sistema para evitar exceções de segurança.

1. **Cadastrar Solicitante (Cliente):**
   * Digite `1` e pressione Enter.
   * Insira o nome (Ex: `Matheus Guilherme`) e o e-mail (Ex: `matheus.gui@gmail.com`).
   * Retorno esperado: `Solicitante cadastrado com sucesso!`

2. **Cadastrar Técnico:**
   * Digite `2` e pressione Enter.
   * Insira o nome (Ex: `Kauê Otsubo`), e-mail (Ex: `k.tsubo@gmail.com`) e selecione a especialidade digitando `1` (Redes).
   * Retorno esperado: `Técnico cadastrado com sucesso com a especialidade Redes!`

### Passo 2: Abertura e Atribuição de Chamados

3. **Abrir um Novo Chamado:**
   * Digite `3` e pressione Enter.
   * Informe o e-mail do solicitante autor (`matheus.gui@gmail.com`), o tipo do problema (`1` para Redes) e a descrição do ocorrido.
   * Retorno esperado: Notificação de sucesso simulada na tela (`[E-mail enviado para matheus.gui@gmail.com]: Seu chamado #1 foi aberto com sucesso!`).

4. **Atribuir Técnico ao Chamado:**
   * Digite `4` e pressione Enter.
   * Informe o ID do chamado (`1`) e o e-mail do técnico responsável (`k.tsubo@gmail.com`).
   * Retorno esperado: O sistema altera o estado do chamado para `EM_ANDAMENTO` e exibe o alerta no painel do técnico.

### Passo 3: Consulta e Relatórios

5. **Mostrar Todos os Chamados:**
   * Digite `6` e pressione Enter.
   * O sistema imprimirá a lista contendo ID, status atual, descrição, autor e técnico designado de cada registro.

6. **Buscar Chamado por ID:**
   * Digite `7` e pressione Enter e informe o ID desejado (`1`).
   * O sistema filtrará e exibirá detalhadamente apenas o chamado correspondente.

### Passo 4: Resolução e Encerramento

7. **Marcar Chamado como Resolvido:**
   * Digite `5` e pressione Enter e informe o ID do chamado (`1`).
   * Retorno esperado: `Chamado finalizado como RESOLVIDO!`

8. **Sair do Sistema:**
   * Digite `0` e pressione Enter para encerrar a execução do programa com segurança.

---

## 8 - VALIDAÇÕES E TRATAMENTOS DE ERROS (CASO DE TESTES)

O sistema conta com um ecossistema robusto de tratamento de exceções para garantir a estabilidade da aplicação contra entradas inválidas:

- **Entrada Inválida no Menu:** Digitar caracteres de texto no menu principal aciona uma captura de `NumberFormatException`. O sistema exibe um aviso de entrada inválida e retorna ao menu sem interromper a execução.
- **Duplicidade de Cadastro:** Tentar registrar dois usuários com o mesmo e-mail dispara uma exceção personalizada de chave já cadastrada, impedindo a duplicidade na memória.
- **Busca por Identificador Inexistente:** Buscar por um ID que não consta no sistema (Ex: `999`) dispara a exceção `ChamadoNaoLocalizadoException`, exibindo uma mensagem limpa de erro em vez de quebrar o software.

## 9 - CONCLUSÃO

Com este projeto, fomos capazes de usar os conteudos ensinados em sala de maneira prática, facilitando a resolução deste trabalho.
O simulador cumpre com os requisitos requeridos pelo professor na disciplina de Orientação a Objetos
