package lucene;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.standard.ClassicAnalyzer;
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
import org.apache.lucene.search.similarities.DefaultSimilarity;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

public class HelloLucene2 {
	  private static Logger log = Logger.getLogger(HelloLucene2.class); 	
	  
	public static void main(String[] args) throws IOException, ParseException {
		log.info("start");
		// 0. Specify the analyzer for tokenizing text.
		// The same analyzer should be used for indexing and searching
		StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_40);
		//ClassicAnalyzer analyzer = new ClassicAnalyzer(Version.LUCENE_40);
		 
		// 1. create the index
		Directory index = new RAMDirectory();

		IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_40,	analyzer);
		log.info("made config");
		IndexWriter iw = new IndexWriter(index, config);
		Similarity sim = new DefaultSimilarity();
		 
		config.setSimilarity(sim);
		log.info("made index");
		
		addDoc(iw, "Lucene for Dummies is the best actor", "55320055Z");
		addDoc(iw, "Managing Gigabytes action mode reactor", "55063554A");
		addDoc(iw, "The Art of Computer Science", "9900333X");
//		iw.deleteAll();
		addDoc(iw, "Lucene mode in Action mode full of action", "19900333X");
		addDoc(iw, "Action mode java code Lucene mode in  full of action", "900333X");
		addDoc(iw, "Lucene in mode Action   is full of action, Lucene in Action mode is full of action mode, ", "193398817");
		addDoc(iw, "The Art of Computer Science", "29900333X");
		addDoc(iw, "The lucenes of Computer Science", "9900333X");
		addDoc(iw, "The action is ", "9903X");
		iw.close();

		// 2. query
		String querystr = "action mode ";

		// the "title" arg specifies the default field to use
		// when no field is explicitly specified in the query.
		Query query = new QueryParser(Version.LUCENE_40, "title", analyzer)	.parse(querystr);

		// 3. search
		int hitsPerPage = 10;
		IndexReader reader = DirectoryReader.open(index);
		IndexSearcher searcher = new IndexSearcher(reader);
		TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage, true);
		log.info("before search");
		searcher.search(query, collector);
		ScoreDoc[] hits = collector.topDocs().scoreDocs;
		log.info("print...");
		// 4. display results
		System.out.println("Found " + hits.length + " hits.");
		for (int i = 0; i < hits.length; ++i) {
			int docId = hits[i].doc;
			Document d = searcher.doc(docId);
			System.out.println((i + 1) + " {" + hits[i].score + "} " + d.get("title") + ". " + d.get("isbn") + "("  );
		}

		// reader can only be closed when there
		// is no need to access the documents any more.
		reader.close();
		
	}

	private static void addDoc(IndexWriter w, String title, String isbn)
			throws IOException {
		Document doc = new Document();
		doc.add(new TextField("title", title, Field.Store.YES));

		// use a string field for isbn because we don't want it tokenized
		doc.add(new StringField("isbn", isbn, Field.Store.YES));
		w.addDocument(doc);
	}
}
