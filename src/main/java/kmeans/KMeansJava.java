package kmeans;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.*;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.mllib.clustering.KMeans;
import org.apache.spark.mllib.clustering.KMeansModel;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.linalg.Vectors;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class KMeansJava {
    public static void main(String[] args) throws Exception {
        SparkConf conf = new SparkConf().setAppName("K-means implement by Java").setMaster("local");
        JavaSparkContext sc = new JavaSparkContext(conf);
        List<Vector> type_0 = new ArrayList<Vector>();
        List<Vector> type_1 = new ArrayList<Vector>();
        List<Vector> type_2 = new ArrayList<Vector>();
        List<Vector> type_3 = new ArrayList<Vector>();

        /*
        1. 加载数据
         */
        String path = "C:\\Users\\shinelon\\Desktop\\sensor_format.txt";
        JavaRDD<String> data = sc.textFile(path);
        JavaRDD<Vector> parsedData = data.map(
                new Function<String, Vector>() {
                    public Vector call(String s) {
                        String[] sarray = s.split(" ");
                        double[] values = new double[sarray.length];
                        for (int i = 0; i < sarray.length; i++) {
                            values[i] = Double.parseDouble(sarray[i]);
                            if (i == 0) {
                                values[i] *= 0.00001;
                            }else if(i==2){
                                values[i] *= 2;
                            }
                        }
                        //转换稠密向量
                        return Vectors.dense(values);
                    }
                }
        );
        parsedData.cache();


        /*
        2. 执行聚类，聚成3类，并行30次
         */
        int numClusters = 4;
        int numIterations = 1;
        /*
        并行度
         */
        int runs = 1;
        /* 获取程序运行时间 */
        long startTime = System.currentTimeMillis();
        KMeansModel clusters = KMeans.train(parsedData.rdd(), numClusters, numIterations, runs);
        long endTime = System.currentTimeMillis(); //获取结束时间

        /*
        3. 计算错误率，评估聚类效果
         */
        double WSSSE = clusters.computeCost(parsedData.rdd());
        System.out.println("Within Set Sum of Squared Errors = " + WSSSE);

        /*
        4. 聚类结果写入文件
        */
        //分类
        for (Vector v : parsedData.collect()) {
            switch (clusters.predict(v)) {
                case 0:
                    type_0.add(v);
                    break;
                case 1:
                    type_1.add(v);
                    break;
                case 2:
                    type_2.add(v);
                    break;
                case 3:
                    type_3.add(v);
                    break;
                default:
                    break;
            }
        }


        //写文件
        System.out.println("++++++++++++++++++聚类结果写文件++++++++++++++++++");
        System.out.println("+------------------------------------------------");
        //聚类0结果处理
        File file0 = new File("C:\\Users\\shinelon\\Desktop\\dateset-0.txt");
        File file0_test = new File("C:\\Users\\shinelon\\Desktop\\dateset-0-test.txt");
        if (!file0.exists()) {
            file0.createNewFile();
        }
        if (!file0_test.exists()) {
            file0_test.createNewFile();
        }
        FileOutputStream out0 = new FileOutputStream(file0, false); //如果追加方式用true
        FileOutputStream out0_test = new FileOutputStream(file0_test, false); //如果追加方式用true

        StringBuffer sb0 = new StringBuffer();
        StringBuffer sb0_test = new StringBuffer();
        sb0.append("================聚类0结果=================\n");
        for (Vector v : type_0) {
            double[] temp = v.toArray();
            sb0.append(temp[1] + " " + temp[2] + "\n");
            sb0_test.append(temp[0]);
            sb0_test.append(" ");
            sb0_test.append(temp[1]);
            sb0_test.append(" ");
            sb0_test.append(temp[2]);
            sb0_test.append("\n");
        }

        out0.write(sb0.toString().getBytes("utf-8"));//注意需要转换对应的字符集
        out0.close();
        out0_test.write(sb0_test.toString().getBytes("utf-8"));//注意需要转换对应的字符集
        out0_test.close();


        //聚类1结果处理
        File file1 = new File("C:\\Users\\shinelon\\Desktop\\dateset-1.txt");
        File file1_test = new File("C:\\Users\\shinelon\\Desktop\\dateset-1-test.txt");
        if (!file1.exists()) {
            file1.createNewFile();
        }
        if (!file1_test.exists()) {
            file1_test.createNewFile();
        }
        FileOutputStream out1 = new FileOutputStream(file1, false); //如果追加方式用true
        FileOutputStream out1_test = new FileOutputStream(file1_test, false); //如果追加方式用true
        StringBuffer sb1 = new StringBuffer();
        StringBuffer sb1_test = new StringBuffer();
        sb1.append("================聚类1结果=================\n");
        for (Vector v : type_1) {
            double[] temp = v.toArray();
            sb1.append(temp[1] + " " + temp[2] + "\n");
            sb1_test.append(temp[0]);
            sb1_test.append(" ");
            sb1_test.append(temp[1]);
            sb1_test.append(" ");
            sb1_test.append(temp[2]);
            sb1_test.append("\n");
        }

        out1.write(sb1.toString().getBytes("utf-8"));//注意需要转换对应的字符集
        out1.close();
        out1_test.write(sb1_test.toString().getBytes("utf-8"));//注意需要转换对应的字符集
        out1_test.close();


        //聚类2结果处理
        File file2 = new File("C:\\Users\\shinelon\\Desktop\\dateset-2.txt");
        File file2_test = new File("C:\\Users\\shinelon\\Desktop\\dateset-2-test.txt");
        if (!file2.exists()) {
            file2.createNewFile();
        }
        if (!file2_test.exists()) {
            file2_test.createNewFile();
        }
        FileOutputStream out2 = new FileOutputStream(file2, false); //如果追加方式用true
        StringBuffer sb2 = new StringBuffer();
        FileOutputStream out2_test = new FileOutputStream(file2_test, false); //如果追加方式用true
        StringBuffer sb2_test = new StringBuffer();
        sb2.append("================聚类2结果=================\n");
        for (Vector v : type_2) {
            double[] temp = v.toArray();
            sb2.append(temp[1] + " " + temp[2] + "\n");
            sb2_test.append(temp[0]);
            sb2_test.append(" ");
            sb2_test.append(temp[1]);
            sb2_test.append(" ");
            sb2_test.append(temp[2]);
            sb2_test.append("\n");
        }

        out2.write(sb2.toString().getBytes("utf-8"));//注意需要转换对应的字符集
        out2.close();
        out2_test.write(sb2_test.toString().getBytes("utf-8"));//注意需要转换对应的字符集
        out2_test.close();

        //聚类2结果处理
        File file3 = new File("C:\\Users\\shinelon\\Desktop\\dateset-3.txt");
        File file3_test = new File("C:\\Users\\shinelon\\Desktop\\dateset-3-test.txt");
        if (!file3.exists()) {
            file3.createNewFile();
        }
        if (!file3_test.exists()) {
            file3_test.createNewFile();
        }
        FileOutputStream out3 = new FileOutputStream(file3, false); //如果追加方式用true
        StringBuffer sb3 = new StringBuffer();
        FileOutputStream out3_test = new FileOutputStream(file3_test, false); //如果追加方式用true
        StringBuffer sb3_test = new StringBuffer();
        sb3.append("================聚类3结果=================\n");
        for (Vector v : type_3) {
            double[] temp = v.toArray();
            sb3.append(temp[1] + " " + temp[2] + "\n");
            sb3_test.append(temp[0]);
            sb3_test.append(" ");
            sb3_test.append(temp[1]);
            sb3_test.append(" ");
            sb3_test.append(temp[2]);
            sb3_test.append("\n");
        }

        out3.write(sb3.toString().getBytes("utf-8"));//注意需要转换对应的字符集
        out3.close();
        out3_test.write(sb3_test.toString().getBytes("utf-8"));//注意需要转换对应的字符集
        out3_test.close();

        //关闭上下文
        sc.close();
        System.out.println("程序运行时间： " + (endTime - startTime) + "ms");
    }
}