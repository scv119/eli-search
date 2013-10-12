package com.eli.index;

import org.apache.log4j.Logger;
import org.apache.lucene.document.*;
import org.apache.lucene.search.Query;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: shenchen
 * Date: 8/11/12
 * Time: 1:41 PM
 * To change this template use File | Settings | File Templates.
 *
 *
 * Build Index based on field with "IField" Annotation,
 * it generate by property name and IField.analyzeType
 *
 *
 */
public abstract class DocumentSupport {
    private static final Logger logger = Logger.getLogger(DocumentSupport.class);
    private float boost = 1.0f;
    private static Map<String, Class<? extends DocumentSupport>> childMap =
            new HashMap<String,  Class<? extends DocumentSupport>>();

    static {
        for (Class<? extends  DocumentSupport> clazz: IndexConfig.registeredClasses) {
            try {
                Field typeField = clazz.getDeclaredField("type");
                childMap.put(typeField.get(null).toString(), clazz);
            } catch (Exception e) {
                logger.error("failed to get field type from clazz" + clazz, e);
            }
        }
    }

    public DocumentSupport() {};

    public DocumentSupport(Document doc) {
        parseDocument(doc, this);
    };

    public Document toDocument() {
        return buildDocument(this);
    }

    public abstract Query toDeleteQuery();

    public static DocumentSupport parseDocument(Document doc) {
        Class<? extends DocumentSupport> clazz = childMap.get(doc.get("type.None"));
        DocumentSupport obj = null;
        if(clazz == null)
            return null;
        try {
            Constructor<? extends DocumentSupport> constructor = clazz.getConstructor();
            obj = constructor.newInstance();
            obj = parseDocument(doc, obj);
        } catch (Exception e) {
            logger.error("failed to construct" + clazz, e);
        }
        return obj;
    }

    static DocumentSupport parseDocument(Document doc, DocumentSupport obj) {

        Class clazz = obj.getClass();
        Field[] fields = clazz.getFields();
        for(Field field:fields){
            if( Modifier.isFinal(field.getModifiers()) || Modifier.isStatic(field.getModifiers())
                    || Modifier.isPrivate(field.getModifiers()) )
                continue;

            IField annotation = field.getAnnotation(IField.class);
            for(IndexType type:annotation.indexTypes()){
                if(type != IndexType.None )
                    continue;

                String indexFieldName = field.getName()+"."+type.name();
                Object value = doc.get(indexFieldName);

                if(value == null)
                    continue;

                try{
                    if(field.getType().equals(String.class)) {
                        field.set(obj, doc.get(indexFieldName));
                    }
                    else if (field.getType().equals(int.class) || field.getClass().equals(Integer.class)) {
                        field.set(obj, Integer.parseInt( doc.get(indexFieldName)));
                    }
                    break;
                }catch (Exception e) {
                    logger.error("failed to set field "+ field.getName() + " doc is" + doc, e);
                }

            }
        }
//        System.out.println(ret.toString());
        return obj;
    }


    static Document buildDocument(Object obj){
        Document ret = new Document();

        Class clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();

        float boost = ((DocumentSupport)(obj)).boost;
        for(Field field:fields){
            if (Modifier.isPrivate(field.getModifiers()))
                continue;
            IField annotation = field.getAnnotation(IField.class);
            for(IndexType type:annotation.indexTypes()){
                String indexFieldName = field.getName()+"."+type.name();
                try{
                    Object data = field.get(obj);
                    if(data instanceof List){
                        List lData = (List)data;

                        for(Object item:lData)
                            ret.add(buildField(type, indexFieldName, item, boost * annotation.boost()));
                    }
                    else
                        ret.add(buildField(type, indexFieldName, data, boost * annotation.boost()));
                }catch(Exception e){
                    logger.error("failed to access field "+ field.getName(), e);
                }
            }
        }
//        System.out.println(ret.toString());
        return ret;
    }

    private static AbstractField buildField(IndexType indexType, String fieldName, Object fieldContent, float boost){
        AbstractField ret = null;
        if(indexType == IndexType.None){
            ret = new org.apache.lucene.document.Field(fieldName, fieldContent.toString(), org.apache.lucene.document.Field.Store.YES, org.apache.lucene.document.Field.Index.NOT_ANALYZED);
        }
        else if(indexType == IndexType.Float){
            ret = new NumericField(fieldName, org.apache.lucene.document.Field.Store.YES, true).setFloatValue((Float)fieldContent);
        }
        else if(indexType == IndexType.Int){
            ret = new NumericField(fieldName, org.apache.lucene.document.Field.Store.YES, true).setIntValue((Integer)fieldContent);
        }
        else {
            ret = new org.apache.lucene.document.Field(fieldName, fieldContent.toString(), org.apache.lucene.document.Field.Store.YES, org.apache.lucene.document.Field.Index.ANALYZED, org.apache.lucene.document.Field.TermVector.WITH_POSITIONS_OFFSETS);
        }

        if(ret != null) ret.setBoost(boost);
        return ret;
    }

    public float getBoost() {
        return boost;
    }

    public void setBoost(float boost) {
        this.boost = boost;
    }
}
