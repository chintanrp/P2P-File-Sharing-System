import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Random;


public class FileGenerator
{
	public static void main(String[] args) throws IOException {
		int i,file_1kb=0,file_10kb=0,file_100kb=0,file_1mb=0,file_10mb=0,file_100mb=0;
		for(i=0;i<1000;i++) {
            //String preFileName = newFileName();
			int j = 3000;
			j=j+i;
            String fileName = ""+j+"";
			try {
				File newFile= new File(fileName+".txt");
				newFile.createNewFile();
				FileWriter fw=new FileWriter(newFile);
				BufferedWriter bw=new BufferedWriter(fw);
				if(file_1kb<600) {
					CopyStringToFile(bw,1);
					file_1kb++;
				}
				else if(file_10kb<300) {
					CopyStringToFile(bw, 10);
					file_10kb++;
				}
				else if(file_10mb<100) {
					CopyStringToFile(bw, 100);
					file_10mb++;
				}
		//TO create 100 mb file
                /*else if(file_100mb<1) {
                    CopyStringToFile(bw, 1024000);
                    file_100mb++;
                }*/
				bw.close();
			}
			catch(IOException e) {
				throw(e);
			}
		}
	}
	
	public static String newStringLine(int f) {
        char[] charList;
        StringBuilder sb = new StringBuilder();
        for (char ch = '0'; ch <= '9'; ++ch)
            sb.append(ch);
        for (char ch = 'a'; ch <= 'z'; ++ch)
            sb.append(ch);
        charList = sb.toString().toCharArray();
		StringBuilder sb1=new StringBuilder();
		Random random=new Random();
        if(f == 0) {
		  for(int k=0;k<10;k++) {
			char ch = charList[random.nextInt(charList.length)];
			sb1.append(ch);
		  }
        }
        else if(f == 1) {
          for(int k=0;k<10;k++) {
            char ch = charList[random.nextInt(charList.length)];
            sb1.append(ch);
          }
        }
		String nextLine = sb1.toString();
		return nextLine;
	}

	public static void CopyStringToFile(BufferedWriter writer,int totalLines) throws IOException
	{
        int j=0;
		while(j<totalLines) {
			String data = newStringLine(0);
			writer.write(data);
			writer.newLine();
			j++;
		}
    }
}
