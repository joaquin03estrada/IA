package IA;

public class Arbol 
{
	private Nodo raiz;
	
	public Arbol(Nodo raiz)
	{
		this.raiz=raiz;
	}

	public Nodo getRaiz() {
		return raiz;
	}

	public void setRaiz(Nodo raiz) {
		this.raiz = raiz;
	}
	
	private void add(Nodo n,Nodo actual)
	{
		if(actual.getDato()>n.getDato())
		{
			if(actual.getIzquierdo()!=null)
			{
				add(n,actual.getIzquierdo());
			}
			else
			{
				actual.setIzquierdo(n);
			}
		}
		else
		{
			if(actual.getDerecho()!=null)
			{
				add(n,actual.getDerecho());
			}
			else
			{
				actual.setDerecho(n);
			}
		}
	}
	
	public void agregar(Nodo n)
	{
		if(raiz==null)
		{
			raiz=n;
		}
		else
		{
			add(n,raiz);
		}
		
	}
	public void agregar(int dato)
	{
		agregar(new Nodo(dato));
	}
	
	public void imprimeArbol(Nodo n)
	{
		if(n!=null)
		{
			imprimeArbol(n.getIzquierdo());
			System.out.println(n.getDato());
			imprimeArbol(n.getDerecho());
			
		}
	}
	
	private  Nodo buscar(int dato)
	{
		Nodo actual = raiz;
		while(actual!=null && actual.getDato()!=dato)
		{
			if(actual.getDato()>dato)
			{
				actual=actual.getIzquierdo();
			}
			else
			{
				actual=actual.getDerecho();
			}
		}
		
		return actual;
	}
	
	
}
