import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import java.util.Scanner;
public class Main {

	public static void main(String[] args) throws IOException 
	{
	
		Scanner leia = new Scanner(System.in);
		System.out.println("Digite o caminho do arquivo");
		String caminho = leia.nextLine();
		
		String resultado = ler_arquivo(caminho);
		
		String vetor[] = resultado.split("[ [\n]]");
			
		int quantidade_vertices = Integer.parseInt(vetor[0]);
		
		int[][] matriz_custo = new int[quantidade_vertices][quantidade_vertices];

		ArrayList<Vertice> lista_aberta = new ArrayList<Vertice>();
		ArrayList<Vertice> lista_fechada = new ArrayList<Vertice>();

		Vertice vetor_vertices[] = new Vertice[quantidade_vertices];
		
		for(int cont=0; cont<quantidade_vertices;cont++)
		{
			vetor_vertices[cont] = new Vertice();
			vetor_vertices[cont].setPosicao_vetor(cont);
		}
		
		
		// definindo a origem e o destino
		
		vetor_vertices[Integer.parseInt(vetor[1])].setOrigem(true); 
		vetor_vertices[Integer.parseInt(vetor[2])].setDestino(true);

		
		// próximo valor fora a quantidade de vertices(0),origem(1),destino(2)
		int percorredor_vetor_lido=3;
		
		for(int cont=0;cont<quantidade_vertices;cont++)
		{
			for(int cont2=0;cont2<quantidade_vertices;cont2++)
			{
				
				// não tem caminho, represento como -1 no meu código
				if(vetor[percorredor_vetor_lido].equals("#"))
				{	
					matriz_custo[cont][cont2] = -1;
				}else
				{
					matriz_custo[cont][cont2] = Integer.parseInt(vetor[percorredor_vetor_lido]);
				}
				
				percorredor_vetor_lido++;
			}
			
		}
		

		/*	
		    v0|v1|v2|v3|v4|v5|
		 v0[ 0|10|5 |-1|-1|-1|
		 v1[-1| 0|-1| 1|-1|-1|
		 v2[-1| 3| 0| 8| 2|-1|
		 v3[-1|-1|-1| 0| 4| 4|
		 v4[-1|-1|-1|-1| 0| 6|
		 v5[-1|-1|-1|-1|-1| 0|

		 */

		// setando os vertices de origem e os de destino

		/*
		vetor_vertices[0].setOrigem(true);
		vetor_vertices[5].setDestino(true);
		*/


		Vertice vertice_percorredor = conseguir_vertice_origem(vetor_vertices);
		vertice_percorredor.setAberto(true);
		lista_aberta.add(vertice_percorredor);

		
		boolean caminho_encontrado = false;
		
		// enquanto houver vertices abertas
		
		do
		{
			// ordenando a lista aberta
			Collections.sort(lista_aberta);
			
			
			// vertice_percorredor receberá o nó aberto com o menor custo acumulado

			vertice_percorredor = lista_aberta.get(0);


			
			// remove o vertice atual da lista aberta e adiciona na fechada

		
			lista_aberta.remove(vertice_percorredor);
			lista_fechada.add(vertice_percorredor);

			if(vertice_percorredor.isDestino())
			{
				caminho_encontrado = true;
				

			}else
			{

				// processamento do caminho

				for(int cont=0; cont< quantidade_vertices; cont++)
				{
					
					
					// visitando o vértice pela primeira vez
					if(matriz_custo[vertice_percorredor.getPosicao_vetor()][cont] > 0 && 
							!lista_fechada.contains(vetor_vertices[cont]))
					{
						
						
						
						// se o elemento não foi visitado, será inserido na lista aberta
						if(!vetor_vertices[cont].isVisitado())
						{
							lista_aberta.add(vetor_vertices[cont]);
						}
						
						// o custo será a soma do caminho acumulado atualmente com a distância
						// do vértice atual para esse vizinho
						if(vetor_vertices[cont].isVisitado() == false)
						{

							int novo_custo = vertice_percorredor.getCusto_caminho_acumulado() +
									matriz_custo[vertice_percorredor.getPosicao_vetor()][cont];

							vetor_vertices[cont].setCusto_caminho_acumulado(novo_custo);
							
							// definindo o vertice anterior

							vetor_vertices[cont].setAnterior(vertice_percorredor);
							vetor_vertices[cont].setVisitado(true);


						}else
						{			// vizinho já foi visitado, resta saber se pode otimizar caminho
							
							//novo_custo_vizinho deverá ser o custo acumulado pelo vertice atual mais a
							//distância entre o atual e o vizinho alvo
							
							int novo_custo_vizinho = vertice_percorredor.getCusto_caminho_acumulado() +
									matriz_custo[vertice_percorredor.getPosicao_vetor()][cont];

							
							
							
							// opção que otimiza o caminho minimo do vizinho
							if(novo_custo_vizinho < vetor_vertices[cont].getCusto_caminho_acumulado() )
							{
								
								// atualizando o custo do vizinho e redefinindo o vertice anterior dele para o
								// vertice atual. também marca o no atual como já visitado 
								

								vetor_vertices[cont].setCusto_caminho_acumulado(novo_custo_vizinho);
								vetor_vertices[cont].setAnterior(vertice_percorredor);
								
							}


						}
					}

				}
			}


		// enquanto a lista não estiver vazia e o vertice percorredor não for o destino	
			
		}while((!lista_aberta.isEmpty() && !vertice_percorredor.isDestino()));

	

		if(caminho_encontrado) 
		{
			// mostrando caminho
			System.out.println("Caminho encontrado");
			System.out.println("Distância = " + vertice_percorredor.getCusto_caminho_acumulado());
			System.out.print("Caminho = ");

			Vertice percorredor_temp = vertice_percorredor;
			
			while(percorredor_temp!= null)
			{
				System.out.print("V["+ percorredor_temp.getPosicao_vetor()+ "] ");
				percorredor_temp = percorredor_temp.getAnterior();
			}
		}else
		{
			System.out.println("Caminho não encontrado");
		}

	}
	
	
	// função que retorna o vertice de origem
	
	public static Vertice conseguir_vertice_origem(Vertice vetor[])
	{
		for(int cont=0;cont <vetor.length; cont++)
		{

			if(vetor[cont].isOrigem())
			{
				return vetor[cont];
			}

		}

		return null;
	}

	
	static public String ler_arquivo(String caminho) throws IOException
	{
		
		BufferedReader buffer_leitura = new BufferedReader(new FileReader(caminho));
		String linha = "";
		String retorno = linha;
		while (true) 
		{
			
			if (linha != null) 
			{
				
				retorno+=linha+'\n';
			} else
				break;
			linha = buffer_leitura.readLine();
			
		}
		buffer_leitura.close();
		
		
		
		// eliminando o primeiro \n
		
		retorno = retorno.substring(1,retorno.length());
		//System.out.print("Linha\n" + retorno);
		return retorno;
	}


}
