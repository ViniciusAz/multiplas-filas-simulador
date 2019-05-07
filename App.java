public class App {

  public static void main(String[] args) {

    int limiteIteracoes = 50;
    float tempoInicial = 2.0f;
    int quantidadeFilas = 3;

    Simulador s = new Simulador(tempoInicial, quantidadeFilas);

    //novaFila = chegada min, chegada max, atendimento min, atendimento max, servidores, capacidade
    s.novaFila(1,2,2,3,2,3);
    s.novaFila(4,5,10,13,3,7);
    //novaFila = atendimento min, atendimento max, servidores, capacidade
    s.novaFila(20,25,1,2);

    //relaciona = fila origem, fila destino, probabilidade de 0 a 1
    s.relaciona(0,1,0.7f); //Fila1 transfere para Fila2 em 70% das vezes
    s.relaciona(0,2,0.3f);

    s.relaciona(1,3,1); //Fila2 faz a saida em 100% das vezes
    s.relaciona(2,3,1); //Fila2 faz a saida em 100% das vezes

    //Chegadas
    s.relaciona(3,0,1); //Chega na Fila1
    s.relaciona(3,1,1); //Chega na Fila2

    // Inicia a aplicacao
    s.start();

    // executa as iteracoes
    for (int i = 0; i < limiteIteracoes; i++)
      s.next();

    // Encerra a execucao e imprime a saida
    s.stop();

  }
}
