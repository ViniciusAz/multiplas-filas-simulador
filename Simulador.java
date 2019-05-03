import java.util.Random;

public class Simulador {

  private float tempoInit;
  private float tempo;

  private Evento agenda = new Evento();
  private Fila[] filas;
  private int numFilas;
  private int fid;
  private float[][] relacao;

  public Simulador(float tempo, int numFilas) {
    tempoInit = tempo;
    this.tempo = tempo;
    filas = new Fila[numFilas];
    this.numFilas = numFilas;
    fid = 0;
    relacao = new float[numFilas+1][numFilas+1];
  }

  public void novaFila(float chMin, float chMax, float atMin, float atMax, int servers, int cap) {
    filas[fid] = new Fila(fid, chMin, chMax, atMin, atMax, servers, cap);
    fid++;
  }

  public void novaFila(float atMin, float atMax, int servers, int cap) {
    filas[fid] = new Fila(fid, atMin, atMax, servers, cap);
    fid++;
  }

  public void relaciona(int f1, int f2, float p) {
    relacao[f1][f2] = p;
  }

  public void start() {
    System.out.println("Iniciando simulação no tempo " + tempoInit);
    for (int i = 0; i < numFilas; i++) {
      if (relacao[numFilas][i] == 1) {
        agendaChegada(tempo, filas[i]);
      }
    }
    next();
  }

  public void next() {
    char etipo = agenda.tipo();
    int idfila = agenda.idfila();
    System.out.println(tempo + " Fila [" + idfila + "]: " + filas[idfila].lotacao() + "/" + filas[idfila].capacidade());
    int filadest = 0;
    if (etipo == 't') {
      filadest = agenda.destino();
    }
    float etempo = agenda.executa();

    if(etipo == 'c') chegada(etempo, idfila);
    else if (etipo == 't') tranferencia(etempo, idfila, filadest);
    else saida(etempo, idfila);
  }

  public void chegada(float t, int f) {
    System.out.println("Chegada : " + t);
    // contabiliza tempo
    float tempoAntigo = tempo;
    tempo = t;
    if(!filas[f].isFull()) { // se FILA nao esta cheia
      filas[f].entra(tempoAntigo, tempo); //  FILA++
      if(filas[f].lotacao() <= filas[f].servers()) { //  se FILA <= numServers
        //calcula probabilidade para sair ou transferir se for possivel
        // if (relacao[f][numFilas] == 1) {
          float prob = 0;
          float aleatorio = random();
          for (int i = 0; i <= numFilas; i++) {
System.out.println("R ["+f+"]["+i+"] = " +relacao[f][i] + " aleatorio " + aleatorio);
            if (relacao[f][i] > 0) {
              if (aleatorio <= relacao[f][i]+prob) {
                if (i == numFilas) agendaSaida(tempo, filas[f]);
                else agendaTransferencia(tempo, filas[f], i);
                break;
              } else {
                prob += relacao[f][i];
              }
            }
          } //for
        // } //if relacao[f][numFilas] == 1
      } //if lotacao <=servers
    } else {
      filas[f].desistiu();
    }
    agendaChegada(tempo, filas[f]);
  }

  public void tranferencia(float t, int f, int d) {
    System.out.println("Tranferencia : " + t);
    // contabiliza tempo
    float tempoAntigo = tempo;
    tempo = t;

    filas[f].sai(tempoAntigo, tempo);
    if (filas[f].lotacao() >= filas[f].servers()) {
      if (relacao[f][numFilas] == 1) {
        float prob = 0;
        float aleatorio = random();
        for (int i = 0; i <= numFilas; i++) {
System.out.println("R ["+f+"]["+i+"] = " +relacao[f][i] + " aleatorio " + aleatorio);
          if (relacao[f][i] > 0) {
            if (aleatorio <= relacao[f][i]+prob) {
              if (i == numFilas) agendaSaida(tempo, filas[f]);
              else agendaTransferencia(tempo, filas[f], i);
              break;
            } else {
              prob += relacao[f][i];
            }
          }
        } //for
      } //if relacao[f][numFilas] == 1
    }
    if(!filas[d].isFull()) { // se FILA nao esta cheia
      filas[d].entra(tempoAntigo, tempo);
      if (filas[d].lotacao() <= filas[d].servers() ) {
        if (relacao[d][numFilas] == 1) {
          float prob = 0;
          float aleatorio = random();
          for (int i = 0; i <= numFilas; i++) {
  System.out.println("R ["+d+"]["+i+"] = " +relacao[d][i] + " aleatorio " + aleatorio);
            if (relacao[d][i] > 0) {
              if (aleatorio <= relacao[d][i]+prob) {
                if (i == numFilas) agendaSaida(tempo, filas[d]);
                else agendaTransferencia(tempo, filas[d], i);
                break;
              } else {
                prob += relacao[d][i];
              }
            }
          } //for
        } //if relacao[f][numFilas] == 1
      }
    } else {
      filas[d].desistiu();
    }
  }

  public void saida(float t, int f) {
    System.out.println("Saida : " + t);
    float tempoAntigo = tempo;
    tempo = t; //  contabiliza tempo
    filas[f].sai(tempoAntigo, tempo); //  FILA ––
System.out.println("SAIU CORRENDO = " + f);
    if (filas[f].lotacao() >= filas[f].servers()) { //  se FILA >= numServers
      agendaSaida(tempo, filas[f]);
    }
  }

  public float calcula(float max, float min) {
    // Random r = new Random();
    // return ((max - min) * (r.nextInt(10000)/10000.0f) + min);
    return ((max - min) * (aleatorio(3,2,7,0.0001f)) + min);
  }

  private float random() {
    Random r = new Random();
    return r.nextInt(10000)/10000.0f;
  }

  public void agendaChegada(float t, Fila f) {
    t += calcula(f.chMin(), f.chMax());
    System.out.println("Agenda Chegada : " + t);
    agenda.add(t, 'c', f.id(), -1);
  }

  public void agendaTransferencia(float t, Fila f, int destino) {
    t += calcula(f.atMin(), f.atMax());
    System.out.println("Agenda Transferencia : " + t);
    agenda.add(t, 't', f.id(), destino);
  }

  public void agendaSaida(float t, Fila f) {
    t += calcula(f.atMin(), f.atMax());
    System.out.println("Agenda Saida : " + t);
    agenda.add(t, 's', f.id(), -1);
  }

  public void numDesistencias() {
    for (int i = 0; i < numFilas; i++) {
      System.out.println("Fila " + i + " : " + filas[i].numDesistencias());
    }
  }
  public float aleatorio(int a, int m, int c, float xi) {
    float aux = (a * xi) + c;
    float f = aux % m;
    return f/m;
  }
}
