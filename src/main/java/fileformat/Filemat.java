package fileformat;

import java.awt.*;
import java.io.*;

/**
 * @Author: Skyrimgo
 * @Date: 2020/4/28 9:41
 */
public class Filemat {
    public static void main(String[] args) throws IOException {
        File inputfile = new File("C:\\Users\\shinelon\\Desktop\\sensor.txt");
        BufferedReader br = new BufferedReader(new FileReader(inputfile));
        File outfile = new File("C:\\Users\\shinelon\\Desktop\\sensor_format.txt");
        if (!outfile.exists()) {
            outfile.createNewFile();
        }
        BufferedWriter bw = new BufferedWriter(new FileWriter(outfile));
        StringBuilder sb = new StringBuilder();
        while (br.ready()) {
            String t = br.readLine();
            String[] temp = t.split(" ");
            if (Math.abs(Double.parseDouble(temp[0]) - 5.0) > 0.1 && Math.abs(Double.parseDouble(temp[0]) - 3.0) > 0.1) {
                sb.append(t + "\n");
            }
            if (Math.abs(Double.parseDouble(temp[0]) - 3.0) < 0.1) {
                if (Math.random() > 0.5) {
                    sb.append(t + "\n");
                }
            }

        }
        bw.write(sb.toString());
        bw.flush();

    }

}
