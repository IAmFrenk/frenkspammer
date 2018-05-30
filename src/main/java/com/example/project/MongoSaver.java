package com.example.project;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import java.util.logging.Logger;

public class MongoSaver {
	
	public static boolean saveEmail(String to, String from, String subject, String text, Boolean html, String userName, String password) {
//		String userName = "Frenk";
//		String password = "123";
		String database = "friendspammerdb";
		
		MongoCredential credential = MongoCredential.createCredential(userName, database, password.toCharArray());
		
		boolean success = true;
		
		try (MongoClient mongoClient = new MongoClient(new ServerAddress("YOUR HOST", 27939), credential, MongoClientOptions.builder().build()) ) {
			
			MongoDatabase db = mongoClient.getDatabase( database );
			
			MongoCollection<Document> c = db.getCollection("email");
			
			Document  doc = new Document ("to", to)
			        .append("from", from)
			        .append("subject", subject)
			        .append("text", text)
			        .append("asHtml", html);
			c.insertOne(doc);
		} catch (MongoException mongoException) {
			Logger logger = Logger.getLogger(EmailSender.class.getName());
			logger.config("XXXXXXXXXXXXXXXXXX ERROR WHILE SAVING TO MONGO XXXXXXXXXXXXXXXXXXXXXXXXXX");
			logger.config(mongoException.toString());
			success = false;
		}
		
		return success;
 		
	}
	
	
	public static void main(String ...args) {
		Logger logger = Logger.getLogger(EmailSender.class.getName());
		logger.config("test");
	}

}
