/**
 * @author laurence
 * @since 2016年9月26日
 *
 */
package com.duitang.service.karma.trace;

import java.net.URISyntaxException;
import java.util.Map;

import com.duitang.service.karma.boot.KarmaFinder;

/**
 * @author laurence
 * @since 2016年9月26日
 *
 */
public class Finder implements KarmaFinder {

	static BaseVisitor visitor = null;
	static TracerLogger logger = null;

	static {
		initVisitor();
		logger = new TracerLogger() {

			@Override
			public void log(String msg, TraceCellVisitor<Map> visitor, TraceCell tc) {
				// ignore
			}

			@Override
			public void log(String msg, TraceCell tc) {
				// ignore
			}

		};
	}

	synchronized static void initVisitor() {
		if (visitor == null) {
			visitor = new BaseVisitor();
		}
	}

	@Override
	public <T> T find(Class<T> clazz) {
		initVisitor();
		return (T) visitor;
	}

	public static void enableZipkin(String grp, String url) throws URISyntaxException {
		visitor.addReporter(grp,
				ReporterFactory.createReporter(com.duitang.service.karma.trace.ReporterFactory.ZIPKIN, url));
	}

	public static void disableZipkin(String grp) {
		visitor.removeReporter(grp);
	}

	public static void enableConsole(boolean enabled) {
		ReporterSender.useConsole = enabled;
	}

	public static void enableGELFUDP(String host, int port) {
		logger = ReporterFactory.createGELFUDPLogger(host, port);
	}

}
