package com.epam.deltix.qsrv.hf.tickdb.schema.migration;

import com.epam.deltix.qsrv.hf.pub.md.RecordClassSet;
import com.epam.deltix.qsrv.hf.tickdb.schema.*;
import com.epam.deltix.timebase.messages.schema.*;
import com.epam.deltix.util.collections.generated.ObjectArrayList;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class Test_RenameFieldNameChangeMessageBuilder {

    private SchemaChangeMessageBuilder schemaChangeMessageBuilder = new SchemaChangeMessageBuilder();

    @Test
    public void testRenameFieldNameMigration() {
        StreamMetaDataChange streamMetaDataChange = getStreamMetaDataChange();

        SchemaChangeMessage actualSchemaChangeMessage = schemaChangeMessageBuilder.build(streamMetaDataChange, "event", 0l);

        assertThat(actualSchemaChangeMessage, is(getExpectedSchemaChangeMessage()));
    }

    private StreamMetaDataChange getStreamMetaDataChange() {
        StreamMetaDataChange streamMetaDataChange = new StreamMetaDataChange();

        com.epam.deltix.qsrv.hf.pub.md.DataField newFieldState = new com.epam.deltix.qsrv.hf.pub.md.NonStaticDataField( "field2", "title", new com.epam.deltix.qsrv.hf.pub.md.VarcharDataType("UTF8", false, false));

        com.epam.deltix.qsrv.hf.pub.md.RecordClassDescriptor targetDescriptor = new com.epam.deltix.qsrv.hf.pub.md.RecordClassDescriptor( "guid2", "name", "title", false, null, newFieldState);

        com.epam.deltix.qsrv.hf.pub.md.DataField oldFieldState = new com.epam.deltix.qsrv.hf.pub.md.NonStaticDataField( "field", "title", new com.epam.deltix.qsrv.hf.pub.md.VarcharDataType("UTF8", false, false));

        com.epam.deltix.qsrv.hf.pub.md.RecordClassDescriptor sourceDescriptor = new com.epam.deltix.qsrv.hf.pub.md.RecordClassDescriptor( "guid1", "name", "title", false, null, oldFieldState);

        RecordClassSet targetClassSet = new RecordClassSet();
        targetClassSet.setClassDescriptors(targetDescriptor);

        RecordClassSet sourceClassSet = new RecordClassSet();
        sourceClassSet.setClassDescriptors(sourceDescriptor);

        streamMetaDataChange.setMetaData(targetClassSet);
        streamMetaDataChange.setSource(sourceClassSet);

        AbstractFieldChange fieldChange = new FieldChange(oldFieldState, newFieldState, FieldAttribute.Name);
        ClassDescriptorChange classDescriptorChange = new ClassDescriptorChange(
                sourceDescriptor,
                targetDescriptor,
                new AbstractFieldChange[]{fieldChange}
        );

        ArrayList<ClassDescriptorChange> changes = streamMetaDataChange.changes;
        if (changes == null) {
            streamMetaDataChange.changes = new ArrayList<>();
        }
        streamMetaDataChange.changes.add(classDescriptorChange);


        return streamMetaDataChange;
    }

    private SchemaChangeMessage getExpectedSchemaChangeMessage() {
        SchemaChangeMessage schemaChangeMessage = new SchemaChangeMessage();
        schemaChangeMessage.setTimeStampMs(0);
        schemaChangeMessage.setSymbol("event");

        ObjectArrayList<ClassDescriptorInfo> previousState = new ObjectArrayList<>();
        com.epam.deltix.timebase.messages.schema.RecordClassDescriptor sourceDescriptor = new com.epam.deltix.timebase.messages.schema.RecordClassDescriptor();
        ObjectArrayList<DataFieldInfo> sourceDescriptorFields = new ObjectArrayList<>();

        com.epam.deltix.timebase.messages.schema.VarcharDataType varcharDataType = new com.epam.deltix.timebase.messages.schema.VarcharDataType();
        varcharDataType.setEncodingType(-1000);
        varcharDataType.setEncoding("UTF8");
        varcharDataType.setLength(0);
        varcharDataType.setIsMultiline(false);
        varcharDataType.setIsNullable(false);

        com.epam.deltix.timebase.messages.schema.DataField previousFieldState = new com.epam.deltix.timebase.messages.schema.NonStaticDataField();
        previousFieldState.setTitle("title");
        previousFieldState.setName("field");
        previousFieldState.setDataType(varcharDataType);

        sourceDescriptorFields.add(previousFieldState);

        sourceDescriptor.setTitle("title");
        sourceDescriptor.setName("name");
        sourceDescriptor.setDataFields(sourceDescriptorFields);
        sourceDescriptor.setIsAbstract(false);

        previousState.add(sourceDescriptor);

        schemaChangeMessage.setPreviousState(previousState);

        ObjectArrayList<ClassDescriptorInfo> newState = new ObjectArrayList<>();
        com.epam.deltix.timebase.messages.schema.RecordClassDescriptor targetDescriptor = new com.epam.deltix.timebase.messages.schema.RecordClassDescriptor();
        targetDescriptor.setName("name");
        targetDescriptor.setTitle("title");
        targetDescriptor.setIsAbstract(false);

        ObjectArrayList<DataFieldInfo> targetDescriptorFields = new ObjectArrayList<>();

        com.epam.deltix.timebase.messages.schema.DataField renamedField = new com.epam.deltix.timebase.messages.schema.NonStaticDataField();
        renamedField.setTitle("title");
        renamedField.setName("field2");
        renamedField.setDataType(varcharDataType);

        targetDescriptorFields.add(renamedField);

        targetDescriptor.setDataFields(targetDescriptorFields);
        newState.add(targetDescriptor);
        schemaChangeMessage.setNewState(newState);

        ObjectArrayList<SchemaDescriptorChangeActionInfo> changes = new ObjectArrayList<>();
        SchemaDescriptorChangeAction alterAction = new SchemaDescriptorChangeAction();
        alterAction.setNewState(targetDescriptor);
        alterAction.setChangeTypes(SchemaDescriptorChangeType.FIELDS_CHANGE);
        alterAction.setPreviousState(sourceDescriptor);

        ObjectArrayList<SchemaFieldChangeActionInfo> fieldChanges = new ObjectArrayList<>();
        SchemaFieldChangeAction fieldAction = new SchemaFieldChangeAction();
        fieldAction.setPreviousState(previousFieldState);
        fieldAction.setNewState(renamedField);
        fieldAction.setChangeTypes(SchemaFieldChangeType.RENAME);

        fieldChanges.add(fieldAction);

        alterAction.setFieldChangeActions(fieldChanges);

        changes.add(alterAction);

        schemaChangeMessage.setDescriptorChangeActions(changes);

        return schemaChangeMessage;
    }
}
