import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Mapper.Context;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class WordCount3 {
	public static class Map extends
			Mapper<LongWritable, Text, Text, IntWritable> {
		private final static IntWritable one = new IntWritable(1);

		private Text word = new Text();
		private Set pattern = new HashSet<>();

		@Override
		protected void setup(Context context) throws IOException,
				InterruptedException {
			// TODO Auto-generated method stub
			
			URI[] patternFiles = context.getCacheFiles();
			// super.setup(context);
			if (patternFiles.length != 1) {
				System.out.println("enter only one file for pattern");
			}
			if (patternFiles != null && patternFiles.length > 0) {
				URI patternFile = patternFiles[0];
				BufferedReader bufferedReader = new BufferedReader(
						new FileReader(patternFile.toString()));
				String patternWord = null;
				StringBuilder sb = new StringBuilder();
				while ((patternWord = bufferedReader.readLine()) != null) {
					// ******************************************************************
					// sb.append(stopWord.toLowerCase());
					sb.append(patternWord);
				}
				
				String[] inputWords = sb.toString().split("\\s");
				for(String s : inputWords){
					pattern.add(s);
					System.out.println(s);
				}
				bufferedReader.close();

			}

		}

		@Override
		protected void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException {
			String line = value.toString();
			StringTokenizer tokenizer = new StringTokenizer(line);
			while (tokenizer.hasMoreTokens()) {
				word.set(tokenizer.nextToken());
				if(pattern.contains(word.toString().toLowerCase())){		
				context.write(word, one);
				System.out.println(word);
				}
			}
		}
	}

	public static class Reduce extends
			Reducer<Text, IntWritable, Text, IntWritable> {
		private IntWritable value = new IntWritable(0);

		@Override
		protected void reduce(Text key, Iterable<IntWritable> values,
				Context context) throws IOException, InterruptedException {
			int sum = 0;
			for (IntWritable value : values)
				sum += value.get();
			value.set(sum);
			context.write(key, value);
		}
	}

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf);
		job.addCacheFile(new URI(args[2]));
		job.setJarByClass(WordCount3.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		job.setMapperClass(Map.class);
		job.setReducerClass(Reduce.class);
		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);
		job.setNumReduceTasks(1);

		FileInputFormat.setInputPaths(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));

		boolean success = job.waitForCompletion(true);
		System.out.println(success);
	}
}