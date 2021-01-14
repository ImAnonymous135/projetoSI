/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacao;

import controller.Controller;
import controller.License;
import controller.encryptions.KeyStorage;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Scanner;

/**
 *
 * @author josea
 */
public class aplicacao {
    public static void main(String[] args) throws IOException, Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println("Hello");
        Controller c = new Controller("aplicacao", "1");
        while (true) {
            System.out.println("Escolha uma opção:");
            System.out.println("1 - Criar pedido de licença.");
            System.out.println("2 - Verificar se está registado.");
            System.out.println("3 - Mostrar informação de licença.");
            System.out.println("0 - Sair.");
            String opcao = sc.nextLine();
            switch (opcao) {
                case "1":
                    c.startRegistration();
                    break;
                case "2":
                    if (c.isRegistered()) {
                        System.out.println("Esta registado!");
                    } else {
                        System.out.println("Nao esta registado!");
                    }
                    break;
                case "3":
                    if (c.isRegistered()) {
                        c.showLicenseInfo();
                    } else {
                        System.out.println("Nao esta registado!");
                    }
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Opção invalida.");
            }
        }
    }

}
