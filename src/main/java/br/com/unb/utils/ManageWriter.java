package br.com.unb.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.DirectoryStream;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ManageWriter {
	// Constantes
	static final int TAMANHO_BUFFER = 4096; // 4kb

	public static PrintWriter createFile(String adf, String outputFolder) {
		try {
			PrintWriter adfFile = new PrintWriter(new BufferedWriter(new FileWriter(outputFolder + adf)));
			return adfFile;
		} catch (IOException e) {
			String msg = "Error: Can't create output model file.";
			System.out.println(msg);
			throw new RuntimeException(msg);
		}
	}

	public static String readFileAsString(File file) {
		String res = null;
		String filePath = file.getAbsolutePath();
		try {
			res = FileUtility.readFileAsString(filePath);
		} catch (IOException e) {
			String msg = "Error: file " + filePath + " not found.";
			System.out.println(msg);
			throw new RuntimeException(msg);
		}
		return res;
	}

	

	public static String readFileAsString(String filePath) {
		String res = null;
		try {
			res = FileUtility.readFileAsString(filePath);
		} catch (IOException e) {
			String msg = "Error: file " + filePath + " not found.";
			System.out.println(msg);
			throw new RuntimeException(msg);
		}
		return res;
	}

	public static void printModel(PrintWriter adf, String... modules) {
		for (String module : modules) {
			adf.println(module);
		}
		adf.close();
	}

	public static File generateFile(final String path, final String nameFile, final String message) {
		File dir = new File(path);
		dir.mkdirs();
		return ManageWriter.generateFile(dir + "/" + nameFile, message);
	}

	public static File generateFile(final String nameFile, final String message) {
		try {
			File file = new File(nameFile);
			FileWriter arq = new FileWriter(file.getAbsolutePath());
			PrintWriter writeArq = new PrintWriter(arq);
			writeArq.printf(message);
			arq.close();

			return file;
		} catch (IOException e) {
			throw new RuntimeException("Error: Can't create file.");
		}
	}

	public static void toCompactFile(String arqEntrada, String arqSaida) throws IOException {
		int cont;
		byte[] dados = new byte[TAMANHO_BUFFER];

		BufferedInputStream origem = null;
		FileInputStream streamDeEntrada = null;
		FileOutputStream destino = null;
		ZipOutputStream saida = null;
		ZipEntry entry = null;

		try {
			destino = new FileOutputStream(new File(arqSaida));
			saida = new ZipOutputStream(new BufferedOutputStream(destino));
			File file = new File(arqEntrada);
			streamDeEntrada = new FileInputStream(file);
			origem = new BufferedInputStream(streamDeEntrada, TAMANHO_BUFFER);
			entry = new ZipEntry(file.getName());
			saida.putNextEntry(entry);

			while ((cont = origem.read(dados, 0, TAMANHO_BUFFER)) != -1) {
				saida.write(dados, 0, cont);
			}
			origem.close();
			saida.close();
		} catch (IOException e) {
			throw new IOException(e.getMessage());
		}
	}
	

	public static Map<String, String> toCompactFolder(String arqEntrada, String arqSaida ) {
		Map<String, String> results = new HashMap<String, String>();
		try {
			ManageWriter.createFolder(arqEntrada);
			FileOutputStream fos = new FileOutputStream(arqSaida);
			ZipOutputStream zos = new ZipOutputStream(fos);
			DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(arqEntrada));
			for (Path path : directoryStream) {
				String fileName = path.getFileName().toString();
				results.put(fileName, ManageWriter.readFileAsString(arqEntrada + "/" + fileName));
				byte[] bytes = Files.readAllBytes(path);
				zos.putNextEntry(new ZipEntry(fileName));
				zos.write(bytes, 0, bytes.length);
				zos.closeEntry();
			}
			zos.close();
			
			if(results.isEmpty()) {
				throw new RuntimeException("Fails when trying to generate zip file.");
			}
		}  catch (Exception ex) {
			throw new RuntimeException(ex.getMessage());
		}
		
		return results;
	}
	

	public static void createFolder(String path) throws IOException {
		if (!Files.exists(Paths.get(path))) {
			Files.createDirectory(Paths.get(path));
		}
	}

	public static void cleanFolder(String path) throws IOException {
		if (Files.exists(Paths.get(path))) {
			Files.walk(Paths.get(path), FileVisitOption.FOLLOW_LINKS).sorted(Comparator.reverseOrder())
					.map(Path::toFile).forEach(f -> {
						if (f.isFile()) {
							f.delete();
						}
					});
		}
	}
}
