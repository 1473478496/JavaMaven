package cn.sunline.ws.foreignInterface.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class CommonXMLUtils {

	
	public static CommonXMLUtils xmlUtil;
	
	public static CommonXMLUtils getInstance() {
		if (xmlUtil == null) {
			xmlUtil = new CommonXMLUtils();
		}
		return xmlUtil;
	}

	public Document getDocument(String xml, String endcoding)
			throws IOException, JDOMException {
		Document doc = null;
		ByteArrayInputStream in = null;
		SAXBuilder builder = new SAXBuilder();
		try {
			in = new ByteArrayInputStream(xml.getBytes(endcoding));
			doc = builder.build(in);
		} catch (IOException ex) {
			ex.printStackTrace();
			throw new IOException();
		} catch (JDOMException ex) {
			ex.printStackTrace();
			throw new JDOMException();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (in != null) {
				in.close();
			}
			in = null;
		}
		return doc;
	}
	
	public byte[] getXMLData(String objAlias, String templateName, Object obj,
			Map<String, Object> velParamMap, String charsetName)
			throws ResourceNotFoundException, ParseErrorException, Exception {
		String className = XMLTemplateLoader.class.getName();
		VelocityEngine veEngine = new VelocityEngine();
		Properties p = new Properties();
		p.put("input.encoding", "UTF-8");
		p.put("output.encoding", "UTF-8");
		p.put("resource.loader", "srl");
		p.put("srl.resource.loader.class", className);
		p.put(Velocity.RUNTIME_LOG_LOGSYSTEM_CLASS,"org.apache.velocity.runtime.log.NullLogSystem");
		veEngine.init(p);
		String xmlPath = "/cn/sunline/ws/foreignInterface/templates/" + templateName;
		Template template = veEngine.getTemplate(xmlPath);
		VelocityContext velocityContext = new VelocityContext();
		velocityContext.put(objAlias, obj);
		// 增加模板解析参数
		for (Map.Entry<String, Object> entry : velParamMap.entrySet()) {
			velocityContext.put(entry.getKey(), entry.getValue());
		}
		StringWriter sw = new StringWriter();
		template.merge(velocityContext, sw);
		return sw.toString().getBytes(charsetName);
	}
		
}
