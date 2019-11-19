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
import java.util.*;
/**
 *
 * @author user
 */
public class Largest {
     static int exam = 543;
     static int data[][] = new int[exam][exam];
	
        static	int sort [][] = new int[exam][2]; //array urutan degree terbesar ke terkecil
	static	int timeslot [][] = new int[exam][2]; //array timeslot per exam

	public static void main(String[] args) throws IOException {

		int ts = 1;
		int students = 18419;
		int count = 0; //untuk menghitung degree
                
                //array baru untuk menyimpan hasil largest degree hasil sementara
		int max [][] = new int [1][2];
		max[0][0] = -1;
		max[0][1] = -1;

		int x = 0; //x untuk sorting degree terbesar, untuk mendapatkan nilai i dari looping
		
		for (int i=0; i<data.length; i++)
		{
			for(int j=0; j<data.length; j++)
			{
				data[i][j] = 0;
			}
		}	
		int degree[][] = new int [exam][2];//mengisi exam x berapa degree tapi belom urutan
		//untuk looping pemberian nomor kolom pertama (kolom exam)
                for (int i=0; i<degree.length; i++)
		{
			for (int j=0; j<degree[0].length; j++)
			{
				degree[i][0] = i+1;
			}
		}
        try {

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
                        //untuk mengisi kolom kedua (kolom jumlah degree per exam)
			for (int i=0; i<exam; i++)
			{
				for (int j=0; j<exam; j++)
				{
					if(data[i][j] > 0)
					{
						count++;
					}
					else
					{
						count = count;
					}
					
				}
				degree[i][1] = count;//1 berarti akan mengisi kolom tersebut
				count=0;
			}
			
                        //untuk mengurutkan degree yang sudah ada dari yang terbesar ke terkecil 
			for(int a=0; a<degree.length; a++)
			{
				for(int i=0; i<degree.length; i++)
				{
					if(max[0][1]<degree[i][1])
					{
						max[0][0] = degree[i][0];
						max[0][1] = degree[i][1];
						x = i;
					}				
				}
				degree[x][0] = -2; //menginisasi degree exam yang udah kepindah
				degree[x][1] = -2;
				sort[a][0] = max[0][0];
				sort[a][1] = max[0][1];
				max[0][0] = -1;
				max[0][1] = -1;
			}
			//untuk mengisi array kolom 1 = exam, kolom 2 = 0
			for(int i=0; i<timeslot.length; i++)
			{
				for(int j=0; j<timeslot[i].length; j++)
				{
					timeslot[i][0] = i+1; //mengisi baris ke i kolom ke 1 sesuai exam
					timeslot[i][1] = 0; //mengisi baris ke i kolom ke 2 sama dengan 0
				}
			}
        // untuk assign exam ke time slot berdasarkan sortingan degree
		for(int i=0; i<sort.length; i++)
		{
			for (int j=0; j<ts; j++)
			{
				if(isSafe(i, j))
				{
					timeslot[sort[i][0]-1][1] = j+1;
					break;
				}
                                else {
                                    ts++;
                                }
			}
		}
		for(int i=0; i<timeslot.length; i++) //print timeslot
		{
				for(int j=0; j<timeslot[i].length; j++)
				{
					System.out.print(timeslot[i][j] + " ");
				}
				System.out.println();
		}
                TextFileWritingExample1();
               /*int jadwal[][] = new int[exam][2];
                int min[][] = new int [1][2];
                min [0][0] = 0;
		min [0][1] = 34;
                for(int a=0; a<jadwal.length; a++)
			{
				for(int i=0; i<timeslot.length; i++)
				{
					if(min[0][1]>timeslot[i][1])
					{
						min[0][0] = timeslot[i][0];
						min[0][1] = timeslot[i][1];
						x = i;
					}				
				}
				timeslot[x][0] = 0; 
				timeslot[x][1] = 34;
				jadwal[a][0] = min[0][0];
				jadwal[a][1] = min[0][1];
				min[0][0] = 0;
				min[0][1] = 33;
			}
                for(int i=0; i<jadwal.length; i++) //print timeslot
		{
				for(int j=0; j<jadwal[i].length; j++)
				{
					System.out.print(jadwal[i][j] + " ");
				}
				System.out.println();
		}*/
        } 
                    catch (IOException e) {
                    e.printStackTrace();
                    }
    }
                    public static boolean isSafe(int index, int m)
                    {
                        for(int i=0; i<sort.length; i++)
                            if(data[sort[index][0]-1][i]!=0 && timeslot[i][1] == m+1)
				return false;
                                return true;
                    }
	public static void TextFileWritingExample1() {

    try {
        FileWriter writer = new FileWriter("LargestDegreeFirst.res", true);
        for (int i = 0; i <timeslot.length; i++) {
            for (int j = 0; j <timeslot[i].length; j++) {
                  writer.write(timeslot[i][j]+" ");
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