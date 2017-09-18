import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class WordCount2 {
    public static class Map extends Mapper<LongWritable, Text, Text, IntWritable> {
        private final static IntWritable one = new IntWritable(1);
        private Text word = new Text();
       
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String input = value.toString().toLowerCase().trim();
        	String[] line = input.split("[\\s+ \n]+");
            String previous;
            previous = null;
            for(String s : line){
            	if(previous!=null){
            		word.set(previous+ " " +s);
                	context.write(word, one);
            	}
            	previous = s;
            }
            
            
            
 /*           int i =0;
           
            System.out.println("value :" + value);
            while(i< line.length-1){
//            	if(line[i].equals("")){
//            		i++;
//            	}
            //	else{
            	current = line[i];
            	next = line[i+1];
            	word.set(current + " " +next);
            	context.write(word, one);
//            	if(i<10){
//            		System.out.println("key = " + word);
//            	}
            	i++;
            //	}
            }*/
            
            
            
            
            
            
//            StringTokenizer tokenizer = new StringTokenizer(line);
//            while (tokenizer.hasMoreTokens()) {
//            	t = tokenizer.nextToken();
//                word.set(tokenizer.nextToken());
//                temp.set(tokenizer.nextToken());
//                context.write(word, one);
//            }
        }
    }

    public static class Reduce extends Reducer<Text, IntWritable, Text, IntWritable> {
        private IntWritable value = new IntWritable(0);
        @Override
        protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            int sum = 0;
            for (IntWritable value : values)
                sum += value.get();
            value.set(sum);
            context.write(key, value);
        }
    }

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job = new Job(conf, "wordcount");
        job.setJarByClass(WordCount2.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        job.setMapperClass(Map.class);
        job.setReducerClass(Reduce.class);
        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);
        job.setNumReduceTasks(1);

        FileInputFormat .setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path("output2.txt"));

        boolean success = job.waitForCompletion(true);
        System.out.println(success);
    }
}