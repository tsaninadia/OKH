/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timetabling;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;

public class readFile {

    static int exam = 543;
	static int data[][] = new int[exam][exam];

	public static void main(String[] args) throws IOException {

		int ts = 32;
		int students = 18419;
		
		int count = 0;
		

		/*for(int a=0, a<exam, a++)
			for(int b=0, b<exam-1, b++)
				data[a][b] = 0;
		*/
		
		int degree[] = new int [exam];
        try {

            File f = new File("F:\\Kuliah\\Semester 7\\OKH\\Time Tabling\\Toronto\\car-f-92.stu");

            BufferedReader b = new BufferedReader(new FileReader(f));

            String readLine = "";

            System.out.println("Reading file using Buffered Reader");

            while ((readLine = b.readLine()) != null) {
                //System.out.println(readLine);
				String tmp [] = readLine.split(" ");
				for(int i=0; i<tmp.length-1; i++)
				{
					for(int j=i+1; j<tmp.length; j++)
					{
						data[Integer.parseInt(tmp[i])-1][Integer.parseInt(tmp[j])-1]++;
					}
				}
				
				
				//for(String s: tmp)
				//{
					
			//		System.out.print(Integer.parseInt(s) + " ");
				//}
				//System.out.println(tmp.length);
            }
			/*String[] tmp = data.split(" ");    //Split space
			for(String s: tmp)
			myArrayList.add(s);*/
			for(int i=0; i<exam; i++)
				for(int j=0; j<exam; j++)
					System.out.print(data[i][j] + " " );
					System.out.println();
			/*for (int i=0; i<exam-1; i++)
			{
				for (int j=0; j<exam-1; j++)
				{
					if(data[i][j] > 0)
					{
						count++;
					}
					else
					{
						count = count;
					}
					degree[i]=count;
				}
				//degree[i] = count;
				count=0;
			}*/
			
		TextFileWritingExample1();	
				
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
	public static void TextFileWritingExample1() {

    try {
        FileWriter writer = new FileWriter("MyFile.txt", true);
        for (int i = 0; i < 532; i++) {
            for (int j = 0; j < 542; j++) {
                  writer.write(data[i][j]+" ");
            }
            //this is the code that you change, this will make a new line between each y value in the array
            writer.write("\n");   // write new line
        }
        writer.close();
    } catch (IOException e) {
        e.printStackTrace();
    }

}

}