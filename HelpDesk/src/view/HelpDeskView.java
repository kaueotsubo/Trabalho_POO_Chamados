package view;

import control.Controller;
import java.util.Scanner;

public class HelpDeskView {

    private final Controller control;
    private final Scanner teclado;

    public HelpDeskView(Controller control) {
        this.control = control;
        this.teclado = new Scanner(System.in);
    }

    public void iniciar() {
        byte opcao;
        do {
            System.out.print("\nPressione enter para continuar. ");
            teclado.nextLine();
            exibirMenu();
            try {
                opcao = Byte.parseByte(teclado.nextLine());
                processarOpcao(opcao);
            } catch (NumberFormatException e) {
                opcao = -1;
                System.out.println("Opção inválida! Digite um número inteiro menor do que 128");
            } catch (RuntimeException e) {
                opcao = -1;
                System.out.println(e.getMessage());
            }
        } while (opcao != 0);
        teclado.close();
    }

    private void processarOpcao(byte opcao) {
        String nome, email, titulo, descricao;
        int idChamado = 0;

        switch (opcao) {
            case 1:
                System.out.print("Nome do Solicitante: ");
                nome = teclado.nextLine();
                System.out.print("Email: ");
                email = lerEmailValido();
                control.cadastrarSolicitante(nome, email);
                System.out.println("Solicitante cadastrado com sucesso!");
                break;

            case 2:
                System.out.print("Nome do Técnico: ");
                nome = teclado.nextLine();
                System.out.print("Email: ");
                email = lerEmailValido();

                // Menu numérico para a especialidade do Técnico
                System.out.println("Selecione a Especialidade do Técnico: ");
                System.out.println("1 - Redes");
                System.out.println("2 - Hardware");
                System.out.println("3 - Software");
                System.out.print("Opção: ");

                String espSelecionada = "";
                try {
                    byte opcaoEsp = Byte.parseByte(teclado.nextLine());
                    if (opcaoEsp == 1) {
                        espSelecionada = "Redes";
                    } else if (opcaoEsp == 2) {
                        espSelecionada = "Hardware";
                    } else if (opcaoEsp == 3) {
                        espSelecionada = "Software";
                    } else {
                        throw new IllegalArgumentException("Opção de especialidade inválida! Escolha 1, 2 ou 3.");
                    }
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Entrada inválida! Digite apenas o número correspondente à especialidade.");
                }

                // Envia a string limpa ("Redes", "Hardware" ou "Software") para o controller
                control.cadastrarTecnico(nome, email, espSelecionada);
                System.out.println("Técnico cadastrado com sucesso com a especialidade " + espSelecionada + "!");
                break;

            case 3:
                System.out.print("Email do Solicitante autor: ");
                email = teclado.nextLine();

                // Menu numérico para o tipo de problema
                System.out.println("Selecione o Tipo do Problema: ");
                System.out.println("1 - Redes");
                System.out.println("2 - Hardware");
                System.out.println("3 - Software");
                System.out.print("Opção: ");

                String tipoSelecionado = "";
                try {
                    byte opcaoTipo = Byte.parseByte(teclado.nextLine());
                    if (opcaoTipo == 1) {
                        tipoSelecionado = "Redes";
                    } else if (opcaoTipo == 2) {
                        tipoSelecionado = "Hardware";
                    } else if (opcaoTipo == 3) {
                        tipoSelecionado = "Software";
                    } else {
                        throw new IllegalArgumentException("Opção de tipo inválida! Escolha 1, 2 ou 3.");
                    }
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Entrada inválida! Digite apenas o número correspondente ao tipo.");
                }

                System.out.print("Descrição detalhada do Problema: ");
                descricao = teclado.nextLine();

                // Passa o tipo selecionado (Redes, Hardware ou Software) como o Título do chamado
                control.abrirChamado(email, tipoSelecionado, descricao);
                System.out.println("Chamado de " + tipoSelecionado + " aberto com sucesso!");
                break;

            case 4:
                idChamado = lerIdChamado();
                System.out.print("Email do Técnico que vai assumir: ");
                email = teclado.nextLine();

                control.atribuirTecnicoAoChamado(idChamado, email);
                System.out.println("Técnico atribuído com sucesso!");
                break;

            case 5:
                idChamado = lerIdChamado();
                control.resolverChamado(idChamado);
                System.out.println("Chamado finalizado como RESOLVIDO!");
                break;

            case 6:
                System.out.println(control.getRelatorioChamados());
                break;

            case 7:
                idChamado = lerIdChamado();
                System.out.println(control.getRelatorioChamado(idChamado));
                break;

            case 0:
                System.out.println("Você escolheu sair do sistema!");
                break;

            default:
                System.out.println("Opção inválida!");
                break;
        }
    }

    private String lerEmailValido() {
        String input = teclado.nextLine();
        if (!input.contains("@")) {
            throw new IllegalArgumentException("E-mail inválido! Deve conter '@'.");
        }
        return input;
    }

    private int lerIdChamado() {
        boolean leuId = false;
        int id = 0;
        while (!leuId) {
            try {
                System.out.print("Informe o ID do Chamado: ");
                id = Integer.parseInt(teclado.nextLine());
                leuId = true;
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido! Digite um número inteiro.");
            }
        }
        return id;
    }

    private void exibirMenu() {
        System.out.println("\n=== HELP DESK ===");
        System.out.println("1- Cadastrar Solicitante.");
        System.out.println("2- Cadastrar Técnico.");
        System.out.println("3- Abrir um Novo Chamado.");
        System.out.println("4- Atribuir Técnico a um Chamado.");
        System.out.println("5- Marcar Chamado como Resolvido.");
        System.out.println("6- Mostrar todos os chamados.");
        System.out.println("7- Buscar chamado por ID.");
        System.out.println("0- Sair do sistema.");
        System.out.print("Opção: ");
    }
}