package main;

import java.io.File;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

import databaseOperations.GetConnection;
import databaseOperations.TransactionDAO;
import reading.Reading;
import transaction.Transaction;

public class MainWithoutThreads {

	public static void main(String[] args) throws SQLException, ParseException {
		Reading reader = new Reading();
		File directory = new File("./logArchives");
		File[] archivesList = directory.listFiles();
		ArrayList<Transaction> operationsList = null;
		
		for (int i = 0; i < archivesList.length; i++) {
			Double inicio = (double) System.currentTimeMillis();
			operationsList = reader.read("./logArchives/" + archivesList[i].getName());
			System.out.println("\n\n\t\""+archivesList[i].getName()+"\"");
			System.out.println("\tLeitura feita: " + ((System.currentTimeMillis() - inicio) / 1000) + "s");

			Double meio = (double) System.currentTimeMillis();
			TransactionDAO dao = new TransactionDAO(GetConnection.connect());
			dao.insertTransactions(operationsList);
//			dao.deleteTransaction(6);
//			dao.updateTransaction(1, "TsRANSACTION_TYPE", "aasidjasjisda");
			System.out.println("\tInserção feita: " + ((System.currentTimeMillis() - meio) / 1000) + "s");
			GetConnection.disconnect();
		}

		
		
//		for (int i = 0; i < operationsList.size(); i++) {
//			System.out.println("TIPO: " + operationsList.get(i).getTransaction_Type());
//			System.out.println();
//			System.out.println("DATA: " + operationsList.get(i).getDate());
//			System.out.println("HORA: " + operationsList.get(i).getHour());
//			System.out.println("ID ADM: " + operationsList.get(i).getTerminal_ID());
//			System.out.println("SEQUENCIA: " + operationsList.get(i).getSequence_NB());
//			System.out.println("ID OPERADOR: " + operationsList.get(i).getOperator_ID());
//			System.out.println("NOME OPERADOR: " + operationsList.get(i).getOperator_Name());
//			System.out.println("ID COLETA: " + operationsList.get(i).getCollect_ID());
//			System.out.println("ID SANGRIA: " + operationsList.get(i).getSangria_ID());
//			System.out.println("PDV: " + operationsList.get(i).getPDV_NB());
//			System.out.println("CODIGO DO LACRE: " + operationsList.get(i).getSeal_CD());
//			System.out.println("TOTAL: " + operationsList.get(i).getTotal());
//			System.out.println("DENOMINACAO 2: " + operationsList.get(i).getDenomination(0));
//			System.out.println("DENOMINACAO 5: " + operationsList.get(i).getDenomination(1));
//			System.out.println("DENOMINACAO 10: " + operationsList.get(i).getDenomination(2));
//			System.out.println("DENOMINACAO 20: " + operationsList.get(i).getDenomination(3));
//			System.out.println("DENOMINACAO 50: " + operationsList.get(i).getDenomination(4));
//			System.out.println("DENOMINACAO 100: " + operationsList.get(i).getDenomination(5));
//			System.out.println("\n************************************\n\n");
//		}
	}
}
