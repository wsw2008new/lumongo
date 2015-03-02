package org.lumongo.test.client;

import org.lumongo.cluster.message.Lumongo;
import org.lumongo.fields.annotations.DefaultSearch;
import org.lumongo.fields.annotations.Faceted;
import org.lumongo.fields.annotations.Indexed;
import org.lumongo.fields.annotations.Settings;
import org.lumongo.fields.annotations.UniqueId;

@Settings(indexName = "test", numberOfSegments = 1)
public class MapperTest {

	@Indexed(analyzer = Lumongo.LMAnalyzer.LC_KEYWORD, fieldName = "testField1Exact")
	@Indexed(analyzer = Lumongo.LMAnalyzer.STANDARD, fieldName = "testField1")
	@Faceted
	private String testField1;

	@DefaultSearch
	@Indexed(analyzer = Lumongo.LMAnalyzer.STANDARD)
	private String testField2;

	@UniqueId
	private String id;

}