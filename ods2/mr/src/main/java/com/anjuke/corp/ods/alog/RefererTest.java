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
	
    public static class RefererTestMapper 
            extends Mapper<Object, Text, Text, IntWritable> {
    	
        private final static Text _bot = new Text("机器人");
        private final static Text _innerweb = new Text("内网");
        private final static Text _outerweb = new Text("外网");
        private final static Text _rest = new Text("其他");
    	
        private final static IntWritable _one = new IntWritable(1);
		
        public void map(Object key, Text value, Context context) 
                throws IOException, InterruptedException {
			
            try {
            	
                String[] values = value.toString().split("\\t");
            	
                if (values[Const.LOGFORMAT.get("request_uri")].startsWith("/prop/view")) {
            		
                    if (values[Const.LOGFORMAT.get("user_agent")].toLowerCase().contains("spider") | 
            	        values[Const.LOGFORMAT.get("user_agent")].toLowerCase().contains("bot")) {
                        context.write(_bot, _one);
            	    }
            	    else if (values[Const.LOGFORMAT.get("referer")].contains("anjuke.com") | 
            	             values[Const.LOGFORMAT.get("referer")].contains("haozu.com") |
            	             values[Const.LOGFORMAT.get("referer")].contains("jinpu.com")) {
            	        context.write(_innerweb, _one);
            	    }
                    else {
                        context.write(_outerweb, _one);
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
        
        private IntWritable _result = new IntWritable();
        
        public void reduce(Text key, Iterable<IntWritable> values, Context context) 
                throws IOException, InterruptedException {
        
            int sum = 0;
            for (IntWritable value : values) {
                sum += value.get();
            }
            
            _result.set(sum);
            context.write(key, _result);	
        }
    }
    /**
     * @param args
     * @throws IOException 
     * @throws ClassNotFoundException 
     * @throws InterruptedException 
     */
    public static void main(String[] args) 
            throws IOException, InterruptedException, ClassNotFoundException {
        
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
