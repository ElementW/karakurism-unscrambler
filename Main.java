//package garble;

import java.util.*;
import java.lang.*;
import java.io.*;

import com.karakurism.scramble.ScrambleReader;
import com.karakurism.scramble.ScrambleDataInfo;

public class Main {
	public static void main (String[] args) throws java.lang.Exception {
		if (args.length < 1) {
			System.out.println("Usage: java -cp unscramble.jar Main <file names>");
			return;
		}
		// The following string is the package's name, used as content unscrambling key
		// Refer to refactored ScrambleReader code... here's the stack:
		// Android resource 0x7f050004 = 2131034116 requested
		// by jp.co.ideaf.neptune.nepkamijigenapp.cd.a(Context, int)    line 40
		// by jp.co.ideaf.neptune.nepkamijigenapp.AlarmDialogActivity.onCreate(Bundle)    line 110
		//   when creating this.b, that is
		int key = ("jp.co.ideaf.neptune.nepkamijigenapp".hashCode());
		// 1106451589 btw ;)
		
		// How I found the key:
		// ScrambleReader's constructor takes a data stream, and, as I presumed, a key as 2nd arg
		// jp.co.ideaf.neptune.nepkamijigenapp.fa.a(String, int, boolean, float)    line ? (JD decompiler error)
		//   ASSEMBLY ANALYSIS:
		//   ScrambleReader.<init> called
		//   after loading local variable 2 (here, 2nd argument) of type int
		//   after loading the java.io.InputStream stored in memory slot 6
		// by jp.co.ideaf.neptune.nepkamijigenapp.AlarmDialogActivity.c()    line 73
		//   which uses var jp.co.ideaf.neptune.nepkamijigenapp.AlarmDialogActivity.b as 2nd argument
		// by by jp.co.ideaf.neptune.nepkamijigenapp.AlarmDialogActivity.onCreate(Bundle)    line 123
		byte buf[] = new byte[1024*64];
		for (int i=0; i < args.length; i++) {
			String fname = args[i];
			File inFile = new File(fname),
				outFile = new File(fname + ".unscr");
			FileInputStream fis;
			try {
				fis = new FileInputStream(inFile);
			} catch (FileNotFoundException e) {
				System.err.println("Failed to open " + inFile.getName() + " for reading: skipped");
				continue;
			}
			ScrambleReader rd = new ScrambleReader(fis, key);
			ScrambleDataInfo info = rd.getScrambleInfo();
			if (info.header != 256) {
				System.err.println(inFile.getName() + ": not or badly scrambled (bad key?), skipped [header="+info.header+"]");
				continue;
			}
			
			FileOutputStream fos;
			try {
				fos = new FileOutputStream(outFile);
			} catch (FileNotFoundException e) {
				System.err.println("Failed to open " + outFile.getName() + " for writing: skipped");
				continue;
			}
			rd.unscramble(fos, buf, info.dataLength);
			fis.close();
			fos.close();
			
			inFile.delete();
			outFile.renameTo(inFile);
			
			System.out.println("Processed " + inFile.getName());
		}
	}
}