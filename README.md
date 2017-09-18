# CloudComputing
Assignments


There are three programs: 
Word count1: the simple word count generation program
It takes one input argument and writes the output.
bin/hadoop jar WordCount1.jar WordCount1 input output

Word count 2: it takes one input bible and outputs the pairs that are present in the file and their frequency.
bin/hadoop jar wordcount2.jar WordCount2 input output

// for first and second program the output is generated without arguments in /user/ubuntu/directories

Wordcount 3: it takes three input argument. They are input file, output file and pattern file. This program out puts the count of the words which are present in the pattern.
bin/hadoop jar wordcount3.jar WordCount3 input output pattern

Command to execute the files:
bin/hadoop jar wordcount.jar input output
The Link for github is:
https://github.com/Shweta5201/CloudComputing.git

The output links are: 
Wordcount single word:
http://ec2-34-229-87-148.compute-1.amazonaws.com:50070/explorer.html#/user/ubuntu/output1.txt

word count pairs:
http://ec2-34-229-87-148.compute-1.amazonaws.com:50070/explorer.html#/user/ubuntu/output2.txt

word count pattern:
http://ec2-34-229-87-148.compute-1.amazonaws.com:50070/explorer.html#/user/WordCount/output3.txt


Details of my aws machines:
172.31.7.97   ec2-34-229-87-148.compute-1.amazonaws.com  namenode

172.31.13.86  ec2-34-207-205-213.compute-1.amazonaws.com  datanode1

172.31.3.203  ec2-34-204-49-78.compute-1.amazonaws.com	datanode2

172.31.4.162  ec2-34-229-94-160.compute-1.amazonaws.com	datanode3


Link for browsing hdfs:
http://ec2-34-229-87-148.compute-1.amazonaws.com:50070/dfshealth.html#tab-startup-progress

