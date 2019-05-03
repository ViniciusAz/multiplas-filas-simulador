public class CongruenteLinear {
  public float aleatorio(int a, int m, int c, int xi) {
    float aux = (a * xi) + c;
    float f = aux % m;
    return f/m;
  }
}
