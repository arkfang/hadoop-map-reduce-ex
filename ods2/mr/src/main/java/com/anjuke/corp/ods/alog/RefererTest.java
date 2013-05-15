package com.anjuke.corp.ods.alog;

import java.io.IOException;

//import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class RefererTest  { 
	
    public static class RefererTestMapper extends 
	    Mapper<Object, Text, Text, IntWritable> {
    	
    	private final static Text bot = new Text("机器人");
    	private final static Text innerweb = new Text("内网");
    	private final static Text outerweb = new Text("外网");
    	private final static Text rest = new Text("其他");
    	
    	/*private final static Text bot = new Text("bot");
    	private final static Text innerweb = new Text("innerweb");
    	private final static Text outerweb = new Text("outerweb");*/
    	private final static IntWritable one = new IntWritable(1);
		
        public void map(Object key, Text value, Context context) 
		    throws IOException, InterruptedException {
			
        	try {
        		
        		String[] values = value.toString().split("\\t");
        		
        		if (values[10].contains("/prop/view")) {
        			
        			if (values[14].contains("spider") | values[14].contains("bot")) {
        				context.write(bot, one);
        			}
        			else if (values[13].contains("anjuke.com") | values[13].contains("haozu.com") |
        					(values[13].contains("jinpu.com"))) {
        				context.write(innerweb, one);
        			}
        			else {
        				context.write(outerweb, one);
        			}
        		}
        	}
        	catch (Exception e) {
        		e.printStackTrace();
        	}
		}
	}

	public static class RefererTestReducer 
	    extends Reducer<Text, IntWritable, Text, IntWritable> {
		
		private IntWritable result = new IntWritable();
		
		public void reduce(Text key, Iterable<IntWritable> values, Context context) 
		    throws IOException, InterruptedException {
			
			int sum = 0;
			for (IntWritable value : values) {
				sum += value.get();
			}
			result.set(sum);
			context.write(key, result);	
		}
	}
	/**
	 * @param args
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
		// TODO Auto-generated method stub
		//Configuration conf = new Configuration();
		
		Job job = new Job();
		job.setJarByClass(RefererTest.class);
		
		FileInputFormat.addInputPaths(job, "/home/arkfang/alog.sample.log");
		FileOutputFormat.setOutputPath(job, new Path("/home/arkfang/alog.sample.log-out"));
		
		job.setMapperClass(RefererTestMapper.class);
		job.setReducerClass(RefererTestReducer.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(IntWritable.class);
		
		job.setInputFormatClass(TextInputFormat.class);
		
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}

}
