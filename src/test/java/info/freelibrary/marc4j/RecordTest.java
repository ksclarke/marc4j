
package info.freelibrary.marc4j;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.marc4j.marc.ControlField;
import org.marc4j.marc.DataField;
import org.marc4j.marc.MarcFactory;
import org.marc4j.marc.Record;
import org.marc4j.marc.VariableField;

import info.freelibrary.marc4j.utils.StaticTestRecords;

public class RecordTest {

    Record record = StaticTestRecords.getSummerlandRecord();

    /**
     * Tests the various ways to get fields from a {@link Record}.
     */
    @Test
    public void testGetFields() {
        final String cn = record.getControlNumber();
        assertEquals("12883376", cn);

        final ControlField cf = record.getControlNumberField();
        assertEquals("001", cf.getTag());
        assertEquals("12883376", cf.getData());

        List<? extends VariableField> fieldList = record.getVariableFields();
        assertEquals(15, fieldList.size());

        fieldList = record.getControlFields();
        assertEquals(3, fieldList.size());

        fieldList = record.getDataFields();
        assertEquals(12, fieldList.size());

        final VariableField field = record.getVariableField("245");
        assertEquals("245", field.getTag());

        fieldList = record.getVariableFields("650");
        assertEquals(3, fieldList.size());

        final String[] fields = { "245", "260", "300" };
        fieldList = record.getVariableFields(fields);
        assertEquals(3, fieldList.size());
    }

    /**
     * Tests {@link Record#find(String)} and {@link Record#find(String, String)} and
     * {@link Record#find(String[], String)}.
     *
     * @throws Exception
     */
    @Test
    public void testFind() throws Exception {
        VariableField field = record.getVariableField("245");
        assertEquals(true, field.find("Summerland"));
        assertEquals(true, field.find("Sum*erland"));
        assertEquals(true, field.find("[Cc]habo[a-z]"));

        field = record.getVariableField("008");
        assertEquals(true, field.find("eng"));

        List<? extends VariableField> result = record.find("Summerland");
        assertEquals(1, result.size());
        field = result.get(0);
        assertEquals("245", field.getTag());

        result = record.find("Chabon");
        assertEquals(2, result.size());

        result = record.find("100", "Chabon");
        assertEquals(1, result.size());

        final String[] tags = { "100", "260", "300" };
        result = record.find(tags, "Chabon");
        assertEquals(1, result.size());

        result = record.find("040", "DLC");
        assertTrue(result.size() > 0);

        final DataField df = (DataField) result.get(0);
        final String agency = df.getSubfield('a').getData();

        assertTrue(agency.matches("DLC"));
    }

    /**
     * Tests creating a new {@link Record}.
     *
     * @throws Exception
     */
    @Test
    public void testCreateRecord() throws Exception {
        final MarcFactory factory = MarcFactory.newInstance();
        final Record record = factory.newRecord("00000cam a2200000 a 4500");
        assertEquals("00000cam a2200000 a 4500", record.getLeader().marshal());

        record.addVariableField(factory.newControlField("001", "12883376"));

        final DataField df = factory.newDataField("245", '1', '0');
        df.addSubfield(factory.newSubfield('a', "Summerland /"));
        df.addSubfield(factory.newSubfield('c', "Michael Chabon."));
        record.addVariableField(df);
    }

}
