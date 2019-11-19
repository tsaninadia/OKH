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

public class First {
        static int exam = 543;
	static int data[][] = new int[exam][exam];
	static int ts = 32;
	static int timeslot[][]= new int[exam][2];

	public static void main(String[] args) throws IOException 
	{	
		try 
		{
			File f = new File("F:\\Kuliah\\Semester 7\\OKH\\Time Tabling\\Toronto\\car-f-92.stu");

            BufferedReader b = new BufferedReader(new FileReader(f));

            String readLine = "";

            while ((readLine = b.readLine()) != null) {
                //System.out.println(readLine);
				String tmp [] = readLine.split(" ");
				for(int i=0; i<tmp.length; i++)
				{
					for(int j=0; j<tmp.length; j++)
					{
						if(tmp[i] != tmp[j])
						{
                                                    data[Integer.parseInt(tmp[i])-1][Integer.parseInt(tmp[j])-1]++;
						}
					}
				}	
            }
		//TextFileWritingExample1();	
        } catch (IOException e) {
            e.printStackTrace();
        }
		for (int i=0; i<timeslot.length; i++)
		{
                            timeslot[i][0] = i+1;	
                            timeslot[i][1] = 0;
                }
		//ini gapake SET  tapi masih salah
		for(int i=0; i<data.length; i++)
		{
			for (int j=0; j<ts; j++)
			{
				if(isSafe(i, j) == true)
				{
					timeslot[i][1] = j+1;
					break;
				}
			}
		}
		for(int i=0; i<timeslot.length; i++)
		{
                    for(int j=0; j<timeslot[i].length; j++)
                    {
                        System.out.print(timeslot[i][j] + " ");
                    }
			System.out.println();
		}
    }
	// untuk mengecek bentrok ga buat di loopingnya
	public static boolean isSafe(int index, int m)
	{
		for(int i=0; i<data.length; i++)
			if(data[index][i]!=0 && timeslot[i][1] == m+1)
                            return false;
		return true;
	}		
	 
	public static void TextFileWritingExample1() {
		try 
		{
			FileWriter writer = new FileWriter("FirstCourseFirst.txt", true);
			for (int i = 0; i <data.length; i++) {
				for (int j = 0; j <data.length; j++) {
					  writer.write(data[i][j]+" ");
				}
				//this is the code that you change, this will make a new line between each y value in the array
				writer.write("\n");   // write new line
			}
			writer.close();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}    
}