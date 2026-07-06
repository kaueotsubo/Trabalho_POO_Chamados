# Sistema de Chamados HelpDesk

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

## 1 - INTRODUÇÃO DO PROBLEMA E SOLUÇÃO

Em ambientes corporativos ou acadêmicos, a solicitação de suporte técnico muitas vezes ocorre de forma desorganizada (via e-mails perdidos, mensagens informais ou anotações em papel). Isso gera perda de informações, dificuldade em priorizar tarefas críticas, falta de acompanhamento do status do chamado e frustração tanto para quem solicita quanto para quem atende.

---

## 2 - REQUISITOS FUNCIONAIS

- RF01: O sistema deve permitir o cadastro de novos usuários, diferenciando o perfil entre Cliente e Técnico.
- RF02: O sistema deve permitir que um Cliente registre um novo chamado informando a descrição do problema.
- RF03: O sistema deve permitir que um Técnico assuma a responsabilidade pelo atendimento de um chamado.
- RF04: O sistema deve gerenciar e atualizar os status dos chamados (Aberto, Em Andamento, Resolvido, Fechado).
- RF05: O sistema deve acionar uma interface de notificação para alertar os envolvidos quando houver alterações no andamento.
- RF06: O sistema deve possuir uma funcionalidade de busca para consultar usuários e chamados específicos registrados na base.

---

## 3 - CASOS DE USO

![](imgs/usodiagrama.png)

---

## 4 - DIAGRAMA DE CLASSES

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
