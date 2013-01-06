package okj.easy.core;

import java.util.ArrayList;

import org.ege.utils.exception.EasyGEngineRuntimeException;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Base64Coder;

/**
 * 
 * @FileName: eUtils.java
 * @CreateOn: Sep 15, 2012 - 11:11:38 AM
 * @Author: TrungNT
 */
public final class eUtils {

	public static final class base64 {
		public static final String	format	= "256encoded";

		public static void encodeAndSave (FileHandle file) {
			String tmp = Base64Coder.encodeString(file.readString());
			String path = file.file().getAbsolutePath();
			path = path.replace(file.extension(), format);
			FileHandle newFile = new FileHandle(path);
			newFile.writeString(tmp, false);
		}

		public static String decodeToString (FileHandle file) {
			String tmp = Base64Coder.decodeString(file.readString());
			return tmp;
		}

		public static String decodeToString (byte[] encoded) {
			return Base64Coder.decodeString(new String(encoded));
		}

		public static String decodeToString (String s) {
			String tmp = Base64Coder.decodeString(s);
			return tmp;
		}

		public static String decodeToString (char[] encoded) {
			return new String(Base64Coder.decode(encoded));
		}

		public static void decodeAndSave (FileHandle file, String extension) {
			String tmp = Base64Coder.decodeString(file.readString());
			String path = file.file().getAbsolutePath();
			if (!path.contains(format))
				throw new EasyGEngineRuntimeException("Your file is not 64 encoded");
			path = path.replace(format, extension);
			FileHandle newFile = new FileHandle(path);
			newFile.writeString(tmp, false);
		}

	}

	/**
	 * 
	 * @author trung
	 */
	public static final class array {
		public static boolean compare (int[] array1, int[] array2) {
			if (array1.length == array2.length) {
				int countTheSameNumber = 0;

				for (int j = 0; j < array1.length; j++) {
					if (contain(array2, array1[j]))
						countTheSameNumber++;
				}

				if (countTheSameNumber == array1.length)
					return true;
			}

			return false;
		}

		public static boolean contain (int[] array, int value) {
			for (int i = 0; i < array.length; i++) {
				if (array[i] == value)
					return true;
			}
			return false;
		}

		public static boolean equal (int[] i1, int[] i2) {
			if (i1.length != i2.length)
				return false;
			int count = 0;
			for (int i = 0; i < i1.length; i++) {
				for (int j = 0; j < i2.length; j++) {
					if (i1[i] == i2[j])
						count++;
				}
				if (count != 1)
					return false;
				count--;
			}
			return true;
		}
	}

	/**
	 * 
	 * @author trung
	 */
	public static final class string {
		public static ArrayList<String> readLine (String str) {
			int leng = str.length();
			ArrayList<String> tmp = new ArrayList<String>();
			StringBuilder builder = new StringBuilder();

			for (int i = 0; i < leng; i++) {
				if (str.charAt(i) != '\n') {
					builder.append(str.charAt(i));
				} else {
					if (builder.toString().length() > 0)
						tmp.add(builder.toString());
					builder.setLength(0);
				}
			}
			return tmp;
		}

		public static String removeString (String oriString, String removeString) {
			if (oriString.contains(removeString)) {
				return oriString.replace(removeString, "");
			}
			return null;
		}

		public static int atoi (String oriString) {
			StringBuilder tmp = new StringBuilder();
			tmp.append('0');
			for (int i = 0; i < oriString.length(); i++) {
				if (isNumber(oriString.charAt(i))) {
					tmp.append(oriString.charAt(i));
				}
			}
			return Integer.parseInt(tmp.toString());
		}

		/**
		 * This method to read a string into array of number Such as: "+0+1" ->
		 * int[ ]{0,1} ; "-1+1" -> int[ ]{-1,1}
		 * 
		 * @param str
		 *            this string should be Symmetric and the first element will
		 *            be '+' or '-' , such as "+0+1" or "-123+694" -> int[
		 *            ]{-123,+694}
		 * @param numberOfElementInString
		 *            the number of element such as: "+1-2+3" have *"three
		 *            element"* int[ ]{1,-2,3}
		 * @return array if integer
		 */
		public static int[] toNumber (String str, int numberOfElementInString) {
			int numb = numberOfElementInString;
			int[] tmp = new int[numb];

			int sizeOf1Element = str.length() / numb;
			for (int i = 0; i < numb; i++) {
				String temp = str.substring(i * sizeOf1Element, sizeOf1Element * (i + 1));
				if (temp.charAt(0) == '-')
					tmp[i] = -1 * atoi(temp);
				else
					tmp[i] = 1 * atoi(temp);
			}
			return tmp;
		}

		public static boolean isNumber (char c) {
			switch (c) {
				case '0':
					return true;
				case '1':
					return true;
				case '2':
					return true;
				case '3':
					return true;
				case '4':
					return true;
				case '5':
					return true;
				case '6':
					return true;
				case '7':
					return true;
				case '8':
					return true;
				case '9':
					return true;
				default:
					return false;
			}
		}
	}
}
