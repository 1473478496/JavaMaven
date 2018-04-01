package cn.sunline.ws.foreignInterface.utils;

import java.io.InputStream;

import org.apache.commons.collections.ExtendedProperties;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.resource.Resource;
import org.apache.velocity.runtime.resource.loader.ResourceLoader;

public class XMLTemplateLoader extends ResourceLoader{


	@Override
	public long getLastModified(Resource arg0) {
		return 0;
	}

	@Override
	public InputStream getResourceStream(String filepath) throws ResourceNotFoundException {
		return this.getClass().getResourceAsStream(filepath);
	}

	@Override
	public void init(ExtendedProperties arg0) {
	}

	@Override
	public boolean isSourceModified(Resource arg0) {
		return false;
	}



}
