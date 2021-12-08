
//Classe que representa a thread de tarefa
public class Tarefa extends Thread{

    //Nome da tarefa
    private String nome;
    //Quantidade de storypoints que toma
    private int storyPoints;
    //Flags de estado
    private boolean esperando = true;
    private boolean sendoFeita = false;
    private boolean feita = false;
    //Flag para continuar
    private boolean continua = true;

    public Tarefa(String nome, int storyPoints){
        this.nome = nome;
        this.storyPoints = storyPoints;
    }

    //Se a tarefa foi feita, imediatamente termina a thread
    //Senão, verifica se está esperando ou sendo feita
    //Imprime se está sendo feita mas não se está esperando para evitar poluição do console
    //Espera um segundo para continuar
    @Override
    public void run(){
        if(continua){
            if (feita) {
                removerTarefa();
            } else if (esperando || sendoFeita) {
                if (sendoFeita) System.out.println("[A tarefa " + nome + " está sendo feita!]");
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                run();
            }
        }
    }

    public void removerTarefa(){
        sendoFeita = false;
        esperando = false;
        continua = false;
        if(feita){
            System.out.println("[Tarefa " + nome + " concluída!]");
        } else {
            System.out.println("[Tarefa " + nome + " dada a outro programador!]");
        }
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getStoryPoints() {
        return storyPoints;
    }

    public void setStoryPoints(int storyPoints) {
        this.storyPoints = storyPoints;
    }

    public boolean isEsperando() {
        return esperando;
    }

    public void setEsperando(boolean esperando) {
        this.esperando = esperando;
    }

    public boolean isSendoFeita() {
        return sendoFeita;
    }

    public void setSendoFeita(boolean sendoFeita) {
        this.sendoFeita = sendoFeita;
    }

    public boolean isFeita() {
        return feita;
    }

    public void setFeita(boolean feita) {
        this.feita = feita;
    }

    public boolean isContinua() {
        return continua;
    }

    public void setContinua(boolean continua) {
        this.continua = continua;
    }
}
