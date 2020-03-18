package reading;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import transaction.Transaction;

public class Reading {

	/**
	 * @param archivePath
	 * @return
	 */
	public ArrayList<Transaction> read(String archivePath) {
		BufferedReader bufferedReader = null; // buffer that read the line of all the archive
		String line;
		String[] separator;
		boolean isSangria = false;
		boolean isRecolhimento = false;
		ArrayList<Transaction> operationsList = new ArrayList<>();

		try { // if an error occur, it will go to the catch of exceptions 
			bufferedReader = new BufferedReader(new FileReader(archivePath));
			line = bufferedReader.readLine();

			while (bufferedReader.ready()) {
				line = bufferedReader.readLine();
				while (line.matches("\\s*"))
					line = bufferedReader.readLine();

				Transaction op = new Transaction();

				if (line.matches("DEPOSITO\\s*")) {
					op.setTransaction_Type("DEPOSITO");
				} else if (line.matches("DEPOSITO SANGRIA\\s*")) {
					isSangria = true;
					op.setTransaction_Type("DEPOSITO SANGRIA");
				} else if (line.matches("RECOLHIMENTO\\s*")) {
					isRecolhimento = true;
					op.setTransaction_Type("RECOLHIMENTO");
				} else {
					while ((!(line.contains("********")) && (line = bufferedReader.readLine()) != null))
						;
					continue;
				}

				line = bufferedReader.readLine();
				while (line.matches("\\s*"))
					line = bufferedReader.readLine();
				separator = line.split("\\s+");
				op.setDate(separator[1]);
				op.setHour(separator[3]);

				line = bufferedReader.readLine();
				while (line.matches("\\s*"))
					line = bufferedReader.readLine();
				separator = line.split("\\s+");
				op.setTerminal_ID(separator[2]);
				op.setSequence_NB(Integer.parseInt(separator[3].substring(separator[3].indexOf(":") + 1)));

				line = bufferedReader.readLine();
				while (line.matches("\\s*"))
					line = bufferedReader.readLine();
				separator = line.split("\\s+");
				op.setOperator_ID(Integer.parseInt(separator[2]));

				line = bufferedReader.readLine();
				while (line.matches("\\s*"))
					line = bufferedReader.readLine();
				separator = line.split("\\s+");
				if ((!line.contains("OPERADOR") && separator.length == 1
						|| (line.contains("OPERADOR") && separator.length == 2)))
					op.setOperator_Name(null);
				else {
					int i;
					if (separator[1].contains("OPERADOR"))
						i = 2;
					else
						i = 1;
					for (; i < separator.length; i++) {
						op.setOperator_Name(separator[i] + " ");
					}
				}

				line = bufferedReader.readLine();
				while (line.matches("\\s*"))
					line = bufferedReader.readLine();
				separator = line.split("\\s+");
				if (separator.length == 2)
					op.setCollect_ID(null);
				else
					op.setCollect_ID(separator[2]);

				if (isSangria) {
					isSangria = false;
					line = bufferedReader.readLine();
					while (line.matches("\\s*"))
						line = bufferedReader.readLine();
					separator = line.split("\\s+");
					op.setSangria_ID(Integer.parseInt(separator[2]));

					line = bufferedReader.readLine();
					while (line.matches("\\s*"))
						line = bufferedReader.readLine();
					separator = line.split("\\s+");
					op.setPDV_NB(Integer.parseInt(separator[1]));
				}

				if (isRecolhimento) {
					isRecolhimento = false;
					line = bufferedReader.readLine();
					while (line.matches("\\s*"))
						line = bufferedReader.readLine();
					separator = line.split("\\s+");
					if (separator[0].matches("CODIGO"))
						op.setSeal_CD(separator[3]);
				}

				while (!(line.contains("VALOR DEPOSITADO") || line.contains("VALORES VALIDADOS")
						|| line.contains("TOTAL (A + B)")))
					line = bufferedReader.readLine();
				if (line.contains("VALOR DEPOSITADO")) {
					separator = line.split("\\s+");
					separator = separator[2].split(",");
					op.setTotal(Integer.parseInt(separator[0]));

					line = bufferedReader.readLine();
					while (!line.contains("$"))
						line = bufferedReader.readLine();

					for (int i = 0; i < 6; i++) {
						separator = line.split("\\s+");
						op.setDenomination(Integer.parseInt(separator[2]), i);
						line = bufferedReader.readLine();
					}
				} else if (line.contains("VALORES VALIDADOS")) {
					separator = line.split("\\s+");
					separator = separator[3].split(",");
					op.setTotal(Integer.parseInt(separator[0]));

					line = bufferedReader.readLine();
					while (!line.contains("$"))
						line = bufferedReader.readLine();
					separator = line.split("\\s+");
					for (int i = 0; i < 6; i++) {
						separator = line.split("\\s+");
						op.setDenomination(Integer.parseInt(separator[2]), i);
						line = bufferedReader.readLine();
					}
				} else if (line.contains("TOTAL (A + B)")) {
					separator = line.split("\\s+");
					separator = separator[4].split(",");
					op.setTotal(Integer.parseInt(separator[0].replace(".", "")));

					line = bufferedReader.readLine();
					while (!line.contains("$"))
						line = bufferedReader.readLine();
					separator = line.split("\\s+");
					for (int i = 0; i < 6; i++) {
						separator = line.split("\\s+");
						op.setDenomination(Integer.parseInt(separator[2]), i);
						line = bufferedReader.readLine();
					}
				}
				while ((!(line.contains("********")) && (line = bufferedReader.readLine()) != null));

				operationsList.add(op);
			}
		} catch (FileNotFoundException e) { // arquivo don't exist
			System.out.println("File Not Found Exception: \n" + e.getMessage());
		} catch (ArrayIndexOutOfBoundsException e) { // array invalid index
			System.out.println("Index Out Of Bounds Exception: \n" + e.getMessage());
		} catch (IOException e) {
			System.out.println("IO Exception: \n" + e.getMessage());
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					System.out.println("IO Exception: \n" + e.getMessage());
				}
			}
		}
		return operationsList;
	}
}
