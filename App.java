public class App {

  public static void main(String[] args) {
    Simulador s = new Simulador(2, 2);

    s.novaFila(1,2,2,3,2,3);
    s.novaFila(1,2,4,5,3,5);

    s.relaciona(0,1,1);

    s.relaciona(1,0,0.4f);
    s.relaciona(1,2,0.6f);
    //Entradas
    s.relaciona(2,0,1);
    s.relaciona(2,1,1);

    //
    // s.relaciona(0,1,0.8f);


    s.start();

    int limite = 0;
    while (limite < 225) {
        s.next();
        limite++;
    }

    s.numDesistencias();

    System.out.println("Gonzalez");
  }
}
