package mitw.survivalgames.utils.mysql.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

import mitw.survivalgames.utils.tlib.IO;

/**
 * @Author sky
 * @Since 2018-08-28 15:01
 */
public class FileUtils {

	/**
	 * ?��??��?�件资�?��?�件
	 *
	 * @param target   �?
	 * @param filename ??�件???
	 * @return {@link InputStream}
	 */
	@SuppressWarnings("rawtypes")
	public static InputStream getResource(final Class target, final String filename) {
		try {
			final URL url = target.getClassLoader().getResource(filename);
			if (url == null)
				return null;
			else {
				final URLConnection connection = url.openConnection();
				connection.setUseCaches(false);
				return connection.getInputStream();
			}
		} catch (final IOException ignored) {
			return null;
		}
	}

	/**
	 * ??�入??�件
	 *
	 * @param inputStream 输入�?
	 * @param file        ??�件
	 */
	public static void inputStreamToFile(final InputStream inputStream, final File file) {
		try {
			final String text = new String(IO.readFully(inputStream), Charset.forName("utf-8"));
			final FileWriter fileWriter = new FileWriter(FileUtils.createNewFile(file));
			fileWriter.write(text);
			fileWriter.close();
		} catch (final IOException ignored) {
		}
	}

	/**
	 * �?测�?�件并�?�建
	 *
	 * @param file ??�件
	 */
	public static File createNewFile(final File file) {
		if (file != null && !file.exists()) {
			try {
				file.createNewFile();
			} catch (final Exception ignored) {
			}
		}
		return file;
	}

	/**
	 * �?测�?�件并�?�建（目录�??
	 *
	 * @param file ??�件
	 */
	public static void createNewFileAndPath(final File file) {
		if (!file.exists()) {
			final String filePath = file.getPath();
			final int index = filePath.lastIndexOf(File.separator);
			File folder;
			if ((index >= 0) && (!(folder = new File(filePath.substring(0, index))).exists())) {
				folder.mkdirs();
			}
			try {
				file.createNewFile();
			} catch (final IOException ignored) {
			}
		}
	}

}
