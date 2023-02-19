package util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileLoader {
	public static List<String> readFile(String path) {
		List<String> lines = new ArrayList<>();
		String l;

		try (BufferedReader br = new BufferedReader(new InputStreamReader(
				Objects.requireNonNull(FileLoader.class.getClassLoader().getResourceAsStream(path))))) {
			String headerLine = br.readLine();
			while ((l = br.readLine()) != null) {
				lines.add(l);
			}
		} catch (FileNotFoundException f) {
			f.printStackTrace();
		} catch (IOException io) {
			io.printStackTrace();
		}

		return lines;
	}

}
