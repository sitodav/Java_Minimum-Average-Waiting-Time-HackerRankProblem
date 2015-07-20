
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;


public class TestClass
{
	
	public static void main(String[] args) throws NumberFormatException, IOException
	{
		BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(read.readLine());
		ArrayList<Cliente> al = new ArrayList<Cliente>();
		al.add(new Cliente(-1,-1));
		for(int i=0;i<N;i++)
		{
			String[] t = read.readLine().split(" ");
			al.add(new Cliente( Long.parseLong(t[0]) ,Long.parseLong(t[1]) ));
		}
		for(int i=(int)(0.5*(al.size()-1));i>=1;i-- )
		{
			heapifyDown(al, i, true,false);
		}
		
		
		ArrayList<Cliente> heap = new ArrayList<Cliente>();
		heap.add(new Cliente(-1,-1)); //dummy
		
		
		
		Cliente first = al.get(1);
		al.set(1,al.get(al.size()-1));
		al.remove(al.size()-1);
		heapifyDown(al, 1, true, false);
		heap.add(first);
		
		BigDecimal tFin = new BigDecimal(first.getTArrivo());
		BigDecimal attesa = new BigDecimal(0);
		
		for(int i=0;i<N;i++)
		{
			Cliente pop = heap.get(1);
			heap.set(1, heap.get(heap.size()-1));
			heap.remove(heap.size()-1);
			heapifyDown(heap,1,true,true);
//			System.out.println("Estratto "+pop.getTArrivo());
		
			while(al.size()>1)
			{
				Cliente el = al.get(1);
				
				if(el.getTArrivo() > tFin.longValue() && el.getTArrivo() < new BigDecimal(tFin.longValue() + pop.getTDurata()).longValue() )
				{
					heap.add(el);
					heapifyUp(heap, heap.size()-1, true,true);
					
					Cliente t = al.get(al.size()-1);
					al.set(1, t);
					al.remove(al.size()-1);
					heapifyDown(al,1,true,false);
					
				}
				else
				{
					break;
				}
			}
			
			attesa = attesa.add( new BigDecimal((tFin.longValue() - pop.getTArrivo() + pop.getTDurata())) );
			tFin = new BigDecimal(tFin.longValue() + pop.getTDurata());
			
		}
		System.out.println(attesa.longValue()/N);
		
		
	}
	
	public static void heapifyDown(ArrayList<Cliente> al,int i,boolean minHeap,boolean flag)
	{
		if(i > 0.5 * (al.size()-1)  )
			return;
		int iMax =i;
		Cliente max = al.get(i);
		
		long v1 = 0,v2=0;
		v1 = flag ? al.get(2*i).getTDurata() : al.get(2*i).getTArrivo();
		v2 = flag ? al.get(i).getTDurata() : al.get(i).getTArrivo();
		
		if( (!minHeap && v1 > v2 || (minHeap && v1 <  v2))  )
		{
			iMax = 2*i;
			max = al.get(2*i);
		}
		if(2*i+1 < al.size())
		{
			v1 = flag ? al.get(2*i+1).getTDurata() : al.get(2*i+1).getTArrivo();
			v2 = flag ? max.getTDurata() : max.getTArrivo();
			
			if(((!minHeap && v1 > v2) ||  (minHeap && v1 < v2)) )
			{
				iMax = 2*i+1;
				max = al.get(2*i+1);
			}
		}
		if(al.get(i) != max)
		{
			Cliente t = al.get(i);
			al.set(i,al.get(iMax));
			al.set(iMax,t);
			heapifyDown(al,iMax,minHeap,flag);
		}
		
	}
	
	public static void heapifyUp(ArrayList<Cliente> al,int i,boolean minHeap,boolean flag) //se minheap è true 
	{
		
		if(i==1)
			return;
		int iMax=i;
		Cliente max = al.get(i);
		
		long v1=0,v2=0;
		v1 = flag ? al.get((int)(0.5*i)).getTDurata() : al.get((int)(0.5*i)).getTArrivo() ;
		v2 = flag ? max.getTDurata() : max.getTArrivo();
		
		if( (minHeap && v1 > v2) || (!minHeap && v1 < v2 ))
		{
			Cliente t = al.get(i);
			al.set(i,al.get((int)(0.5 * i)));
			al.set((int)(0.5 * i),t);
			heapifyUp(al,(int)(0.5*i),minHeap,flag);
		}
		
	}
	
	static class Cliente
	{
		long  tArrivo;
		long tDurata;
		boolean flag = true;
		public Cliente(long arr,long dur)
		{
			tArrivo = arr;
			tDurata = dur;
		}
		public long getTArrivo(){return tArrivo;}
		public long getTDurata(){return tDurata;}
	}
	
}













