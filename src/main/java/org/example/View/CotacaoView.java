package org.example.View;

import org.example.Controller.CotacaoController;
import org.example.Model.Cotacao;
import java.util.Scanner;

public class CotacaoView {
    private final Scanner scanner;
    private final CotacaoController controller;

    public CotacaoView() {
        this.scanner = new Scanner(System.in);
        this.controller = new CotacaoController();
    }

    public void iniciar() {
        exibirCabecalho();

        while (true) {
            exibirMenu();
            String opcao = scanner.nextLine().trim();

            switch (opcao) {
                case "1":
                    consultarCotacao();
                    break;
                case "2":
                    exibirMoedasDisponiveis();
                    break;
                case "3":
                    exibirSobre();
                    break;
                case "0":
                    exibirRodape();
                    scanner.close();
                    return;
                default:
                    System.out.println("\n Opção inválida! Tente novamente.\n");
            }
        }
    }

    private void exibirCabecalho() {
        System.out.println("\n╔════════════════════════════════════════════════╗");
        System.out.println("║     SISTEMA DE COTAÇÃO DE MOEDAS - MVC        ║");
        System.out.println("╚════════════════════════════════════════════════╝");
    }

    private void exibirMenu() {
        System.out.println("\n┌────────────────────────────────────────────────┐");
        System.out.println("│               MENU PRINCIPAL                   │");
        System.out.println("├────────────────────────────────────────────────┤");
        System.out.println("│ 1 - Consultar Cotação                          │");
        System.out.println("│ 2 - Listar Moedas Disponíveis                  │");
        System.out.println("│ 3 - Sobre o Sistema                            │");
        System.out.println("│ 0 - Sair                                       │");
        System.out.println("└────────────────────────────────────────────────┘");
        System.out.print("\nEscolha uma opção: ");
    }

    private void consultarCotacao() {
        System.out.println("\n┌─── CONSULTAR COTAÇÃO ──────────────────────────┐");
        System.out.print("│ Digite o código da moeda (ex: USD, EUR): ");
        String codigo = scanner.nextLine().trim().toUpperCase();

        if (codigo.isEmpty()) {
            System.out.println("│  Código não pode estar vazio!");
            System.out.println("└────────────────────────────────────────────────┘");
            return;
        }

        System.out.println("│ 🔄 Buscando cotação...");

        Cotacao cotacao = controller.buscarCotacao(codigo);

        if (cotacao.isSucesso()) {
            exibirCotacao(cotacao);
        } else {
            System.out.println("│");
            System.out.println("│  " + cotacao.getMensagemErro());
            System.out.println("└────────────────────────────────────────────────┘");
        }
    }

    private void exibirCotacao(Cotacao cotacao) {
        System.out.println("└────────────────────────────────────────────────┘");
        System.out.println("\n╔════════════════════════════════════════════════╗");
        System.out.println("║              COTAÇÃO ATUAL                     ║");
        System.out.println("╠════════════════════════════════════════════════╣");

        String[] linhas = cotacao.getMoeda().toString().split("\n");
        for (String linha : linhas) {
            System.out.printf("║ %-46s ║%n", linha);
        }

        System.out.println("╚════════════════════════════════════════════════╝");
    }

    private void exibirMoedasDisponiveis() {
        System.out.println("\n╔════════════════════════════════════════════════╗");
        System.out.println("║          MOEDAS DISPONÍVEIS                    ║");
        System.out.println("╠════════════════════════════════════════════════╣");

        String[] moedas = controller.getMoedasDisponiveis();
        for (String moeda : moedas) {
            System.out.printf("║ • %-44s ║%n", moeda);
        }

        System.out.println("╚════════════════════════════════════════════════╝");
    }

    private void exibirSobre() {
        System.out.println("\n╔════════════════════════════════════════════════╗");
        System.out.println("║              SOBRE O SISTEMA                   ║");
        System.out.println("╠════════════════════════════════════════════════╣");
        System.out.println("║ Sistema desenvolvido em arquitetura MVC        ║");
        System.out.println("║ Utiliza a API AwesomeAPI para cotações        ║");
        System.out.println("║                                                ║");
        System.out.println("║ Estrutura do Projeto:                          ║");
        System.out.println("║ • Model: Classes de domínio (Moeda, Cotacao)  ║");
        System.out.println("║ • View: Interface console                      ║");
        System.out.println("║ • Controller: Lógica HTTP e parsing JSON       ║");
        System.out.println("╚════════════════════════════════════════════════╝");
    }

    private void exibirRodape() {
        System.out.println("\n╔════════════════════════════════════════════════╗");
        System.out.println("║     Obrigado por usar o sistema! Até logo!     ║");
        System.out.println("╚════════════════════════════════════════════════╝\n");
    }
}