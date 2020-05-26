package eapli.base.gestaoproducao.exportacao.application.xml;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Taken from
 * https://stackoverflow.com/questions/13568543/how-do-you-specify-the-date-format-used-when-jaxb-marshals-xsddatetime
 *
 * Adapts the Java date class into a proper exporting for XML(using Jaxb)
 */
public class DateAdapter extends XmlAdapter<String, Date> {

	private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

	@Override
	public String marshal(Date v) throws Exception {
		synchronized (dateFormat) {
			return dateFormat.format(v);
		}
	}

	@Override
	public Date unmarshal(String v) throws Exception {
		synchronized (dateFormat) {
			return dateFormat.parse(v);
		}
	}

}