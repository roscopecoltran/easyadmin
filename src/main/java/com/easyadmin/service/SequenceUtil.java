package com.easyadmin.service;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;
import org.bson.Document;

/**
 * sequence util
 * <p>
 * getNextSequence base on mongo
 */
public class SequenceUtil {

    public final static String MY_SEQUENCE_COLLECTION = "mySequnceCollections";

    private final static String MY_SEQUENCE_NAME = "personId";

    public static void main(String[] args) {
        Object nextSequence = getNextSequence(MY_SEQUENCE_NAME);
        System.out.println(nextSequence);
    }


    private static void createCountersCollection(MongoCollection countersCollection, String sequenceName) {

        Document document = new Document();
        document.append("_id", sequenceName);
        document.append("seq", 0);
        countersCollection.insertOne(document);
    }

    /**
     * get next sequence
     * <p>
     * for this project backend use {entity+"_"+field} name to keep identity
     *
     * @param sequenceName must identity for one collection
     * @return
     */
    public static Object getNextSequence(String sequenceName) {
        MongoCollection<Document> countersCollection = DbUtil.getCollection(MY_SEQUENCE_COLLECTION);
        if (countersCollection.count() == 0) {
            createCountersCollection(countersCollection, sequenceName);
        }
        Document searchQuery = new Document("_id", sequenceName);
        Document increase = new Document("seq", 1);
        Document updateQuery = new Document("$inc", increase);
        Document result = countersCollection.findOneAndUpdate(searchQuery, updateQuery, new FindOneAndUpdateOptions().upsert(true).returnDocument(ReturnDocument.AFTER));
        return result.get("seq");
    }

}
