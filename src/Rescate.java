import java.util.Scanner;

class Rescate {

    static final int CELDA_DESCONOCIDA = 0;
    static final int CELDA_NAUFRAGO = 1;
    static final int CELDA_AGUA = 2;
    static final int NUMERO_SOLDADOS = 16;
    static final int MAXIMO_TURNOS = 50;

    static final String DESCONOCIDA_VISTA = " . ";
    static final String NAUFRAGO_VISTA = "\\O/";
    static final String AGUA_VISTA = " ~ ";

    static final String[] JUEGO_VISTA = { DESCONOCIDA_VISTA, NAUFRAGO_VISTA, AGUA_VISTA };

    static final int FILA = 0;
    static final int COLUMNA = 1;

    public static void main(String[] args) {

        int[][] elMar = new int[8][8];
        int[][] elMarVista = new int[8][8];

        int[] coordenada = { 0, 0 };

        inicializaMapa(elMar, CELDA_AGUA);
        inicializaMapa(elMarVista, CELDA_DESCONOCIDA);
        ubicaSoldados(elMar, NUMERO_SOLDADOS);

        int turno = 0;
        int rescatados = 0;
        boolean rescatando = true;

        while (rescatando) {
            turno++;
            imprimeMapa(elMarVista);
            System.out.println("Estas en el turno " + turno);
            System.out.println("Has rescatado " + rescatados);

            pideCoordenada(elMar, coordenada);
            actualizaJuego(elMar, elMarVista, coordenada);
            rescatados = cuentaRescatados(elMarVista);

            if (turno >= MAXIMO_TURNOS || rescatados == NUMERO_SOLDADOS) {
                rescatando = false;
            }
        }
    }

    private static void imprimeMapa(int[][] mapa) {
        System.out.print(" +");
        for (int columna = 0; columna < 8; columna++) {
            System.out.print("-" + (char) ('a' + columna) + "-");
        }
        System.out.println();
        for (int fila = 0; fila < mapa.length; fila++) {
            System.out.print((fila + 1) + "|");
            for (int columna = 0; columna < mapa[fila].length; columna++) {
                System.out.print(JUEGO_VISTA[mapa[fila][columna]]);
            }
            System.out.println();
        }
    }

    private static void actualizaJuego(int[][] elMar, int[][] elMarVista, int[] coordenada) {
        int filaElegida = coordenada[FILA];
        int columnaElegida = coordenada[COLUMNA];
        elMarVista[filaElegida][columnaElegida] = elMar[filaElegida][columnaElegida];
    }

    private static int cuentaRescatados(int[][] elMarVista) {
        int rescatados = 0;
        for (int[] fila : elMarVista) {
            for (int elemento : fila) {
                if (elemento == CELDA_NAUFRAGO) {
                    rescatados++;
                }
            }
        }
        return rescatados;
    }

    private static void pideCoordenada(int[][] elMar, int[] coordenada) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Ingrese coordenada: ");
        String coordenadaUsuario = scanner.next();

        int columnaElegida = (int) coordenadaUsuario.charAt(0) - 97;
        int filaElegida = coordenadaUsuario.charAt(1) - '0' - 1;

        coordenada[0] = filaElegida;
        coordenada[1] = columnaElegida;
    }

    private static void ubicaSoldados(int[][] mapa, int maximoSoldados) {
        for (int soldado = 0; soldado <= maximoSoldados; soldado++) {
            boolean soldadoColocado = false;
            do {
                int fila = (int) (Math.random() * 7) + 1;
                int columna = (int) (Math.random() * 7) + 1;
                soldadoColocado = mapa[fila][columna] == 1 ? false : true;
                mapa[fila][columna] = CELDA_NAUFRAGO;
            } while (!soldadoColocado);
        }
    }

    private static void inicializaMapa(int[][] mapa, int valor) {
        for (int fila = 0; fila < mapa.length; fila++) {
            for (int columna = 0; columna < mapa[fila].length; columna++) {
                mapa[fila][columna] = valor;
            }
        }
    }

}