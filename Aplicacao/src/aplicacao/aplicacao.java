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
import java.util.Scanner;

/**
 *
 * @author josea
 */
public class aplicacao {

    public static void main(String[] args) throws IOException, Exception {
               
        System.out.println(KeyStorage.getPublicKey("123456", "servicePublic.txt"));
        
        /*Scanner sc = new Scanner(System.in);
        System.out.println("Hello");

        System.out.println("Qual o nome da app?");
        String nomeApp = sc.nextLine();
        System.out.println("Qual a versão?");
        String versao = sc.nextLine();
        Controller c = new Controller(nomeApp, versao);

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
                    try {
                    if (c.isRegistered()) {
                        System.out.println("Esta registado!");
                    } else {
                        System.out.println("Nao esta registado!");
                    }
                } catch (Exception ex) {
                    System.out.println("Problema no ficheiro");
                }
                break;
                case "3":
                    c.showLicenseInfo();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Opção invalida.");
            }
        }*/
    }
}
