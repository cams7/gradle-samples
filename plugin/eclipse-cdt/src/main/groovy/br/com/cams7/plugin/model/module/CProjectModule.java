/**
 * 
 */
package br.com.cams7.plugin.model.module;

import groovy.util.Node;

/**
 * @author cams7
 *
 * @description Represents an 'storageModule' in the Eclipse cproject.
 */
public interface CProjectModule {

	public static final String TAG_STORAGEMODULE = "storageModule";

	String getModuleId();

	void appendNode(Node node);

}
