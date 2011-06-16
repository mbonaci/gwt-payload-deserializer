package hr.infodom.gwt.util;

import java.util.ArrayList;
import java.util.List;

public class PayloadObject {
	private int payloadPosition;
	private Object payloadValue;
	private List<Class<?>> parentType;
	private String attributeName;
	private Class<?> valueType;
	private Object value;
	private boolean isInStringTable;
	private boolean isRelevantForFuzzing;
	
	
	public PayloadObject(){
		
	}
	
//	public PayloadObject(boolean isInStrTbl, int pos, int pval){
//		this.isInStringTable = isInStrTbl;
//		this.payloadPosition = pos;
//		this.payloadValue = pval;
//	}
	
	public PayloadObject(boolean isInStrTbl, int pos, Object payloadVal){
		this.isInStringTable = isInStrTbl;
		this.payloadPosition = pos;
		this.payloadValue = payloadVal;
	}
	
	public int getPayloadPosition() {
		return payloadPosition;
	}
	public void setPayloadPosition(int payloadPosition) {
		this.payloadPosition = payloadPosition;
	}
	public Object getPayloadValue() {
		return payloadValue;
	}
	public void setPayloadValue(Object payloadValue) {
		this.payloadValue = payloadValue;
	}
	public List<Class<?>> getParentType() {
		return parentType;
	}
	public void setParentType(List<Class<?>> parentType) {
		this.parentType = parentType;
	}
	public void addParentType(Class<?> parentType) {
		if(this.parentType == null){
			this.parentType = new ArrayList<Class<?>>();
		}
		this.parentType.add(parentType);
	}
	
	public String getAttributeName() {
		return attributeName;
	}
	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}
	public Class<?> getValueType() {
		return valueType;
	}
	public void setValueType(Class<?> valueType) {
		this.valueType = valueType;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}

	public boolean isInStringTable() {
		return isInStringTable;
	}

	public void setInStringTable(boolean isInStringTable) {
		this.isInStringTable = isInStringTable;
	}

	public boolean isRelevantForFuzzing() {
		return isRelevantForFuzzing;
	}

	public void setRelevantForFuzzing(boolean isRelevantForFuzzing) {
		this.isRelevantForFuzzing = isRelevantForFuzzing;
	}
	
	public String toString(){
		return String.valueOf(payloadPosition) + " " + payloadValue + " " + valueType + " " +  value;
	}
	
	public PayloadObject clone(){
		PayloadObject po = new PayloadObject();
		po.setAttributeName(attributeName);
		po.setInStringTable(isInStringTable);
		po.setParentType(parentType);
		po.setPayloadPosition(payloadPosition);
		po.setPayloadValue(payloadValue);
		po.setRelevantForFuzzing(isRelevantForFuzzing);
		po.setValue(value);
		po.setValueType(valueType);
		
		return po;
	}
}
