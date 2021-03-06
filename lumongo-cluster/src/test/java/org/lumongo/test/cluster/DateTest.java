package org.lumongo.test.cluster;

import org.lumongo.DefaultAnalyzers;
import org.lumongo.client.command.CreateOrUpdateIndex;
import org.lumongo.client.command.Query;
import org.lumongo.client.command.Store;
import org.lumongo.client.config.IndexConfig;
import org.lumongo.client.pool.LumongoWorkPool;
import org.lumongo.client.result.CreateOrUpdateIndexResult;
import org.lumongo.client.result.QueryResult;
import org.lumongo.cluster.message.Lumongo.FieldConfig.FieldType;
import org.lumongo.cluster.message.Lumongo.FieldSort.Direction;
import org.lumongo.doc.ResultDocBuilder;
import org.lumongo.fields.FieldConfigBuilder;
import org.lumongo.util.LogUtil;

import java.util.Date;

public class DateTest {
	public static void main(String[] args) {
		ServerTestBase serverTest = new ServerTestBase();

		try {
			LogUtil.loadLogConfig();
			serverTest.startSuite(1);
			Thread.sleep(1000);
		}
		catch (Exception e1) {
			e1.printStackTrace();
		}

		//create the connection pool
		LumongoWorkPool lumongoWorkPool = serverTest.getLumongoWorkPool();

		try {

			String defaultSearchField = "content";

			IndexConfig indexConfig = new IndexConfig(defaultSearchField);

			indexConfig.addFieldConfig(FieldConfigBuilder.create("title", FieldType.STRING).indexAs(DefaultAnalyzers.STANDARD).facet());
			indexConfig.addFieldConfig(FieldConfigBuilder.create("date", FieldType.DATE).index().facet());

			CreateOrUpdateIndex createOrUpdateIndex = new CreateOrUpdateIndex(SingleNodeTest.MY_TEST_INDEX, 2, indexConfig);
			@SuppressWarnings("unused") CreateOrUpdateIndexResult result = lumongoWorkPool.createOrUpdateIndex(createOrUpdateIndex);
			org.bson.Document mongoDocument = new org.bson.Document();

			mongoDocument.put("title", "this is a fancy title");

			mongoDocument.put("date", new Date());

			Store s = new Store("id1", SingleNodeTest.MY_TEST_INDEX);
			s.setResultDocument(ResultDocBuilder.newBuilder().setDocument(mongoDocument));
			lumongoWorkPool.store(s);

			Query query = new Query(SingleNodeTest.MY_TEST_INDEX, "*:*", 10);
			query.addFieldSort("date", Direction.ASCENDING);
			QueryResult queryResult = lumongoWorkPool.query(query);

			System.out.println(queryResult.getTotalHits());

		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				serverTest.stopSuite();
			}
			catch (Exception e) {
				e.printStackTrace();
			}

		}
	}
}
