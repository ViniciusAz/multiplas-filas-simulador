public class Fila{
    private int id;
    private float atMax; // at = tempo de atendimento
    private float atMin;
    private float chMin; // ch = tempo de chegada
    private float chMax;
    private int lotacao;
    private int capacidade;
    private int numServers;
    private int desistencia; // contabilizador de desistencias
    private float[] log; // contebilizador de resultados da simulacao
    private float tempoLocal; // tempo local de mudanca de estados da fila (controle resultados)

    public Fila(int id, float tempoInicial, float chMin, float chMax, float atMin, float atMax, int servers, int cap) {
        this.id = id;
        this.atMin = atMin;
        this.atMax = atMin;
        this.chMin = chMin;
        this.chMax = chMax;
        capacidade = cap;
        numServers = servers;
        lotacao = 0;
        desistencia = 0;
        log = new float[capacidade+1];
        tempoLocal = tempoInicial;

    }
    public Fila(int id, float tempoInicial, float atMin, float atMax, int servers, int cap) {
        this.id = id;
        this.atMin = atMin;
        this.atMax = atMin;
        capacidade = cap;
        numServers = servers;
        lotacao = 0;
        desistencia = 0;
        log = new float[capacidade+1];
        tempoLocal = tempoInicial;
    }

    public void entra(float tempoGlobal) {
      log[lotacao] += (tempoGlobal - tempoLocal);
      tempoLocal = tempoGlobal;
      // System.out.println("Total = " + log[lotacao]);
      lotacao++;
    }

    public void sai(float tempoGlobal) {
      log[lotacao] += (tempoGlobal - tempoLocal);
      tempoLocal = tempoGlobal;
      // System.out.println("Total = " + log[lotacao]);
      lotacao--;
    }

    public void imprimeStatistics(float tI, float tF) {
      // System.out.println("Tempo total = " + (tF - tI));
      for (int i = 0; i < capacidade+1; i++) {
        System.out.println("Fila [" + id + "] esteve " + String.format("%.2f", (100.0f*log[i]/(tF-tI))) + "% do tempo com lotação " +i+"/"+capacidade);
      }
    }

    public boolean isEmpty() {
      return (lotacao == 0);
    }

    public boolean isFull() {
      return (lotacao == capacidade);
    }

    public int lotacao() {
      return lotacao;
    }
    public int capacidade() {
      return capacidade;
    }
    public int servers() {
      return numServers;
    }

    public int id() { return id; }
    public float chMin() { return chMin; }
    public float chMax() { return chMax; }
    public float atMin() { return atMin; }
    public float atMax() { return atMax; }

    public void desistiu() {
      desistencia++;
    }

    public int numDesistencias() {
      return desistencia;
    }
    // public void logger()
}
