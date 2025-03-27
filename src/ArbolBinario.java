import java.util.ArrayList;
import java.util.List;

public class ArbolBinario {
    private Nodo raiz;

    public ArbolBinario() {
        raiz = null;
    }

    public void insertar(int valor) {
        raiz = insertarRec(raiz, valor);
    }

    private Nodo insertarRec(Nodo raiz, int valor) {
        if (raiz == null) {
            raiz = new Nodo(valor);
            return raiz;
        }

        if (valor < raiz.valor) {
            raiz.izquierda = insertarRec(raiz.izquierda, valor);
        } else if (valor > raiz.valor) {
            raiz.derecha = insertarRec(raiz.derecha, valor);
        }

        return raiz;
    }

    public void eliminar(int valor) {
        raiz = eliminarRec(raiz, valor);
    }

    private Nodo eliminarRec(Nodo raiz, int valor) {
        if (raiz == null) return raiz;

        if (valor < raiz.valor) {
            raiz.izquierda = eliminarRec(raiz.izquierda, valor);
        } else if (valor > raiz.valor) {
            raiz.derecha = eliminarRec(raiz.derecha, valor);
        } else {
            if (raiz.izquierda == null) {
                return raiz.derecha;
            } else if (raiz.derecha == null) {
                return raiz.izquierda;
            }

            raiz.valor = minValor(raiz.derecha);
            raiz.derecha = eliminarRec(raiz.derecha, raiz.valor);
        }

        return raiz;
    }

    private int minValor(Nodo raiz) {
        int minv = raiz.valor;
        while (raiz.izquierda != null) {
            minv = raiz.izquierda.valor;
            raiz = raiz.izquierda;
        }
        return minv;
    }

    public Nodo getRaiz() {
        return raiz;
    }

    public List<Integer> preOrden() {
        List<Integer> resultado = new ArrayList<>();
        preOrdenRec(raiz, resultado);
        return resultado;
    }

    private void preOrdenRec(Nodo raiz, List<Integer> resultado) {
        if (raiz != null) {
            resultado.add(raiz.valor);
            preOrdenRec(raiz.izquierda, resultado);
            preOrdenRec(raiz.derecha, resultado);
        }
    }

    public List<Integer> inOrden() {
        List<Integer> resultado = new ArrayList<>();
        inOrdenRec(raiz, resultado);
        return resultado;
    }

    private void inOrdenRec(Nodo raiz, List<Integer> resultado) {
        if (raiz != null) {
            inOrdenRec(raiz.izquierda, resultado);
            resultado.add(raiz.valor);
            inOrdenRec(raiz.derecha, resultado);
        }
    }

    public List<Integer> postOrden() {
        List<Integer> resultado = new ArrayList<>();
        postOrdenRec(raiz, resultado);
        return resultado;
    }

    private void postOrdenRec(Nodo raiz, List<Integer> resultado) {
        if (raiz != null) {
            postOrdenRec(raiz.izquierda, resultado);
            postOrdenRec(raiz.derecha, resultado);
            resultado.add(raiz.valor);
        }
    }

    public Nodo buscar(int valor) {
        return buscarRec(raiz, valor);
    }

    private Nodo buscarRec(Nodo raiz, int valor) {
        if (raiz == null || raiz.valor == valor) {
            return raiz;
        }
        if (valor < raiz.valor) {
            return buscarRec(raiz.izquierda, valor);
        } else {
            return buscarRec(raiz.derecha, valor);
        }
    }

    public int alturaNodo(int valor) {
        return alturaNodoRec(raiz, valor, 1);
    }

    private int alturaNodoRec(Nodo raiz, int valor, int nivelActual) {
        if (raiz == null) {
            return -1;
        }
        if (raiz.valor == valor) {
            return nivelActual;
        }
        int alturaIzquierda = alturaNodoRec(raiz.izquierda, valor, nivelActual + 1);
        if (alturaIzquierda != -1) {
            return alturaIzquierda;
        }
        return alturaNodoRec(raiz.derecha, valor, nivelActual + 1);
    }

    public int contarHijos(int valor) {
        Nodo nodo = buscar(valor);
        if (nodo == null) {
            return -1;
        }
        int contador = 0;
        if (nodo.izquierda != null) contador++;
        if (nodo.derecha != null) contador++;
        return contador;
    }
}