package de.uni_kassel.vs.cn.planDesigner.deserialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.Task;

import java.io.IOException;
import java.util.Iterator;

public class EntryPointTaskDeserializer extends StdDeserializer<Task> {

    public EntryPointTaskDeserializer() {
        this(null);
    }

    public EntryPointTaskDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Task deserialize(
            JsonParser jsonparser,
            DeserializationContext context)
            throws IOException, JsonProcessingException {
        TreeNode tree = jsonparser.getCodec().readTree(jsonparser);
        Iterator<String> iter = tree.fieldNames();
        while (iter.hasNext()) {
            System.out.println(iter.next());
        }
        return new Task();
    }
}
