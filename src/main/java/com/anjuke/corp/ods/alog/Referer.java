/*
 * Referer
 * 
 * Version 1.0
 * 
 * 2013/05/16
 * 
 * Copyright 2012 Anjuke Inc.
 */

package com.anjuke.corp.ods.alog;

import java.io.IOException;

import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.hadoop.conf.Configuration;
import com.anjuke.corp.ods.alog.Const;

public final class Referer {

    private Referer() {
    }

    public static class RefererTestMapper extends Mapper<Object, Text, Text, IntWritable> {

        private final static Text _bot = new Text("机器人");
        private final static Text _innerweb = new Text("内网");
        private final static Text _outerweb = new Text("外网");
        private final static Text _rest = new Text("其他");

        private final static IntWritable _one = new IntWritable(1);

        public void map(Object key, Text value, Context context)
                throws IOException, InterruptedException {

            try {

                String[] values = value.toString().split("\\t");

                String requestUri = values[Const.LOGFORMAT.get("request_uri")];
                String userAgent = values[Const.LOGFORMAT.get("user_agent")]
                        .toLowerCase();
                String referer = values[Const.LOGFORMAT.get("referer")];


                if (requestUri.startsWith("/prop/view")) {

                    if (userAgent.contains("spider")
                            || userAgent.contains("bot")) {

                        context.write(_bot, _one);
                    } else if (referer.contains("anjuke.com")
                            || referer.contains("haozu.com")
                            || referer.contains("jinpu.com")) {
                        context.write(_innerweb, _one);
                    } else {
                        context.write(_outerweb, _one);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static class RefererTestReducer
        extends Reducer<Text, IntWritable, Text, IntWritable> {

        private IntWritable _result = new IntWritable();

        public void reduce(Text key, Iterable<IntWritable> values,
                Context context) throws IOException, InterruptedException {

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

        String inputPath = "";
        String outputPath = "";

        Configuration conf = new Configuration();
        String[] otherArgs = new GenericOptionsParser(conf, args)
                .getRemainingArgs();

        Job job = new Job(conf, "Referer collect");
        job.setJarByClass(Referer.class);

        if (otherArgs.length == 0) {
            System.err.println("Usage Referer <inputpath> [outputpath]");
            System.exit(2);
        } else if (otherArgs.length == 1) {
            inputPath = otherArgs[0];
            outputPath = inputPath + "-out";
        } else {
            inputPath = otherArgs[0];
            outputPath = otherArgs[1];
        }

        System.out.println(inputPath);

        FileInputFormat.addInputPaths(job, inputPath);
        FileOutputFormat.setOutputPath(job, new Path(outputPath));

        job.setMapperClass(RefererTestMapper.class);
        job.setReducerClass(RefererTestReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        job.setInputFormatClass(TextInputFormat.class);

        int exitStatus = 0;

        if (job.waitForCompletion(true)) {
            exitStatus = 0;
        } else {
            exitStatus = 1;
        }

        System.exit(exitStatus);
    }

}
