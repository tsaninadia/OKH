
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Penjadwalan
{
    public static void main(String[] args) throws IOException
    {
        String nama[][] = {{"car-f-92", "Carleton92"}, {"car-s-91", "Carleton91"}, {"ear-f-83", "EarlHaig83"}, {"hec-s-92", "EdHEC92"},
                {"kfu-s-93", "KingFahd93"}, {"lse-f-91", "LSE91"}, {"pur-s-93", "pur-s-93"}, {"rye-s-93", "rye-s-93"}, {"sta-f-83", "St.Andrews83"},
                {"tre-s-92", "Trent92"},	{"uta-s-92", "TorontoAS92"}, {"ute-s-92", "TorontoE92"}, {"yor-f-83", "YorkMills83"}};
        int jts[] = {32, 35, 24, 18, 20, 18, 42, 23, 13, 23, 35, 10, 21};
        for(int i=0; i<nama.length;i++)
            System.out.println(i+1 + "  "+ nama[i][0]);
        Scanner in = new Scanner(System.in);
        System.out.print("Nomor data yang akan dijadwalankan : ");
        int pilihan = in.nextInt();
        String ex = "";
        String out = "";
        int jj = -1;
        for(int i=0; i<nama.length; i++)
        {
            if(pilihan==i+1)
            {
                ex = nama[i][0];
                out = nama[i][1];
                jj = jts[i];
            }
        }
        String file = "F:\\Kuliah\\Semester 7\\OKH\\Time Tabling\\Toronto\\" + ex;
        BufferedReader reader = new BufferedReader(new FileReader(file + ".crs"));
        int exam = 0;
        while (reader.readLine() != null) exam++;
        reader.close();
        BufferedReader pembaca = new BufferedReader(new FileReader(file + ".stu"));
        int siswa = 0;
        while (pembaca.readLine() != null) siswa++;
        pembaca.close();

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


            for (int i=0; i<exam; i++)
            {
                for (int j=0; j<exam; j++)
                    if(data[i][j] > 0)
                        count++;
                    else
                        count = count;
                degree[i][1] = count;
                count=0;
            }

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
                degree[x][0] = -2;
                degree[x][1] = -2;
                sort[a][0] = max[0][0];
                sort[a][1] = max[0][1];
                max[0][0] = -1;
                max[0][1] = -1;
            }

            for(int i=0; i<timeslot.length; i++)
            {
                for(int j=0; j<timeslot[i].length; j++)
                {
                    timeslot[i][0] = i+1;
                    timeslot[i][1] = -1;
                }
            }
            int [][] jadual = new int[exam][2];
            int [][] sat = new int[exam][2];
            for(int  i=0; i<jadual.length; i++) {
                jadual[i][0] = i+1;
                jadual[i][1] = -1;
                sat[i][0] = sort[i][0];
                sat[i][1] = exam;
            }

            for(int i=0; i<exam; i++)
            {
                int index = calculate(sat, exam);
                for (int j=0; j<=ts; j++)
                {
                    if(isOk(sat[index][0]-1, j, data, jadual, sat))
                    {
                        jadual[sat[index][0]-1][1] = j;
                        sat[index][1] = 100000;
                        int ind =0;
                        for(int k=0; k<data.length; k++)
                        {
                            if(data[sat[index][0]-1][k]!=0)
                            {
                                ind = k;
                                for(int l=0; l<sat.length; l++)
                                {
                                    if(sat[l][0]==k+1)
                                    {
                                        sat[l][1] = sat[l][1]-1;
                                    }
                                }
                            }
                        }
                        break;
                    }
                    else{
                        ts++;
                    }
                }
            }

            long awal = System.nanoTime();
            Jadwal aaa = new Jadwal(jadual, data, siswa, out, jj);
            aaa.HCTABU();

           for(int i=0; i<1; i++)
            {
                for (int j=0; j<=ts; j++)
                {
                    if(isSafe(i, j, data, sort, timeslot))
                    {
                        timeslot[sort[i][0]-1][1] = j;
                        break;
                    }
                    else{
                        ts++;
                    }
                }
            }

            Jadwal fix = new Jadwal(timeslot, data, siswa, out, jj);
            max[0][0] = 1000;
            max[0][1] = 1000;
            int[][] baru = new int[data.length][2];
            for(int n=0; n<baru.length; n++)
            {
                baru[n][0] = n+1;
                baru[n][1] = -1;
            }

            long selesai = System.nanoTime();
            long akhir  = selesai-awal;
            System.out.println("Lama Running Time = "+(double)akhir/1000000000);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void back(int ex, int jad, int[][]cm, int[][]sort, int[][]jadwal, int limit)
    {
        if(ex>=0 && ex<jadwal.length && jad>=0 && jad<=limit)
        {
            if(isSafe(ex, jad, cm, sort, jadwal))
            {
                jadwal[sort[ex][0]-1][1] = jad;
                back(ex+1, 0, cm, sort, jadwal, limit);
            }
            else
            {
                if(jad+1!=limit)
                {
                    back(ex, jad+1, cm, sort, jadwal, limit);
                }
                else
                {
                    for(int i=ex; i<sort.length; i++)
                        jadwal[sort[i][0]-1][1] = -1;
                    back(ex-1, jadwal[sort[ex-1][0]-1][1]+1, cm, sort, jadwal, limit);
                }
            }
        }
        else
        {
            for(int i=0; i<jadwal.length; i++)
            {
                for(int j=0; j<jadwal[i].length; j++)
                {
                    System.out.print(jadwal[i][j] + " ");
                }
                System.out.println();
            }
        }
    }

    public static boolean isOk(int ex, int jad, int[][]cm, int [][]timeslot, int[][]largest)
    {
        for(int i=0; i<cm.length; i++)
            if(cm[ex][i]!=0 && timeslot[i][1]==jad)
                return false;
            return true;
    }

    public static int calculate(int[][]sat, int batas)
    {
        int min = 10000;
        int index = 0;
        for(int i=0; i<sat.length; i++)
        {
            if (sat[i][1] < min)
            {
                index = i;
                min = sat[i][1];
            }
        }
        return index;
    }

    public static boolean isSafe(int index, int m, int dat[][], int[][]srt, int[][]jadwal)
    {
        for(int i=0; i<srt.length; i++)
            if(dat[srt[index][0]-1][i]!=0 && jadwal[i][1] == m)
                return false;
        return true;
    }
}