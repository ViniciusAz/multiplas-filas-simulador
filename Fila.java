public class Fila{
    private int id;
    private float atMax; // at = tempo de atendimento
    private float atMin;
    private float chMin; // ch = tempo de chegada
    private float chMax;
    private int lotacao;
    private int capacidade;
    private int numServers;
    private int desistencia;
    // private float[] log;

    public Fila(int id, float chMin, float chMax, float atMin, float atMax, int servers, int cap) {
        this.id = id;
        this.atMin = atMin;
        this.atMax = atMin;
        this.chMin = chMin;
        this.chMax = chMax;
        capacidade = cap;
        numServers = servers;
        lotacao = 0;
        desistencia = 0;
        // log[] = new float[capacidade+1];
    }
    public Fila(int id, float atMin, float atMax, int servers, int cap) {
        this.id = id;
        this.atMin = atMin;
        this.atMax = atMin;
        capacidade = cap;
        numServers = servers;
        lotacao = 0;
        desistencia = 0;
        // log[] = new float[capacidade+1];
    }

    public void entra(float tA, float tB) {
      //log
      lotacao++;
    }

    public void sai(float tA, float tB) {
      lotacao--;
      //log
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
