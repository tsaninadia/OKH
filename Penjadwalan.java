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
import java.util.Scanner;

/**
 *
 * @author user
 */
public class Penjadwalan {
    public static void main(String[] args) throws IOException {
        String nama[][] = {{"car-f-92", "Carleton92"}, {"car-s-91", "Carleton91"}, {"ear-f-83", "EarlHaig83"}, {"hec-s-92", "EdHEC92"},
                          {"kfu-s-93", "KingFahd93"}, {"lse-f-91", "LSE91"}, {"pur-s-93", "pur-s-93"}, {"rye-s-93", "rye-s-93"}, {"sta-f-83", "St.Andrews83"},
                          {"tre-s-92", "Trent92"}, {"uta-s-92", "TorontoAS92"}, {"ute-s-92", "TorontoE92"}, {"yor-f-83", "YorkMills83"}};
        for(int i=0; i<nama.length;i++)
            System.out.println(i+1 + "  "+ nama[i][0]);
            Scanner in = new Scanner(System.in);
            System.out.print("Nomor data yang akan dijadwalankan : ");
            int pilihan = in.nextInt();
            String ex = "";
            String out = "";
            for(int i=0; i<nama.length; i++){
                if(pilihan==i+1){
                    ex = nama[i][0];
                    out = nama[i][1];
                }
            }
            String file = "F:\\Kuliah\\Semester 7\\OKH\\Time Tabling\\Toronto\\" + ex;
            BufferedReader reader = new BufferedReader(new FileReader(file + ".crs"));
            int exam = 0;
            while (reader.readLine() != null) exam++;
            reader.close();
            BufferedReader baca = new BufferedReader(new FileReader(file + ".stu"));
            int student = 0;
            while (baca.readLine() != null) student++;
            reader.close();
            int data[][] = new int[exam][exam];
            int sort [][] = new int[exam][2];
            int timeslot [][] = new int[exam][2];
            int ts = 1;
            int count = 0;
            int max [][] = new int [1][2];
            max[0][0] = -1;
            max[0][1] = -1;
            int x = 0;
            for (int i=0; i<data.length; i++)
                for(int j=0; j<data.length; j++)
                    data[i][j] = 0;
                    int degree[][] = new int [exam][2];
                    for (int i=0; i<degree.length; i++)
			for (int j=0; j<degree[0].length; j++)
                            degree[i][0] = i+1;
                    long mulai = System.nanoTime();
            try {

            File f = new File(file+".stu");

            BufferedReader b = new BufferedReader(new FileReader(f));

            String readLine = "";

            while ((readLine = b.readLine()) != null) {
                //System.out.println(readLine);
                String tmp [] = readLine.split(" ");
		for(int i=0; i<tmp.length; i++)
                    for(int j=0; j<tmp.length; j++)
                        if(tmp[i] != tmp[j])
			data[Integer.parseInt(tmp[i])-1][Integer.parseInt(tmp[j])-1]++;
            }
            for (int i=0; i<exam; i++){
		for (int j=0; j<exam; j++)
                    if(data[i][j] > 0)
                    count++;
                    else
                        count = count;					
                        degree[i][1] = count;
                        count=0;
            }
            for(int a=0; a<degree.length; a++){
                for(int i=0; i<degree.length; i++){
                    if(max[0][1]<degree[i][1]){
                        max[0][0] = degree[i][0];
			max[0][1] = degree[i][1];
			x = i;
                    }				
		}
                degree[x][0] = -2;
		degree[x][1] = -2;
		sort[a][0] = max[0][0];
		sort[a][1] = max[0][1];
		max[0][0] = -1;
		max[0][1] = -1;
            }
            for(int i=0; i<timeslot.length; i++){
                for(int j=0; j<timeslot[i].length; j++){
                    timeslot[i][0] = i+1;
                    timeslot[i][1] = -1;
		}
            }
            for(int i=0; i<sort.length; i++){
        	for (int j=0; j<ts; j++){
                    if(isSafe(i, j, data, sort, timeslot)){
                        timeslot[sort[i][0]-1][1] = j;
                        break;
                    }else{
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
                
		TextFileWritingExample1(timeslot, out);	
        } catch (IOException e) {
            e.printStackTrace();
        }
         long selesai = System.nanoTime();
         long runningtime = selesai - mulai;
         System.out.println("Running time: " + (double)runningtime/1000000000);
         System.out.println ("Penalty: " + Penalty(timeslot, data, student));
    }
	public static double Penalty(int jadwal[][], int matrix[][], int student){
            double nilaiPenalty = 0;
            for(int i=0; i<matrix.length-1; i++){
                for(int j=i+1; j<matrix.length; j++){
                    if(matrix[i][j]!=0){
                        if(Math.abs(jadwal[j][1]-jadwal[i][1])>=1 && Math.abs(jadwal[j][1]-jadwal[i][1])<=5){
                            nilaiPenalty = nilaiPenalty + (matrix[i][j]*(Math.pow(2,5-(Math.abs(jadwal[j][1]-jadwal[i][1])))));  
                        }
                    }
                } 
            }
            return (nilaiPenalty/student);
        }
	public static boolean isSafe(int index, int m, int dat[][], int[][]srt, int[][]jadwal)
	{
		for(int i=0; i<srt.length; i++)
			if(dat[srt[index][0]-1][i]!=0 && jadwal[i][1] == m)
				return false;
		return true;
	}
	     
	public static void TextFileWritingExample1(int[][]jadwal, String namaFile) {
            try {
                FileWriter writer = new FileWriter(namaFile+".sol", true);
                for (int i = 0; i <jadwal.length; i++) {
                for (int j = 0; j <jadwal[i].length; j++) {
                writer.write(jadwal[i][j]+ " ");
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