package jena_test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedList;

import org.apache.jena.rdf.model.*;

public class Reader {
	private int cnt = 0; // cnt 0 (subject), 1 (predicate), 2 (object)
	private static String filePath = "C:\\Users\\Alexander\\Desktop\\Neues Textdokument.txt";
	private LinkedList<RDFhelper> rdfList = new LinkedList();
	
	public class RDFhelper{
		private String subject;
		private String predicate;
		private String object;
		
		public RDFhelper(String subject, String predicate, String object){
			this.subject = subject;
			this.predicate = predicate;
			this.object = object;
		}
		public String getSubject(){
			return this.subject ;
		}
		public String getPredicate(){
			return this.predicate ;
		}
		public String getObject(){
			return this.object;
		}
		public String toString(){
			return this.subject+this.predicate+this.object;
		}
	}
	
	public void readFile(String filename){
		try {
			FileInputStream in = new FileInputStream(new File(filename));
			int c;
			StringBuilder subject = new StringBuilder();
			subject.append("http://");
			StringBuilder predicate = new StringBuilder();
			StringBuilder object = new StringBuilder();
			
			while((c = in.read()) != -1){
				// 44 == ','
				if(c == 13 || c == 10){ // 13 und 10 Absatz
					cnt = 0; // set cnt to subject
					System.out.println(c+"\n");
					if (predicate.length()!= 0){ // if subject not empty create new rdfhelper object
					this.rdfList.add(new RDFhelper(subject.toString(),predicate.toString(),object.toString()));
					subject = new StringBuilder();
					subject.append("http://");
					predicate = new StringBuilder();
					object = new StringBuilder();
					}
				} else {
					if(c == 44){ // = Beistrich
						// reset cnt 
						System.out.println(c+"\n");
						cnt++;
						if(cnt == 3){ // reset cnt and create rdf helper object
							cnt = 0; 
							this.rdfList.add(new RDFhelper(subject.toString(),predicate.toString(),object.toString()));
							subject = new StringBuilder();
							subject.append("http://");
							predicate = new StringBuilder();
							object = new StringBuilder();
						}
					} else {
					// fill according StringBuilder with chars
					switch(cnt){
					case 0: subject.append((char)c);
					System.out.print((char)c);
					break;
					case 1: predicate.append((char)c);
					System.out.print((char)c);
					break;
					case 2: object.append((char)c);
					System.out.print((char)c);
					break;
					}
				}
				}
				}
			this.rdfList.add(new RDFhelper(subject.toString(),predicate.toString(),object.toString()));
			System.out.println(c+"\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	} 
	
	public void print(){
		this.rdfList.forEach(s-> System.out.println("subjekt: "+s.getSubject()+"\npredicate: "+s.getPredicate()+"\nobject: "+s.getObject()));
	}
	
	public void setFilePath(String hardFilePath){
		this.filePath = hardFilePath;
	}
	
	public LinkedList<RDFhelper> getTriples(){
		return rdfList;
	}
	
	public static void main(String[] args){
		Reader r  = new Reader();
		r.readFile(filePath);
		r.print();
		
	}
}
