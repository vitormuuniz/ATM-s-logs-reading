package reading;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;

import databaseOperations.GetConnection;
import databaseOperations.TransactionDAO;
import transaction.Transaction;

public class FolderVerification {

	private Reading reader = new Reading();
	private ArrayList<Transaction> transactionsList;
	private TransactionDAO dao;
	private String target = "./logArchivesREADED/";
	private String source;

	public FolderVerification(String source) {
		this.source = source;
	}

	public synchronized void verify(int thread) throws SQLException, IOException {
		File directory = new File(this.source);
		File archivesList[] = directory.listFiles();

		System.out.println("\t====== " + "THREAD " + thread + " ======");
		
		if (archivesList.length > 0) {
			newFile(archivesList[0]);
		} else
			System.out.println("Waiting for archives in directory...");
	}

	public void newFile(File file) throws IOException, SQLException {

		try {
			Files.move(Paths.get(this.source + file.getName()), Paths.get(target + file.getName()));
			// target is passed with the file name cuz the archive was already moved
			// when the program get in this line
			System.out.println("\t" + file.getName());

			Double beginning = (double) System.currentTimeMillis();
			transactionsList = reader.read(target + file.getName());
			System.out.println("\tReading performed in " + ((System.currentTimeMillis() - beginning) / 1000) + "s");
			System.out.println("\t*********************************");

			Double middle = (double) System.currentTimeMillis();
			dao = new TransactionDAO(GetConnection.connect());
			dao.insertTransactions(transactionsList);
//				dao.deleteTransaction(6);
//				dao.updateTransaction(3, "TRANSACTION_TYPE", "aasidjasjisda");
			System.out.println("\tInsertions performed in " + ((System.currentTimeMillis() - middle) / 1000) + "s");
			GetConnection.disconnect();

			System.out.println("\tFile readed and moved successfully");
			System.out.println("\t*********************************");
		} catch (FileAlreadyExistsException | FileNotFoundException ex) {
			
			System.out.println("Failed to move the file \"" + file.getName() + "\"");
			
			if (ex instanceof FileAlreadyExistsException) {
				System.out.println(
						"Archive cannot moved. It already readed and moved to target directory and will be deleted from this directory.\n\n");
				Files.delete(Paths.get("./logArchives/" + file.getName()));
			} else {
				System.out.println("Archive wasn't founded");
			}
		}
	}
}
