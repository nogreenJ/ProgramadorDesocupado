import java.util.Scanner;

/**
 * Autor: João Luiz Daré Pinto
 * Instituição: IFSUL Campus Passo Fundo
 * Disciplina: Sistemas Operacionais II
 */
public class Main {
    public static void main(String[] args){
        int n = 1;
        Scanner s = new Scanner(System.in);
        System.out.println("\nInsira o nome do programador: ");
        Programador programador = new Programador(s.nextLine());
        programador.start();
        s.nextLine();
        while(n != 0){
            try{
                n = s.nextInt();
            }catch(Exception e){
                e.printStackTrace();
            }
            if(n != 0) programador.addTarefas(n);
        }
        programador.setContinua(false);
    }
}
