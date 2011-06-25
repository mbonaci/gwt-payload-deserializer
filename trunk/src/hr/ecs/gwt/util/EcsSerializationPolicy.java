package hr.ecs.gwt.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.server.rpc.SerializationPolicy;
import com.google.gwt.user.server.rpc.impl.TypeNameObfuscator;

/**
 * Enhanced GWTRPCServiceExporter's serialization policy method except it
 * doesn't use default serialization policy loading mechanism - not loading from
 * module path URL. Serialization policy file is read using HTTP connection from
 * the context request comes.
 *
 * {@link ConfigurableRPCServiceExporterFactory} is responsible for
 * instantiating service exporter with appropriate serialization policy.
 *
 * This code is copied from {@link GWTRPCServiceExporter} and slightly modified.
 *
 * FYI: serialization policy file is created with GWT's
 * ProxyCreator.writeSerializationPolicyFile
 *
 * Example:
 *
 * Suppose we have client (at /client) and server (at /server). Using old
 * approach serialization policy file had to be located under /client context
 * using filesystem. RemoteContextSerializationPolicy is loading it from the
 * context path location of the service you are calling using HTTP connection
 * request to /client/strongName.gwt.rpc file.
 *
 * @author eldzi
 */
public class EcsSerializationPolicy extends SerializationPolicy  implements TypeNameObfuscator{
	  /**
	   * Field serializable types are primitives and types on the specified
	   * whitelist.
	   */
	  private static boolean isFieldSerializable(Class<?> clazz, Map<Class<?>, Boolean> whitelist) {
	    if (clazz.isPrimitive()) {
	      return true;
	    }
	    return whitelist.containsKey(clazz);
	  }

	  /**
	   * Instantiable types are primitives and types on the specified whitelist
	   * which can be instantiated.
	   */
	  private static boolean isInstantiable(Class<?> clazz, Map<Class<?>, Boolean> whitelist) {
	    if (clazz.isPrimitive()) {
	      return true;
	    }
	    Boolean instantiable = whitelist.get(clazz);
	    return (instantiable != null && instantiable);
	  }

	  private final Map<Class<?>, Set<String>> clientFields;
	  private final Map<Class<?>, Boolean> deserializationWhitelist;
	  private final Map<Class<?>, Boolean> serializationWhitelist;
//	  private final Map<Class<?>, String> typeIds;
	  private final Map<String, Class<?>> typeIdsToClasses = new HashMap<String, Class<?>>();

	  /**
	   * Constructs a {@link SerializationPolicy} from several {@link Map}s.
	   */
	  public EcsSerializationPolicy(
	      Map<Class<?>, Boolean> serializationWhitelist,
	      Map<Class<?>, Boolean> deserializationWhitelist) {
	    this(serializationWhitelist, deserializationWhitelist,
	        null);
	  }

	  /**
	   * Constructs a {@link SerializationPolicy} from several {@link Map}s.
	   */
	  public EcsSerializationPolicy(
	      Map<Class<?>, Boolean> serializationWhitelist,
	      Map<Class<?>, Boolean> deserializationWhitelist,
	      Map<Class<?>, Set<String>> clientFields) {
	    if (serializationWhitelist == null || deserializationWhitelist == null) {
	      throw new NullPointerException("whitelist");
	    }

	    this.serializationWhitelist = serializationWhitelist;
	    this.deserializationWhitelist = deserializationWhitelist;
//	    this.typeIds = obfuscatedTypeIds;
	    this.clientFields = clientFields;

	    /*
	    for (Map.Entry<Class<?>, String> entry : obfuscatedTypeIds.entrySet()) {
	      assert entry.getKey() != null : "null key";
	      assert entry.getValue() != null : "null value for "
	          + entry.getKey().getName();
	      assert !typeIdsToClasses.containsKey(entry.getValue()) : "Duplicate type id "
	          + entry.getValue();
	      typeIdsToClasses.put(entry.getValue(), entry.getKey());
	    }
	    */
	  }

	  public final String getClassNameForTypeId(String id)
	      throws SerializationException {
	    Class<?> clazz = typeIdsToClasses.get(id);
	    if (clazz == null) {
	      return null;
	    }

	    return clazz.getName();
	  }

	  @Override
	  public Set<String> getClientFieldNamesForEnhancedClass(Class<?> clazz) {
	    if (clientFields == null) {
	      return null;
	    }
	    Set<String> fieldNames = clientFields.get(clazz);
	    return fieldNames == null ? null : Collections.unmodifiableSet(fieldNames);
	  }

	  /*
	  public final String getTypeIdForClass(Class<?> clazz)
	      throws SerializationException {
	    return typeIds.get(clazz);
	  }
	  */

	  /*
	   * (non-Javadoc)
	   * 
	   * @see
	   * com.google.gwt.user.server.rpc.SerializationPolicy#shouldDerializeFields
	   * (java.lang.String)
	   */
	  @Override
	  public boolean shouldDeserializeFields(Class<?> clazz) {
	    return isFieldSerializable(clazz, deserializationWhitelist);
	  }

	  /*
	   * (non-Javadoc)
	   * 
	   * @see
	   * com.google.gwt.user.server.rpc.SerializationPolicy#shouldSerializeFields
	   * (java.lang.String)
	   */
	  @Override
	  public boolean shouldSerializeFields(Class<?> clazz) {
	    return isFieldSerializable(clazz, serializationWhitelist);
	  }

	  /*
	   * (non-Javadoc)
	   * 
	   * @see
	   * com.google.gwt.user.server.rpc.SerializationPolicy#validateDeserialize(
	   * java.lang.String)
	   */
	  @Override
	  public void validateDeserialize(Class<?> clazz) throws SerializationException {
	    if (!isInstantiable(clazz, deserializationWhitelist)) {
	      throw new SerializationException(
	          "Type '"
	              + clazz.getName()
	              + "' was not included in the set of types which can be deserialized by this SerializationPolicy or its Class object could not be loaded. For security purposes, this type will not be deserialized.");
	    }
	  }

	  /*
	   * (non-Javadoc)
	   * 
	   * @see
	   * com.google.gwt.user.server.rpc.SerializationPolicy#validateSerialize(java
	   * .lang.String)
	   */
	  @Override
	  public void validateSerialize(Class<?> clazz) throws SerializationException {
	    if (!isInstantiable(clazz, serializationWhitelist)) {
	      throw new SerializationException(
	          "Type '"
	              + clazz.getName()
	              + "' was not included in the set of types which can be serialized by this SerializationPolicy or its Class object could not be loaded. For security purposes, this type will not be serialized.");
	    }
	  }

	@Override
	public String getTypeIdForClass(Class<?> clazz)
			throws SerializationException {
		// TODO Auto-generated method stub
		return null;
	}
}
