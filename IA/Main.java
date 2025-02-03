package IA;

public class Main {

	public static void main(String[] args) 
	{
		Arbol arbol = new Arbol(new Nodo(8));
		
		arbol.agregar(3);
		arbol.agregar(11);
		arbol.agregar(5);
		arbol.agregar(15);
		arbol.agregar(13);
		arbol.agregar(17);
		arbol.agregar(29);
		arbol.agregar(10);
		
		arbol.imprimeArbol(arbol.getRaiz());
		
		
		System.out.println("FIN...");
	}

}
