package gerador;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class GeradorXsl {

	public static void escreverXsl(String nomeRamo, String dataHora, ArrayList<String> fontes) {
		
		try {
			/* abrindo o arquivo */
			String pathFileString = JOptionPane.showInputDialog("Informe o caminho do arquivo excel \nExemplo: C:/Users/Hugo/Documents/arquivo.xlsx");
			
			FileInputStream file = new FileInputStream(new File(pathFileString));
			
			/* validando se é um arquivo excel através da classe XSSFWorkbook */
			XSSFWorkbook wbWorkbook = new XSSFWorkbook(file);
			
			/* acessando a primeira planilha do arquivo */
			XSSFSheet testeSheet = wbWorkbook.getSheetAt(0);
						
			/* obtendo o número da última linha da planilha */
			int ultimaLinha = testeSheet.getLastRowNum();
			
			int proximaLinha = ultimaLinha + 1;
			
			/* contador para criação das células */
			int cont = 0;
			
			Row newRow = null;
			for(int i = 0; i < fontes.size(); i++ ) {
				
				/* criando uma nova linha após a última linha da planilha */
				newRow = testeSheet.createRow(proximaLinha);
				
				/* criará 03 células para cada nova linha */
				Cell newCell = null;
				for(int j = 0; j < 3; j++) {
					/* criando nova célula para a nova linha */
					newCell = newRow.createCell(j);
					/* setando o valor para a célula 0 */
					if(j == 0) newCell.setCellValue(nomeRamo);
					/* setando o valor para a célula 1 */
					if(j == 1) newCell.setCellValue(dataHora);
					
					if(j == 2)
						newCell.setCellValue(fontes.toArray()[cont].toString());
				}
				cont++;
				proximaLinha++;
			}
			
			/* apontando para o arquivo que quero gravar os dados */
			FileOutputStream outputStream = new FileOutputStream(pathFileString);
			
			/* escrevendo os dados no arquivo */
			wbWorkbook.write(outputStream);
			
			outputStream.close();
			wbWorkbook.close();
			
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, e + " - " + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e + " - " + e.getMessage());
			e.printStackTrace();
		}
		
		JOptionPane.showMessageDialog(null, "Arquivo alterado com sucesso!");
		System.out.println("Arquivo alterado com sucesso!");
	}

}
