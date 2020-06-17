package it.com.joaquimmnetto.lambdagateway.infra.inbound;

import com.google.gson.Gson;
import com.joaquimmnetto.lambdagateway.infra.tools.GSONSerializer;
import com.joaquimmnetto.lambdagateway.infra.tools.Serializer;
import com.joaquimmnetto.lambdagateway.infra.inbound.exception.DeserializatonException;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.fail;

public class GSONSerializerTest {

    private final Gson gson = new Gson();
    private final Serializer serializer = new GSONSerializer(gson);

    static class SampleClass{
        private String key;

        public SampleClass(String key) {
            this.key = key;
        }

        public String key() {
            return key;
        }
    }

    @Test
    public void serializesObjectWithGson() {
        SampleClass toSerialize = new SampleClass("value");

        String serialized = serializer.serialize(toSerialize);

        assertThat(serialized, is("{\"key\":\"value\"}"));
    }

    @Test
    public void deserializesValidJSONObjectWithGson() {
        String validJson = "{\"key\":\"value\"}";

        var deserialized = serializer.deserialize(validJson, SampleClass.class);

        assertThat(deserialized.key(), is("value"));
    }

    @Test
    public void putsNullInNonMatchingFieldsWhenDeserializing() {
        String nonMatchingJson = "{\"koy\":\"value\"}";

        var deserialized = serializer.deserialize(nonMatchingJson, SampleClass.class);

        assertThat(deserialized.key(), is(nullValue()));
    }

    @Test(expected = DeserializatonException.class)
    public void throwsExceptionWhenDeserializingInvalidJSONObject() {
        String invalidJson = "\"key\":\"value\"}";

        serializer.deserialize(invalidJson, SampleClass.class);

        fail("Should had thrown DeserializationException");
    }



}