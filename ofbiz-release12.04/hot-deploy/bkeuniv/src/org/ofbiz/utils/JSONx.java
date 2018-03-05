package org.ofbiz.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Date;

import org.apache.commons.io.IOUtils;
import org.ofbiz.base.util.Assert;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;

/** A JSON object. */
public final class JSONx {

    // TODO: Find a generic way to modify mapper options
    private static final ObjectMapper mapper = new ObjectMapper();
    
    static {
    	SimpleModule module = new SimpleModule();
    	DateTimeSerializer serializer = new DateTimeSerializer();
		module.addSerializer(java.util.Date.class, serializer);
		module.addSerializer(java.sql.Date.class, serializer);
		module.addSerializer(java.sql.Timestamp.class, serializer);
		mapper.registerModule(module);
    }
    
    /**
     * Creates a <code>JSON</code> instance from an <code>InputStream</code>.
     * The method assumes the character set is UTF-8.
     * 
     * @param inStream
     * @return a <code>JSON</code> instance
     * @throws IOException
     */
    public static JSONx from(InputStream inStream) throws IOException {
        Assert.notNull("inStream", inStream);
        String jsonString = IOUtils.toString(inStream, "UTF-8");
        return from(jsonString);
    }

    /**
     * Creates a <code>JSON</code> instance from an unknown data type.
     * 
     * @param object
     * @return a <code>JSON</code> instance
     * @throws IOException
     */
    public static JSONx from(Object object) throws IOException {
        Assert.notNull("object", object);
        try {
            return from(mapper.writeValueAsString(object));
        } catch (Exception e) {
            throw new IOException(e);
        }
    }

    /**
     * Creates a <code>JSON</code> instance from a <code>Reader</code>.
     * 
     * @param reader
     * @return a <code>JSON</code> instance
     * @throws IOException
     */
    public static JSONx from(Reader reader) throws IOException {
        Assert.notNull("reader", reader);
        String jsonString = IOUtils.toString(reader);
        return from(jsonString);
    }

    /**
     * Creates a <code>JSON</code> instance from a <code>String</code>.
     * 
     * @param jsonString
     * @return a <code>JSON</code> instance
     */
    public static JSONx from(String jsonString) {
        Assert.notNull("jsonString", jsonString);
        // TODO: Validate String
        return new JSONx(jsonString);
    }

    private final String jsonString;

    private JSONx(String jsonString) {
        this.jsonString = jsonString;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof JSONx) {
            return jsonString.equals(((JSONx)obj).jsonString);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return jsonString.hashCode();
    }

    /**
     * Converts this <code>JSON</code> object to the specified type.
     * 
     * @param targetClass
     * @return an object of the specified type
     * @throws IOException
     */
    public <T> T toObject(Class<T> targetClass) throws IOException {
        Assert.notNull("targetClass", targetClass);
        try {
            return mapper.readValue(jsonString, targetClass);
        } catch (IOException e) {
            throw e;
        } catch (Exception e) {
            throw new IOException(e);
        }
    }

    @Override
    public String toString() {
        return jsonString;
    }
    
    private static class DateTimeSerializer extends JsonSerializer<Date> {

		@Override
		public void serialize(Date value, JsonGenerator gen, SerializerProvider serializers)
				throws IOException, JsonProcessingException {
			gen.writeNumber(Long.toString(value.getTime()));
		}
		
	}
}
