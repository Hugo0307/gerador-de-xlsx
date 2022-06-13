package leitor;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import gerador.GeradorXsl;

public class LeitorArquivoHtml {
		
		private String nomeRamo;
		private String dataHora;
		private String fonteLinha;
		private boolean linhaAnteriorIsTagA;
		private ArrayList<String> fontes = new ArrayList<String>();
		
		public void getLeitor() {
		
			try {
				BufferedReader bReader = 
						new BufferedReader(new FileReader(obterCaminhoArquivo()));
				
				while (bReader.ready()) {
					/*lendo e obtendo linha por linha */
					String linha = bReader.readLine();
					
					/* obtendo a linha que contém o nome do ramo */
					if (linha.contains(" TCAP/")) 
						nomeRamo = linha;
					
					/* obtendo a linha que contém a data e hora */
					if (linha.contains(" BRT")) 
						dataHora = linha;
					
					/* verifica se alinha anterior foi a tag a */
					if (linhaAnteriorIsTagA) {
						
						/* despreza as linhas que não é nome de fonte */
						if (!linha.contains("Rebase") && !linha.contains("/TCAP")) {
							/* só adiciona à lista se o nome do fonte adicionado anteriormente não for igual */ 
							if (!linha.equals(fonteLinha)) {
								fontes.add(linha.trim());
							}
							fonteLinha = linha;
						}
						linhaAnteriorIsTagA = false;
					}
					
					/* verifica se contém a tag a na linha */
					if (linha.contains("<a "))
						linhaAnteriorIsTagA = true;
				}
				bReader.close();
				
			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(null, e + " - " + e.getMessage());
				e.printStackTrace();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, e + " - " + e.getMessage());
				e.printStackTrace();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e + " - " + e.getMessage());
				e.printStackTrace();
			}
			
			getExibirConsole(nomeRamo, dataHora, fontes);
			GeradorXsl.escreverXsl(nomeRamo, dataHora, fontes);
		}
		
		private String obterCaminhoArquivo() {
			
			boolean continua = true;
			String pathFileHtml = "";
			
			while(continua) {
				
				pathFileHtml = JOptionPane.showInputDialog(
						"Informe o path do arquivo HTML. \nExemplo: C:/Users/Hugo/Documents/VCMUpdateReport2022-06-03T17-57-54Z.html");
				if(pathFileHtml.isBlank()) {
					JOptionPane.showMessageDialog(null, "Campo não pode ser vazio. Tente novamente!");
					continue;
				} else if(!pathFileHtml.contains(".html")) {
					pathFileHtml += ".html";
				}
				
				String addMaisUm = JOptionPane.showInputDialog(
						"Deseja informar mais? \nS - Sim | N - Não");

				if(addMaisUm.equalsIgnoreCase("s")) {
					continue;
				} else {
					continua = false;
					continue;
				}
			
			}
			
			return pathFileHtml;
		}
		
		public void getExibirConsole(String nomeRamo, String dataEHora, ArrayList<String> fontes ) {
			
			if(nomeRamo != null) {
				nomeRamo = nomeRamo.substring(22);
				System.out.println("Nome do ramo: " + nomeRamo);
			} else {
				throw new NullPointerException(
						"Nome do ramo não pode ser nulo!");
			}
			if(dataEHora != null) {
				dataEHora = dataEHora.substring(5, 26);
				System.out.println("Data/Hora: " + dataEHora);
			} else {
				throw new NullPointerException(
						"Data e hora não podem ser nulos!");
			}
			if(!fontes.isEmpty()) {
				System.out.println("FONTES:");
				
				/* percorrendo lista de fontes e imprimindo cada um sem espaços antes nem depois*/
				for (String fonte : fontes) {
					if(!fonte.equals(null))
						System.out.println(fonte.trim());
				}
			} else {
				throw new IllegalArgumentException(
						"A lista de arquivos fonte está vazia!");
			}
		}

}
