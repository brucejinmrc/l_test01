package lucene;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
/**
 * Search test using Lucence search engine
 * @author bruce
 *
 */
public class LuceneSearcher {

	private static Logger log = Logger.getLogger(LuceneSearcher.class);

	private StandardAnalyzer analyzer;
	private Directory index; 
	private IndexWriterConfig config; 
	private IndexWriter indexWriter;  
	
	public LuceneSearcher() {
		super();
		analyzer = new StandardAnalyzer(Version.LUCENE_40);
		index = new RAMDirectory();
		config = new IndexWriterConfig(Version.LUCENE_40,analyzer);
		try {
			this.indexWriter = new IndexWriter(index, config);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/************************************************************
	 * Add a text
	 ************************************************************/
	public void addDoc(String id, String text) {

		try {
			Document doc = new Document();
			doc.add(new StringField("id", id, Field.Store.YES));
			doc.add(new TextField("text", text, Field.Store.YES));
			indexWriter.addDocument(doc);
			//indexWriter.close();	 
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return;
	}
	/************************************************************
	 * Add a text
	 ************************************************************/
	public ScoreDoc[] search(String querystr) {

		ScoreDoc[] hits = null;	
		try {
			Query query = new QueryParser(Version.LUCENE_40, "text", analyzer).parse(querystr);
			int hitsPerPage = 9;
			IndexReader reader = DirectoryReader.open(index);
			IndexSearcher searcher = new IndexSearcher(reader);
			TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage, true);
			searcher.search(query, collector);
			hits = collector.topDocs().scoreDocs;
			 
			reader.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return hits;
	}

	public static void main(String[] args) throws IOException, ParseException {
		String text = "The short answer: Anyone. The long answer: " +
				"m-Power is one of the most flexible development platforms available. " +
				"It's used by all types of companies for all types of purposes, " +
				"and offers a unique set of benefits depending on your goals or skill level. " +
				"Here are four of the most common types of m-Power users. " +
				"Select each option to see how m-Power helps each type in different ways. ";

		LuceneSearcher ls = new LuceneSearcher();
		ls.addDoc("1", "The short answer: Anyone. The long answer: m-Power is one of the most flexible development platforms available. " +
				"m-Power is used by all types of companies for all types of purposes");
		ls.addDoc("2" ,"It's used by all types of companies for all types of purposes, " +
				"and offers a unique set of benefits depending on your goals or skill level. " +
				"Here are four of the most common types of m-Power users. " +
				"Select each option to see how m-Power helps each type in different ways m-Power");
		
		String query = "m-Power the most flexible"; //contain all                    //contain
		ScoreDoc[] hits = ls.search(query);
		for (int i = 0; i < hits.length; i++) {			
			log.info(hits[i] );
		}
		 
	}
}
