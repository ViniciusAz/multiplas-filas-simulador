public class Evento {

    class Node {
      private float tempo;
      private Node next;
      private char tipo; // 'c'=chega, 's' = sai, 't' = transfere
      private int idfila;
      private int destino;

      public Node(float tempo, char tipo, int fila, int dest) {
          this.tempo = tempo;
          next = null;
          this.tipo = tipo;
          idfila = fila;
          destino = dest;
      }
    }

    private Node head;
    private Node tail;
    private int count;

    public Evento() {
        head = null;
        tail = null;
        count = 0;
    }

    public void add(float tempo, char tipo, int id, int dest) {
        Node novo = new Node(tempo, tipo, id, dest);
        Node i = head;
        Node pai = null;
        boolean added = false;

        if (isEmpty()) {
            head = novo;
            tail = novo;
            count++;
        } else {
            // primeiro testa se é possivel colocar na head
            if(novo.tempo < head.tempo) {
                novo.next = head;
                head = novo;
            } else {
                // testa se tem alguem depois da head
                if(head.next != null) {
                    pai = head;
                    i = head.next;
                // se nao existe entao insere e se marca como tail
                } else {
                    head.next = novo;
                    tail = novo;
                    added = true;
                }
                // neste caso vamos iterar ate conseguir adicionar o evento
                while(!added) {
                    // enquanto nao for adicionado, itera e procura um espaco
                    if(novo.tempo < i.tempo) {
                        novo.next = i;
                        pai.next = novo;
                        added = true;
                    } else {
                      // testa se tem alguem depois do i
                        if(i.next != null) {
                          pai = i;
                          i = i.next;
                        // senao marca como tail e insere no final da lista
                        } else {
                            i.next = novo;
                            tail = novo;
                            added = true;
                        }
                    }
                }
            }
            count++;
        } //end else (if empty)
        // imprime();
    } //end add

    public void imprime() {
        String x = "Próximos Eventos : ";
        Node i = head;
        boolean fim = false;
        while(!fim) {
          if(i.tipo == 'c') x += "[C(" + i.idfila + ")=";
          else if(i.tipo == 't') x += "[T(" + i.idfila + ")=";
          else  x += "[S(" + i.idfila + ")=";
          x+= i.tempo + "], ";
          if(i.next == null) fim = true;
          else i = i.next;
        }

        System.out.println(x);
    }

    public boolean isEmpty() {
        return (count == 0);
    }

    public char tipo() {
        return head.tipo;
    }

    public int idfila() {
      return head.idfila;
    }

    public int destino() {
      return head.destino;
    }

    public float executa() {
        float temp = head.tempo;
        if(head != tail) {
            head = head.next;
        } else {
            head = null;
        }
        count--;
        return temp;
    }
}
