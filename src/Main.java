import javax.swing.JOptionPane;

import leitor.LeitorArquivoHtml;

public class Main {

	public static void main(String[] args) {

		LeitorArquivoHtml lerArquivoHtml = new LeitorArquivoHtml();
		
		try {
			lerArquivoHtml.getLeitor();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e + " - " + e.getMessage());
			e.printStackTrace();
		}

	}

}
