package org.lumongo.client.config;

import org.lumongo.cluster.message.Lumongo.FieldConfig;
import org.lumongo.cluster.message.Lumongo.IndexSettings;
import org.lumongo.fields.FieldConfigBuilder;

import java.util.TreeMap;

public class IndexConfig {

	private String defaultSearchField;
	private Boolean applyUncommittedDeletes;
	private Double requestFactor;
	private Integer minSegmentRequest;
	private Integer numberOfSegments;
	private String indexName;
	private Integer commitInterval;
	private Double segmentTolerance;
	private Integer segmentQueryCacheSize;
	private Integer segmentQueryCacheMaxAmount;
	private Boolean storeDocumentInMongo;
	private Boolean storeDocumentInIndex;

	private TreeMap<String, FieldConfig> fieldMap;

	public IndexConfig() {
		this(null);
	}

	public IndexConfig(String defaultSearchField) {
		this.defaultSearchField = defaultSearchField;
		this.fieldMap = new TreeMap<>();
	}

	public String getDefaultSearchField() {
		return defaultSearchField;
	}

	public void setDefaultSearchField(String defaultSearchField) {
		this.defaultSearchField = defaultSearchField;
	}

	public Boolean getApplyUncommittedDeletes() {
		return applyUncommittedDeletes;
	}

	public void setApplyUncommittedDeletes(Boolean applyUncommittedDeletes) {
		this.applyUncommittedDeletes = applyUncommittedDeletes;
	}

	public Double getRequestFactor() {
		return requestFactor;
	}

	public void setRequestFactor(Double requestFactor) {
		this.requestFactor = requestFactor;
	}

	public Integer getMinSegmentRequest() {
		return minSegmentRequest;
	}

	public void setMinSegmentRequest(Integer minSegmentRequest) {
		this.minSegmentRequest = minSegmentRequest;
	}

	public Integer getNumberOfSegments() {
		return numberOfSegments;
	}

	public void setNumberOfSegments(Integer numberOfSegments) {
		this.numberOfSegments = numberOfSegments;
	}

	public String getIndexName() {
		return indexName;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	public Integer getCommitInterval() {
		return commitInterval;
	}

	public void setCommitInterval(Integer commitInterval) {
		this.commitInterval = commitInterval;
	}

	public Double getSegmentTolerance() {
		return segmentTolerance;
	}

	public void setSegmentTolerance(Double segmentTolerance) {
		this.segmentTolerance = segmentTolerance;
	}

	public Integer getSegmentQueryCacheSize() {
		return segmentQueryCacheSize;
	}

	public void setSegmentQueryCacheSize(Integer segmentQueryCacheSize) {
		this.segmentQueryCacheSize = segmentQueryCacheSize;
	}

	public Integer getSegmentQueryCacheMaxAmount() {
		return segmentQueryCacheMaxAmount;
	}

	public void setSegmentQueryCacheMaxAmount(Integer segmentQueryCacheMaxAmount) {
		this.segmentQueryCacheMaxAmount = segmentQueryCacheMaxAmount;
	}

	public void addFieldConfig(FieldConfigBuilder FieldConfigBuilder) {
		addFieldConfig(FieldConfigBuilder.build());
	}

	public void addFieldConfig(FieldConfig fieldConfig) {
		this.fieldMap.put(fieldConfig.getStoredFieldName(), fieldConfig);
	}

	public FieldConfig getFieldConfig(String fieldName) {
		return this.fieldMap.get(fieldName);
	}

	public TreeMap<String, FieldConfig> getFieldConfigMap() {
		return fieldMap;
	}

	public Boolean getStoreDocumentInMongo() {
		return storeDocumentInMongo;
	}

	public void setStoreDocumentInMongo(Boolean storeDocumentInMongo) {
		this.storeDocumentInMongo = storeDocumentInMongo;
	}

	public Boolean getStoreDocumentInIndex() {
		return storeDocumentInIndex;
	}

	public void setStoreDocumentInIndex(Boolean storeDocumentInIndex) {
		this.storeDocumentInIndex = storeDocumentInIndex;
	}

	public TreeMap<String, FieldConfig> getFieldMap() {
		return fieldMap;
	}

	public void setFieldMap(TreeMap<String, FieldConfig> fieldMap) {
		this.fieldMap = fieldMap;
	}

	public IndexSettings getIndexSettings() {
		IndexSettings.Builder isb = IndexSettings.newBuilder();
		if (defaultSearchField != null) {
			isb.setDefaultSearchField(defaultSearchField);
		}
		if (applyUncommittedDeletes != null) {
			isb.setApplyUncommittedDeletes(applyUncommittedDeletes);
		}
		if (requestFactor != null) {
			isb.setRequestFactor(requestFactor);
		}
		if (minSegmentRequest != null) {
			isb.setMinSegmentRequest(minSegmentRequest);
		}

		if (commitInterval != null) {
			isb.setCommitInterval(commitInterval);
		}
		if (segmentTolerance != null) {
			isb.setSegmentTolerance(segmentTolerance);
		}
		if (segmentQueryCacheSize != null) {
			isb.setSegmentQueryCacheSize(segmentQueryCacheSize);
		}

		if (segmentQueryCacheMaxAmount != null) {
			isb.setSegmentQueryCacheMaxAmount(segmentQueryCacheMaxAmount);
		}

		if (storeDocumentInIndex != null) {
			isb.setStoreDocumentInIndex(storeDocumentInIndex);
		}

		if (storeDocumentInMongo != null) {
			isb.setStoreDocumentInMongo(storeDocumentInMongo);
		}

		for (String fieldName : fieldMap.keySet()) {
			FieldConfig fieldConfig = fieldMap.get(fieldName);
			isb.addFieldConfig(fieldConfig);
		}

		return isb.build();
	}

	protected void configure(IndexSettings indexSettings) {
		this.defaultSearchField = indexSettings.getDefaultSearchField();
		this.applyUncommittedDeletes = indexSettings.getApplyUncommittedDeletes();
		this.requestFactor = indexSettings.getRequestFactor();
		this.minSegmentRequest = indexSettings.getMinSegmentRequest();
		this.commitInterval = indexSettings.getCommitInterval();
		this.segmentTolerance = indexSettings.getSegmentTolerance();
		this.segmentQueryCacheSize = indexSettings.getSegmentQueryCacheSize();
		this.segmentQueryCacheMaxAmount = indexSettings.getSegmentQueryCacheMaxAmount();
		this.storeDocumentInIndex = indexSettings.getStoreDocumentInIndex();
		this.storeDocumentInMongo = indexSettings.getStoreDocumentInMongo();
		this.fieldMap = new TreeMap<>();

		for (FieldConfig fc : indexSettings.getFieldConfigList()) {
			fieldMap.put(fc.getStoredFieldName(), fc);
		}

	}

	public static IndexConfig fromIndexSettings(IndexSettings indexSettings) {
		IndexConfig ic = new IndexConfig();
		ic.configure(indexSettings);
		return ic;
	}

}
