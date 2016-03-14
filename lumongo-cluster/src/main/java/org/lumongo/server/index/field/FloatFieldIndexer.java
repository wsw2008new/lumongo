package org.lumongo.server.index.field;

import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.LegacyFloatField;

public class FloatFieldIndexer extends NumericFieldIndexer {

	public static final FloatFieldIndexer INSTANCE = new FloatFieldIndexer();

	protected FloatFieldIndexer() {

	}

	@Override
	protected Field createField(Number o, String indexedFieldName) {
		return new LegacyFloatField(indexedFieldName, o.floatValue(), Store.YES);
	}

}
