import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//Classe que representa a thread do programador
public class Programador extends Thread{

    //Nome do programador
    private String nome;
    //Máximo de storypoints que pode ter
    int storypoints = 20;
    //Lista de tarefas inacabadas
    List<Tarefa> tarefasList = new ArrayList<>();
    //Flags para estados
    boolean jogando = true;
    boolean programando = false;
    //Contador de tarefas feitas
    int tarefasFeitas = 0;
    //Contador de tempo da tarefa atual
    //Quando contador = storypoints da tarefa, finaliza a tarefa e reseta
    int tempoTrabalhado = 0;
    //Armazena indexação da tarefa atual
    int tarefaAtualIndex = -1;
    //Flag para continuar
    boolean continua = true;

    public Programador(String nome){
        this.nome = nome;
    }

    @Override
    public void run(){
        //Utiliza novas linhas para limpar tela
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        //Se for para continuar
        if(continua){
            //Se possui tarefas inacabadas
            if (tarefasList.size() > 0) {
                //Se não está programando
                if (!programando) {
                    //Guarda indexação da tarefa atual
                    tarefaAtualIndex = tarefasList.size() - 1;
                    while(tarefasList.get(tarefaAtualIndex) == null && tarefaAtualIndex != 0){
                        tarefaAtualIndex--;
                    }
                    //Começa a trabalhar na tarefaAtual, para de jogar
                    //Seta tarefaAtual como sendo feita e fora da espera
                    //Reseta tempo trabalhado
                    System.out.println("[Há tarefas! O programador começa a trabalhar na tarefa "
                            + tarefasList.get(tarefaAtualIndex).getNome() + ".]");
                    jogando = false;
                    programando = true;
                    tarefasList.get(tarefaAtualIndex).setEsperando(false);
                    tarefasList.get(tarefaAtualIndex).setSendoFeita(true);
                    tempoTrabalhado = 0;
                }//Se estiver programando, mas tempotrabalhado = storypoints da tarefa
                else if (tarefaAtualIndex >= 0 &&
                        tarefasList.get(tarefaAtualIndex).getStoryPoints() <= tempoTrabalhado) {
                    //Programador terminou a tarefa, restaura storypoints usados
                    //Seta tarefa como feita e a remove da lista de inacabadas
                    //Adiciona tarefa feita e altera estado do jogador
                    storypoints += tarefasList.get(tarefaAtualIndex).getStoryPoints();
                    tarefasList.get(tarefaAtualIndex).setFeita(true);
                    tarefasList.remove(tarefaAtualIndex);
                    tarefasFeitas++;
                    programando = false;
                    tarefaAtualIndex = -1;
                }
                tempoTrabalhado++;
            }//Se não há tarefas inacabadas
            else {
                //Programador começa a jogar se não estiver
                if (!jogando) {
                    System.out.println("[Sem tarefas! O programador começa a jogar " + gameDecider() + ".]");
                    jogando = true;
                    programando = false;
                }
            }
            //Imprime estado atual do programador e tarefas
            printStatus();
            //Espera três segundos
            try {
                sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //Continua a thread
            run();
        }//Se não for para continuar
        else {
            //Finaliza todas as threads e a thread do programador
            finalizar();
            System.out.print("______________________________________________________\n");
            System.out.print("O programador " + nome + " foi para casa deixando:\n");
            System.out.print(tarefasFeitas + " tarefas terminadas\n");
            System.out.print(tarefasList.size() + " tarefas inacabadas\n");
            System.out.print("Boa noite, "+ nome + "!\n");
            System.out.print("------------------------------------------------------\n");
        }
    }

    private void finalizar(){
        for(int i = 0; i < tarefasList.size(); i++){
            tarefasList.get(i).setContinua(false);
        }
    }

    //Adiciona tarefas com storypoints aleatórios entre 1 e 4 na lista
    //Nomes das tarefas são gerados por tarefas concluídas + index do loop
    //Se não há storypoints o suficiente para a tarefa, a descarta
    //Se storypoints forem esgotados descarta todas as próximas tarefas
    public void addTarefas(int n){
        Random r = new Random();
        int sp, failed = 0;
        for(int i = 0; i < n; i++){
            if(storypoints != 0){
                sp = r.nextInt(3) + 1;
                Tarefa t = new Tarefa("Tarefa" + tarefasFeitas + i, sp);
                if (storypoints - sp > 0) {
                    tarefasList.add(t);
                    storypoints -= t.getStoryPoints();
                    t.start();
                } else failed++;
            } else {
                failed += n-i;
                break;
            }
        }
        System.out.println("[" + (n-failed) + " tarefas foram inseridas com sucesso, " + failed + " não couberam.]");
    }

    //Imprime dados atuais na tela
    private void printStatus(){
        //Utiliza novas linhas para limpar tela
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.print("________________________________________________________\n");
        System.out.print("StoryPoints livres: " + storypoints + "\n");
        //Considera uma tarefa fora da fila se estiver sendo desenvolvida
        System.out.print("Tarefas em fila: " + (programando ? tarefasList.size() - 1 : tarefasList.size()) + "\n");
        System.out.print("Tarefas realizadas: " + tarefasFeitas + "\n");
        if(jogando && !programando){
            System.out.println("O programador " + nome + " está jogando " + gameDecider() + "!");
        } else if (programando && !jogando){
            System.out.println("O programador " + nome + " está trabalhando!");
        }
        System.out.print("Insira quantas tarefas adicionar, ou 0 para encerrar\n");
        System.out.print("--------------------------------------------------------\n");
    }

    //Decide aleatóriamente o jogo que o programador está jogando, só por diversão
    private String gameDecider(){
        Random r = new Random();
        switch(r.nextInt(3)){
            case 1:
                return "pacman";
            case 2:
                return "campo minado";
            default:
                return "paciência";
        }
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getStorypoints() {
        return storypoints;
    }

    public void setStorypoints(int storypoints) {
        this.storypoints = storypoints;
    }

    public List<Tarefa> getTarefasList() {
        return tarefasList;
    }

    public void setTarefasList(List<Tarefa> tarefasList) {
        this.tarefasList = tarefasList;
    }

    public boolean isJogando() {
        return jogando;
    }

    public void setJogando(boolean jogando) {
        this.jogando = jogando;
    }

    public boolean isProgramando() {
        return programando;
    }

    public void setProgramando(boolean programando) {
        this.programando = programando;
    }

    public int getTarefasFeitas() {
        return tarefasFeitas;
    }

    public void setTarefasFeitas(int tarefasFeitas) {
        this.tarefasFeitas = tarefasFeitas;
    }

    public int getTempoTrabalhado() {
        return tempoTrabalhado;
    }

    public void setTempoTrabalhado(int tempoTrabalhado) {
        this.tempoTrabalhado = tempoTrabalhado;
    }

    public boolean isContinua() {
        return continua;
    }

    public void setContinua(boolean continua) {
        this.continua = continua;
    }
}
