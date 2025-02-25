package Puzzle8;

import java.util.*;

public class Main {

    public static class Resultado {
        String algoritmo;
        int nodosExpandidos;
        long tiempoMs;
        int costoSolucion;
        boolean solucionEncontrada;
        
        public Resultado(String algoritmo, int nodosExpandidos, long tiempoMs, int costoSolucion, boolean solucionEncontrada) {
            this.algoritmo = algoritmo;
            this.nodosExpandidos = nodosExpandidos;
            this.tiempoMs = tiempoMs;
            this.costoSolucion = costoSolucion;
            this.solucionEncontrada = solucionEncontrada;
        }
    }
    
    public static void main(String[] args) {
        int[][] inicio = {
            {1, 2, 3},
            {4, 0, 6},
            {7, 5, 8}
        };
        int[][] solucion = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 0}
        };

        int limite = 10;

        System.out.println("-----Busqueda en Anchura-----");
        Resultado resBA = buscarSolucionAnchura(new Nodo(inicio, 0), solucion);

        System.out.println("\n-----Busqueda en Profundidad-----");
        Resultado resBP = buscarSolucionProfundidad(new Nodo(inicio, 0), solucion);

        System.out.println("\n-----Busqueda de Costo Uniforme-----");
        Resultado resBCU = buscarSolucionCostoUniforme(new Nodo(inicio, 0), solucion);

        System.out.println("\n-----Busqueda en Profundidad Lim-----");
        Resultado resBPL = buscarSolucionProfundidadLimitada(new Nodo(inicio, 0), solucion, limite);
        
        imprimirTablaComparativa(Arrays.asList(resBA, resBP, resBCU, resBPL));
    }
    
    public static Resultado buscarSolucionAnchura(Nodo inicio, int[][] solucion) {
        long inicioTiempo = System.currentTimeMillis();
        int nodosExpandidos = 0;
        ArrayList<Nodo> frontera = new ArrayList<>();
        HashSet<String> visitados = new HashSet<>();
        frontera.add(inicio);
        visitados.add(Arrays.deepToString(inicio.getEstado()));
        int paso = 0;

        while (!frontera.isEmpty()) {
            Nodo revisar = frontera.remove(0);
            paso++;
            System.out.println();
            imprimirEstado(revisar.getEstado());
            nodosExpandidos++;
            if (Arrays.deepEquals(revisar.getEstado(), solucion)) {
                long finTiempo = System.currentTimeMillis();
                System.out.println("Solucion encontrada");
                return new Resultado("Anchura", nodosExpandidos, finTiempo - inicioTiempo, revisar.getCosto(), true);
            }
            for (Nodo hijo : generarHijos(revisar)) {
                String estadoString = Arrays.deepToString(hijo.getEstado());
                if (!visitados.contains(estadoString)) {
                    frontera.add(hijo);
                    visitados.add(estadoString);
                }
            }
        }
        long finTiempo = System.currentTimeMillis();
        System.out.println("No se encontro solucion");
        return new Resultado("Anchura", nodosExpandidos, finTiempo - inicioTiempo, -1, false);
    }
    
    public static Resultado buscarSolucionProfundidad(Nodo inicio, int[][] solucion) {
        long inicioTiempo = System.currentTimeMillis();
        int nodosExpandidos = 0;
        Stack<Nodo> pila = new Stack<>();
        HashSet<String> visitados = new HashSet<>();
        pila.push(inicio);
        visitados.add(Arrays.deepToString(inicio.getEstado()));
        int paso = 0;
        
        while (!pila.isEmpty()) {
            Nodo revisar = pila.pop();
            paso++;
            System.out.println();
            imprimirEstado(revisar.getEstado());
            nodosExpandidos++;
            if (Arrays.deepEquals(revisar.getEstado(), solucion)) {
                long finTiempo = System.currentTimeMillis();
                System.out.println("Solucion encontrada");
                return new Resultado("Profundidad", nodosExpandidos, finTiempo - inicioTiempo, revisar.getCosto(), true);
            }
            ArrayList<Nodo> hijos = generarHijos(revisar);
            Collections.reverse(hijos);
            for (Nodo hijo : hijos) {
                String estadoString = Arrays.deepToString(hijo.getEstado());
                if (!visitados.contains(estadoString)) {
                    pila.push(hijo);
                    visitados.add(estadoString);
                }
            }
        }
        long finTiempo = System.currentTimeMillis();
        System.out.println("No se encontro solucion");
        return new Resultado("Profundidad", nodosExpandidos, finTiempo - inicioTiempo, -1, false);
    }
    
    public static Resultado buscarSolucionCostoUniforme(Nodo inicio, int[][] solucion) {
        long inicioTiempo = System.currentTimeMillis();
        int nodosExpandidos = 0;
        PriorityQueue<Nodo> cola = new PriorityQueue<>(Comparator.comparingInt(Nodo::getCosto));
        HashSet<String> visitados = new HashSet<>();
        cola.add(inicio);
        visitados.add(Arrays.deepToString(inicio.getEstado()));
        int paso = 0;
        
        while (!cola.isEmpty()) {
            Nodo revisar = cola.poll();
            paso++;
            System.out.println();
            imprimirEstado(revisar.getEstado());
            nodosExpandidos++;
            if (Arrays.deepEquals(revisar.getEstado(), solucion)) {
                long finTiempo = System.currentTimeMillis();
                System.out.println("Solucion encontrada");
                return new Resultado("Costo Uniforme", nodosExpandidos, finTiempo - inicioTiempo, revisar.getCosto(), true);
            }
            for (Nodo hijo : generarHijos(revisar)) {
                hijo.setCosto(revisar.getCosto() + 1);
                String estadoString = Arrays.deepToString(hijo.getEstado());
                if (!visitados.contains(estadoString)) {
                    cola.add(hijo);
                    visitados.add(estadoString);
                }
            }
        }
        long finTiempo = System.currentTimeMillis();
        System.out.println("No se encontro solucion");
        return new Resultado("Costo Uniforme", nodosExpandidos, finTiempo - inicioTiempo, -1, false);
    }
    
    public static Resultado buscarSolucionProfundidadLimitada(Nodo inicio, int[][] solucion, int limite) {
        long inicioTiempo = System.currentTimeMillis();
        int[] nodosExpandidos = {0};
        int paso = 0;
        boolean exito = busquedaProfundidadLimitada(inicio, solucion, limite, new HashSet<>(), nodosExpandidos, paso);
        long finTiempo = System.currentTimeMillis();
        if (exito) {
            System.out.println("Solucion encontrada");
            return new Resultado("Profundidad Limitada", nodosExpandidos[0], finTiempo - inicioTiempo, inicio.getCosto(), true);
        } else {
            System.out.println("No se encontro solucion");
            return new Resultado("Profundidad Limitada", nodosExpandidos[0], finTiempo - inicioTiempo, -1, false);
        }
    }
    
    public static boolean busquedaProfundidadLimitada(Nodo nodo, int[][] solucion, int limite, HashSet<String> visitados, int[] contExpandidos, int paso) {
        contExpandidos[0]++;
        paso++;
        System.out.println();
        imprimirEstado(nodo.getEstado());
        if (Arrays.deepEquals(nodo.getEstado(), solucion)) {
            return true;
        }
        if (nodo.getCosto() >= limite) {
            return false;
        }
        visitados.add(Arrays.deepToString(nodo.getEstado()));
        for (Nodo hijo : generarHijos(nodo)) {
            hijo.setCosto(nodo.getCosto() + 1);
            String estadoString = Arrays.deepToString(hijo.getEstado());
            if (!visitados.contains(estadoString)) {
                boolean exito = busquedaProfundidadLimitada(hijo, solucion, limite, visitados, contExpandidos, paso);
                if (exito) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public static ArrayList<Nodo> generarHijos(Nodo nodoActual) {
        ArrayList<Nodo> hijos = new ArrayList<>();
        int[][] estado = nodoActual.getEstado();
        int[] posCero = ubicarPosicionCero(estado);
        int fila = posCero[0], col = posCero[1];
        int[][] movimientos = { {-1,0}, {1,0}, {0,-1}, {0,1} };
        for (int[] mov : movimientos) {
            int nuevaFila = fila + mov[0];
            int nuevaCol = col + mov[1];
            if (nuevaFila >= 0 && nuevaFila < estado.length && nuevaCol >= 0 && nuevaCol < estado[0].length) {
                int[][] nuevoEstado = clonar(estado);
                nuevoEstado[fila][col] = nuevoEstado[nuevaFila][nuevaCol];
                nuevoEstado[nuevaFila][nuevaCol] = 0;
                Nodo nuevoNodo = new Nodo(nuevoEstado, nodoActual.getCosto() + 1);
                nuevoNodo.setPadre(nodoActual);
                hijos.add(nuevoNodo);
            }
        }
        return hijos;
    }
    
    public static int[][] clonar(int[][] estado) {
        int[][] clon = new int[estado.length][estado[0].length];
        for (int i = 0; i < estado.length; i++) {
            for (int j = 0; j < estado[0].length; j++) {
                clon[i][j] = estado[i][j];
            }
        }
        return clon;
    }
    
    public static int[] ubicarPosicionCero(int[][] estado) {
        int[] posicion = new int[2];
        for (int i = 0; i < estado.length; i++) {
            for (int j = 0; j < estado[0].length; j++) {
                if (estado[i][j] == 0) {
                    posicion[0] = i;
                    posicion[1] = j;
                    return posicion;
                }
            }
        }
        return posicion;
    }
    
    public static void imprimirEstado(int[][] estado) {
        for (int i = 0; i < estado.length; i++) {
            for (int j = 0; j < estado[0].length; j++) {
                System.out.print("[" + estado[i][j] + "]");
            }
            System.out.println();
        }
        System.out.println();
    }
    
    public static void imprimirTablaComparativa(List<Resultado> resultados) {
        System.out.println("\n------------------------------------------------------------------------------------------------------------------");
        System.out.println("| Algoritmo de busqueda       | Nodos Expandidos | Tiempo (ms) | Costo Solucion | Solucion |");
        System.out.println("--------------------------------------------------------------------------------------------------------------------");
        for (Resultado res : resultados) {
            System.out.printf("| %-29s | %-16d | %-11d | %-14s | %-8s |\n",
                res.algoritmo,
                res.nodosExpandidos,
                res.tiempoMs,
                (res.costoSolucion >= 0 ? res.costoSolucion : "N/A"),
                (res.solucionEncontrada ? "Si" : "No"));
        }
        System.out.println("--------------------------------------------------------------------------------------------------------------------");
    }
}
