package com.hmsonline.storm.cassandra.bolt.mapper;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import storm.trident.tuple.TridentTuple;

import com.hmsonline.storm.cassandra.exceptions.TupleMappingException;

/**
 * Interface for mapping Trident <code>storm.trident.tuple.TridentTuple</code>
 * objects to Cassandra rows.
 * 
 * @author tgoetz
 *
 * @param <K> - the Cassandra row key type
 * @param <C> - the Cassandra column key/name type
 * @param <V> - the Cassandra column value type
 */
/* TODO tgoetz: This interface is suffering from serious growing pains,
 * which is a good indication that the API needs to be rethought.
 */
public interface TridentTupleMapper<K, C, V> extends Serializable {
    /**
     * Given a <code>storm.trident.tuple.TridentTuple</code> object, 
     * return the name of the column family that data should be 
     * written to.
     *
     * @param tuple
     * @return the column family name
     */
    String mapToColumnFamily(TridentTuple tuple) throws TupleMappingException;

    /**
     * Given a <code>storm.trident.tuple.TridentTuple</code> object,
     * return an object representing the Cassandra row key.
     * 
     * @param tuple
     * @return
     * @throws TupleMappingException
     */
    K mapToRowKey(TridentTuple tuple) throws TupleMappingException;

    /**
     * Given a <code>storm.trident.tuple.TridentTuple</code> object,
     * return a <code>java.util.Map</code> object representing the 
     * column names/values to persist to Cassandra.
     * 
     * @param tuple
     * @return
     * @throws TupleMappingException
     */
    Map<C, V> mapToColumns(TridentTuple tuple) throws TupleMappingException;

    /**
     * Given a <code>storm.trident.tuple.TridentTuple</code> object,
     * return a list of column name (column key) objects in order to execute a 
     * slice query against a particular row.
     * 
     * The row key will be determined by the return value of <code>mapToRowKey()</code>.
     * 
     * 
     * @param tuple
     * @return
     * @throws TupleMappingException
     */
    List<C> mapToColumnsForLookup(TridentTuple tuple) throws TupleMappingException;

    /**
     * Map a <code>storm.trident.tuple.TridentTuple</code> object 
     * to a an end key in order to perform a Cassandra column range query.
     * 
     * The row key will be determined by the return value of <code>mapToRowKey()</code>.
     * 
     * The start key/column will be determined by the value of <code>mapToStartKey()</code>.
     * 
     * @param tuple
     * @return
     * @throws TupleMappingException
     */
    C mapToEndKey(TridentTuple tuple) throws TupleMappingException;

    /**
     * 
     * @param tuple
     * @return
     * @throws TupleMappingException
     */
    C mapToStartKey(TridentTuple tuple) throws TupleMappingException;

    boolean shouldDelete(TridentTuple tuple);

    /**
     * Returns the row key class.
     * 
     * @return
     */
    Class<K> getKeyClass();

    /**
     * Returns the coulumn name(key) class.
     * 
     * @return
     */
    Class<C> getColumnNameClass();
    
    
    /**
     * Returns the column value class.
     * 
     * @return
     */
    Class<V> getColumnValueClass();
}
