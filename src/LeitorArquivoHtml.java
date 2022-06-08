

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JOptionPane;

public class LeitorArquivoHtml {
	
	public static void main(String[] args) {
		
		String nomeRamo = "";
		String dataHora = "";
		boolean linhaAnteriorIsTagA = false;
		Set<String> fontes = new HashSet<String>();
		
		try {
			String pathFileHtml = JOptionPane.showInputDialog("Informe o path do arquivo HTML. \nExemplo: C:/Users/Hugo/Documents/VCMUpdateReport2022-06-03T17-57-54Z.html");
			BufferedReader bReader = 
					new BufferedReader(new FileReader(pathFileHtml));
			
			while (bReader.ready()) {
				/*lendo e obtendo linha por linha */
				String linha = bReader.readLine();
				
				/* obtendo a linha que contém o nome do ramo */
				if (linha.contains(" TCAP/")) nomeRamo = linha;
				
				/* obtendo a linha que contém a data e hora */
				if (linha.contains(" BRT")) dataHora = linha;
				
				/* verifica se alinha anterior foi a tag a */
				if (linhaAnteriorIsTagA) {
					
					/* despreza as linhas que não é nome de fonte */
					if (!linha.contains("Rebase") && !linha.contains("/TCAP"))
						fontes.add(linha.trim());
					
					linhaAnteriorIsTagA = false;
				}
				
				/* verifica se contém a tag a na linha */
				if (linha.contains("<a "))
					linhaAnteriorIsTagA = true;
				
			}
			nomeRamo = nomeRamo.substring(22);
			System.out.println("Nome do ramo: " + nomeRamo);
			
			dataHora = dataHora.substring(5, 26);
			System.out.println("Data/Hora: " + dataHora);
			
			System.out.println("FONTES:");
			
			/* percorrendo lista de fontes e imprimindo cada um sem espaços antes nem depois*/
			for (String fonte : fontes)
				System.out.println(fonte.trim());
			
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
		
		GeradorXsl.escreverXsl(nomeRamo, dataHora, fontes);
		
	}

}
